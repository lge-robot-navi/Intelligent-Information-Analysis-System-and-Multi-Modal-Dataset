package com.lge.mams.mqtt.stat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lge.mams.agentif.udperver.SeqImageProvider;
import com.lge.mams.api.web.AgentSettingsMgr;
import com.lge.mams.mqtt.cloudstat.CloudStat;

@Component
public class MqttAgentStatManager {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	AgentSettingsMgr agentSettings;

	@Autowired
	SeqImageProvider seqImgProvider;

	List<MqttAgentInfo> agents = new ArrayList<MqttAgentInfo>();

	@PostConstruct
	private void postConstruct() {
		logger.debug("post construct");
		// post construct 의 순서는 어떻게 되는가?
		// agent 목록이 변경되었을 때, refresh 는 어떻게 처리할 것인가? (id가 변경되었을 때)
		// 스프링 ApplicationEventPublisher 를 이용하여, 이벤트를 발생하고, 이에 대해서 반응하여처리하면 될 것.

		agents = new ArrayList<MqttAgentInfo>(); // 새로 기동하는 경우는 초기화 하고 실행될 수 있도록.
		initph();
		initgw();
	}

	public void refreshMqttAgentStatManager() {
		agents = new ArrayList<MqttAgentInfo>(); // 새로 기동하는 경우는 초기화 하고 실행될 수 있도록.
		initph();
		initgw();
	}

	private MqttAgentInfo getNewAgentInfo(String location, String agentType, Integer agentId) {
		MqttAgentInfo info = new MqttAgentInfo();
		info.location = location;
		info.agentType = agentType;
		info.agentId = agentId;
		info.start();
		return info;
	}

	private void initph() {
		agentSettings.getPohangList().stream().forEach(ele -> {
			MqttAgentInfo info = new MqttAgentInfo();
			info.location = "ph";
			info.agentId = ele.getRobotId();
			if (ele.isFixed()) {
				info.agentType = "FIXEDROBOT"; // agentType ROBOT FIXEDROBOT 동일하게 처리할 것. 고정형인 경우, info.fixed = true로 설정할것.
			} else {
				info.agentType = "ROBOT"; // agentType ROBOT FIXEDROBOT 동일하게 처리할 것. 고정형인 경우, info.fixed = true로 설정할것.
			}
			info.start();
			agents.add(info);
		});
		// ENVMAP,
		// DRONE
		// SCHEDULER
//		agents.add(getNewAgentInfo("ph", "DRONE", 1));
//		agents.add(getNewAgentInfo("ph", "ENVMAP", 1));
//		agents.add(getNewAgentInfo("ph", "SCHEDULER", 1));
	}

	private void initgw() {
		agentSettings.getGwangju().stream().forEach(ele -> {
			MqttAgentInfo info = new MqttAgentInfo();
			info.location = "gw";
			info.agentId = ele.getRobotId();
			if (ele.isFixed()) {
				info.agentType = "FIXEDROBOT"; // agentType ROBOT FIXEDROBOT 동일하게 처리할 것. 고정형인 경우, info.fixed = true로 설정할것.
			} else {
				info.agentType = "ROBOT"; // agentType ROBOT FIXEDROBOT 동일하게 처리할 것. 고정형인 경우, info.fixed = true로 설정할것.
			}
			info.start();
			agents.add(info);
		});
		// ENVMAP,
		// DRONE
		// SCHEDULER
//		agents.add(getNewAgentInfo("gw", "DRONE", 1));
//		agents.add(getNewAgentInfo("gw", "ENVMAP", 1));
//		agents.add(getNewAgentInfo("gw", "SCHEDULER", 1));
	}

	synchronized public void setHealthy(String location, Integer agentId, CloudStat stat) {
		// logger.info("location : {}, agentid : {}", location, agentId);
		// logger.info("agents: {}", YamlUtil.pretty(this.agents));
		agents.stream().filter(ele -> location.equals(ele.location) && agentId == ele.agentId).forEach(ele -> {
			// logger.info("agent reset: {}", YamlUtil.pretty(ele));
			ele.reset();
			ele.stat = stat;
		});
	}

	synchronized public void setHealthy(String location, Integer agentId) {
		agents.stream().filter(ele -> location.equals(ele.location) && agentId == ele.agentId).forEach(ele -> {
			ele.reset();
		});
	}

	synchronized public List<Map> getStatList() {
		List<Map> list = new ArrayList<Map>();

		Date now = new Date();
		agents.stream().forEach(ele -> {
			Map map = new HashMap<String, Object>();
			map.put("location", ele.location);
			map.put("agentType", ele.agentType);
			map.put("agentId", ele.agentId);
			boolean isHealthy = seqImgProvider.isConnected(now, ele.agentId, ele.location, 30 * 1000);
			if (isHealthy) {
				map.put("isHealthy", isHealthy);
			} else {
				map.put("isHealthy", ele.getElapsedMs() < 30 * 1000 ? true : false);
			}
			list.add(map);
		});

		// logger.info("agents : {}", YamlUtil.pretty(list));

		return list;
	}

	synchronized public List<MdlMqttStat> getStatList2() {
		List<MdlMqttStat> list = new ArrayList<MdlMqttStat>();

		Date now = new Date();
		agents.stream().forEach(ele -> {
			MdlMqttStat map = new MdlMqttStat();
			map.location = ele.location; // ph or gw
			map.agentType = ele.agentType; // ROBOT, FIXEDROBOT
			map.agentId = ele.agentId; //
			map.fixed = ele.fixed;
			map.stat = ele.stat;
			map.isHealthy = ele.getElapsedMs() < 10 * 1000 ? true : false;
			list.add(map);
		});

		// logger.info("agents : {}", YamlUtil.pretty(list));

		return list;
	}

}
