package com.lge.mams.management.system.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.lge.mams.common.util.DateSupportUtil;
import com.lge.mams.common.util.HttpSessionUtils;
import com.lge.mams.common.util.MessageManage;
import com.lge.mams.common.web.controller.BaseController;
import com.lge.mams.common.web.entity.Result;
import com.lge.mams.constants.TilesSuffix;
import com.lge.mams.management.system.entity.RecordingEntity;
import com.lge.mams.management.system.service.RecordingService;

import net.sf.json.JSONObject;

/**
 * Recording Controller
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
@RequestMapping("/system/recording")
public class RecordingController extends BaseController {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(RecordingController.class);

	private final String PREFIX = "system/recording/";

	@Autowired private RecordingService service;

	/**
	 * Recording List 를 조회한다.
	 * @Mehtod Name : getList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"", "list"})
	public String list(@ModelAttribute RecordingEntity entity, HttpSession session, Model model) {

		if(!HttpSessionUtils.isLoginUser(session)) {
    		return "redirect:/login";
		} 
		
		if (StringUtils.isEmpty(entity.getStartDt()) && StringUtils.isEmpty(entity.getEndDt())) {
    		entity.setStartDt(DateSupportUtil.getDefaultStart());
    		entity.setEndDt(DateSupportUtil.getDefaultEnd());
    	}

		model.addAttribute("list", service.getList(entity));
		model.addAttribute("paging", entity.getPaging());
		
		return PREFIX + "list" + TilesSuffix.EMPTY;
	}

	/**
	 * Recording 등록 폼
	 * @Mehtod Name : insertForm
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String addForm(@ModelAttribute RecordingEntity entity, Model model, HttpServletRequest request) {

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			model.addAttribute("message", flashMap.get("message"));
		}

		return PREFIX + "form" + TilesSuffix.EMPTY;
	}

	/**
	 * Recording 등록
	 * @Mehtod Name : insert
	 * @param entity
	 * @param result
	 * @param model
	 * @return
	 */
//	@RequestMapping(value = "insert", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public String add(HttpServletRequest request) {
//
//		String str = request.getParameter("obj");
//		str = str.replaceAll("&quot;", "\"");
//		str = "{\"agentInfo\":" + str + "}";
//		logger.info("agentInfo {}", str);
//
//		try {
//			
////			service.insert(entity);
//			logger.info("recording db insert");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "fail"; 
//		}
//		return "success"; 
//	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(@RequestBody Map<Object, String> body) {
  	  
		String recFileName = body.get("recFileName").toString();
		String recFilePath = body.get("recFilePath").toString();
		String recAgentId = body.get("recAgentId").toString();
		
		logger.debug("recFileName: "+recFileName);
		logger.debug("recFilePath: "+recFilePath);
		logger.debug("recAgentId: "+recAgentId);

		try {
			
			RecordingEntity recEntity = new RecordingEntity();
			recEntity.setRecAgentId(recAgentId);
			recEntity.setRecFileName(recFileName);
			recEntity.setRecFilePath(recFilePath);
			
			service.insert(recEntity);

		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(); 
		}
		return Result.success();
	}

	/**
	 * Recording 수정 폼
	 * @Mehtod Name : update
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(@ModelAttribute RecordingEntity entity, Model model, HttpServletRequest request) {

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			model.addAttribute("message", flashMap.get("message"));
		}

		model.addAttribute("codeEntity", service.get(entity));

		return PREFIX + "update" + TilesSuffix.EMPTY;
	}

	/**
	 * Recording 수정
	 * @Mehtod Name : update
	 * @param entity
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject update(@ModelAttribute RecordingEntity entity, BindingResult bindingResult) {

		JSONObject result = new JSONObject();
		result.put("resultCd", "fail");
		result.put("resultMsg", MessageManage.getMessage("save.fail"));

		// Validator Error
		if (bindingResult.hasErrors()) {
		}

		entity = service.update(entity);

		result.put("resultCd", "success");
		result.put("resultMsg", MessageManage.getMessage("save.success"));

		return result;
	}
}