package com.lge.mams.mqtt.envmap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MqttEnvMapManager {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private EnvMapData mapPh = new EnvMapData();
	private EnvMapData mapGw = new EnvMapData();

	@PostConstruct
	private void postConstruct() {
		logger.debug("post construct.");
		mapPh = new EnvMapData();
		mapGw = new EnvMapData();
	}

	public EnvMapData ph() {
		return mapPh;
	}

	public EnvMapData gw() {
		return mapGw;
	}
}
