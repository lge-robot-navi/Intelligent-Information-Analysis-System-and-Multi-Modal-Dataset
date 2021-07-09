package com.lge.mams.mqtt.stat;

import org.apache.commons.lang3.time.StopWatch;

import com.lge.mams.mqtt.cloudstat.CloudStat;

/**
 * Agent 유형을 나타냄.
 * 테스트 과정에서 데이터가 그리 많지 않을 것이기 때문에, hashtable를 사용하지 않고, 리스트 방식으로 처리함.
 *
 */
public class MqttAgentInfo {
	public String location; // ph, gw ( 포항, 광주)
	public String agentType;
	public Integer agentId;
	public boolean fixed;
	private StopWatch stopwatch;
	public CloudStat stat;

	public long getElapsedMs() {
		return stopwatch.getTime();
	}

	public void reset() {
		stopwatch.reset();
		stopwatch.start();
	}

	public void start() {
		stopwatch = new StopWatch();
		stopwatch.start();
	}
}
