package com.lge.mams.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lge.mams.jpa.model.MdlLog;
import com.lge.mams.jpa.model.TbAgentStat;
import com.lge.mams.jpa.model.TbEventInfo;

@Component
public class WsAgentIfHandler extends TextWebSocketHandler {
	private final Logger logger = LoggerFactory.getLogger(WsAgentIfHandler.class);
	private static final Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd-HHmmss.SSS").create();

	private static Map<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();


	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
		logger.info("payload: " + message.getPayload());
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("new Sessioin ID: {}", session.getId());
		users.put(session.getId(), session);
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
	
	public void pushData(WsAgentIfData data) {
		String jsonStr = "";
		jsonStr = gson.toJson(data);
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
	}
	
	public void pushClearEvent(Long sn) {
		WsAgentIfData data = new WsAgentIfData();
		data.setMsgId("CLEAREVENT");
		TbEventInfo d = new TbEventInfo();
		d.setEventSn(sn);
		data.setData(d);
		pushData(data);
	}
	
	public void pushData(MdlLog d) {
		WsAgentIfData data = new WsAgentIfData();
		data.setMsgId("LOG");
		data.setData(d);
		pushData(data);		
	}
}
