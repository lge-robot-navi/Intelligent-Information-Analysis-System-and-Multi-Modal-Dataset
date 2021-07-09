package com.lge.crawling.admin.management.sensorDataInfo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lge.crawling.admin.common.util.Config;
import com.lge.crawling.admin.common.util.DateSupportUtil;
import com.lge.crawling.admin.common.util.DateUtil;
import com.lge.crawling.admin.common.util.MessageManage;
import com.lge.crawling.admin.common.util.ZipDirectory;
import com.lge.crawling.admin.common.web.controller.BaseController;
import com.lge.crawling.admin.constants.TilesSuffix;
import com.lge.crawling.admin.management.sensorDataInfo.entity.IdsSensorDataInfoEntity;
import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataFileInfoEntity;
import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataFileListEntity;
import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataFilePackageInfoEntity;
import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataFileWorkerInfoEntity;
import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataJsonFileInfoEntity;
import com.lge.crawling.admin.management.sensorDataInfo.service.SensorDataFileInfoService;
import com.lge.crawling.admin.management.sensorDataInfo.service.SensorDataFilePackageInfoService;
import com.lge.crawling.admin.management.sensorDataInfo.service.SensorDataFileWorkerInfoService;
import com.lge.crawling.admin.management.sensorDataInfo.service.SensorDataJsonFileInfoService;
import com.lge.crawling.admin.management.system.entity.AdminEntity;
import com.lge.crawling.admin.management.system.service.AdminService;

import net.sf.json.JSONObject;

/**
 * 이미지 파일 정보 Controller
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
@RequestMapping("/sensorData/sensorDataInfo")
public class SensorDataFileInfoController extends BaseController {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(SensorDataFileInfoController.class);

	private final String PREFIX = "sensorData/sensorDataFileInfo/";

	private static final String IMG_PATH = "/image/";

	private static final String JSON_PATH = "/json/";

	private static final String XML_PATH = "/xml/";

	@Autowired private SensorDataFileInfoService sensorDataFileInfoService;

	@Autowired private SensorDataFilePackageInfoService sensorDataFilePackageInfoService;

	@Autowired private SensorDataJsonFileInfoService sensorDataJsonFileInfoService;

	@Autowired private SensorDataFileWorkerInfoService sensorDataFileWorkerInfoService;

	@Autowired private AdminService adminService;

	/**
	 * 이미지 파일 정보 List 를 조회한다.
	 * @Mehtod Name : getList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"", "list"})
	public String list(@ModelAttribute SensorDataFileInfoEntity entity, Model model) {

		if (StringUtils.isEmpty(entity.getStartDt()) && StringUtils.isEmpty(entity.getEndDt())) {
    		entity.setStartDt(DateSupportUtil.getDefaultStart());
    		entity.setEndDt(DateSupportUtil.getDefaultEnd());
    	}

		model.addAttribute("list", sensorDataFileInfoService.getList(entity));
		model.addAttribute("paging", entity.getPaging());
		model.addAttribute("commonConfig", Config.getCommon());

		return PREFIX + "list" + TilesSuffix.DEFAULT;
	}

	/**
	 * 관리자 정보 List 를 조회한다.
	 * @Mehtod Name : getAdminList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "adminList")
	public String list(@ModelAttribute AdminEntity entity, Model model) {

		// 작업자만 조회하도록
		entity.setAdminCd("200"); // 200 : Worker

		model.addAttribute("list", adminService.getList(entity));
		model.addAttribute("paging", entity.getPaging());

		return PREFIX + "admin-list" + TilesSuffix.TMPL;
	}

	/**
	 * 이미지 정보 List 를 조회한다.
	 * @Mehtod Name : getImageList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "imageList")
	public String getImageList(@ModelAttribute SensorDataFileInfoEntity entity, Model model) {

		if (StringUtils.isEmpty(entity.getStartDt()) && StringUtils.isEmpty(entity.getEndDt())) {
    		entity.setStartDt(DateSupportUtil.getDefaultStart());
    		entity.setEndDt(DateSupportUtil.getDefaultEnd());
    	}

		model.addAttribute("list", sensorDataFileInfoService.getList(entity));
		model.addAttribute("paging", entity.getPaging());

		return PREFIX + "image-list" + TilesSuffix.TMPL;
	}

	/**
	 *  센서데이터 정보입력
	 * @param req
	 * @param session
	 * @param uid
	 * @return
	 * @throws IOException
	 * @throws ArchiveException
	 */
	@RequestMapping(value = "addSensorData", method = RequestMethod.GET)
	public String addSensorData(@RequestParam("fileuid") String fileuid, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws IOException {
		
		logger.info("Input Data - fileuid : " + fileuid);

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(req);
		flashMap.put("message", MessageManage.getMessage("save.success"));

		try {
			sensorDataFilePackageInfoService.addSensorData(session, fileuid);
		} catch (Exception e) {
			logger.error("EX", e);
			flashMap.put("message", MessageManage.getMessage("save.fail"));
		}

		return "redirect:list";
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

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(req);
		flashMap.put("message", MessageManage.getMessage("save.success"));

		try {
			sensorDataFilePackageInfoService.addImagePackageUpload(session, req);
		} catch (Exception e) {
			flashMap.put("message", MessageManage.getMessage("save.fail"));
			logger.debug("Exception:"+e.getMessage());
			e.printStackTrace();
		}

		return "redirect:list";
	}

	/**
	 * 이미지 파일 패키지 다운로드
	 * @method imagePackageFileDownload
	 * @param arrayParams
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "download")
	public ModelAndView imagePackageFileDownload(@RequestParam(value="checkArray[]") List<String> arrayParams, HttpServletRequest req) throws UnsupportedEncodingException, FileNotFoundException {
		logger.debug("Image file download!!!");

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(req);
		flashMap.put("message", MessageManage.getMessage("save.success"));

		// Model And View
		ModelAndView mav = new ModelAndView();

		try {
			// 패키지 일련번호 조회 (신규)
			SensorDataFilePackageInfoEntity sensorDataFilePackageInfoEntity = new SensorDataFilePackageInfoEntity();
			String sensorDataFilePackageSq = sensorDataFilePackageInfoService.getSensorDataFilePackageSeq(sensorDataFilePackageInfoEntity);

			// 작업 폴더 생성
			final String rootPath = Config.getCommon().getString("SENSORDATA_PACKAGE_ROOT_PATH");
			final String workRootPath = Config.getCommon().getString("SENSORDATA_PACKAGE_UPLOAD_WORK_ROOT_PATH");
			final String uploadRootPath = Config.getCommon().getString("SENSORDATA_PACKAGE_UPLOAD_ROOT_PATH");
			final String downloadPath = Config.getCommon().getString("SENSORDATA_PACKAGE_DOWNLOAD_ROOT_PATH");
			logger.debug("workRootPath : {}", workRootPath);
			logger.debug("uploadRootPath : {}", uploadRootPath);
			logger.debug("downloadPath : {}", downloadPath);

			String dbRootFilePath = String.format("/%s/%s/%s",
					DateUtil.getCurrentDate("yyyy"),
					DateUtil.getCurrentDate("MMdd"),
					sensorDataFilePackageSq
					);
			final File workPath = new File(String.format("%s/%s", workRootPath, dbRootFilePath));
			final File imageWorkPath = new File(String.format("%s/%s", workRootPath, dbRootFilePath)+IMG_PATH);
			final File jsonWorkPath = new File(String.format("%s/%s", workRootPath, dbRootFilePath)+JSON_PATH);
			logger.debug("imageWorkPath : {}", imageWorkPath);
			logger.debug("jsonWorkPath : {}", jsonWorkPath);

			if (!imageWorkPath.exists()) {
				logger.info("imageWorkPath path is create! : {}", imageWorkPath);
				imageWorkPath.mkdirs();
			}
			if (!jsonWorkPath.exists()) {
				logger.info("jsonWorkPath path is create! : {}", jsonWorkPath);
				jsonWorkPath.mkdirs();
			}

			// 이미지 파일 리스트
			List<SensorDataFileListEntity> sensorDataFileList = new ArrayList<>();

			// 이미지 파일 정보 조회
			for (String sensorDataFileSq : arrayParams) {
				SensorDataFileListEntity sensorDataFileListEntity = new SensorDataFileListEntity();
				SensorDataFileInfoEntity sensorDataFileInfoEntity = new SensorDataFileInfoEntity();
				sensorDataFileInfoEntity.setSensorDataFileSq(sensorDataFileSq);
				sensorDataFileInfoEntity = sensorDataFileInfoService.get(sensorDataFileInfoEntity);
				// 작업 폴더에 파일 복사
				File srcFile = new File(rootPath+sensorDataFileInfoEntity.getSensorDataFilePath());
				FileUtils.copyFileToDirectory(srcFile, imageWorkPath);
				logger.info("File is copied!\r\nworkPath : {}\r\ndestPath: {}", srcFile, imageWorkPath);
				SensorDataJsonFileInfoEntity sensorDataJsonFileInfoEntity = new SensorDataJsonFileInfoEntity();
				sensorDataJsonFileInfoEntity.setSensorDataFileSq(sensorDataFileSq);
				sensorDataJsonFileInfoEntity = sensorDataJsonFileInfoService.get(sensorDataJsonFileInfoEntity);
				if (sensorDataJsonFileInfoEntity != null) {
					sensorDataFileListEntity.setSensorDataJsonFileNm(sensorDataFileInfoEntity.getSensorDataFileNm().split("\\.")[0] + ".json");
					sensorDataFileListEntity.setSensorDataJsonFileLoc(JSON_PATH);
					FileUtils.writeStringToFile(new File(jsonWorkPath+"/"+sensorDataFileListEntity.getSensorDataJsonFileNm()), sensorDataJsonFileInfoEntity.getSensorDataJsonFileDesc(), "UTF-8");
				}
				sensorDataFileListEntity.setSensorDataFileLoc(sensorDataFileInfoEntity.getSensorDataFilePath());
				sensorDataFileListEntity.setSensorDataFileNm(sensorDataFileInfoEntity.getSensorDataFileNm());
				sensorDataFileListEntity.setSensorDataFileSizeX(sensorDataFileInfoEntity.getSensorDataFileScaleX());
				sensorDataFileListEntity.setSensorDataFileSizeY(sensorDataFileInfoEntity.getSensorDataFileScaleY());
				sensorDataFileListEntity.setSensorDataFileTp(sensorDataFileInfoEntity.getSensorDataFileTypeCd());
//				sensorDataFileListEntity.setSensorDataFileDownloadPathUrl(sensorDataFileInfoEntity.getSensorDataFileDownloadPathUrl());
//				sensorDataFileListEntity.setSensorDataFileDownloadPathTp(sensorDataFileInfoEntity.getSensorDataFileDownloadPathCd());
				sensorDataFileListEntity.setDeleteYn("N");
				sensorDataFileListEntity.setCreateDate(sensorDataFileInfoEntity.getSensorDataFilePackageRegistDt());

				sensorDataFileList.add(sensorDataFileListEntity);
			}

			// 메타정보 JSON 파일 생성
			IdsSensorDataInfoEntity idsSensorDataInfoEntity = new IdsSensorDataInfoEntity();
			idsSensorDataInfoEntity.setSensorDataFilePackageIdSq(sensorDataFilePackageSq);
			idsSensorDataInfoEntity.setSensorDataFilePackageFileNm(DateUtil.getCurrentDate("yyyyMMddHHmmss")+"_"+sensorDataFilePackageSq);
			idsSensorDataInfoEntity.setSensorDataFilePackageDesc("");
			idsSensorDataInfoEntity.setSensorDataFilePackageYn("Y");
			idsSensorDataInfoEntity.setWorkTp("I");
			idsSensorDataInfoEntity.setMetaFileRegistDt(DateUtil.getCurrentDate("yyyyMMddHHmmss"));
			idsSensorDataInfoEntity.setSensorDataFileList(sensorDataFileList);

			Gson gson = new GsonBuilder().create();
			FileUtils.writeStringToFile(new File(workPath+"/"+"lds_img.info"), gson.toJson(idsSensorDataInfoEntity), "UTF-8");

			// 패키지 압축
			ZipDirectory zipDir = new ZipDirectory();
	        File inputDir = workPath;
	        File outputZipFile = new File(String.format("%s/%s", downloadPath, dbRootFilePath)+"/"+DateUtil.getCurrentDate("yyyyMMddHHmmss")+"_"+sensorDataFilePackageSq+".zip");
	        zipDir.zipDirectory(inputDir, outputZipFile);

	        // File
	        File file = outputZipFile;
	        if (!file.exists()) {
	        	logger.error("FileNotFoundException");
	        	throw new FileNotFoundException();
	        }

	        // forward download view
	        mav.addObject("downloadFile", file);
	        mav.setViewName("downloadView");
		} catch (Exception e) {
			flashMap.put("message", MessageManage.getMessage("save.fail"));
			logger.debug(e.getMessage());
			e.printStackTrace();
			mav.setViewName("redirect:list");
		}
		return mav;
	}
	
	
	/**
	 * 센서데이터 파일 패키지 다운로드
	 * @method sensorDataPackageFileDownload
	 * @param sdt
	 * @param edt
	 * @param type
	 * @param name
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "downloadPackage")
	public ModelAndView sensorDataPackageFileDownload(
			@RequestParam(value = "sdt") String sdt, 
			@RequestParam(value = "edt") String edt,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "name") String name, HttpServletRequest req) throws UnsupportedEncodingException, FileNotFoundException {
		
		
//		logger.debug("sdt: "+sdt);
//		logger.debug("edt: "+edt);
//		logger.debug("type: "+type);
//		logger.debug("name: "+name);
//		logger.debug(">>>>>>>>>>>> downloadPackage called");
		
		
		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(req);
		flashMap.put("message", MessageManage.getMessage("save.success"));

		// Model And View
		ModelAndView mav = new ModelAndView();

		try {
			// 패키지 일련번호 조회 (신규)
//			SensorDataFilePackageInfoEntity sensorDataFilePackageInfoEntity = new SensorDataFilePackageInfoEntity();
//			String sensorDataFilePackageSq = sensorDataFilePackageInfoService.getSensorDataFilePackageSeq(sensorDataFilePackageInfoEntity);

			// 작업 폴더 생성
			final String rootPath = Config.getCommon().getString("SENSORDATA_PACKAGE_ROOT_PATH");
			final String workRootPath = Config.getCommon().getString("SENSORDATA_PACKAGE_UPLOAD_WORK_ROOT_PATH");
			final String uploadRootPath = Config.getCommon().getString("SENSORDATA_PACKAGE_UPLOAD_ROOT_PATH");
			final String downloadPath = Config.getCommon().getString("SENSORDATA_PACKAGE_DOWNLOAD_ROOT_PATH");
			logger.debug("workRootPath : {}", workRootPath);
			logger.debug("uploadRootPath : {}", uploadRootPath);
			logger.debug("downloadPath : {}", downloadPath);

			String dbRootFilePath = String.format("/%s/%s/%s",
					DateUtil.getCurrentDate("yyyy"),
					DateUtil.getCurrentDate("MMdd"),
					DateUtil.getCurrentDate("HHmmss")
					);
			final File workPath = new File(String.format("%s/%s", workRootPath, dbRootFilePath));
			final File imageWorkPath = new File(String.format("%s/%s", workRootPath, dbRootFilePath)+IMG_PATH);
			final File jsonWorkPath = new File(String.format("%s/%s", workRootPath, dbRootFilePath)+JSON_PATH);
			logger.debug("workPath : {}", workPath);
			logger.debug("imageWorkPath : {}", imageWorkPath);
			logger.debug("jsonWorkPath : {}", jsonWorkPath);

			if (!imageWorkPath.exists()) {
				logger.info("imageWorkPath path is create! : {}", imageWorkPath);
				imageWorkPath.mkdirs();
			}
			if (!jsonWorkPath.exists()) {
				logger.info("jsonWorkPath path is create! : {}", jsonWorkPath);
				jsonWorkPath.mkdirs();
			}

			SensorDataFileInfoEntity entity = new SensorDataFileInfoEntity();
			entity.setStartDt(sdt);
			entity.setEndDt(edt);
			entity.setSensorDataFileTypeCd(type);
			entity.setSensorDataFileNm(name);
			
			// 조회
			List<SensorDataFileInfoEntity> list = sensorDataFileInfoService.getAllList(entity);
			
			// 이미지 파일 리스트
			List<SensorDataFileListEntity> sensorDataFileList = new ArrayList<>();

			// 이미지 파일 정보 조회
//			for (String sensorDataFileSq : arrayParams) {
			String sensorDataFileSq;
			for(int i=0; i<list.size(); i++) {
				sensorDataFileSq = list.get(i).getSensorDataFileSq(); 
				SensorDataFileListEntity sensorDataFileListEntity = new SensorDataFileListEntity();
				SensorDataFileInfoEntity sensorDataFileInfoEntity = new SensorDataFileInfoEntity();
				sensorDataFileInfoEntity.setSensorDataFileSq(sensorDataFileSq);
				sensorDataFileInfoEntity = sensorDataFileInfoService.get(sensorDataFileInfoEntity);
				// 작업 폴더에 파일 복사
				File srcFile = new File(rootPath+sensorDataFileInfoEntity.getSensorDataFilePath());
				FileUtils.copyFileToDirectory(srcFile, imageWorkPath);
				logger.info("File is copied!\r\nworkPath : {}\r\ndestPath: {}", srcFile, imageWorkPath);
				SensorDataJsonFileInfoEntity sensorDataJsonFileInfoEntity = new SensorDataJsonFileInfoEntity();
				sensorDataJsonFileInfoEntity.setSensorDataFileSq(sensorDataFileSq);
				sensorDataJsonFileInfoEntity = sensorDataJsonFileInfoService.get(sensorDataJsonFileInfoEntity);
				if (sensorDataJsonFileInfoEntity != null) {
					sensorDataFileListEntity.setSensorDataJsonFileNm(sensorDataFileInfoEntity.getSensorDataFileNm().split("\\.")[0] + ".json");
					sensorDataFileListEntity.setSensorDataJsonFileLoc(JSON_PATH);
					FileUtils.writeStringToFile(new File(jsonWorkPath+"/"+sensorDataFileListEntity.getSensorDataJsonFileNm()), sensorDataJsonFileInfoEntity.getSensorDataJsonFileDesc(), "UTF-8");
				}
				sensorDataFileListEntity.setSensorDataFileLoc(sensorDataFileInfoEntity.getSensorDataFilePath());
				sensorDataFileListEntity.setSensorDataFileNm(sensorDataFileInfoEntity.getSensorDataFileNm());
				sensorDataFileListEntity.setSensorDataFileSizeX(sensorDataFileInfoEntity.getSensorDataFileScaleX());
				sensorDataFileListEntity.setSensorDataFileSizeY(sensorDataFileInfoEntity.getSensorDataFileScaleY());
				sensorDataFileListEntity.setSensorDataFileTp(sensorDataFileInfoEntity.getSensorDataFileTypeCd());
//				sensorDataFileListEntity.setSensorDataFileDownloadPathUrl(sensorDataFileInfoEntity.getSensorDataFileDownloadPathUrl());
//				sensorDataFileListEntity.setSensorDataFileDownloadPathTp(sensorDataFileInfoEntity.getSensorDataFileDownloadPathCd());
				sensorDataFileListEntity.setDeleteYn("N");
				sensorDataFileListEntity.setCreateDate(sensorDataFileInfoEntity.getSensorDataFilePackageRegistDt());

				sensorDataFileList.add(sensorDataFileListEntity);
			}

			// 메타정보 JSON 파일 생성
//			IdsSensorDataInfoEntity idsSensorDataInfoEntity = new IdsSensorDataInfoEntity();
//			idsSensorDataInfoEntity.setSensorDataFilePackageIdSq(sensorDataFilePackageSq);
//			idsSensorDataInfoEntity.setSensorDataFilePackageFileNm(DateUtil.getCurrentDate("yyyyMMddHHmmss")+"_"+sensorDataFilePackageSq);
//			idsSensorDataInfoEntity.setSensorDataFilePackageDesc("");
//			idsSensorDataInfoEntity.setSensorDataFilePackageYn("Y");
//			idsSensorDataInfoEntity.setWorkTp("I");
//			idsSensorDataInfoEntity.setMetaFileRegistDt(DateUtil.getCurrentDate("yyyyMMddHHmmss"));
//			idsSensorDataInfoEntity.setSensorDataFileList(sensorDataFileList);

//			Gson gson = new GsonBuilder().create();
//			FileUtils.writeStringToFile(new File(workPath+"/"+"lds_img.info"), gson.toJson(idsSensorDataInfoEntity), "UTF-8");

			// 패키지 압축
			ZipDirectory zipDir = new ZipDirectory();
	        File inputDir = workPath;
//	        File outputZipFile = new File(String.format("%s/%s", downloadPath, dbRootFilePath)+"/"+DateUtil.getCurrentDate("yyyyMMddHHmmss")+"_"+sensorDataFilePackageSq+".zip");
	        File outputZipFile = new File(String.format("%s/%s", downloadPath, dbRootFilePath)+"/"+DateUtil.getCurrentDate("yyyyMMddHHmmss")+".zip");
	        zipDir.zipDirectory(inputDir, outputZipFile);

	        // File
	        File file = outputZipFile;
	        if (!file.exists()) {
	        	logger.error("FileNotFoundException");
	        	throw new FileNotFoundException();
	        }

	        // forward download view
	        mav.addObject("downloadFile", file);
	        mav.setViewName("downloadView");
		} catch (Exception e) {
			flashMap.put("message", MessageManage.getMessage("save.fail"));
			logger.debug(e.getMessage());
			e.printStackTrace();
			mav.setViewName("redirect:list");
		}
		return mav;
	}
	

	/**
	 * 이미지 XML 정보 다운로드
	 * @method imageXmlInfoDownload
	 * @param arrayParams
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "xml-download")
	public ModelAndView imageXmlInfoDownload(@RequestParam(value="checkArray[]") List<String> arrayParams, HttpServletRequest req) throws UnsupportedEncodingException, FileNotFoundException {
		logger.trace("Image file XML download!!!");

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(req);

		// Model And View
		ModelAndView mav = new ModelAndView();

		try {
			// 작업 폴더 생성
			final String workRootPath = Config.getCommon().getString("SENSORDATA_PACKAGE_UPLOAD_WORK_ROOT_PATH");
			final String uploadRootPath = Config.getCommon().getString("SENSORDATA_PACKAGE_UPLOAD_ROOT_PATH");
			final String downloadPath = Config.getCommon().getString("SENSORDATA_PACKAGE_DOWNLOAD_ROOT_PATH");
			logger.debug("workRootPath : {}", workRootPath);
			logger.debug("uploadRootPath : {}", uploadRootPath);
			logger.debug("downloadPath : {}", downloadPath);

			String dbRootFilePath = String.format("/%s/%s/%s/%s",
					DateUtil.getCurrentDate("yyyy"),
					DateUtil.getCurrentDate("MMdd"),
					DateUtil.getCurrentDate("HHmmss"),
					"XML"
					);
			final File workPath = new File(String.format("%s/%s", workRootPath, dbRootFilePath));
			final File xmlWorkPath = new File(String.format("%s/%s", workRootPath, dbRootFilePath)+XML_PATH);
			logger.debug("xmlWorkPath : {}", xmlWorkPath);

			if (!xmlWorkPath.exists()) {
				logger.info("xmlWorkPath path is create! : {}", xmlWorkPath);
				xmlWorkPath.mkdirs();
			}

			// 이미지 파일 정보 조회
			for (String sensorDataFileSq : arrayParams) {
				SensorDataFileListEntity sensorDataFileListEntity = new SensorDataFileListEntity();
				SensorDataFileInfoEntity sensorDataFileInfoEntity = new SensorDataFileInfoEntity();
				sensorDataFileInfoEntity.setSensorDataFileSq(sensorDataFileSq);
				sensorDataFileInfoEntity = sensorDataFileInfoService.get(sensorDataFileInfoEntity);
				SensorDataJsonFileInfoEntity sensorDataJsonFileInfoEntity = new SensorDataJsonFileInfoEntity();
				sensorDataJsonFileInfoEntity.setSensorDataFileSq(sensorDataFileSq);
				sensorDataJsonFileInfoEntity = sensorDataJsonFileInfoService.get(sensorDataJsonFileInfoEntity);
				if (sensorDataJsonFileInfoEntity != null) {
					sensorDataFileListEntity.setSensorDataJsonFileNm(sensorDataFileInfoEntity.getSensorDataFileNm().split("\\.")[0] + ".xml");
					FileUtils.writeStringToFile(new File(xmlWorkPath+"/"+sensorDataFileListEntity.getSensorDataJsonFileNm()), sensorDataJsonFileInfoEntity.getSensorDataJsonXmlConvFileDesc(), "UTF-8");
				}
			}

			// 패키지 압축
			ZipDirectory zipDir = new ZipDirectory();
			File inputDir = workPath;
			File outputZipFile = new File(String.format("%s/%s", downloadPath, dbRootFilePath)+"/"+DateUtil.getCurrentDate("yyyyMMddHHmmss")+"_XML.zip");
			zipDir.zipDirectory(inputDir, outputZipFile);

			// File
			File file = outputZipFile;
			if (!file.exists()) {
				logger.error("FileNotFoundException");
				throw new FileNotFoundException();
			}

			// forward download view
			mav.addObject("downloadFile", file);
			mav.setViewName("downloadView");
		} catch (Exception e) {
			flashMap.put("message", MessageManage.getMessage("save.fail"));
			logger.debug(e.getMessage());
			e.printStackTrace();
			mav.setViewName("redirect:list");
		}
		return mav;
	}

	/**
	 * 이미지 정보 등록 폼
	 * @Mehtod Name : insertForm
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String addForm(@ModelAttribute SensorDataFileInfoEntity entity, Model model, HttpServletRequest request) {

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
	public String add(@ModelAttribute SensorDataFileInfoEntity entity,
			BindingResult result,
			Model model,
			RedirectAttributes attrs,
			HttpServletRequest request) {

		// Validator Error
		if (result.hasErrors()) {
		}

		// 등록
		sensorDataFileInfoService.insert(entity);

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("message", MessageManage.getMessage("save.success"));

		// Redirect Params
		attrs.addAttribute("sensorDataFileSq", entity.getSensorDataFileSq());

		return "redirect:update";
	}

	/**
	 * 작업자 할당
	 * @Mehtod Name : assignWorker
	 * @return
	 */
	@RequestMapping(value = "assignWorker", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject assignWorker(@RequestBody SensorDataFileWorkerInfoEntity sendData, BindingResult bindingResult) {

		JSONObject result = new JSONObject();
		result.put("resultCd", "fail");
		result.put("resultMsg", MessageManage.getMessage("save.fail"));

		try {
			// Validator Error
			if (bindingResult.hasErrors()) {
			}

			String[] imageList = sendData.getImageArray();
			logger.debug("image List Size:"+imageList.length);
			String[] workerList = sendData.getWorkerArray();
			logger.debug("worker List Size:"+workerList.length);

			for (String worker : workerList) {
				for (String image : imageList) {
					sendData.setSensorDataFileSq(image);
					sendData.setSensorDataFileWorkerId(worker);
					SensorDataFileWorkerInfoEntity dupCheck = sensorDataFileWorkerInfoService.get(sendData);
					// 중복 생략
					if (dupCheck == null) {
						// 등록
						sensorDataFileWorkerInfoService.insert(sendData);
					}
				}
			}

			result.put("resultCd", "success");
			result.put("resultMsg", MessageManage.getMessage("save.success"));

		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 이미지 태깅 정보 초기화
	 * @Mehtod Name : initImageTaggingInfo
	 * @return
	 */
	@RequestMapping(value = "initImageTaggingInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONObject initImageTaggingInfo(@RequestParam(value = "sensorDataJsonFileSq") String sensorDataJsonFileSq, @ModelAttribute SensorDataJsonFileInfoEntity entity, BindingResult bindingResult) {

		JSONObject result = new JSONObject();
		result.put("resultCd", "fail");
		result.put("resultMsg", MessageManage.getMessage("save.fail"));

		try {
			// Validator Error
			if (bindingResult.hasErrors()) {
			}

			// 초기화
			entity.setSensorDataJsonFileSq(sensorDataJsonFileSq);
			entity.setSensorDataJsonFileDesc("");
			entity.setSensorDataJsonXmlConvFileDesc("");
			SensorDataJsonFileInfoEntity updateResult = sensorDataJsonFileInfoService.update(entity);

			if (updateResult != null) {
				result.put("resultCd", "success");
				result.put("resultMsg", MessageManage.getMessage("save.success"));
			}

		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 이미지 정보 수정 폼
	 * @Mehtod Name : updateForm
	 * @param entity
	 * @param model
	 * @return
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(@ModelAttribute SensorDataFileInfoEntity entity, Model model, HttpServletRequest request) {

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			model.addAttribute("message", flashMap.get("message"));
		}

		try {
			SensorDataJsonFileInfoEntity sensorDataJsonFileInfoEntity = new SensorDataJsonFileInfoEntity();
			SensorDataJsonFileInfoEntity sensorDataJsonFileInfoEntityNull = new SensorDataJsonFileInfoEntity();
			sensorDataJsonFileInfoEntity.setSensorDataFileSq(entity.getSensorDataFileSq());
			sensorDataJsonFileInfoEntity = sensorDataJsonFileInfoService.get(sensorDataJsonFileInfoEntity);
			
			if(sensorDataJsonFileInfoEntity == null) {
				sensorDataJsonFileInfoEntityNull.setSensorDataFileSq(entity.getSensorDataFileSq());
				sensorDataJsonFileInfoEntityNull.setSensorDataJsonFileDesc("");
				sensorDataJsonFileInfoEntityNull.setSensorDataJsonXmlConvFileDesc("");
			}

			model.addAttribute("sensorDataFileInfo", sensorDataFileInfoService.get(entity));
			if(sensorDataJsonFileInfoEntity == null) {
				model.addAttribute("sensorDataJsonFileInfo", sensorDataJsonFileInfoEntityNull);
			} else {
				model.addAttribute("sensorDataJsonFileInfo", sensorDataJsonFileInfoEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

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
	public String update(@ModelAttribute SensorDataFileInfoEntity entity,
			BindingResult result,
			Model model,
			RedirectAttributes attrs,
			HttpServletRequest request) {

		// Validator Error
		if (result.hasErrors()) {
		}

		// Update
		entity = sensorDataFileInfoService.update(entity);

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("message", MessageManage.getMessage("save.success"));

		// Redirect Params
		attrs.addAttribute("sensorDataFileSq", entity.getSensorDataFileSq());

		return "redirect:update";
	}

	/**
	 * 이미지 정보 삭제
	 * @Mehtod Name : delete
	 * @param entity
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(@ModelAttribute SensorDataFileInfoEntity entity,
			BindingResult result,
			Model model,
			RedirectAttributes attrs,
			HttpServletRequest request) {

		// Validator Error
		if (result.hasErrors()) {
		}

		// Delete
		int deleteResult = sensorDataFileInfoService.deleteImageInfo(entity);

		// Message
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("message", MessageManage.getMessage("save.fail"));

		if (deleteResult > 0) {
			flashMap.put("message", MessageManage.getMessage("save.delete"));
		}

		return "redirect:list";
	}

}