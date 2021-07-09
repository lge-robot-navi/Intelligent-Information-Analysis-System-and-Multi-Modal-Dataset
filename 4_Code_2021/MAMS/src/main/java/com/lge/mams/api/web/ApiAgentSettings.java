package com.lge.mams.api.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/agents")
public class ApiAgentSettings {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	AgentSettingsMgr mgr;

	@RequestMapping("get/gwangju")
	public @ResponseBody List<AgentSetting> getGwangju() {
		logger.debug("gwangju list");
		return mgr.listGwangju;
	}

	@RequestMapping("get/pohang")
	public @ResponseBody List<AgentSetting> getPohang() {
		logger.debug("pohang list");
		return mgr.listPohang;
	}

	@RequestMapping("get/gwangju/{agentid}")
	public @ResponseBody AgentSetting getGwangjuSingle(@PathVariable int agentid) {
		logger.debug("gwangju {}", agentid);
		return mgr.getAgentSetting(mgr.listGwangju, agentid);
	}

	@RequestMapping("get/pohang/{agentid}")
	public @ResponseBody AgentSetting getPohangSingle(@PathVariable int agentid) {
		logger.debug("pohang {}", agentid);
		return mgr.getAgentSetting(mgr.listPohang, agentid);
	}

	@RequestMapping("set/gwangju/{agentid}/{stop}")
	public @ResponseBody AgentSetting setGwangjuSingle(@PathVariable int agentid, @PathVariable boolean stop) {
		logger.debug("gwangju {}, {}", agentid, stop);
		mgr.setAgentSetting(mgr.listGwangju, agentid, stop);
		return mgr.getAgentSetting(mgr.listGwangju, agentid);
	}

	@RequestMapping("set/pohang/{agentid}/{stop}")
	public @ResponseBody AgentSetting setPohangSingle(@PathVariable int agentid, @PathVariable boolean stop) {
		logger.debug("pohang {}, {}", agentid, stop);
		mgr.setAgentSetting(mgr.listPohang, agentid, stop);
		return mgr.getAgentSetting(mgr.listPohang, agentid);
	}

	@RequestMapping(value = "set/gwangju", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> saveGSettings(@RequestBody List<AgentSetting> list) {
		logger.debug("settings...{} ", ToStringBuilder.reflectionToString(list, ToStringStyle.MULTI_LINE_STYLE));

		for (AgentSetting s : list) {
			mgr.setAgentSetting(mgr.listGwangju, s.getRobotId(), s.isStop());
		}

		HashMap<String, String> rlt = new HashMap<String, String>();
		rlt.put("resultCode", "OK");
		rlt.put("resultMsg", "성공하였습니다");

		return rlt;
	}

	@RequestMapping(value = "set/pohang", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> savePSettings(@RequestBody List<AgentSetting> list) {
		logger.debug("settings...{} ", ToStringBuilder.reflectionToString(list, ToStringStyle.MULTI_LINE_STYLE));

		for (AgentSetting s : list) {
			mgr.setAgentSetting(mgr.listPohang, s.getRobotId(), s.isStop());
		}

		HashMap<String, String> rlt = new HashMap<String, String>();
		rlt.put("resultCode", "OK");
		rlt.put("resultMsg", "성공하였습니다");

		return rlt;
	}
}
