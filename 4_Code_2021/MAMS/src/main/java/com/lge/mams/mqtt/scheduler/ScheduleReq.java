package com.lge.mams.mqtt.scheduler;

import java.util.ArrayList;
import java.util.List;

public class ScheduleReq {
	public String reqType;
	public int agentId = 0;
	public double posy = 0;
	public double posx = 0;
	public List<Robot> robots = new ArrayList<Robot>();
}
