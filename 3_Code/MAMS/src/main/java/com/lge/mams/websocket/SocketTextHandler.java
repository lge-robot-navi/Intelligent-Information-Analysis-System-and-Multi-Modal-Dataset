package com.lge.mams.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class SocketTextHandler extends TextWebSocketHandler {

	private final Logger log = LoggerFactory.getLogger(SocketTextHandler.class);
	private static final Gson gson = new GsonBuilder().create();
	private final String AWS_PUBLIC_IP = "52.79.114.42";

	private Map<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();

	// private UserRegistry registry = new UserRegistry();
	private WebSocketSession clientSession; // web client session(brws)

	// public UserRegistry registry() {
	// return new UserRegistry();
	// }
	
		
	// 클라이언트에서 send를 이용해서 메시지 발송을 한 경우 이벤트 핸들링
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {

		log.info("payload: "+message.getPayload());
		JsonObject jsonMessage = gson.fromJson(message.getPayload(), JsonObject.class);
		// UserSession user = registry.getBySession(session);
		log.info("jsonMessage: "+jsonMessage);

		log.debug("session id: {}", session.toString());
		System.out.println(session.getLocalAddress().toString());
		log.debug(session.getLocalAddress().toString());

		if (users.get(session.getId()) != null) {
			log.debug("Incoming message from user : {}", jsonMessage); 
		} else {
			log.debug("Incoming message from new user: {}", jsonMessage);
		}
		
		System.out.println("session: "+session);
		log.debug("session: "+session);

		String id = jsonMessage.get("ID").getAsString();
		String msgType = jsonMessage.get("MSG_TYPE").getAsString();
		System.out.println(" id: " + id + ", msgType: " + msgType);
		log.debug("id: " + id + ", msgType: " + msgType);
		log.debug("session.getLocalAddress().getHostName(): "+session.getLocalAddress().getHostName());
		
		if (id.equals(session.getLocalAddress().getHostName())) {		//for local
//		if (id.equals(AWS_PUBLIC_IP)) {
			System.out.println("Received webclientSession message");
			log.debug("Received webclientSession message");
			clientSession = session;
		} else {
			
			
			log.info(">>> user.values: "+users.values());
			System.out.println(">>> user.values: "+users.values());
			for (WebSocketSession s : users.values()) {
				s.sendMessage(message);
			}
			
			
			if (clientSession != null) {
				System.out.println("mjchoi local clientSession : " + clientSession.getId());
				log.debug("mjchoi local clientSession : " + clientSession.getId());
				if (msgType.equals("notice") || msgType.equals("response")) {
					clientSession.sendMessage(message);
				} else if (msgType.equals("request")) {
					System.out.println("request");
					log.debug("request");
					WebSocketSession s = getSessionByIP(id);
					if (s != null) {
						s.sendMessage(message);
					}
				} else {
					System.out.println("none");
					log.debug("none");
				}
			} else {
				System.out.println("clientSession is null!!");
				log.debug("clientSession is null!!");
			}
		}
	}

	// 클라이언트에서 접속을 하여 성공할 경우 발생하는 이벤트
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("new Sessioin ID: {}", session.getId());
		//메시지 발송을 위해 유저목록 추가
		users.put(session.getId(), session);
	}

	// 클라이언트에서 연결을 종료할 경우 발생하는 이벤트
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		log.info("closed Sessioin ID: {}  CloseStatus: {}", session.getId(), status);
		// 메시지 발송 제외를 위해 유저목록에서 삭제
		users.remove(session.getId());

		if (clientSession != null) {
			//log.info("afterConnectionClosed getRemoteAddress name : {}", session.getRemoteAddress().getHostName());
			String jsonStr = "{\"ID\":\"";
			jsonStr += session.getRemoteAddress().getHostName();
			jsonStr += "\",\"MSG_TYPE\":\"response\", ";
			jsonStr += "\"VALUES\": {\"result\": \"ok\", \"value1\":\"status\", \"value2\": \"disconnected\"}}";
			clientSession.sendMessage(new TextMessage(jsonStr));			
		}
	}
	
	public WebSocketSession getSessionByIP(String id) {
		for (WebSocketSession s : users.values()) {
			log.info("remote name : {}", s.getRemoteAddress().getHostName());
			if (id.equals(s.getRemoteAddress().getHostName())) {
				log.info("getSessionByIP() session id : {}", s.getId());
				return s;
			}
		}
		return null;
	}

	public void pollingAgentStatus() throws InterruptedException {
//		log.info("pollingAgentStatus()");  
		log.info(">>> scheduler: user.values: "+users.values());
		System.out.println(">>> scheduler: user.values: "+users.values());
		for (WebSocketSession s : users.values()) {
			//log.info("polling : {}", s);
			
			if (s != clientSession && s.isOpen()) {
				String jsonStr = "{\"ID\":\"";
				jsonStr += s.getRemoteAddress().getHostName();
				jsonStr += "\",\"MSG_TYPE\":\"request\",\"DATA_TYPE\":\"status\"}";
				log.info("pollingAgentStatus : {}", jsonStr);
				try {
					s.sendMessage(new TextMessage(jsonStr));
					Thread.sleep(1000);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
