package com.lge.mams.api.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lge.mams.jpa.impl.TaCodeInfoRepository;
import com.lge.mams.jpa.model.TaCodeInfo;

@Controller
@RequestMapping("api")
public class ApiController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	TaCodeInfoRepository repoCodeInfo;
	
	@RequestMapping("codeInfo")
	public @ResponseBody List<TaCodeInfo> getCodeInfo(@RequestParam String cdgrpCd){
		logger.debug("code Info : {}", cdgrpCd);
		List<TaCodeInfo> list;
		
		//list = repoCodeInfo.findByCdgrpCd(cdgrpCd);
		list = repoCodeInfo.findByCdgrpCdOrderByOrderNoAsc(cdgrpCd);
		
		return list;
	}
	
	
}
