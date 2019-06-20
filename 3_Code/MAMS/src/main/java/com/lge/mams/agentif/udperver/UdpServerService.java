package com.lge.mams.agentif.udperver;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lge.mams.common.util.CiaConfig;

@Component
public class UdpServerService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private UdpServer server;

	@Value("#{appProp['udpserver.port']}")
	private int udpServerPort;
	
	@Autowired
	private CiaConfig ciaConfig;
	
	@Autowired
	private SeqImageProvider imageProvider;
	
	
	@PostConstruct
	private void start() {
		logger.debug("START Udp Server PORT(" + udpServerPort + ")");
		
		if( server != null ) {
			server.stopServer();
			server = null;
		}
		
		server = new UdpServer();
		server.setCiaConfig(ciaConfig);
		server.startServer(udpServerPort, imageProvider);
	}
	
	@PreDestroy
	private void stop() {
		logger.debug("STOP Udp Server");
		if( server != null ) {
			server.stopServer();
			server = null;
		}
	}
	
	
}
