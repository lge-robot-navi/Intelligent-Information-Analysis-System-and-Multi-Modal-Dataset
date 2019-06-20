package com.lge.crawling.admin.management.sensorDataInfo.service;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lge.crawling.admin.management.imageInfo.entity.ImageFileInfoEntity;
import com.lge.crawling.admin.management.sensorData.entity.SensorDataTaggingGetInfoEntity;
import com.lge.crawling.admin.management.sensorData.mapper.SensorDataTaggingGetInfoMapper;
import com.lge.crawling.admin.management.sensorData.service.SensorDataTaggingGetInfoService;
import com.lge.crawling.admin.management.sensorDataInfo.entity.*;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.HtmlUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lge.crawling.admin.common.util.Config;
import com.lge.crawling.admin.common.util.CustomFileUtil;
import com.lge.crawling.admin.common.util.DateUtil;
import com.lge.crawling.admin.common.util.DatetimeUtil;
import com.lge.crawling.admin.common.validate.EntityValidator;
import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.constants.Constants;
import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataFilePackageInfoEntity;
import com.lge.crawling.admin.management.sensorDataInfo.mapper.SensorDataFileInfoMapper;
import com.lge.crawling.admin.management.sensorDataInfo.mapper.SensorDataFilePackageInfoMapper;
import com.lge.crawling.admin.management.sensorDataInfo.mapper.SensorDataJsonFileInfoMapper;

import ch.qos.logback.core.util.FileUtil;

/**
 * Image File Package Info Service
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
@Service
public class SensorDataFilePackageInfoService implements GenericService<SensorDataFilePackageInfoEntity> {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(SensorDataFilePackageInfoService.class);

	private static final String PACKAGE_PATH = "/package";

	private static final String IMG_PATH = "/image/";
	private static final String SD_PATH = "/sensorData/";
	private static final String SD_UPLOAD_ROOT_PATH = "/sensorData/upload";

	private static final String JSON_PATH = "/json/";

	private static final String UPLOAD_ROOT_PATH = "/crawling/upload";

	@Autowired private SensorDataFilePackageInfoMapper mapper;

	@Autowired private SensorDataFileInfoMapper sensorDataFileInfoMapper;

	@Autowired private SensorDataJsonFileInfoMapper sensorDataJsonFileInfoMapper;

	@Autowired private SensorDataTaggingGetInfoMapper sensorDataTaggingGetInfoMapper;

	/**
	 * (non-Javadoc)
	 * @see GenericService#get(java.lang.Object)
	 */
	@Override
	public SensorDataFilePackageInfoEntity get(SensorDataFilePackageInfoEntity entity) {
		return mapper.get(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	@Override
	public List<SensorDataFilePackageInfoEntity> getAllList(SensorDataFilePackageInfoEntity entity) {
		return mapper.getAllList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getList(java.lang.Object)
	 */
	@Override
	public List<SensorDataFilePackageInfoEntity> getList(SensorDataFilePackageInfoEntity entity) {
		int count = this.getCount(entity);
		entity.getPagingValue(count);
		return mapper.getList(entity);
	}


	public String getSensorDataFilePackageSeq(SensorDataFilePackageInfoEntity entity) {
		return mapper.getSensorDataFilePackageSeq(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	@Override
	public Integer getCount(SensorDataFilePackageInfoEntity entity) {
		return mapper.count(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#insert(java.lang.Object)
	 */
	@Override
	public SensorDataFilePackageInfoEntity insert(SensorDataFilePackageInfoEntity entity) {
		mapper.insert(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#update(java.lang.Object)
	 */
	@Override
	public SensorDataFilePackageInfoEntity update(SensorDataFilePackageInfoEntity entity) {
		mapper.update(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	@Override
	public int delete(SensorDataFilePackageInfoEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}
	
	/** 
	 * 센서데이터 일별 파일 및 DB정보 입력
	 * @param session
	 * @param req
	 * @param uid
	 * @throws IOException
	 * @throws ArchiveException
	 */
	public void addSensorData(HttpSession session, String fileuid) throws IOException, ArchiveException {

		// Login ID In Session
		String loginIDInSession = (String) session.getAttribute(Constants.SESSION_USER_SEQ);
		logger.debug("loginIDInSession : {}", loginIDInSession);
		
		logger.debug("fileuid : " + fileuid);
		
		List<SensorDataFileInfoEntity> sensorEntityList = new ArrayList<SensorDataFileInfoEntity>();
		
		String orgFilename = "";
		final String tempPath = Config.getCommon().getString("SENSORDATA_PACKAGE_UPLOAD_TEMP_PATH");
		File tempInfoFile = new File(tempPath, fileuid + ".info");
		File tempDataFile = new File(tempPath, fileuid + ".bin");
		
		/* ########## 1. 파일 정보 읽기 ########## */
		// 입력 스트림 생성
		FileReader fileReader = new FileReader(tempInfoFile);
		// 입력 버퍼 생성
		BufferedReader bufReader = new BufferedReader(fileReader);
		String jsonData = "";
		jsonData = bufReader.readLine();
		bufReader.close();
		logger.debug("jsonData: " + jsonData);

		/* ########## 2. JSON 파싱 ########## */
		try {
			JSONParser jsonParser = new JSONParser();
             
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
            JSONObject jsonObject2 = (JSONObject) jsonParser.parse(jsonObject.get("MetaData").toString());

            orgFilename = (String) jsonObject2.get("filename");
            logger.debug("filename: " + orgFilename);
		} catch (ParseException e) {
            e.printStackTrace();
        }
		

		String fileGroup = CustomFileUtil.getFilename(orgFilename);
		fileGroup = fileuid; 
//		String dbRootFilePath = String.format("/%s", SD_PATH);
		String dbRootFilePath = String.format("/%s", fileGroup);
		logger.debug("DB root file path : {}", dbRootFilePath);
		
		final File workPath = new File(String.format("%s/%s", tempPath, dbRootFilePath));
		logger.debug("Work path : {}", workPath);
				
		/* ########## 3. Decompress Zip File ########## */
		String uploadRootPath = Config.getCommon().getString("SENSORDATA_PACKAGE_UPLOAD_ROOT_PATH");

		doZipReal(tempDataFile, workPath, tempPath, dbRootFilePath, fileGroup, loginIDInSession, sensorEntityList, uploadRootPath);
	}
	
	public void doZipReal(File tempDataFile, File workPath, String tempPath, String dbRootFilePath, String fileGroup, 
			String loginIDInSession, List<SensorDataFileInfoEntity> sensorEntityList, String uploadRootPath)throws IOException, ArchiveException  {
		InputStream is = new FileInputStream(tempDataFile);
		ArchiveInputStream in = new ArchiveStreamFactory().createArchiveInputStream("zip", is);

		// Zip file temp path
		String zipTmpPath = workPath.getCanonicalPath();
		logger.debug("Zip temp path : {}", zipTmpPath);

		ZipArchiveEntry entry = null;
		while ((entry = (ZipArchiveEntry) in.getNextEntry()) != null) {
			logger.debug("In Zip Loop");
			String entryName = entry.getName();
			logger.debug("entryName : {}", entryName);
			String[] entryPart = entry.getName().split("/");
			StringBuilder fullPath = new StringBuilder();
			
			// entryName 에 폴더명만 있고, 파일명이 없으면 빠져나온다.
			if(entryPart.length == 1) {
				logger.info("only folder name: {}", entryName);
				continue;
				//break;
			}
			
			if(!entryName.contains(".")) {
				logger.info("only folder name(not found ext): {}", entryName);
				continue;
			}

			// todo: 시간별로 파일 분리
			fullPath.append(zipTmpPath);
			for (String e : entryPart) {
				if (e.indexOf(".") < 1) {
					fullPath.append(File.separator);
					fullPath.append(e);
				}
			}

			logger.debug("entryName : {}", entryName);
			logger.debug("fullPath : {}", fullPath);

			File zipFilePath = new File(fullPath.toString());
			if (!zipFilePath.exists()) {
				logger.info("Zip file path is mkdirs!!!! : {}", zipFilePath);
				zipFilePath.mkdirs();
			}

			File zipFile = new File(zipTmpPath, entryName);
			logger.debug("Zip file : {}", zipFile);

			OutputStream out = new FileOutputStream(zipFile);
			IOUtils.copy(in, out);
			out.close();
			
//			String fileNameExt = entryName.substring(entryName.indexOf("/") + 1, entryName.length());
			String fileNameExt = entryName.substring(entryName.lastIndexOf("/") + 1, entryName.length());
			String fileNameNoExt = fileNameExt.substring(0, fileNameExt.lastIndexOf("."));
			
			// Filename : FX01_NV1_20180830_131829_04.png
			String[] splitStr = fileNameNoExt.split("_");
			String fileAgent = splitStr[0];
			String fileSensorType = splitStr[1];
			String fileDate = splitStr[2];
			if(fileDate.length() < 8) fileDate = "20" + fileDate;
			String fileTime = splitStr[3];
			String filePackageIdSq = splitStr[4];
			
			String fileTypeCd = "";
			String createdDate;
			String registerDate;

			if ("RGB,rgb".contains(fileSensorType)) {
				fileTypeCd = "100";
			} else if ("DEPTH,depth".contains(fileSensorType)) {
				fileTypeCd = "200";
			} else if ("NV1,nv1".contains(fileSensorType)) {
				fileTypeCd = "300";
			} else if ("NV2,nv2".contains(fileSensorType)) {
				fileTypeCd = "400";
			} else if ("THERMAL,thermal".contains(fileSensorType)) {
				fileTypeCd = "500";
			} else if ("SOUND,sound".contains(fileSensorType)) {
				fileTypeCd = "600";
			} else if ("LIDAR,lidar".contains(fileSensorType)) {
				fileTypeCd = "700";
			}

			logger.debug("==========> fileAgent : {}", fileAgent);
			logger.debug("==========> fileSensorType : {}", fileSensorType);
			logger.debug("==========> fileDate : {}", fileDate);
			logger.debug("==========> fileTime : {}", fileTime);
			logger.debug("==========>  filePackageIdSq : {} ", filePackageIdSq);
			logger.debug("==========>  fileNameExt : {}", fileNameExt);
			logger.debug("==========>  fileNameNoExt : {} ", fileNameNoExt);
			logger.debug("==========>  fileTypeCd : {} ", fileTypeCd);
			
			createdDate = fileDate + fileTime;
			registerDate = DatetimeUtil.getCurrentDatetime("yyyyMMddHHmmss");
			logger.debug("Regist Dt : {}", registerDate);

			long imageVolumne = 0;
			int imageWidth = 0;
			int imageHeight = 0;
			// 이미지인 경우만 처리
			if("100".equals(fileTypeCd) || "200".equals(fileTypeCd) || "300".equals(fileTypeCd) 
					|| "400".equals(fileTypeCd) || "500".equals(fileTypeCd)) {
				String imgfilename = String.format("%s%s", tempPath, dbRootFilePath + "/" + entryName);
				File imgFile = new File(imgfilename);
				logger.debug("image filename : " + String.format("%s%s", tempPath, dbRootFilePath + "/" + entryName));
				BufferedImage bi = ImageIO.read(imgFile);
				imageVolumne = imgFile.length();
				imageWidth = bi.getWidth();
				imageHeight = bi.getHeight();
			}

			SensorDataFileInfoEntity sensorDataFileInfoEntity = new SensorDataFileInfoEntity();
			
			sensorDataFileInfoEntity.setSensorDataFileGroup(fileGroup);
			sensorDataFileInfoEntity.setSensorDataFilePackageIdSq(filePackageIdSq);
			sensorDataFileInfoEntity.setSensorDataFileAgent(fileAgent);
			sensorDataFileInfoEntity.setSensorDataFileNm(fileNameExt);
			sensorDataFileInfoEntity.setSensorDataFilePath(SD_UPLOAD_ROOT_PATH + dbRootFilePath + "/" + entryName);
			sensorDataFileInfoEntity.setSensorDataFileSize(String.valueOf(imageVolumne));
			sensorDataFileInfoEntity.setSensorDataFileCreateDt(createdDate);
			sensorDataFileInfoEntity.setSensorDataFileRegistDt(registerDate);
			sensorDataFileInfoEntity.setSensorDataFileScaleX(String.valueOf(imageWidth));
			sensorDataFileInfoEntity.setSensorDataFileScaleY(String.valueOf(imageHeight));
			// sensorDataFileInfoEntity.setSensorDataFileDownloadPathUrl(sensorDataFile.getSensorDataFileDownloadPathUrl());
			sensorDataFileInfoEntity.setSensorDataFileTypeCd(fileTypeCd);
			// sensorDataFileInfoEntity.setSensorDataFileDownloadPathCd(sensorDataFile.getSensorDataFileDownloadPathTp());
			// sensorDataFileInfoEntity.setSensorDataFilePackageIdSq(sensorDataFilePackageInfoEntity.getSensorDataFilePackageIdSq());
			sensorDataFileInfoEntity.setLoginIDInSession(loginIDInSession);
//			logger.debug("sensorDataFileInfoEntity:" + sensorDataFileInfoEntity.toString());
			
			sensorEntityList.add(sensorDataFileInfoEntity);
		}

		in.close();
		is.close();
		
		
		/* ########## 4. DB입력 ########## */
		for(SensorDataFileInfoEntity entity : sensorEntityList) {
			logger.debug("entity:" + entity.toString());
			sensorDataFileInfoMapper.insert(entity);
		}
		
		/* ########## 5. File move to upload folder ########## */
		File destPath = new File(String.format("%s/%s",
				uploadRootPath,
				dbRootFilePath));
		logger.debug("Dest path : {}", destPath);

		// Move
		FileUtils.copyDirectory(workPath, destPath);
		logger.info("Directory is moved!\r\nworkPath : {}\r\ndestPath: {}", workPath, destPath);

		/* ########## 6. Delete temp data ########## */
		CustomFileUtil.deleteAllFiles(workPath.getPath());
		logger.info("Delete folder : {}", workPath);
	}
	

	/**
	 * Image Package Upload
	 * @method addImagePackageUpload
	 * @param session
	 * @param req
	 * @throws IOException
	 * @throws ArchiveException
	 */
	public void addImagePackageUpload(HttpSession session, MultipartHttpServletRequest req) throws IOException, ArchiveException {

		// Login ID In Session
		String loginIDInSession = (String) session.getAttribute(Constants.SESSION_USER_SEQ);
		logger.debug("loginIDInSession : {}", loginIDInSession);

		// ^Loop
		for (Iterator<String> it = req.getFileNames(); it.hasNext();) {

			String fileName = it.next();
			logger.debug("fileName : {}", fileName);

			// MultipartFile
			final MultipartFile multipartFile = req.getFile(fileName);
			logger.debug("MultipartFile : {}", multipartFile);

			String originalFileName = multipartFile.getOriginalFilename();
			logger.debug("Original file name : {}", originalFileName);

			String baseFileName = FilenameUtils.getBaseName(originalFileName);
			logger.debug("baseFileName : {}", baseFileName);

			// ########## 1. Validate Check ##########
			// 1) Content Type
			String contentType = multipartFile.getContentType();
			logger.debug("contentType : {}", contentType);

			// 2) Extension
			String fileExt = FilenameUtils.getExtension(originalFileName);
			logger.debug("File extension : {}", fileExt);

			if (!StringUtils.equals("zip", fileExt)) {
				String errorMsg = String.format("Invalid file extension. -> %s", fileExt);
				logger.error(errorMsg);
				throw new RuntimeException(errorMsg);
			}

			SensorDataFilePackageInfoEntity sensorDataFilePackageInfoEntity = new SensorDataFilePackageInfoEntity();

			// DB File Path
			String workFileName = baseFileName; //DateUtil.getCurrentDate(DateUtil.SERVER_DATE_FORMAT) + "_" + baseFileName;
			logger.debug("workFileName : {}", workFileName);

  
			String dbRootFilePath = String.format("/%s", SD_PATH);

			logger.debug("DB root file path : {}", dbRootFilePath);

			// ########## 2. Move Workspace ##########
			final String workRootPath = Config.getCommon().getString("SENSORDATA_PACKAGE_UPLOAD_WORK_ROOT_PATH");
			logger.debug("workRootPath : {}", workRootPath);

			final File workPath = new File(String.format("%s/%s", workRootPath,
					dbRootFilePath));
			logger.debug("Work path : {}", workPath);

			if (!workPath.exists()) {
				logger.info("Work path is create! : {}", workPath);
				workPath.mkdirs();
			}

			final File workFile = new File(workPath, workFileName + "." + fileExt);
			sensorDataFilePackageInfoEntity.setSensorDataFilePackageSize(String.valueOf(workFile.length()));

			try {
				multipartFile.transferTo(workFile);
			} catch (IllegalStateException e) {
				logger.error("MultipartFile.transferTo - IllegalStateException : {}", e.getMessage());
				throw e;
			} catch (IOException e) {
				logger.error("MultipartFile.transferTo - IOException : {}", e.getMessage());
				throw e;
			}

			doZip(workFile, workPath, workRootPath, dbRootFilePath, loginIDInSession);
		}
		// $Loop
	}
		
		
	public void doZip(File workFile, File workPath, String workRootPath, String dbRootFilePath,
			String loginIDInSession) throws IOException, ArchiveException {
		// ########## 3. Decompress Zip File ##########
		File metaFileJson = null;
		{
			final InputStream is = new FileInputStream(workFile);
			final ArchiveInputStream in = new ArchiveStreamFactory().createArchiveInputStream("zip", is);

			// Zip file temp path
			String zipTmpPath = workPath.getCanonicalPath();
			logger.debug("Zip temp path : {}", zipTmpPath);

			ZipArchiveEntry entry = null;
			while ((entry = (ZipArchiveEntry) in.getNextEntry()) != null) {
				logger.debug("zip loop start.");
				String entryName = entry.getName();
				String[] entryPart = entry.getName().split("/");
				StringBuilder fullPath = new StringBuilder();
				
				logger.debug("entryName :" + entryName);

				// todo: 시간별로 파일 분리
				fullPath.append(zipTmpPath);
				for (String e : entryPart) {
					if (e.indexOf(".") < 1) {
						fullPath.append(File.separator);
						fullPath.append(e);
					}
				}

				logger.debug("entryName : {}", entryName);
				logger.debug("fullPath : {}", fullPath);

				File zipFilePath = new File(fullPath.toString());
				if (!zipFilePath.exists()) {
					logger.info("Zip file path is mkdirs!!!! : {}", zipFilePath);
					zipFilePath.mkdirs();
				}

				File zipFile = new File(zipTmpPath, entryName);
				logger.debug("Zip file : {}", zipFile);


				OutputStream out = new FileOutputStream(zipFile);
				IOUtils.copy(in, out);
				out.close();

				String fileNameExt = entryName.substring(entryName.indexOf("/") + 1, entryName.length());
				String fileNameNoExt = fileNameExt.substring(0, fileNameExt.lastIndexOf("."));
				String extName = fileNameNoExt.substring(0, fileNameNoExt.indexOf("_"));
				String fileTypeCd = "";
				String createdDate;

				if ("RGB,rgb".contains(extName)) {
					fileTypeCd = "100";
				} else if ("NIGHTVISION,nightvision".contains(extName)) {
					fileTypeCd = "200";
				} else if ("DEPTH,depth".contains(extName)) {
					fileTypeCd = "300";
				} else if ("THERMAL,thermal".contains(extName)) {
					fileTypeCd = "400";
				} else if ("LIDAR,lidar".contains(extName)) {
					fileTypeCd = "500";
				} else if ("SOUND,sound".contains(extName)) {
					fileTypeCd = "600";
				}

				logger.debug("fileNameNoExt : " + fileNameNoExt);
				logger.debug("extName:" + extName);
				BigDecimal b = new BigDecimal(fileNameNoExt.substring(extName.length() + 1, fileNameNoExt.length()));
				Long utc = b.longValue();
				System.out.println(b.setScale(3, BigDecimal.ROUND_DOWN));
				logger.debug("mjchoi utc : {}", utc.toString());
				logger.debug("mjchoi fileNameExt : {}", fileNameExt);
				logger.debug("mjchoi fileNameNoExt : {} ", fileNameNoExt, extName);
				logger.debug("mjchoi extName : {}", extName);
				logger.debug("mjchoi only unix time : {}", utc.toString());
				int fractVal = fileNameNoExt.indexOf(".") + 1;
				Date date = new Date(utc * 1000L);

				SimpleDateFormat formatType = new SimpleDateFormat("yyyyMMddHHmmss");
				String cat = fileNameNoExt.substring(fractVal, fractVal + 3);
				createdDate = formatType.format(date) + cat;

				SensorDataFileInfoEntity sensorDataFileInfoEntity = new SensorDataFileInfoEntity();
				File imgFile = new File(String.format("%s/%s", workRootPath, dbRootFilePath + fileNameExt));

				sensorDataFileInfoEntity.setSensorDataFileNm(fileNameExt);
				sensorDataFileInfoEntity.setSensorDataFilePath(SD_PATH + entryName);
				sensorDataFileInfoEntity.setSensorDataFileSize(String.valueOf(imgFile.length()));
				sensorDataFileInfoEntity.setSensorDataFileRegistDt(createdDate);
				sensorDataFileInfoEntity.setSensorDataFileTypeCd(fileTypeCd);
				sensorDataFileInfoEntity.setLoginIDInSession(loginIDInSession);
				logger.debug("sensorDataFileInfoEntity:" + sensorDataFileInfoEntity.toString());
				sensorDataFileInfoMapper.insert(sensorDataFileInfoEntity);
			}

			in.close();
			is.close();
		}

		String uploadPath = String.format("/%s", SD_PATH);


		// 3) Building
		// ########## 8. File move to upload folder ##########
		File destPath = new File(String.format("%s/%s",
				Config.getCommon().getString("SENSORDATA_PACKAGE_UPLOAD_ROOT_PATH"), uploadPath));
		logger.debug("Dest path : {}", destPath);

		// Move
		FileUtils.copyDirectory(workPath, destPath);
		logger.info("Directory is moved!\r\nworkPath : {}\r\ndestPath: {}", workPath, destPath);

		// todo: 확인요!! work디렉토리 삭제 안됨.
		// ########## 7. Clear data ##########
		// ======== delete ========
		boolean isDelete = workPath.delete();
		logger.info("Delete result is [{}] : {}", isDelete, workPath);
	}

	private void setTaggingGetInfo(String sensorDataFileSq, String jsonDesc, String loginIDInSession) {
		Gson gson = new Gson();
		SensorDataJsonFileDescEntity descEntity = gson.fromJson(jsonDesc, SensorDataJsonFileDescEntity.class);
		Map<String, Regions_sd> regions = descEntity.getSensorData().getRegions();
		Iterator<String> iter = regions.keySet().iterator();
		while (iter.hasNext()) {
			String key =  iter.next();
			Regions_sd region = regions.get(key);

			SensorDataTaggingGetInfoEntity taggingGetEntity = new SensorDataTaggingGetInfoEntity();
			taggingGetEntity.setSensorDataFileSq(sensorDataFileSq);
			taggingGetEntity.setFirstSensorDataTaggingDataDicIdSq(region.getRegion_attributes().getTagging_dic_1_depth_id());
			taggingGetEntity.setSecondSensorDataTaggingDataDicIdSq(region.getRegion_attributes().getTagging_dic_2_depth_id());
			taggingGetEntity.setThirdSensorDataTaggingDataDicIdSq(region.getRegion_attributes().getTagging_dic_3_depth_id());
			if( org.apache.commons.lang.StringUtils.equals("rect", region.getShape_attributes().getName()) ) {
				taggingGetEntity.setSensorDataTaggingTypeCd("100");
			} else if( org.apache.commons.lang.StringUtils.equals("polygon", region.getShape_attributes().getName()) ) {
				taggingGetEntity.setSensorDataTaggingTypeCd("200");
			}
			taggingGetEntity.setLoginIDInSession(loginIDInSession);
			sensorDataTaggingGetInfoMapper.insert(taggingGetEntity);
		}
	}

}