package com.lge.mams.mqtt.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lge.mams.agentif.model.AgentResult;
import com.lge.mams.jpa.impl.TbEventInfoRepository;
import com.lge.mams.jpa.model.TbEventInfo;
import com.lge.mams.mqtt.MqttHandler;
import com.lge.mams.mqtt.scheduler.MqttSchedulerManager;
import com.lge.mams.mqtt.stat.MqttAgentStatManager;
import com.lge.mams.util.JsonUtil;
import com.lge.mams.websocket.WsAgentIfHandler;

@Controller
@RequestMapping("api/mqtt")
public class ApiMqttController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MqttHandler mqtt;

	@Autowired
	MqttAgentStatManager mqttAgents;

	@Autowired
	TbEventInfoRepository repoEventInfo;

	@Autowired
	MqttSchedulerManager mqttSchedule;

	@Autowired
	private WsAgentIfHandler wsAgent;

	@RequestMapping(method = RequestMethod.POST, value = "hiljson")
	@ResponseBody
	public AgentResult hiljson(@RequestBody() String json) {

		//
		JsonUtil jutil = new JsonUtil(json);

		String topic = jutil.treeToStr("topic");
		Long eventSn = Long.valueOf(jutil.treeToStr("eventSn"));

		// update to
		try {
			TbEventInfo evt = repoEventInfo.findOne(eventSn);
			if (evt != null) {
				evt.setConfirmYn("Y");
				repoEventInfo.save(evt);
			}
		} catch (Exception ex) {
			logger.error("EX", ex);
		}

		wsAgent.pushClearEvent(eventSn);

		String jsonmsg = jutil.treeToJson("body");

		logger.info("hiljson => topic : {}, eventSn : {}, msg :  {}", topic, eventSn, jsonmsg);
		mqtt.publish(topic, jsonmsg);

		return AgentResult.success("성공 하였습니다");
	}

	@RequestMapping(method = RequestMethod.POST, value = "publishjson")
	@ResponseBody
	public AgentResult publishJson(@RequestBody() String json) {

		// { topic:'', body:{} }
		JsonUtil jutil = new JsonUtil(json);

		String topic = jutil.treeToStr("topic");
		String jsonmsg = jutil.treeToJson("body");

		logger.info("topic : {}, msg :  {}", topic, jsonmsg);
		mqtt.publish(topic, jsonmsg);

		return AgentResult.success("성공 하였습니다");
	}

	@RequestMapping(method = RequestMethod.GET, value = "mqttagentstats")
	@ResponseBody
	public List<Map> mqttagentstats() {
		return mqttAgents.getStatList();
	}

	@RequestMapping(method = RequestMethod.GET, value = "mqttoldschedule")
	@ResponseBody
	public String mqttoldschedule() {
		return mqttSchedule.getOldSchedule();
	}

	@RequestMapping(method = RequestMethod.GET, value = "mqttnewschedule")
	@ResponseBody
	public String mqttnewschedule() {
		return mqttSchedule.getNewSchedule();
	}

}
