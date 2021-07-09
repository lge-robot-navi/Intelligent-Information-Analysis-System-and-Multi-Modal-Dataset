package com.lge.mams.mqtt;

import java.util.Date;
import java.util.HashMap;
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

import com.lge.mams.config.LgicConfig;
import com.lge.mams.websocket.WsMqttHandler;

@Component
public class MqttMonitor implements MqttCallback {

	private Logger logger = LoggerFactory.getLogger(getClass());
	MqttAsyncClient mqttClient = null;

	@Autowired
	LgicConfig config;

	@Autowired
	WsMqttHandler wsMqtt;

	@PostConstruct
	private synchronized void start() {
		logger.debug("[mqtt mon]START MQTT Subscriber ");

		String[] topics = {
				"/mams/#",
		};
		int[] qoss = {
				0
		};
		// String broker = "tcp://192.168.0.190:1883";
		String broker = config.getMqttServer();
		long time = new Date().getTime();
		String clientId = "lg-cloud-monitor" + time;
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			mqttClient = new MqttAsyncClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			connOpts.setKeepAliveInterval(300);
			mqttClient.setCallback(this);
			logger.debug("[mqtt mon]Connecting to broker: " + broker);
			mqttClient.connect(connOpts);
			logger.debug("[mqtt mon]Connected");
			Thread.sleep(1000);
			mqttClient.subscribe(topics, qoss);
			logger.debug("[mqtt mon]Subscribed");
		} catch (Exception me) {
			if (me instanceof MqttException) {
				logger.error("[mqtt mon]reason " + ((MqttException) me).getReasonCode());
			}
			logger.error("[mqtt mon]msg " + me.getMessage());
			logger.error("[mqtt mon]loc " + me.getLocalizedMessage());
			logger.error("[mqtt mon]cause " + me.getCause());
			logger.error("[mqtt mon]excep " + me);
			logger.error("[mqtt mon]mqtt error", me);
		}

	}

	@Scheduled(fixedDelay = 5000L)
	public void checkService() {
		// mqtt 가 동작하는지 확인.
		if (mqttClient == null || !mqttClient.isConnected()) {
			logger.error("[mqtt mon] Restart MQTT");
			stop();
			start();
		}
	}

	@PreDestroy
	private void stop() {
		logger.debug("[mqtt mon]STOP MQTT Subscriber");
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
		logger.error("[mqtt mon]connection lost", arg0);
	}

	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// logger.debug("delivery complete");
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {

		logger.debug("[mqtt mon]topic: " + topic);

		String msg = new String(message.getPayload());

		// 각 토픽 구분자에 따라서 처리하도록 할 것.
		String[] toks = topic.split("/");
		// 바이너리 데이터 스킵.
		if (istopic(toks, new String[] { "+", "mams", "+", "etri", "map", "height" })) {
			logger.debug("height");
		} else if (istopic(toks, new String[] { "+", "mams", "+", "etri", "map", "search" })) {
			logger.debug("search");
		} else if (istopic(toks, new String[] { "+", "mams", "+", "etri", "map", "temperature" })) {
			logger.debug("temperature");
		} else if (istopic(toks, new String[] { "+", "mams", "+", "etri", "map", "height_probability" })) {
			logger.debug("height_probability");
		} else if (istopic(toks, new String[] { "+", "mams", "+", "etri", "map", "object_probability" })) {
			logger.debug("object_probability");
		} else {
			logger.debug("[mqtt mon]message: " + msg);

			Map map = new HashMap<String, Object>();
			map.put("topic", topic);
			map.put("msg", msg);
			wsMqtt.pushData(map);
		}

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

	public synchronized void publish(String topic, String msg) {
		if (mqttClient == null || !mqttClient.isConnected()) {
			logger.error("[mqtt mon]mqtt client is null or disconnected.");
			return;
		}

		MqttMessage message = new MqttMessage(msg.getBytes());
		message.setQos(0);
		message.setRetained(false);


		try {
			mqttClient.publish(topic, message);
		} catch (MqttException e) {
			logger.error("[mqtt mon]publish error", e);
		}
	}
}
