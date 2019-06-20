package com.lge.mams.management.system.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lge.mams.common.util.DateSupportUtil;
import com.lge.mams.common.web.controller.BaseController;
import com.lge.mams.common.web.entity.AbstractPage;
import com.lge.mams.common.web.entity.PagingValue;
import com.lge.mams.constants.TilesSuffix;
import com.lge.mams.jpa.impl.TaCodeInfoRepository;
import com.lge.mams.jpa.impl.TbEventInfoRepository;
import com.lge.mams.jpa.model.TaCodeInfo;
import com.lge.mams.jpa.model.TbEventInfo;
import com.lge.mams.util.DateUtil;

/**
 * Recording Controller
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
@RequestMapping("/system/event")
public class EventController extends BaseController {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);

	private final String PREFIX = "system/event/";


	@Autowired
	TbEventInfoRepository repoEvent;
	
	@Autowired
	TaCodeInfoRepository repoCode;


	/**
	 * Recording List 를 조회한다.
	 * @Mehtod Name : getList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"", "list"})
	public String list(@ModelAttribute AbstractPage entity, HttpSession session, Model model) throws Exception{
		logger.debug("list");
		
	
		if (StringUtils.isEmpty(entity.getStartDt()) && StringUtils.isEmpty(entity.getEndDt())) {
    		entity.setStartDt(DateSupportUtil.getDefaultStart());
    		entity.setEndDt(DateSupportUtil.getDefaultEnd());
    	}

		Sort sort = new Sort(Sort.Direction.DESC, "eventSn");
		Pageable page = new PageRequest(entity.getPage()-1, entity.getPageSize(), sort);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = sdf.parse(entity.getStartDt());
		Date end = sdf.parse(entity.getEndDt());
		end = DateUtil.addDay(end, 1);
		

		
		logger.debug("START:" + ToStringBuilder.reflectionToString(start.toString()));
		logger.debug("END:" + ToStringBuilder.reflectionToString(end.toString()));
		
		
		Page<TbEventInfo> result = repoEvent.findByEventDtBetween(page,start, end);

		
		PagingValue paging = new PagingValue(entity.getPageSize(), entity.getPage(), (int)result.getTotalElements());
		
		List<TaCodeInfo> codes = repoCode.findByCdgrpCd("TA011");
		
		for(TbEventInfo info:result.getContent()) {
			String id = (new Integer(info.getAbnormalId())).toString();
			//codes.stream().filter( code -> code.getCodeCd().equals(id)).forEach(code -> info.setAbnormal(code));
			for(TaCodeInfo code: codes) {
				if( code.getCodeCd().equals(id)) {
					info.setAbnormal(code);
				}
			}
		}
		
		model.addAttribute("list", result.getContent());
		model.addAttribute("paging", paging);
		
		return PREFIX + "event-list" + TilesSuffix.EMPTY;
	}
}