package com.lge.mams.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

public class UserSession {

	private final Logger log = LoggerFactory.getLogger(UserSession.class);

	private String id;

	public UserSession(WebSocketSession session) {
		this.id = session.getId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
