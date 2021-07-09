package com.lge.mams.api.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lge.mams.jpa.impl.TaCodeInfoRepository;
import com.lge.mams.jpa.model.TaCodeInfo;

import tv.twelvetone.json.JsonObject;
import tv.twelvetone.json.JsonValue;
import tv.twelvetone.rjson.RJsonParserFactory;

@Component
public class AgentSettingsMgr {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	TaCodeInfoRepository repoCodeInfo;

	List<AgentSetting> listPohang = new ArrayList<AgentSetting>();
	List<AgentSetting> listGwangju = new ArrayList<AgentSetting>();

	/**
	 * json : {lat:36.091418, lon:129.331982, fixed:Y, markerImage:robot.png ,agentId:1,areaCode:P}
	 * json : {lat:36.090453, lon:129.332206, fixed:Y, markerImage:robot.png ,agentId:2,areaCode:P}
	 * json : {lat:36.090388, lon:129.333351, fixed:Y, markerImage:robot.png ,agentId:3,areaCode:P}
	 * json : {lat:36.090321, lon:129.334087, fixed:Y, markerImage:cctv.png,agentId:4,areaCode:P }
	 * json : {lat:36.091262, lon:129.333897, fixed:Y, markerImage:cctv.png ,agentId:5,areaCode:P}
	 * json : {lat:36.090959, lon:129.332862, fixed:Y, markerImage:cctv.png ,agentId:6,areaCode:P}
	 * 
	 * json : {lat:35.1064, lon:126.8951, fixed:Y, markerImage:robot.png ,agentId:1,areaCode:G}
	 * json : {lat:35.106918, lon:126.895787, fixed:Y, markerImage:robot.png ,agentId:2,areaCode:G}
	 * json : {lat:35.104987, lon:126.893684, fixed:Y, markerImage:robot.png ,agentId:3,areaCode:G}
	 * json : {lat:35.104153, lon:126.895207, fixed:Y, markerImage:cctv.png ,agentId:4,areaCode:G}
	 * json : {lat:35.104162, lon:126.894338, fixed:Y, markerImage:cctv.png ,agentId:5,areaCode:G}
	 * json : {lat:35.104355, lon:126.893317, fixed:Y, markerImage:cctv.png ,agentId:6,areaCode:G}
	 */

	public List<AgentSetting> getPohangList() {
		return listPohang;
	}

	public List<AgentSetting> getGwangju() {
		return listGwangju;
	}

	AgentSetting getAgentSetting(List<AgentSetting> list, int robotId) {
		Optional<AgentSetting> opt = list.stream().filter(s -> s.robotId == robotId).findFirst();
		if (opt.isPresent()) return opt.get();
		return null;
	}

	void setAgentSetting(List<AgentSetting> list, int robotId, boolean stop) {
		Optional<AgentSetting> opt = list.stream().filter(s -> s.robotId == robotId).findFirst();

		if (opt.isPresent()) {
			opt.get().stop = stop;
		}
	}

	@PostConstruct
	void init() {
		logger.debug("init");
		Function<String, AgentSetting> getRobotId = json -> {
			JsonValue parsed = new RJsonParserFactory().createParser().stringToValue(json);
			JsonObject jobj = parsed.asObject();
			AgentSetting s = new AgentSetting();
			s.robotId = jobj.getInt("agentId", -1);
			s.fixed = "Y".equals(jobj.getString("fixed", "N")) ? true : false;
			return s;
		};

		List<TaCodeInfo> list = repoCodeInfo.findByCdgrpCdAndUseYn("TA009", "Y"); // 포항.

		listPohang = new ArrayList<AgentSetting>();
		listGwangju = new ArrayList<AgentSetting>();

		for (TaCodeInfo l : list) {
			String json = l.getCodeDs();
			logger.debug("json : {}", json);
			AgentSetting s = getRobotId.apply(json);
			listPohang.add(s);
		}

		list = repoCodeInfo.findByCdgrpCdAndUseYn("TA010", "Y"); // 광주.
		for (TaCodeInfo l : list) {
			String json = l.getCodeDs();
			logger.debug("json : {}", json);
			AgentSetting s = getRobotId.apply(json);
			listGwangju.add(s);
		}
	}

	public void refreshAgentList() {
		init();
	}

}
