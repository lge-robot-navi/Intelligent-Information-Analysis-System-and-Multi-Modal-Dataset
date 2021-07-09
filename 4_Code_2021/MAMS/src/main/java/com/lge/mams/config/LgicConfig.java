package com.lge.mams.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class LgicConfig {

	@Autowired
	private Environment env;

	private LgicConfigInfo config;

	public String getDevType() {
		String devtype = env.getProperty("DEV_TYPE");
		return devtype;
	}

	private void initConfig() {
		String devtype = env.getProperty("DEV_TYPE");
		config = new LgicConfigInfo();
		if ("PC".equals(devtype)) {
			config.eventImageDir = "C:/test/images/events/";
			config.mntrImageDir = "C:/test/images/mntr/";
			config.voiceDir = "C:/test/voices/mntr/";
			config.mqttServer = "tcp://192.168.111.26:1883";
			// config.mqttServer = "tcp://220.81.76.111:1883";
		} else if ("AWS".equals(devtype)) {
			config.eventImageDir = "/home/lgic/cia2019/images/events/";
			config.mntrImageDir = "/home/lgic/cia2019/images/mntr/";
			config.voiceDir = "/home/lgic/cia2019/voices/mntr/";
			config.mqttServer = "tcp://127.0.0.1:1883";
		} else {
			config.eventImageDir = "/home/lgic/cia2019/images/events/";
			config.mntrImageDir = "/home/lgic/cia2019/images/mntr/";
			config.voiceDir = "/home/lgic/cia2019/voices/mntr/";
			config.mqttServer = "tcp://127.0.0.1:1883";
		}
	}

	public String getEventImageDir() {
		if (config == null)
			initConfig();
		return config.eventImageDir;
	}

	public String getMntrImageDir() {
		if (config == null)
			initConfig();
		return config.mntrImageDir;
	}

	public String getVoiceDir() {
		if (config == null)
			initConfig();
		return config.voiceDir;
	}

	public String getMqttServer() {
		if (config == null)
			initConfig();
		return config.mqttServer;
	}

}
