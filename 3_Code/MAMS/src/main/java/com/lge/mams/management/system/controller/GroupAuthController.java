package com.lge.mams.management.system.controller;

import com.lge.mams.common.util.MessageManage;
import com.lge.mams.common.web.controller.BaseController;
import com.lge.mams.constants.TilesSuffix;
import com.lge.mams.management.system.entity.GroupAuthEntity;
import com.lge.mams.management.system.entity.GroupAuthFormEntity;
import com.lge.mams.management.system.service.GroupAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

/**
 * 그룹별 권한관리 Controller
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
@RequestMapping("/system/admin-group/group-auth")
public class GroupAuthController extends BaseController {

	private final String PREFIX = "system/group-auth/";

	@Autowired private GroupAuthService service;

	/**
	 * 그룹별 권한 관리 리스트
	 * @Mehtod Name : getList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"", "list"})
	public String list(@ModelAttribute GroupAuthEntity entity, Model model) {
		model.addAttribute("list", service.getList(entity));
		model.addAttribute("paging", entity.getPaging());
		return PREFIX + "list" + TilesSuffix.DEFAULT;
	}

	/**
	 * 그룹별 권한 관리 수정 폼
	 * @Mehtod Name : update
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "update")
	public String updateForm(@ModelAttribute GroupAuthEntity entity, Model model) {
		model.addAttribute("groupAuthEntity", service.get(entity) );
		return PREFIX + "update" + TilesSuffix.DEFAULT;
	}

	/**
	 * 그룹별 권한 관리 수정
	 * @Mehtod Name : update
	 * @param entity
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "update")
	public String update(@ModelAttribute GroupAuthEntity entity, BindingResult result, Model model) {
		// Validator Error
		if (result.hasErrors()) {
		}
		entity = service.update(entity);
		model.addAttribute("message", MessageManage.getMessage("save.success"));
		return PREFIX + "update" + TilesSuffix.DEFAULT;
	}

	/**
	 * 권한 프로그램 목록
	 * @Mehtod Name : getListProgram
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "program")
	public String listProgram(@ModelAttribute GroupAuthFormEntity entity, Model model) {

		entity = service.getGroupAuthProgramList(entity);
		model.addAttribute("groupAuthFormEntity", entity);

		return PREFIX + "program" + TilesSuffix.EMPTY;
	}

	/**
	 * 권한 등록/수정 처리
	 * @Mehtod Name : updateProgram
	 * @param entity
	 * @param model
	 * @param available
	 * @param authSel
	 * @param authIns
	 * @param authUpd
	 * @param authDel
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "program")
	@ResponseBody
	public JSONObject updateProgram(@ModelAttribute GroupAuthFormEntity entity,
			Model model) {

		JSONObject result = new JSONObject();

		result.put("resultCd", "fail");
		result.put("resultMsg", MessageManage.getMessage("save.fail"));

		service.updateAuthProgram(entity);

		result.put("resultCd", "success");
		result.put("resultMsg", MessageManage.getMessage("save.success"));

		return result;
	}
}