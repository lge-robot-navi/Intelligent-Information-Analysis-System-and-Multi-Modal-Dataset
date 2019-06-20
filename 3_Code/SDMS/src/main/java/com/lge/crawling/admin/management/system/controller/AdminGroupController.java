package com.lge.crawling.admin.management.system.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lge.crawling.admin.common.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.lge.crawling.admin.common.util.MessageManage;
import com.lge.crawling.admin.constants.TilesSuffix;
import com.lge.crawling.admin.management.system.entity.AdminGroupEntity;
import com.lge.crawling.admin.management.system.service.AdminGroupService;

/**
 * 관리자 그룹 Controller
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
@RequestMapping("/system/admin-group")
public class AdminGroupController extends BaseController {

	/** Logger */
	/*private static final Logger logger = LoggerFactory.getLogger(AdminGroupController.class);*/

	private final String PREFIX = "system/admin-group/";

	@Autowired private AdminGroupService service;

	/**
	 * 관리자 그룹 리스트 조회
	 * @method getList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "", "list" })
	public String list(@ModelAttribute AdminGroupEntity entity, Model model) {

		model.addAttribute("list", service.getList(entity));
		model.addAttribute("paging", entity.getPaging());

		return PREFIX + "list" + TilesSuffix.DEFAULT;
	}

	/**
	 * 관리자 그룹 등록 폼
	 * @method addForm
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String addForm(@ModelAttribute AdminGroupEntity entity, Model model, HttpServletRequest request) {
		return PREFIX + "form" + TilesSuffix.DEFAULT;
	}

	/**
	 * 관리자 그룹 등록
	 * @method add
	 * @param entity
	 * @param result
	 * @param model
	 * @param attrs
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String add(@ModelAttribute AdminGroupEntity entity,
			BindingResult result,
			Model model,
			RedirectAttributes attrs,
			HttpServletRequest request) {

		// Validator Error
		if (result.hasErrors()) {
		}

		// insert
		service.insert(entity);

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("message", MessageManage.getMessage("save.success"));

		// Redirect Params
		attrs.addAttribute("adminGrpId", entity.getAdminGrpId());

		return "redirect:update";
	}

	/**
	 * 관리자 그룹 수정 폼
	 * @method updateForm
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(@ModelAttribute AdminGroupEntity entity, Model model, HttpServletRequest request) {

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			model.addAttribute("message", flashMap.get("message"));
		}

		model.addAttribute("adminGroupEntity", service.get(entity));

		return PREFIX + "update" + TilesSuffix.DEFAULT;
	}

	/**
	 * 관리자 그룹 수정
	 * @method update
	 * @param entity
	 * @param result
	 * @param model
	 * @param attrs
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute AdminGroupEntity entity,
			BindingResult result,
			Model model,
			RedirectAttributes attrs,
			HttpServletRequest request) {

		// Validator Error
		if (result.hasErrors()) {
		}

		// update
		entity = service.update(entity);

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("message", MessageManage.getMessage("save.success"));

		// Redirect Params
		attrs.addAttribute("adminGrpId", entity.getAdminGrpId());

		return "redirect:update";
	}
}
