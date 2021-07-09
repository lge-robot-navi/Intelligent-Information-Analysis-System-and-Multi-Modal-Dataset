package com.lge.mams.mqtt.stat;

import com.lge.mams.mqtt.cloudstat.CloudStat;

public class MdlMqttStat {
	public String location; // ph or gw
	public String agentType; // ROBOT, FIXEDROBOT
	public boolean fixed;
	public int agentId;
	public boolean isHealthy;
	public CloudStat stat;
	public double dist;
}
