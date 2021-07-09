package com.lge.crawling.admin.management.system.controller;

import com.lge.crawling.admin.common.util.MessageManage;
import com.lge.crawling.admin.common.web.controller.BaseController;
import com.lge.crawling.admin.constants.TilesSuffix;
import com.lge.crawling.admin.management.system.entity.ProgramEntity;
import com.lge.crawling.admin.management.system.entity.ProgramFancytreeEntity;
import com.lge.crawling.admin.management.system.service.ProgramFancyreeService;
import com.lge.crawling.admin.management.system.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

/**
 * 프로그램 Controller (메뉴관리)
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
@RequestMapping("/system/program")
public class ProgramController extends BaseController {

	private final String PREFIX = "system/program/";

	@Autowired private ProgramService service;
	@Autowired private ProgramFancyreeService programFancytreeService;

	/**
	 * 메인 메뉴 리스트 조회
	 * @Mehtod Name : getList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"", "list"})
	public String list(@ModelAttribute ProgramEntity entity, Model model) {

		//model.addAttribute("list", service.getListTotal(entity));

		return PREFIX + "list" + TilesSuffix.DEFAULT;
	}

	/**
	 * 메뉴리스트
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String json(@ModelAttribute ProgramFancytreeEntity entity) {
		Gson gson = new Gson();
		return gson.toJson(programFancytreeService.getAllList(entity));
	}

	/**
	 * 메인 메뉴 등록 폼
	 * @Mehtod Name : form
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String form(@ModelAttribute ProgramEntity entity, Model model) {

		model.addAttribute("programEntity", entity);

		//return PREFIX + "form" + TilesSuffix.EMPTY;
		return PREFIX + "form";
	}

	/**
	 * 메인 메뉴 등록
	 * @Mehtod Name : insert
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> add(@ModelAttribute ProgramEntity entity) {

		// Result
		JSONObject result = new JSONObject();

		// insert
		service.insert(entity);

		// entity
		String pgmId = entity.getPgmId();
		entity = new ProgramEntity();
		entity.setPgmId(pgmId);

		// get
		entity = service.get(entity);
		result.put("programEntity", entity);

		// message
		result.put("message", MessageManage.getMessage("save.success"));

//		return PREFIX + "form" + TilesSuffix.EMPTY;
		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
	}

	/**
	 * 메인 메뉴 수정 폼
	 * @Mehtod Name : updateForm
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(@ModelAttribute ProgramEntity entity, Model model) {

		model.addAttribute("programEntity", service.get(entity));

		//return PREFIX + "update" + TilesSuffix.EMPTY;
		return PREFIX + "update";
	}

	/**
	 * 메인 메뉴 수정
	 * @Mehtod Name : update
	 * @param entity
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update(@ModelAttribute ProgramEntity entity) {

		// Result
		JSONObject result = new JSONObject();

		// update
		entity = service.update(entity);

		// entity
		result.put("programEntity", entity);

		// success message
		result.put("message", MessageManage.getMessage("save.success"));

		//return PREFIX + "update" + TilesSuffix.EMPTY;
		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
	}

	/**
	 * 프로그램 정렬순서 변경
	 * @Mehtod Name : updateRank
	 * @param entity
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update-rank", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONObject updateRank(@RequestParam(value = "pgmIds") String[] pgmIds,
			@RequestParam(value = "upperPgmIds") String[] upperPgmIds,
			@RequestParam(value = "rankNos") String[] rankNos,
			@ModelAttribute ProgramEntity entity,
			BindingResult result, Model model) {

		// Validate Error
		if (result.hasErrors()) {
		}

		int count = service.updateRank(entity, pgmIds, upperPgmIds, rankNos);

		JSONObject res = new JSONObject();
		res.put("count", count);

		return res;
	}

	/**
	 * 서브 메뉴 등록 폼
	 * @Mehtod Name : form
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "sub-form", method = RequestMethod.GET)
	public String subForm(@ModelAttribute ProgramEntity entity, Model model) {

		model.addAttribute("mainMenu", service.getMainList());

		return PREFIX + "sub-form" + TilesSuffix.DEFAULT;
	}

	/**
	 * 서브 메뉴 등록
	 * @Mehtod Name : insert
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "sub-form", method = RequestMethod.POST)
	public String subAdd(@ModelAttribute ProgramEntity entity, BindingResult result, Model model) {

		// Validate Error
		if (result.hasErrors()) {
		}

		service.insert(entity);

		model.addAttribute("message", MessageManage.getMessage("save.success"));

		return PREFIX + "sub-form" + TilesSuffix.DEFAULT;
	}

	/**
	 * 서브 메뉴 수정 폼
	 * @Mehtod Name : updateForm
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "sub-update", method = RequestMethod.GET)
	public String subUpdateForm(@ModelAttribute ProgramEntity entity, Model model) {

		model.addAttribute("mainMenu", service.getMainList());
		model.addAttribute("programEntity", service.get(entity));

		return PREFIX + "sub-update" + TilesSuffix.DEFAULT;
	}

	/**
	 * 서브 메뉴 수정
	 * @Mehtod Name : update
	 * @param entity
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "sub-update", method = RequestMethod.POST)
	public String subUpdate(@ModelAttribute ProgramEntity entity, BindingResult result, Model model) {

		// Validate Error
		if (result.hasErrors()) {
		}

		entity = service.update(entity);

		model.addAttribute("message", MessageManage.getMessage("save.success"));

		return PREFIX + "sub-update" + TilesSuffix.DEFAULT;
	}
}