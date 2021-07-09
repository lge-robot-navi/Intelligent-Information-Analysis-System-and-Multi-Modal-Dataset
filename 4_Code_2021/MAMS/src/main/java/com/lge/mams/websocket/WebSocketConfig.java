package com.lge.mams.websocket;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@ComponentScan
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private SocketTextHandler sth = new SocketTextHandler();

	@Autowired
	private WsAgentIfHandler wsAgent;

	@Autowired
	private WsMqttHandler wsMqtt;

	private Timer timer;
	private TimerTask timerTask;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		System.out.println("mjchoi registerWebSocketHandlers");
		sth = new SocketTextHandler();
		// wsAgent = new WsAgentIfHandler();

		registry.addHandler(sth, "/agent").setAllowedOrigins("*");
		registry.addHandler(wsAgent, "/agentif").setAllowedOrigins("*");
		registry.addHandler(wsMqtt, "/mqtt").setAllowedOrigins("*");

		timer = new Timer();
		timerTask = new TimerTask() {
			@Override
			public void run() {
				try {
					sth.pollingAgentStatus();
					// wsAgent.testMsg();
				} catch (InterruptedException e) {
					logger.error("E", e);
				}
			}
		};
		timer.schedule(timerTask, 1000, 10000);
	}

	@PreDestroy
	public void preDestory() {
		if (timer != null) timer.cancel();
	}

}
