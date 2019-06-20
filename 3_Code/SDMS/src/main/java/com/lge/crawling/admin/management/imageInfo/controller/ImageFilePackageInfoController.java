package com.lge.crawling.admin.management.imageInfo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.lge.crawling.admin.common.util.Config;
import com.lge.crawling.admin.common.util.DateSupportUtil;
import com.lge.crawling.admin.common.util.MessageManage;
import com.lge.crawling.admin.common.web.controller.BaseController;
import com.lge.crawling.admin.constants.TilesSuffix;
import com.lge.crawling.admin.management.imageInfo.entity.ImageFilePackageInfoEntity;
import com.lge.crawling.admin.management.imageInfo.service.ImageFileInfoService;
import com.lge.crawling.admin.management.imageInfo.service.ImageFilePackageInfoService;

/**
 * 이미지 파일 패키지 정보 Controller
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
@RequestMapping("/image/imagePackageInfo")
public class ImageFilePackageInfoController extends BaseController {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(ImageFilePackageInfoController.class);

	private final String PREFIX = "image/imageFilePackageInfo/";

	@Autowired private ImageFileInfoService imageFileInfoService;

	@Autowired private ImageFilePackageInfoService imageFilePackageInfoService;

	/**
	 * 이미지 파일 패키지 정보 List 를 조회한다.
	 * @Mehtod Name : getList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"", "list"})
	public String list(@ModelAttribute ImageFilePackageInfoEntity entity, Model model) {

		if (StringUtils.isEmpty(entity.getStartDt()) && StringUtils.isEmpty(entity.getEndDt())) {
    		entity.setStartDt(DateSupportUtil.getDefaultStart());
    		entity.setEndDt(DateSupportUtil.getDefaultEnd());
    	}

		model.addAttribute("list", imageFilePackageInfoService.getList(entity));
		model.addAttribute("paging", entity.getPaging());

		return PREFIX + "list" + TilesSuffix.DEFAULT;
	}

	/**
	 * 이미지 파일 패키지 업로드
	 * @Mehtod Name : upload
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 * @throws ArchiveException
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String mapContentsUpload(MultipartHttpServletRequest req,
			HttpServletResponse res, HttpSession session) throws IOException, ArchiveException {
		logger.trace("[URI] : {}", req.getRequestURI());

		imageFilePackageInfoService.addImagePackageUpload(session, req);

		return "redirect:list";
	}

	/**
	 * 이미지 파일 패키지 다운로드
	 * @method download
	 * @param param
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "download")
	public ModelAndView imagePackageFileDownload(@RequestParam(value = "imageFilePackageIdSq") String imageFilePackageIdSq) throws UnsupportedEncodingException, FileNotFoundException {
		logger.trace("map file download!!!");
		logger.debug("imageFilePackageIdSq : {}", imageFilePackageIdSq);

		// Model And View
		ModelAndView mav = new ModelAndView();

		ImageFilePackageInfoEntity imageFilePackageInfoEntity = new ImageFilePackageInfoEntity();
		imageFilePackageInfoEntity.setImageFilePackageIdSq(imageFilePackageIdSq);
		imageFilePackageInfoEntity = imageFilePackageInfoService.get(imageFilePackageInfoEntity);

		// File
		String fileName = imageFilePackageInfoEntity.getImageFilePackageNm();
		String downloadRootPath = Config.getCommon().getString("IMAGE_PACKAGE_UPLOAD_ROOT_PATH") + imageFilePackageInfoEntity.getImageFilePackagePath();

		File file = new File(downloadRootPath, fileName+".zip");
		if (!file.exists()) {
			logger.error("FileNotFoundException - fileName: {}", fileName);
			throw new FileNotFoundException();
		}

		// forward download view
		mav.addObject("downloadFile", file);
		mav.setViewName("downloadView");

		return mav;
	}

	/**
	 * 이미지 정보 등록 폼
	 * @Mehtod Name : insertForm
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String addForm(@ModelAttribute ImageFilePackageInfoEntity entity, Model model, HttpServletRequest request) {

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			model.addAttribute("message", flashMap.get("message"));
		}

		return PREFIX + "form" + TilesSuffix.DEFAULT;
	}

	/**
	 * 이미지 정보 등록
	 * @Mehtod Name : insert
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String add(@ModelAttribute ImageFilePackageInfoEntity entity,
			BindingResult result,
			Model model,
			RedirectAttributes attrs,
			HttpServletRequest request) {

		// Validator Error
		if (result.hasErrors()) {
		}

		// 등록
		imageFilePackageInfoService.insert(entity);

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("message", MessageManage.getMessage("save.success"));

		// Redirect Params
		attrs.addAttribute("imageFilePackageSq", entity.getImageFilePackageIdSq());

		return "redirect:update";
	}

	/**
	 * 이미지 정보 수정 폼
	 * @Mehtod Name : updateForm
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(@ModelAttribute ImageFilePackageInfoEntity entity, Model model, HttpServletRequest request) {

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			model.addAttribute("message", flashMap.get("message"));
		}

		model.addAttribute("imageFilePackageInfo", imageFilePackageInfoService.get(entity));

		return PREFIX + "update" + TilesSuffix.DEFAULT;
	}

	/**
	 * 이미지 정보 수정
	 * @Mehtod Name : update
	 * @param entity
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute ImageFilePackageInfoEntity entity,
			BindingResult result,
			Model model,
			RedirectAttributes attrs,
			HttpServletRequest request) {

		// Validator Error
		if (result.hasErrors()) {
		}

		// Update
		entity = imageFilePackageInfoService.update(entity);

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("message", MessageManage.getMessage("save.success"));

		// Redirect Params
		attrs.addAttribute("imageFilePackageSq", entity.getImageFilePackageIdSq());

		return "redirect:update";
	}

}