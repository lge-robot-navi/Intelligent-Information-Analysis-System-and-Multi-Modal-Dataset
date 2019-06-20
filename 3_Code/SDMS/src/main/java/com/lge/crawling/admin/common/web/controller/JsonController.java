package com.lge.crawling.admin.common.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lge.crawling.admin.common.util.JsonDataResponse;
import com.lge.crawling.admin.common.web.entity.JsonEntity;
import com.lge.crawling.admin.constants.TilesSuffix;

@Controller
@RequestMapping("/api/mntr")
public class JsonController {

	@RequestMapping("")
	public String main() {
		
//		return "websocket/wsmain" + TilesSuffix.EMPTY;
		return "websocket/wsmain";
	}
	
	@RequestMapping("connect/{agentId}/{agentType}/{agentIp}/{videoStreamingPort}/{audioStreamingPort}")
	public @ResponseBody String connect(@PathVariable String agentId, @PathVariable int agentType, 
			@PathVariable String agentIp, @PathVariable int videoStreamingPort, @PathVariable int audioStreamingPort) {
		
		JsonEntity vo = new JsonEntity();
		vo.setMsgType(1000);
		vo.setAgentId(agentId);
		vo.setAgentType(agentType);
		vo.setAgentIp(agentIp);
		vo.setVideoStreamingPort(videoStreamingPort);
		vo.setAudioStreamingPort(audioStreamingPort);
		
		String resStr = JsonDataResponse.cmd2000(vo);
		
		return resStr;
	}
}
