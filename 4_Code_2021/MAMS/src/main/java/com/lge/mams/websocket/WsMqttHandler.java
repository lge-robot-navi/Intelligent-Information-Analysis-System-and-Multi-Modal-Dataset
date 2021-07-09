package com.lge.mams.websocket;

import java.io.IOException;
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

@Component
public class WsMqttHandler extends TextWebSocketHandler {
	private final Logger logger = LoggerFactory.getLogger(WsMqttHandler.class);
	private static final Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd-HHmmss.SSS").create();

	private static Map<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();

	@Autowired
	WsAgentIfHandler wsagent;

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
		logger.info("[mqtt]payload: " + message.getPayload());
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("[mqtt]new Sessioin ID: {}", session.getId());
		users.put(session.getId(), session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		logger.info("[mqtt]closed Sessioin ID: {}  CloseStatus: {}", session.getId(), status);
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

	public synchronized void pushData(Object data) {
		String jsonStr = "";
		jsonStr = gson.toJson(data);
		// wsagent.pushData("MQTT", data);
		logger.debug("msg send :" + jsonStr + ", cnt : " + users.values().size());

		try {
			for (WebSocketSession s : users.values()) {
				logger.debug("id:" + s.getId());
				s.sendMessage(new TextMessage(jsonStr));
			}
		} catch (IOException e) {
			logger.error("E", e);
		}
	}
}
