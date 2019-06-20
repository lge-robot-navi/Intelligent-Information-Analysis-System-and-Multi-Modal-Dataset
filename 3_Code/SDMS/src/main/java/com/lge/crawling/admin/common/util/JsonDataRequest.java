package com.lge.crawling.admin.common.util;

import com.lge.crawling.admin.common.web.entity.JsonEntity;

public class JsonDataRequest {

	public static String cmd1000(JsonEntity vo) {
		
		StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append("\"msgType\": \"1000\",");
		sb.append("\"agentId\": \""+vo.getAgentId()+"\",");
		sb.append("\"agentType\": \""+vo.getAgentType()+"\",");
		sb.append("\"agentIp\": \""+vo.getAgentIp()+"\",");
		sb.append("\"videoStreamingPort\": \""+vo.getVideoStreamingPort()+"\",");
		sb.append("\"audioStreamingPort\": \""+vo.getAudioStreamingPort()+"\"");
		sb.append("}");
		
		return sb.toString();
	}
}
