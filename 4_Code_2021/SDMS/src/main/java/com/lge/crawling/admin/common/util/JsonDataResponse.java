package com.lge.crawling.admin.common.util;

import com.lge.crawling.admin.common.web.entity.JsonEntity;

public class JsonDataResponse {

	public static String cmd2000(JsonEntity vo) {
		
		StringBuilder sb = new StringBuilder();

		// TODO: 응답처리
		
		String[] resultCode = {"000","E01","E02","E03"};
		int nRandCode = (int)Math.floor(Math.random() * 4);
		
		sb.append("{");
		sb.append("\"msgType\": \"2000\",");
		sb.append("\"agentId\": \""+vo.getAgentId()+"\",");
		sb.append("\"resultCode\": \""+resultCode[nRandCode]+"\"");
		sb.append("}");
		
		return sb.toString();
	}
}
