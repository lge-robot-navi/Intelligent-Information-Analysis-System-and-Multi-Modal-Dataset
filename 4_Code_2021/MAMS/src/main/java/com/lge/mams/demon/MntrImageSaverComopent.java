package com.lge.mams.demon;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lge.mams.agentif.udperver.UdpPacket;
import com.lge.mams.config.LgicConfig;

/**
 * 이미지를 저장하는 역할
 * 
 * @author dulee
 *
 */
@Component
public class MntrImageSaverComopent {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LgicConfig config;

	MntrImageSaver thread;

	public void push(UdpPacket img) {
		if (thread != null) {
			thread.push(img);
		}
	}

	@PostConstruct
	private void start() {
		logger.debug("START MntrImageSaverService");

		if (thread != null) {
			thread.stopServer();
			thread = null;
		}

		thread = new MntrImageSaver();
		thread.config = this.config;
		thread.startServer();
	}

	@PreDestroy
	private void stop() {
		logger.debug("STOP MntrImageSaverService");
		if (thread != null) {
			thread.stopServer();
			thread = null;
		}
	}

}
