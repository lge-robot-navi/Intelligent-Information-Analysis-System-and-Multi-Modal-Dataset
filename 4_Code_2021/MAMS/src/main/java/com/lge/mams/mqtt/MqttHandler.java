package com.lge.mams.mqtt;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lge.mams.agentif.events.EventImageHandler;
import com.lge.mams.agentif.model.AgentResult;
import com.lge.mams.agentif.service.AgentManager;
import com.lge.mams.agentif.service.EventTimerMgr;
import com.lge.mams.agentif.udperver.SeqImageProvider;
import com.lge.mams.config.LgicConfig;
import com.lge.mams.jpa.impl.TbAgentStatRepository;
import com.lge.mams.jpa.impl.TbEventInfoRepository;
import com.lge.mams.jpa.model.TbAgentStat;
import com.lge.mams.jpa.model.TbEventInfo;
import com.lge.mams.mqtt.cloudstat.CloudStat;
import com.lge.mams.mqtt.envmap.MqttEnvMapManager;
import com.lge.mams.mqtt.etri.EtriEvent;
import com.lge.mams.mqtt.scheduler.MqttSchedulerManager;
import com.lge.mams.mqtt.stat.MqttAgentStatManager;
import com.lge.mams.util.JsonUtil;
import com.lge.mams.util.MapUtil;
import com.lge.mams.websocket.WsAgentIfHandler;

@Component
public class MqttHandler implements MqttCallback {

	private Logger logger = LoggerFactory.getLogger(getClass());
	MqttAsyncClient mqttClient = null;

	@Autowired
	LgicConfig config;

	@Autowired
	WsAgentIfHandler wsagent;

	@Autowired
	MqttAgentStatManager mqttAgents;

	@Autowired
	MqttSchedulerManager mqttScheduler;

	@Autowired
	MqttEnvMapManager mqttEnvMap;

	@PostConstruct
	private synchronized void start() {
		logger.debug("START MQTT Subscriber ");

//		String[] topics = { "/mams/+/cloud/pong", "/mams/+/etri/event", "/mams/+/etri/map/#", "/mams/+/scheduler/res" };
		String[] topics = { "/mams/+/cloud/#", "/mams/+/etri/event", "/mams/+/etri/map/#", "/mams/+/scheduler/res" }; // cloud 하위까지
		int[] qoss = { 0, 0, 0, 0 };
		// String broker = "tcp://192.168.0.190:1883";
		String broker = config.getMqttServer();
		long time = new Date().getTime();
		String clientId = "lg-cloud" + time;
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			mqttClient = new MqttAsyncClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			connOpts.setKeepAliveInterval(300);
			mqttClient.setCallback(this);
			logger.debug("Connecting to broker: " + broker);
			mqttClient.connect(connOpts);
			logger.debug("Connected");
			Thread.sleep(1000);
			mqttClient.subscribe(topics, qoss);
			logger.debug("Subscribed");
		} catch (Exception me) {
			if (me instanceof MqttException) {
				logger.error("reason " + ((MqttException) me).getReasonCode());
			}
			logger.error("msg " + me.getMessage());
			logger.error("loc " + me.getLocalizedMessage());
			logger.error("cause " + me.getCause());
			logger.error("excep " + me);
			logger.error("mqtt error", me);
		}

	}

	@Scheduled(fixedDelay = 5000L)
	public void checkService() {
		// mqtt 가 동작하는지 확인.
		if (mqttClient == null || !mqttClient.isConnected()) {
			stop();
			start();
		} else {
			// 정상인 케이스.
			publish("/mams/ph/cloud/ping", "{}");
			publish("/mams/gw/cloud/ping", "{}");
		}
	}

	@Autowired
	WsAgentIfHandler wsAgent;

	@Scheduled(fixedDelay = 8000L)
	public void sendMqttAgentsWebSocket() {
		// pulling해서 가져가는 것으로 우선 처리.
		wsAgent.pushMqttAgents(mqttAgents.getStatList());
	}

	@PreDestroy
	private void stop() {
		logger.debug("STOP MQTT Subscriber");
		if (mqttClient != null && mqttClient.isConnected()) {
			try {
				mqttClient.disconnect();
				mqttClient = null;
			} catch (MqttException e) {
				logger.error("stop", e);
				mqttClient = null;
			}
		}
	}

	public void connectionLost(Throwable arg0) {
		logger.error("connection lost", arg0);
	}

	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// logger.debug("delivery complete");
	}

	boolean isequal(String s1, String s2) {
		if (s1 == null && s2 == null)
			return true;
		if (s1 == null || s2 == null)
			return false;
		return s1.equals(s2);
	}

	boolean istopic(String[] toks, String first, String second) {
		if ("mams".equals(toks[1]) && isequal(toks[3], first) && isequal(toks[4], second)) {
			return true;
		}
		return false;
	}

	boolean istopic(String[] toks, String[] topic) {
		if (toks == null || topic == null)
			return false;
		if (toks.length != topic.length)
			return false;
		for (int i = 0; i < toks.length; i++) {
			if ("+".equals(topic[i]))
				continue;
			if (toks[i].equals(topic[i]))
				continue;
			return false;
		}
		return true;
	}

	// @Autowired AgentIfController agentController;

	@Autowired
	private EventTimerMgr eventTimerMgr;

	@Autowired
	private TbEventInfoRepository repoEvent;

	@Autowired
	private EventImageHandler eventImageHandler;

	@Autowired
	private TbAgentStatRepository repoStat;

	@Autowired
	private SeqImageProvider seqImgProvider;

	@Autowired
	private AgentManager agentMgr;

	public AgentResult saveEvent(TbEventInfo event) {
		try {
			if ("0".contentEquals(event.getAbnormalId())) {
				logger.error("abnormalid is zero");
				return AgentResult.success("abnormalid is zero");
			}

			if (!"Y".contentEquals(event.getFbNeed())) {
				if (!eventTimerMgr.isEventTime(event.getAreaCode(), event.getRobotId(), event.getAbnormalId())) {
					logger.error("{}", String.format("not event time %s %d", event.getAreaCode(), event.getRobotId()));
					return AgentResult.success(String.format("not event time %s %d", event.getAreaCode(), event.getRobotId()));
				}
			}

			repoEvent.save(event);

			if ("P".equals(event.getAreaCode())) {
				eventImageHandler.savePohangImages(event.getEventSn(), event.getRobotId());
			} else if ("G".equals(event.getAreaCode())) {
				eventImageHandler.saveGwangjuImages(event.getEventSn(), event.getRobotId());
			}

			wsAgent.pushData(event);

			return AgentResult.success("eventSn : " + event.getEventSn());

		} catch (Exception e) {
			logger.error("Json", e);
			return AgentResult.fail(-1, "Fail : " + e.getMessage());
		}
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {

		logger.debug("topic: " + topic);

		// logger.debug("message: " + msg);

		// 각 토픽 구분자에 따라서 처리하도록 할 것.
		String[] toks = topic.split("/");
		String loc = "";
		if (toks.length >= 3) {
			loc = toks[2];
		}
		// /mams/ph/scheduler/res 을 /로 분리하면, toks는 legnth 5로 됨을 주의할 것.
		// /mams/ph/cloud/pong 을 / 로 분리하면 5 .

		// if ("mams".equals(toks[1]) && "scheduler".equals(toks[3]) &&
		// "res".equals(toks[4])) {
		if (istopic(toks, new String[] { "+", "mams", "+", "scheduler", "res" })) {
			String msg = new String(message.getPayload());
			logger.debug("message: " + msg);
			// 스케쥴러 응답.
			wsagent.pushScheduleResData(msg);
			mqttScheduler.addSchedulingData(msg);

			// } else if ("mams".equals(toks[1]) && "cloud".equals(toks[3]) &&
			// "pong".equals(toks[4])) {
		} else if (istopic(toks, new String[] { "+", "mams", "+", "cloud", "status" })) {
			String msg = new String(message.getPayload());
			logger.debug("message: " + msg);

			// wsagent.pushMqttAgents(msg);

			JsonUtil jutil = new JsonUtil(msg);
			CloudStat stat = jutil.treeToValue(CloudStat.class);

			// 상태 설정.
			mqttAgents.setHealthy(loc, stat.getRobotId(), stat);

			logger.debug("(cloudstat)parsed json : {},  {}", stat.getTimestamp(), JsonUtil.pretty(stat));

			doCloudStat(stat, loc);

		} else if (istopic(toks, new String[] { "+", "mams", "+", "cloud", "pong" })) {
			String msg = new String(message.getPayload());
			logger.debug("message: " + msg);
			JsonUtil jutil = new JsonUtil(msg);
			Map map = jutil.treeToMap();
			logger.debug("pong", JsonUtil.pretty(map));
			mqttAgents.setHealthy(toks[1], MapUtil.toint(map, "agentId"));
		} else if (istopic(toks, new String[] { "+", "mams", "+", "etri", "map", "search" })) {
			byte[] bytes = message.getPayload();
			if ("ph".equals(loc)) {
				mqttEnvMap.ph().search.fromBytes(bytes);
			} else if ("gw".equals(loc)) {
				mqttEnvMap.gw().search.fromBytes(bytes);
			}
		} else if (istopic(toks, new String[] { "+", "mams", "+", "etri", "map", "height" })) {
			byte[] bytes = message.getPayload();
			if ("ph".equals(loc)) {
				mqttEnvMap.ph().height.fromBytes(bytes);
			} else if ("gw".equals(loc)) {
				mqttEnvMap.gw().height.fromBytes(bytes);
			}
		} else if (istopic(toks, new String[] { "+", "mams", "+", "etri", "map", "temperature" })) {
			byte[] bytes = message.getPayload();
			if ("ph".equals(loc)) {
				mqttEnvMap.ph().temperature.fromBytes(bytes);
			} else if ("gw".equals(loc)) {
				mqttEnvMap.gw().temperature.fromBytes(bytes);
			}
		} else if (istopic(toks, new String[] { "+", "mams", "+", "etri", "map", "height_probability" })) {
			byte[] bytes = message.getPayload();
			// logger.debug("height_probability bytes : {}", StrUtil.bytesToHexStr(bytes, 30));
			if ("ph".equals(loc)) {
				mqttEnvMap.ph().height_probability.fromBytes(bytes);
			} else if ("gw".equals(loc)) {
				mqttEnvMap.gw().height_probability.fromBytes(bytes);
			}
		} else if (istopic(toks, new String[] { "+", "mams", "+", "etri", "map", "object_probability" })) {
			byte[] bytes = message.getPayload();
			// logger.debug("object_probability bytes : {}", StrUtil.bytesToHexStr(bytes, 30));
			if ("ph".equals(loc)) {
				mqttEnvMap.ph().object_probability.fromBytes(bytes);
			} else if ("gw".equals(loc)) {
				mqttEnvMap.gw().object_probability.fromBytes(bytes);
			}
		} else if (istopic(toks, new String[] { "+", "mams", "+", "etri", "event" })) {
			String json = new String(message.getPayload());
			logger.debug("message: " + json);
			JsonUtil jutil = new JsonUtil(json);
			EtriEvent event = jutil.treeToValue(EtriEvent.class);

			logger.debug("parsed json : {}", JsonUtil.pretty(event));
			// 이벤트 변환하여, 저장.

			doEtriEvent(event, loc);
			// 이벤트 신규 포맷으로 변경하도록 할 것.
		} else {
			String msg = new String(message.getPayload());
			logger.debug("message: " + msg);
		}

	}

	private void doCloudStat(CloudStat stat, String loc) {
		//
		TbAgentStat tbstat = new TbAgentStat();
		String PG = "";
		if ("ph".equals(loc)) {
			PG = "P";
		} else if ("gw".equals(loc)) {
			PG = "G";
		}
		tbstat.load(stat);
		logger.debug("convert to tbevent : {}", JsonUtil.pretty(tbstat));

		repoStat.save(tbstat);

		agentMgr.set(tbstat);

		wsAgent.pushData(tbstat);
	}

	private void doEtriEvent(EtriEvent evt, String loc) {
		if (evt.status == 0) {
			return;
		}
		//
		TbEventInfo tbevt = new TbEventInfo();
		String PG = "";
		if ("ph".equals(loc)) {
			PG = "P";
		} else if ("gw".equals(loc)) {
			PG = "G";
		}
		tbevt.load(evt, PG);
		logger.debug("convert to tbevent : {}", JsonUtil.pretty(tbevt));
		saveEvent(tbevt);
	}

	public synchronized void publish(String topic, String msg) {
		if (mqttClient == null || !mqttClient.isConnected()) {
			logger.error("mqtt client is null or disconnected.");
			return;
		}

		MqttMessage message = new MqttMessage(msg.getBytes());
		message.setQos(0);

		try {
			mqttClient.publish(topic, message);
		} catch (MqttException e) {
			logger.error("publish error", e);
		}
	}
}
