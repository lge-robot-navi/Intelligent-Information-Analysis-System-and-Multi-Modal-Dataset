package com.lge.crawling.admin.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lge.crawling.admin.common.util.Config;
import com.lge.crawling.admin.jpa.impl.TaCodeInfoRepository;
import com.lge.crawling.admin.jpa.impl.TbAgentRepository;
import com.lge.crawling.admin.jpa.model.TaCodeInfo;

@Controller
@RequestMapping("/test")
public class TestController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	TbAgentRepository repoAgent;
	
	@Autowired
	TaCodeInfoRepository repoCodeInfo;
	
	@RequestMapping(method = RequestMethod.GET, value = "a")
	@ResponseBody public Object a() throws FileNotFoundException, IOException {

		logger.debug("get a");
		
		Iterable<TaCodeInfo> l = repoCodeInfo.findAll();
		
		for(TaCodeInfo code : l) {
			logger.debug(ToStringBuilder.reflectionToString(code,ToStringStyle.MULTI_LINE_STYLE));
		}

		return l;

		//return "test/test";
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "commonconfig")
	public String commonconfig() throws FileNotFoundException, IOException {

		logger.debug("get config {}", Config.getCommon().getProperty("CONFIGID"));

		return "test/test";
	}
}
