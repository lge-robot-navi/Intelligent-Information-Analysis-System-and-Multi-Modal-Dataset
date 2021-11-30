package com.lge.mams.websocket;

import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lge.mams.common.util.DateUtil;
import com.lge.mams.jpa.model.MdlLog;
import com.lge.mams.jpa.model.TbAgentStat;
import com.lge.mams.jpa.model.TbEventInfo;
import com.lge.mams.mqtt.MqttHandler;
import com.lge.mams.util.JsonUtil;

@Component
public class WsAgentIfHandler extends TextWebSocketHandler {
	private final Logger logger = LoggerFactory.getLogger(WsAgentIfHandler.class);
	private static final Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd-HHmmss.SSS").create();

	private static Map<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();

	@Autowired
	MqttHandler mqtt;

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
		logger.info("payload: " + message.getPayload());
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		users.put(session.getId(), session);
		logger.info("new Sessioin ID: {}, cnt={}", session.getId(), users.values().size());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		logger.info("closed Sessioin ID: {}  CloseStatus: {}", session.getId(), status);
		users.remove(session.getId());
	}

	private int cnt = 0;

	public void testMsg() {
		String jsonStr = "json : " + cnt++;
		logger.debug("test msg send :" + jsonStr);

		try {
			for (WebSocketSession s : users.values()) {

				s.sendMessage(new TextMessage(jsonStr));
			}
		} catch (IOException e) {
			logger.error("E", e);
		}
	}

	public synchronized void pushData(WsAgentIfData data) {
		String jsonStr = "";
		jsonStr = gson.toJson(data);
		logger.debug("msg send :" + jsonStr + ", cnt : " + users.values().size());

		try {
			for (WebSocketSession s : users.values()) {
				// logger.debug("id:" + s.getId());
				s.sendMessage(new TextMessage(jsonStr));
			}
		} catch (IOException e) {
			logger.error("E", e);
		}
	}

	public void pushData(TbAgentStat d) {
		WsAgentIfData data = new WsAgentIfData();
		data.setMsgId("STAT");
		data.setData(d);
		pushData(data);
	}

	public void pushData(TbEventInfo d) {
		WsAgentIfData data = new WsAgentIfData();
		data.setMsgId("EVENT");
		data.setData(d);
		pushData(data);

		// 이벤트 발생시점. (roid)

		String loc = "";
		if ("P".equals(d.getAreaCode())) {
			loc = "ph";
		} else {
			loc = "gw";
		}

		Map<String, Object> map = new Hashtable<String, Object>();

		map.put("cmd", "EVENT");
		map.put("eventSn", d.getEventSn());
		map.put("eventDt", DateUtil.format(d.getEventDt(), "yyyyMMdd-HHmmss.SSS"));
		map.put("robotId", d.getRobotId());
		map.put("areaCode", d.getAreaCode());
		map.put("eventPosX", d.getEventPosX());
		map.put("eventPosY", d.getEventPosY());

		String json = JsonUtil.pretty(map);

		mqtt.publish("/mams/" + loc + "/roid/update_event", json);
	}

	public void pushData(String msgid, Object d) {
		WsAgentIfData data = new WsAgentIfData();
		data.setMsgId(msgid);
		data.setData(d);
		pushData(data);
	}

	public void pushScheduleResData(String json) {
		pushData("SCHEDULERES", json);
	}

	public void pushMqttAgents(String json) {
		pushData("MQTTAGENTS", json);
	}

	public void pushMqttAgents(Object o) {
		pushData("MQTTAGENTS", o);
	}

	public void pushClearEvent(Long sn, String areaCode) {
		WsAgentIfData data = new WsAgentIfData();
		data.setMsgId("CLEAREVENT");
		TbEventInfo d = new TbEventInfo();
		d.setEventSn(sn);
		data.setData(d);
		pushData(data);

		String loc = "";
		if ("P".equals(areaCode)) {
			loc = "ph";
		} else {
			loc = "gw";
		}

		Map<String, Object> map = new Hashtable<String, Object>();

		map.put("cmd", "CLEAREVENT");
		map.put("eventSn", sn);

		String json = JsonUtil.pretty(map);

		mqtt.publish("/mams/" + loc + "/roid/update_event", json);

		// LG monitoring server send.(roid)
	}

	public void pushClearEventAll() {
		WsAgentIfData data = new WsAgentIfData();
		data.setMsgId("CLEAREVENTALL");
		pushData(data);
		// LG monitoring server send. ( roid)

		Map<String, Object> map = new Hashtable<String, Object>();

		map.put("cmd", "CLEAREVENTALL");

		String json = JsonUtil.pretty(map);

		// 항상 모든 데이터 삭제.
		mqtt.publish("/mams/ph/roid/update_event", json);
		mqtt.publish("/mams/gw/roid/update_event", json);

	}

	public void pushData(MdlLog d) {
		WsAgentIfData data = new WsAgentIfData();
		data.setMsgId("LOG");
		data.setData(d);
		pushData(data);
	}

	/**
	 * 음성이 수신되는 경우, 클라이언트로 웹소켓을 통하여 파일위치를 전송해줌.
	 * 
	 * @param path
	 */
	public void pushVoiceInfo(String area, int agentId, String path) {
		WsAgentIfData data = new WsAgentIfData();
		data.setMsgId("VOICE");
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("agentId", agentId);
		param.put("area", area);
		param.put("url", path);
		data.setData(param);
		pushData(data);
	}
}
