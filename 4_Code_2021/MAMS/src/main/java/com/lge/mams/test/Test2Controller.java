package com.lge.mams.test;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lge.mams.mqtt.stat.MqttAgentStatManager;

@Controller
@RequestMapping("test2")
public class Test2Controller {

	@InitBinder
	public void initBinding(WebDataBinder b) {

	}

	@RequestMapping(method = RequestMethod.GET, value = "b")
	public String login(HttpSession session) {

		return "test/test";
	}

	@Autowired
	MqttAgentStatManager mqttAgents;

	@RequestMapping(method = RequestMethod.GET, value = "c")
	@ResponseBody
	public List<Map> testc(HttpSession session) {

		return mqttAgents.getStatList();
	}
}
