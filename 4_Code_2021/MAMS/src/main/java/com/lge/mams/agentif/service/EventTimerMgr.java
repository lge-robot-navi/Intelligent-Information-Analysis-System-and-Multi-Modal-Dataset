package com.lge.mams.agentif.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lge.mams.config.AppConfig;

@Component
public class EventTimerMgr {

	Map<String, Long> map = new HashMap<String, Long>();

	@Autowired
	AppConfig config;

	public boolean isEventTime(String areaCode, int robotId, String abnormalId) {
		String key = String.format("%s-%d-%s", areaCode, robotId, abnormalId);
		long now = System.currentTimeMillis();
		if (map.containsKey(key)) {
			long old = map.get(key);
			long millis = Math.abs(now - old);
			if (millis > config.eventSaveMs) {
				map.put(key, now);
				return true;
			}
			return false;
		} else {
			map.put(key, now);
		}
		return true;
	}
}
