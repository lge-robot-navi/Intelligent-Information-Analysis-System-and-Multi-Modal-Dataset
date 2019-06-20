package com.lge.mams.management.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.lge.mams.common.util.MessageManage;
import com.lge.mams.common.web.controller.BaseController;
import com.lge.mams.constants.TilesSuffix;
import com.lge.mams.management.system.entity.CodeGroupEntity;
import com.lge.mams.management.system.service.CodeGroupService;

/**
 * 코드 그룹 Controller
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
@RequestMapping("/system/code-group")
public class CodeGroupController extends BaseController {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(CodeGroupController.class);

	private final String PREFIX = "system/code-group/";

	@Autowired private CodeGroupService service;
//	@Autowired private CodeService codeService;

	/**
	 * 공통코드그룹 리스트 조회
	 * @Mehtod Name : groupList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"", "list"})
	public String groupList(@ModelAttribute CodeGroupEntity entity, Model model) {

		model.addAttribute("list", service.getList(entity));
		model.addAttribute("paging", entity.getPaging());

		return PREFIX + "list" + TilesSuffix.DEFAULT;
	}

	/**
	 * 공통코드그룹 JSON
	 * @return
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<CodeGroupEntity> json() {
		return service.getAllList(new CodeGroupEntity());
	}

	/**
	 * 공통 코드그룹 등록 폼
	 * @Mehtod Name : groupForm
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String groupAddForm(@ModelAttribute CodeGroupEntity entity, Model model, HttpServletRequest request) {

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			model.addAttribute("message", flashMap.get("message"));
		}

		// 정렬순서 다음 번호를 가져와 설정한다.
		entity.setOrderNo(service.getNextOrderNo(entity));

		model.addAttribute("codeGroupEntity", entity);

		return PREFIX + "form" + TilesSuffix.DEFAULT;
	}

	/**
	 * 공통 코드그룹 등록
	 * @Mehtod Name : groupAdd
	 * @param entity
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String groupAdd(@ModelAttribute CodeGroupEntity entity,
			BindingResult result,
			Model model,
			RedirectAttributes attrs,
			HttpServletRequest request) {

		// Validator Error
		if (result.hasErrors()) {
		}

		try {

			service.insert(entity);

		} catch (DuplicateKeyException e) {

			logger.error("공통코드그룹 등록 중 예외 발생 : {}", e.getMessage());

			Object[] obj = { e.getMessage() };
			model.addAttribute("errorMessage", MessageManage.getMessage("save.duplicate", obj));

			return PREFIX + "form" + TilesSuffix.DEFAULT;
		}

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("message", MessageManage.getMessage("save.success"));

		// Redirect Params
		attrs.addAttribute("cdgrpCd", entity.getCdgrpCd());

		return "redirect:update";
	}

	/**
	 * 공통 코드그룹 수정 폼
	 * @Mehtod Name : groupUpdateForm
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String groupUpdateForm(@ModelAttribute CodeGroupEntity entity, Model model, HttpServletRequest request) {

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			model.addAttribute("message", flashMap.get("message"));
		}

		model.addAttribute("codeGroupEntity", service.get(entity));

		return PREFIX + "update" + TilesSuffix.DEFAULT;
	}

	/**
	 * 공통 코드그룹 수정
	 * @Mehtod Name : groupUpdate
	 * @param entity
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String groupUpdate(@ModelAttribute CodeGroupEntity entity,
			BindingResult result,
			RedirectAttributes attrs,
			HttpServletRequest request) {

		// Validator Error
		if (result.hasErrors()) {
		}

		entity = service.update(entity);

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("message", MessageManage.getMessage("save.success"));

		// Redirect Params
		attrs.addAttribute("cdgrpCd", entity.getCdgrpCd());

		return "redirect:update";
	}
}