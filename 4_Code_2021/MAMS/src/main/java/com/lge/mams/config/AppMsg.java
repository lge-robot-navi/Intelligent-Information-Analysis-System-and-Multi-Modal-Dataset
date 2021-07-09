package com.lge.mams.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
class AppMsg implements ApplicationListener<ContextRefreshedEvent> {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LgicConfig settings;

	@Value("#{systemDB['system.db.url']}")
	private String systemdburl;

	@Value("#{systemDB['system.db.password']}")
	private String systemdbpass;

	@Value("#{systemDB['system.db.username']}")
	private String systemdbuser;

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		logger.info("======================================================");
		logger.info("== DEV_TYPE        : {}", settings.getDevType());
		logger.info("== EVENT IMAGE DIR : {}", settings.getEventImageDir());
		logger.info("== MNTR IMAGE DIR  : {}", settings.getMntrImageDir());
		logger.info("== MQTT SERVER     : {}", settings.getMqttServer());
		logger.info("== DB URL          : {}", systemdburl);
		logger.info("== DB USER         : {}", systemdbuser);
		logger.info("== DB PASS         : {}", systemdbpass);
		logger.info("======================================================");
		return;
	}
}