package com.lge.crawling.admin.management.imageTaggingDic.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.lge.crawling.admin.common.util.MessageManage;
import com.lge.crawling.admin.common.web.controller.BaseController;
import com.lge.crawling.admin.constants.TilesSuffix;
import com.lge.crawling.admin.management.imageTaggingDic.entity.TaggingDicEntity;
import com.lge.crawling.admin.management.imageTaggingDic.service.TaggingDicService;

import net.sf.json.JSONObject;

/**
 * 이미지 Tagging Dictionary Controller (메뉴관리)
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
@RequestMapping("/image/tagging-dic")
public class ImageTaggingDicController extends BaseController {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(ImageTaggingDicController.class);

	private final String PREFIX = "image/tagging-dic/";

	@Autowired private TaggingDicService service;

	/**
	 * 이미지 태깅 사전 메뉴 리스트 조회
	 * @Mehtod Name : getList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"", "list"})
	public String list(@ModelAttribute TaggingDicEntity entity, Model model) {

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
	public String json(@ModelAttribute TaggingDicEntity entity) {
		Gson gson = new Gson();
		return gson.toJson(service.getAllList(entity));
	}

	/**
	 * 이미지 정보 List 를 조회한다.
	 * @Mehtod Name : getImageList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "imageFileDicList")
	public String getImageFileDicList(@ModelAttribute TaggingDicEntity entity, Model model) {

		model.addAttribute("list", service.getImageDicList(entity));
		model.addAttribute("paging", entity.getPaging());

		return PREFIX + "image-dic-list" + TilesSuffix.TMPL;
	}

    /**
	 * 이미지 파일 태깅 사전 전체 다운로드
	 * @method downloadAllImageFileTaggingDic
	 * @param entity
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "download-all")
    public void downloadAllImageFileTaggingDic(HttpServletResponse response, HttpServletRequest reques) {
    	try {

    		TaggingDicEntity taggingDicEntity = new TaggingDicEntity();
	    	List<TaggingDicEntity> dicList = service.getAllList(taggingDicEntity);

	    	response.setHeader("Content-Disposition", String.format("attachment; filename=imageTaggginDic.json"));

			String json = new Gson().toJson(dicList);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    /**
     * 이미지 파일 태깅 사전 부분 다운로드
     * @method downloadImageFileTaggingDic
     * @param entity
     * @param model
     * @return
     */
    @RequestMapping(value = "download")
    public void downloadImageFileTaggingDic(@RequestParam(value="checkArray[]") List<String> arrayParams, HttpServletResponse response, HttpServletRequest reques) {
    	try {

    		TaggingDicEntity taggingDicEntity = new TaggingDicEntity();
    		taggingDicEntity.setImageTaggingDicIdSqList(arrayParams);
    		List<TaggingDicEntity> dicList = service.getImageDicDownloodList(taggingDicEntity);

    		response.setHeader("Content-Disposition", String.format("attachment; filename=imageTaggginDic.json"));

    		String json = new Gson().toJson(dicList);
    		response.setContentType("application/json");
    		response.setCharacterEncoding("UTF-8");
    		response.getWriter().write(json);

    	} catch (IOException e) {
    		e.printStackTrace();
    	}

    }

	/**
	 * 메인 메뉴 등록 폼
	 * @Mehtod Name : form
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String form(@ModelAttribute TaggingDicEntity entity, Model model) {

		model.addAttribute("taggingDicEntity", entity);

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
	public ResponseEntity<String> add(@ModelAttribute TaggingDicEntity entity) {

		// Result
		JSONObject result = new JSONObject();

		// insert
		service.insert(entity);

		// entity
		String imageTaggingDataDicIdSq = entity.getImageTaggingDataDicIdSq();
		entity = new TaggingDicEntity();
		entity.setImageTaggingDataDicIdSq(imageTaggingDataDicIdSq);

		// get
		entity = service.get(entity);
		result.put("taggingDicEntity", entity);

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
	public String updateForm(@ModelAttribute TaggingDicEntity entity, Model model) {

		model.addAttribute("taggingDicEntity", service.get(entity));

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
	public ResponseEntity<String> update(@ModelAttribute TaggingDicEntity entity) {

		// Result
		JSONObject result = new JSONObject();

		// update
		entity = service.update(entity);

		// entity
		result.put("taggingDicEntity", entity);

		// success message
		result.put("message", MessageManage.getMessage("save.success"));

		//return PREFIX + "update" + TilesSuffix.EMPTY;
		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
	}
}