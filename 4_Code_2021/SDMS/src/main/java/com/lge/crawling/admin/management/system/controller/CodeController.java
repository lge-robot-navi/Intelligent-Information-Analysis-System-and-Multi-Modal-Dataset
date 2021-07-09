package com.lge.crawling.admin.management.system.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lge.crawling.admin.common.web.controller.BaseController;
import com.lge.crawling.admin.management.system.service.CodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.lge.crawling.admin.common.util.MessageManage;
import com.lge.crawling.admin.constants.TilesSuffix;
import com.lge.crawling.admin.management.system.entity.CodeEntity;
import net.sf.json.JSONObject;

/**
 * 공통코드 Controller
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
@RequestMapping("/system/code-group/code")
public class CodeController extends BaseController {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(CodeController.class);

	private final String PREFIX = "system/code-group/code/";

	@Autowired private CodeService service;

	/**
	 * 공통코드 List 를 조회한다.
	 * @Mehtod Name : getList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"", "list"})
	public String list(@ModelAttribute CodeEntity entity, Model model) {

		model.addAttribute("list", service.getAllList(entity));

		return PREFIX + "list" + TilesSuffix.EMPTY;
	}

	/**
	 * 공통코드 등록 폼
	 * @Mehtod Name : insertForm
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String addForm(@ModelAttribute CodeEntity entity, Model model, HttpServletRequest request) {

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			model.addAttribute("message", flashMap.get("message"));
		}

		return PREFIX + "form" + TilesSuffix.EMPTY;
	}

	/**
	 * 공통코드 등록
	 * @Mehtod Name : insert
	 * @param entity
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject add(@ModelAttribute CodeEntity entity, BindingResult bindingResult) {

		JSONObject result = new JSONObject();
		result.put("resultCd", "fail");
		result.put("resultMsg", MessageManage.getMessage("save.fail"));

		// Validator Error
		if (bindingResult.hasErrors()) {
		}

		try {

			service.insert(entity);

		} catch (DuplicateKeyException e) {

			logger.error("공통코드 등록 중 예외 발생 : {}", e.getMessage());

			Object[] obj = { e.getMessage() };

			result.put("resultCd", "fail");
			result.put("resultMsg", MessageManage.getMessage("save.duplicate", obj));

			return result;
		}

		result.put("resultCd", "success");
		result.put("resultMsg", MessageManage.getMessage("save.success"));

		return result;
	}

	/**
	 * 공통코드 수정 폼
	 * @Mehtod Name : update
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(@ModelAttribute CodeEntity entity, Model model, HttpServletRequest request) {

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			model.addAttribute("message", flashMap.get("message"));
		}

		model.addAttribute("codeEntity", service.get(entity));

		return PREFIX + "update" + TilesSuffix.EMPTY;
	}

	/**
	 * 공통코드 수정
	 * @Mehtod Name : update
	 * @param entity
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject update(@ModelAttribute CodeEntity entity, BindingResult bindingResult) {

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