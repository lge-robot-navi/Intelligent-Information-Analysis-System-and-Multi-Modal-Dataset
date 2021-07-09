package com.lge.mams.agentif.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lge.mams.jpa.model.TbAgentStat;

@Component
public class AgentManager {
	private Logger logger = LoggerFactory.getLogger(getClass());

	Map<Integer, TbAgentStat> mapph = new HashMap<Integer, TbAgentStat>();
	Map<Integer, TbAgentStat> mapgw = new HashMap<Integer, TbAgentStat>();

	public synchronized void set(TbAgentStat stat) {
		if ("P".equals(stat.areaCode)) {
			mapph.put(stat.robotId, stat);
		} else if ("G".equals(stat.areaCode)) {
			mapgw.put(stat.robotId, stat);
		} else {
			logger.warn("unknown areacode : " + stat.areaCode);
		}
	}

	public synchronized ArrayList<TbAgentStat> phlist() {
		return new ArrayList(mapph.values());
	}

	public synchronized ArrayList<TbAgentStat> gwlist() {
		return new ArrayList(mapgw.values());
	}

}
