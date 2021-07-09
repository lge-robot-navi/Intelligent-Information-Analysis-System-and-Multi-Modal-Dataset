package com.lge.mams.mqtt.scheduler;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MqttSchedulerManager {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private String msgold = "{}";
	private String msgnew = "{}";

	@PostConstruct
	private void postConstruct() {
		logger.debug("post construct.");
	}

	public void addSchedulingData(String msg) {
		msgold = msgnew;
		msgnew = msg;
	}

	public String getOldSchedule() {
		return msgold;
	}

	public String getNewSchedule() {
		return msgnew;
	}

}
