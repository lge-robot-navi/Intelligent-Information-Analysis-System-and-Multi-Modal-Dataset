package com.lge.mams.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

	@Value("#{appProp['udpserver.port']}")
	public int udpServerPort;

	@Value("#{appProp['event.save.ms']}")
	public long eventSaveMs;
}
