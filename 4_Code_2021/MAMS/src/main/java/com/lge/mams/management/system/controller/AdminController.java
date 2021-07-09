package com.lge.mams.management.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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

import com.lge.mams.common.util.CryptoUtil;
import com.lge.mams.common.util.MessageManage;
import com.lge.mams.common.web.controller.BaseController;
import com.lge.mams.constants.TilesSuffix;
import com.lge.mams.management.system.entity.AdminEntity;
import com.lge.mams.management.system.entity.AdminGroupEntity;
import com.lge.mams.management.system.service.AdminGroupService;
import com.lge.mams.management.system.service.AdminService;

/**
 * 관리자 Controller
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
@RequestMapping("/system/admin")
public class AdminController extends BaseController {

	/** Logger */
	/*private static final Logger logger = LoggerFactory.getLogger(AdminController.class);*/

	private final String PREFIX = "system/admin/";

	@Autowired private AdminService service;
	@Autowired private AdminGroupService adminGroupService;

	/**
	 * 관리자그룹 List를 돌려준다.
	 * @Mehtod Name : adminGroup
	 * @return
	 */
	@ModelAttribute("adminGroup")
	public List<AdminGroupEntity> adminGroup() {
		AdminGroupEntity adminGroupEntity = new AdminGroupEntity();
		return adminGroupService.getAllList(adminGroupEntity);
	}

	/**
	 * 관리자 정보 List 를 조회한다.
	 * @Mehtod Name : getList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"", "list"})
	public String list(@ModelAttribute AdminEntity entity, Model model) {

		model.addAttribute("list", service.getList(entity));
		model.addAttribute("paging", entity.getPaging());

		return PREFIX + "list" + TilesSuffix.DEFAULT;
	}

	/**
	 * 관리자 정보 등록 폼
	 * @Mehtod Name : insertForm
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String addForm(@ModelAttribute AdminEntity entity, Model model, HttpServletRequest request) {

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			model.addAttribute("message", flashMap.get("message"));
		}

		return PREFIX + "form" + TilesSuffix.DEFAULT;
	}

	/**
	 * 관리자 정보 등록
	 * @Mehtod Name : insert
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String add(@ModelAttribute AdminEntity entity,
			BindingResult result,
			Model model,
			RedirectAttributes attrs,
			HttpServletRequest request) {

		// Validator Error
		if (result.hasErrors()) {
		}

		// 중복 ID 체크
		AdminEntity chkEntity = new AdminEntity();
		chkEntity.setAdminId(entity.getAdminId());

		if (service.get(chkEntity) != null) {
			model.addAttribute("error", "true");
			model.addAttribute("adminEntity", entity);
			return PREFIX + "form" + TilesSuffix.DEFAULT;
		}

		// Encrypt
		String encrypt = CryptoUtil.encryptAdminPassword(entity.getAdminId(), entity.getAdminPw());
		entity.setAdminPw(encrypt);

		// 등록
		service.insert(entity);

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("message", MessageManage.getMessage("save.success"));

		// Redirect Params
		attrs.addAttribute("adminIdSq", entity.getAdminIdSq());

		return "redirect:update";
	}

	/**
	 * 관리자 정보 수정 폼
	 * @Mehtod Name : updateForm
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(@ModelAttribute AdminEntity entity, Model model, HttpServletRequest request) {

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			model.addAttribute("message", flashMap.get("message"));
		}

		model.addAttribute("adminEntity", service.get(entity));

		return PREFIX + "update" + TilesSuffix.DEFAULT;
	}

	/**
	 * 관리자 정보 수정
	 * @Mehtod Name : update
	 * @param entity
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute AdminEntity entity,
			BindingResult result,
			Model model,
			RedirectAttributes attrs,
			HttpServletRequest request) {

		// Validator Error
		if (result.hasErrors()) {
		}

		// Encrypt
		if (StringUtils.isNotEmpty(entity.getAdminPw())) {
			String encrypt = CryptoUtil.encryptAdminPassword(entity.getAdminId(), entity.getAdminPw());
			entity.setAdminPw(encrypt);
		}

		// Update
		entity = service.update(entity);

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("message", MessageManage.getMessage("save.success"));

		// Redirect Params
		attrs.addAttribute("adminIdSq", entity.getAdminIdSq());

		return "redirect:update";
	}

	/**
	 * 관리자 정보 수정 폼 (팝업)
	 * @Mehtod Name : updateFormPop
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updatePop", method = RequestMethod.GET)
	public String updateFormPop(@ModelAttribute AdminEntity entity, Model model, HttpServletRequest request) {

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			model.addAttribute("message", flashMap.get("message"));
		}

		model.addAttribute("adminEntity", service.get(entity));

		return PREFIX + "updatePop" + TilesSuffix.EMPTY;
	}

	/**
	 * 관리자 정보 수정 (팝업)
	 * @Mehtod Name : updatePop
	 * @param entity
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updatePop", method = RequestMethod.POST)
	public String updatePop(@ModelAttribute AdminEntity entity,
			BindingResult result,
			Model model,
			RedirectAttributes attrs,
			HttpServletRequest request) {

		// Validator Error
		if (result.hasErrors()) {
		}

		// Encrypt
		if (StringUtils.isNotEmpty(entity.getAdminPw())) {
			String encrypt = CryptoUtil.encryptAdminPassword(entity.getAdminId(), entity.getAdminPw());
			entity.setAdminPw(encrypt);
		}

		// Update
		entity = service.update(entity);

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("message", MessageManage.getMessage("save.success"));

		// Redirect Params
		attrs.addAttribute("adminIdSq", entity.getAdminIdSq());

		return "forward:/logout";
	}
}