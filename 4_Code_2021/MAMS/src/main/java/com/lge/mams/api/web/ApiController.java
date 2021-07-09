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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lge.mams.api.model.ApiRes;
import com.lge.mams.config.LgicSettings;
import com.lge.mams.config.LgicSettingsComponent;
import com.lge.mams.jpa.impl.TaCodeInfoRepository;
import com.lge.mams.jpa.impl.TbAgentStatRepository;
import com.lge.mams.jpa.model.TaCodeId;
import com.lge.mams.jpa.model.TaCodeInfo;
import com.lge.mams.jpa.model.TbAgentStat;
import com.lge.mams.mqtt.stat.MqttAgentStatManager;
import com.lge.mams.util.YamlUtil;

@Controller
@RequestMapping("api")
public class ApiController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	TaCodeInfoRepository repoCodeInfo;

	@RequestMapping("codeInfo")
	public @ResponseBody List<TaCodeInfo> getCodeInfo(@RequestParam String cdgrpCd) {
		logger.debug("code Info : {}", cdgrpCd);
		List<TaCodeInfo> list;

		// list = repoCodeInfo.findByCdgrpCd(cdgrpCd);
		list = repoCodeInfo.findByCdgrpCdAndUseYnOrderByOrderNoAsc(cdgrpCd, "Y");

		return list;
	}

	@RequestMapping("codeInfoAll")
	public @ResponseBody List<TaCodeInfo> getCodeInfoAll(@RequestParam String cdgrpCd) {
		logger.debug("code Info : {}", cdgrpCd);
		List<TaCodeInfo> list;

		// list = repoCodeInfo.findByCdgrpCd(cdgrpCd);
		list = repoCodeInfo.findByCdgrpCdOrderByOrderNoAsc(cdgrpCd);

		return list;
	}
	
	@RequestMapping("codeInfoPhAndGw")
	public @ResponseBody List<TaCodeInfo> getCodeInfoPhAndGw() {
		List<TaCodeInfo> list;
		List<TaCodeInfo> listAdd;

		list = repoCodeInfo.findByCdgrpCdOrderByOrderNoAsc("TA009");		// 포항 에이전트 리스트
		listAdd = repoCodeInfo.findByCdgrpCdOrderByOrderNoAsc("TA010");		// 광주 에이전트 리스트
		
		list.addAll(listAdd);

		return list;
	}
	
	@RequestMapping("codeInfoMoveAgents")
	public @ResponseBody List<TaCodeInfo> getCodeInfoMoveAgents() {
		List<TaCodeInfo> list;

		list = repoCodeInfo.findByCdgrpCdOrderByOrderNoAsc("TA009");
		
		return list;
	}
	
	@Autowired
	TbAgentStatRepository repoAgentStat;
	
	@Autowired
	MqttAgentStatManager mqttAgentStatManager;

	@Autowired
	AgentSettingsMgr agentSettingsMgr;

	@RequestMapping("refresh/agent/settings")
	public @ResponseBody ApiRes refreshAgentSettings() {
		logger.debug("refreshAgentSettings");

		// 순서 중요함.
		agentSettingsMgr.refreshAgentList();

		mqttAgentStatManager.refreshMqttAgentStatManager();

		return ApiRes.success();

	}

	@RequestMapping(value = "save/agent/useyn", method = RequestMethod.POST)
	public @ResponseBody ApiRes saveSettings(@RequestBody List<TaCodeInfo> list) {
		logger.info("code info {}", YamlUtil.pretty(list));

		list.stream().forEach(ele -> {
			TaCodeId id = new TaCodeId();
			id.cdgrpCd = ele.getCdgrpCd();
			id.codeCd = ele.getCodeCd();
			TaCodeInfo data = repoCodeInfo.findOne(id);

			data.setUseYn(ele.getUseYn());
			repoCodeInfo.save(data);

		});
		return ApiRes.success();
	}

	@Autowired
	LgicSettingsComponent lgicSettings;

	@RequestMapping(value = "settings/pohang", method = RequestMethod.GET)
	public @ResponseBody LgicSettings getSettings() {
		logger.debug("settings... ");

		return lgicSettings.getSettingsPohang();
	}

	@RequestMapping(value = "settings/pohang", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> saveSettings(@RequestBody LgicSettings settings) {
		logger.debug("settings...{} ", ToStringBuilder.reflectionToString(settings, ToStringStyle.MULTI_LINE_STYLE));

		lgicSettings.setSettingsPohang(settings);

		HashMap<String, String> rlt = new HashMap<String, String>();
		rlt.put("resultCode", "OK");
		rlt.put("resultMsg", "성공하였습니다");

		return rlt;
	}

	@RequestMapping(value = "settings/gwangju", method = RequestMethod.GET)
	public @ResponseBody LgicSettings getGSettings() {
		logger.debug("settings... ");
		return lgicSettings.getSettingsGwangju();
	}

	@RequestMapping(value = "settings/gwangju", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> saveGSettings(@RequestBody LgicSettings settings) {
		logger.debug("settings...{} ", ToStringBuilder.reflectionToString(settings, ToStringStyle.MULTI_LINE_STYLE));

		lgicSettings.setSettingsGwangju(settings);

		HashMap<String, String> rlt = new HashMap<String, String>();
		rlt.put("resultCode", "OK");
		rlt.put("resultMsg", "성공하였습니다");

		return rlt;
	}

}
