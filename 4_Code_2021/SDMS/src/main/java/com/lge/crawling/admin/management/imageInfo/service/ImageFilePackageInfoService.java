package com.lge.crawling.admin.management.imageInfo.service;

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
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.lge.crawling.admin.management.image.entity.ImageTaggingGetInfoEntity;
import com.lge.crawling.admin.management.image.mapper.ImageTaggingGetInfoMapper;
import com.lge.crawling.admin.management.image.service.ImageTaggingGetInfoService;
import com.lge.crawling.admin.management.imageInfo.entity.*;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
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
import com.lge.crawling.admin.common.util.DateUtil;
import com.lge.crawling.admin.common.validate.EntityValidator;
import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.constants.Constants;
import com.lge.crawling.admin.management.imageInfo.mapper.ImageFileInfoMapper;
import com.lge.crawling.admin.management.imageInfo.mapper.ImageFilePackageInfoMapper;
import com.lge.crawling.admin.management.imageInfo.mapper.ImageJsonFileInfoMapper;

/**
 * Image File Package Info Service
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
@Service
public class ImageFilePackageInfoService implements GenericService<ImageFilePackageInfoEntity> {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(ImageFilePackageInfoService.class);

	private static final String PACKAGE_PATH = "/package";

	private static final String IMG_PATH = "/image/";

	private static final String JSON_PATH = "/json/";

	private static final String UPLOAD_ROOT_PATH = "/crawling/upload";

	@Autowired private ImageFilePackageInfoMapper mapper;

	@Autowired private ImageFileInfoMapper imageFileInfoMapper;

	@Autowired private ImageJsonFileInfoMapper imageJsonFileInfoMapper;

	@Autowired private ImageTaggingGetInfoMapper imageTaggingGetInfoMapper;

	/**
	 * (non-Javadoc)
	 * @see GenericService#get(java.lang.Object)
	 */
	@Override
	public ImageFilePackageInfoEntity get(ImageFilePackageInfoEntity entity) {
		return mapper.get(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	@Override
	public List<ImageFilePackageInfoEntity> getAllList(ImageFilePackageInfoEntity entity) {
		return mapper.getAllList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getList(java.lang.Object)
	 */
	@Override
	public List<ImageFilePackageInfoEntity> getList(ImageFilePackageInfoEntity entity) {
		int count = this.getCount(entity);
		entity.getPagingValue(count);
		return mapper.getList(entity);
	}


	public String getImageFilePackageSeq(ImageFilePackageInfoEntity entity) {
		return mapper.getImageFilePackageSeq(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	@Override
	public Integer getCount(ImageFilePackageInfoEntity entity) {
		return mapper.count(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#insert(java.lang.Object)
	 */
	@Override
	public ImageFilePackageInfoEntity insert(ImageFilePackageInfoEntity entity) {
		mapper.insert(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#update(java.lang.Object)
	 */
	@Override
	public ImageFilePackageInfoEntity update(ImageFilePackageInfoEntity entity) {
		mapper.update(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	@Override
	public int delete(ImageFilePackageInfoEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
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

			ImageFilePackageInfoEntity imageFilePackageInfoEntity = new ImageFilePackageInfoEntity();

			// DB File Path
			String workFileName = baseFileName; //DateUtil.getCurrentDate(DateUtil.SERVER_DATE_FORMAT) + "_" + baseFileName;
			logger.debug("workFileName : {}", workFileName);

			String dbRootFilePath = String.format("/%s/%s/%s",
					DateUtil.getCurrentDate("yyyy"),
					DateUtil.getCurrentDate("MMdd"),
					//baseFileName,
					workFileName);

			logger.debug("DB root file path : {}", dbRootFilePath);

			// ########## 2. Move Workspace ##########
			final String workRootPath = Config.getCommon().getString("IMAGE_PACKAGE_UPLOAD_WORK_ROOT_PATH");
			logger.debug("workRootPath : {}", workRootPath);

			final File workPath = new File(String.format("%s/%s", workRootPath,
					dbRootFilePath));
			logger.debug("Work path : {}", workPath);

			if (!workPath.exists()) {
				logger.info("Work path is create! : {}", workPath);
				workPath.mkdirs();
			}

			final File workFile = new File(workPath, workFileName + "." + fileExt);
			imageFilePackageInfoEntity.setImageFilePackageSize(String.valueOf(workFile.length()));

			try {
				multipartFile.transferTo(workFile);
			} catch (IllegalStateException e) {
				logger.error("MultipartFile.transferTo - IllegalStateException : {}", e.getMessage());
				throw e;
			} catch (IOException e) {
				logger.error("MultipartFile.transferTo - IOException : {}", e.getMessage());
				throw e;
			}

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

					String entryName = entry.getName();
					String[] entryPart = entry.getName().split("/");
					StringBuilder fullPath = new StringBuilder();
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

					//String entryExt = FilenameUtils.getExtension(entryName);
					if (StringUtils.equals("lds_img.info", entryName)) {
						metaFileJson = new File(zipFile.getAbsolutePath());
					}

					OutputStream out = new FileOutputStream(zipFile);
					IOUtils.copy(in, out);
					out.close();
				}

				in.close();
				is.close();
			}

			/*if (!metaFileJson.exists()) {
				String errorMsg = "metaJsonFileName is empty!";
				logger.error(errorMsg);
				throw new RuntimeException(errorMsg);
			}*/

			// ########## 4. Parse Meta JSON ##########
			logger.debug("metaJsonFileName : {}", metaFileJson);

			Gson gson = new GsonBuilder().create();
			String metaJsonStr = FileUtils.readFileToString(metaFileJson, "UTF-8");
			IdsImgInfoEntity idsImgInfoEntity = gson.fromJson(metaJsonStr, IdsImgInfoEntity.class);

			String jsonString = gson.toJson (idsImgInfoEntity);
			logger.debug("imageFileJsonEntity : {}", jsonString);

			String uploadPath = String.format("/%s/%s/%s",
					DateUtil.getCurrentDate("yyyy"),
					DateUtil.getCurrentDate("MMdd"),
					idsImgInfoEntity.getImageFilePackageIdSq());

			// 패키지명 지정된 이름으로 변경
			imageFilePackageInfoEntity.setImageFilePackageIdSq(idsImgInfoEntity.getImageFilePackageIdSq());
			imageFilePackageInfoEntity.setImageFilePackageNm(idsImgInfoEntity.getImageFilePackageFileNm());
			imageFilePackageInfoEntity.setImageFilePackagePath(uploadPath);
			imageFilePackageInfoEntity.setLoginIDInSession(loginIDInSession);

			String packageName = imageFilePackageInfoEntity.getImageFilePackageNm();
			if (StringUtils.isNotBlank(packageName)) {
				File renameFile = new File(workPath, packageName + "." + fileExt);
				workFile.renameTo(renameFile);
			}

			// ########## 5. Validate Meta File ##########
			if (EntityValidator.isValid(idsImgInfoEntity)) {
				String errorMsg = "imageFileJsonEntity is invalid!!!!!!!!!";
				logger.error(errorMsg);
				throw new RuntimeException(errorMsg);
			}

			// ======== 2) Image Check ========
			for (ImageFileListEntity imageFile : idsImgInfoEntity.getImageFileList()) {
				// Set image path
				imageFile.setImageFileLoc(dbRootFilePath + IMG_PATH);

				File imgFile = new File(workRootPath + imageFile.getImageFileLoc(),
						imageFile.getImageFileNm());
				logger.debug("Image File : {}", imgFile);

				if (!imgFile.exists()) {
					throw new FileNotFoundException("Image File not founed exception. : " + imgFile);
				}
			}

			// ########## 6. DB Logic ##########

			// ======== DB Insert Logic ========
			// 2) Image File Package
			if (idsImgInfoEntity.getWorkTp().equals("I")) {

				ImageFilePackageInfoEntity packageDupCheck = mapper.get(imageFilePackageInfoEntity);

				if (packageDupCheck == null) {
					mapper.insert(imageFilePackageInfoEntity);
					mapper.insertImageFilePackageIdSq(imageFilePackageInfoEntity);
				}

				for (ImageFileListEntity imageFile : idsImgInfoEntity.getImageFileList()) {
					logger.debug("imageFile:"+imageFile.toString());

					ImageFileInfoEntity imageDupCheck = new ImageFileInfoEntity();
					imageDupCheck.setImageFileDownloadPathUrl(imageFile.getImageFileDownloadPathUrl());
					imageDupCheck = imageFileInfoMapper.get(imageDupCheck);

					ImageFileInfoEntity imageFileInfoEntity = new ImageFileInfoEntity();

					if (imageDupCheck == null) {
						File imgFile = new File(String.format("%s/%s", workRootPath, dbRootFilePath+ IMG_PATH + imageFile.getImageFileNm()));
						imageFile.setImageFileLoc(uploadPath + IMG_PATH);
						imageFileInfoEntity.setImageFileNm(imageFile.getImageFileNm());
						imageFileInfoEntity.setImageFilePath(UPLOAD_ROOT_PATH+imageFile.getImageFileLoc()+imageFile.getImageFileNm());
						imageFileInfoEntity.setImageFileSize(String.valueOf(imgFile.length()));
						imageFileInfoEntity.setImageFileScaleX(imageFile.getImageFileSizeX());
						imageFileInfoEntity.setImageFileScaleY(imageFile.getImageFileSizeY());
						imageFileInfoEntity.setImageFileDownloadPathUrl(imageFile.getImageFileDownloadPathUrl());
						imageFileInfoEntity.setImageFileTypeCd(imageFile.getImageFileTp());
						imageFileInfoEntity.setImageFileDownloadPathCd(imageFile.getImageFileDownloadPathTp());
						imageFileInfoEntity.setImageFilePackageIdSq(imageFilePackageInfoEntity.getImageFilePackageIdSq());
						imageFileInfoEntity.setLoginIDInSession(loginIDInSession);
						logger.debug("imageFileInfoEntity:"+imageFileInfoEntity.toString());
						imageFileInfoMapper.insert(imageFileInfoEntity);
					} else {
						imageFileInfoEntity.setImageFileSq(imageDupCheck.getImageFileSq());
					}

					// 태깅 존재할 경우 SKIP
					ImageFileInfoEntity imageFileTaggingCheck = new ImageFileInfoEntity();
					imageFileTaggingCheck.setTaggingYn("Y");
					imageFileTaggingCheck.setImageFileSq(imageFileInfoEntity.getImageFileSq());
					imageFileTaggingCheck = imageFileInfoMapper.get(imageFileTaggingCheck);

					if (imageFileTaggingCheck != null) {
						logger.debug("SKIP : Tagging Data Exist!!");
						continue;
					}

					ImageJsonFileInfoEntity imageJsonFileInfoEntity = new ImageJsonFileInfoEntity();
					imageJsonFileInfoEntity.setLoginIDInSession(loginIDInSession);
					imageJsonFileInfoEntity.setImageFileSq(imageFileInfoEntity.getImageFileSq());
					if (StringUtils.isNotBlank(imageFile.getImageJsonFileNm()) && StringUtils.isNotBlank(imageFile.getImageJsonFileLoc())) {
						File jsonFile = new File(String.format("%s/%s", workRootPath, dbRootFilePath + JSON_PATH + imageFile.getImageJsonFileNm()));
						logger.debug("jsonFile File : {}", jsonFile);
						imageFile.setImageJsonFileLoc(uploadPath + JSON_PATH);
						// ImageJsonFileNm, ImageJsonFilePath, ImageJsonFileSize 사용 안함
						/*imageJsonFileInfoEntity.setImageJsonFileNm(imageFile.getImageJsonFileNm().split("\\.")[0]);
						imageJsonFileInfoEntity.setImageJsonFilePath(UPLOAD_ROOT_PATH+imageFile.getImageJsonFileLoc()+imageFile.getImageJsonFileNm());
						imageJsonFileInfoEntity.setImageJsonFileSize(String.valueOf(jsonFile.length()));*/
						// json filt to String
						String jsonFileString = FileUtils.readFileToString(jsonFile, "UTF-8");
						logger.debug("Image Json File Desc : {}", jsonFileString);
						JSONObject json = new JSONObject();
						json.put("annotation", new JSONObject(jsonFileString));
						String xml = XML.toString(json);
						imageJsonFileInfoEntity.setImageJsonFileDesc(HtmlUtils.htmlUnescape(URLDecoder.decode(jsonFileString, "UTF-8")));
						imageJsonFileInfoEntity.setImageJsonXmlConvFileDesc(HtmlUtils.htmlUnescape(URLDecoder.decode(xml, "UTF-8")));
						imageJsonFileInfoMapper.insert(imageJsonFileInfoEntity);
						this.setTaggingGetInfo(imageFileInfoEntity.getImageFileSq(), jsonFileString, loginIDInSession);
					}
				}
			} else {
				mapper.update(imageFilePackageInfoEntity);
				for (ImageFileListEntity imageFile : idsImgInfoEntity.getImageFileList()) {
					logger.debug("imageFile:"+imageFile.toString());

					ImageFileInfoEntity imageDupCheck = new ImageFileInfoEntity();
					imageDupCheck.setImageFileDownloadPathUrl(imageFile.getImageFileDownloadPathUrl());
					imageDupCheck = imageFileInfoMapper.get(imageDupCheck);

					ImageFileInfoEntity imageFileInfoEntity = new ImageFileInfoEntity();

					if (imageDupCheck == null) {
						File imgFile = new File(String.format("%s/%s", workRootPath, dbRootFilePath + imageFile.getImageFileNm()));
						imageFile.setImageFileLoc(uploadPath + IMG_PATH);
						imageFileInfoEntity.setImageFileNm(imageFile.getImageFileNm());
						imageFileInfoEntity.setImageFilePath(UPLOAD_ROOT_PATH+imageFile.getImageFileLoc()+imageFile.getImageFileNm());
						imageFileInfoEntity.setImageFileSize(String.valueOf(imgFile.length()));
						imageFileInfoEntity.setImageFileScaleX(imageFile.getImageFileSizeX());
						imageFileInfoEntity.setImageFileScaleY(imageFile.getImageFileSizeY());
						imageFileInfoEntity.setImageFileDownloadPathUrl(imageFile.getImageFileDownloadPathUrl());
						imageFileInfoEntity.setImageFileTypeCd(imageFile.getImageFileTp());
						imageFileInfoEntity.setImageFileDownloadPathCd(imageFile.getImageFileDownloadPathTp());
						imageFileInfoEntity.setImageFilePackageIdSq(imageFilePackageInfoEntity.getImageFilePackageIdSq());
						imageFileInfoEntity.setLoginIDInSession(loginIDInSession);
						logger.debug("imageFileInfoEntity:"+imageFileInfoEntity.toString());
						imageFileInfoMapper.update(imageFileInfoEntity);
					} else {
						imageFileInfoEntity.setImageFileSq(imageDupCheck.getImageFileSq());
					}

					// 태깅 존재할 경우 SKIP
					ImageFileInfoEntity imageFileTaggingCheck = new ImageFileInfoEntity();
					imageFileTaggingCheck.setTaggingYn("Y");
					imageFileTaggingCheck.setImageFileSq(imageFileInfoEntity.getImageFileSq());
					imageFileTaggingCheck = imageFileInfoMapper.get(imageFileTaggingCheck);

					if (imageFileTaggingCheck != null) {
						logger.debug("SKIP : Tagging Data Exist!!");
						continue;
					}

					// Json File
					ImageJsonFileInfoEntity imageJsonFileInfoEntity = new ImageJsonFileInfoEntity();
					imageJsonFileInfoEntity.setLoginIDInSession(loginIDInSession);
					imageJsonFileInfoEntity.setImageJsonFileDesc(HtmlUtils.htmlUnescape(URLDecoder.decode(jsonString, "UTF-8")));
					if (StringUtils.isNotBlank(imageFile.getImageJsonFileNm()) && StringUtils.isNotBlank(imageFile.getImageJsonFileLoc())) {
						File jsonFile = new File(String.format("%s/%s", workRootPath, dbRootFilePath + imageFile.getImageJsonFileNm()));
						imageFile.setImageJsonFileLoc(uploadPath + JSON_PATH);
						// ImageJsonFileNm, ImageJsonFilePath, ImageJsonFileSize 사용 안함
						/*imageJsonFileInfoEntity.setImageJsonFileNm(imageFile.getImageJsonFileNm().split("\\.")[0]);
						imageJsonFileInfoEntity.setImageJsonFilePath(UPLOAD_ROOT_PATH+imageFile.getImageJsonFileLoc()+imageFile.getImageJsonFileNm());
						imageJsonFileInfoEntity.setImageJsonFileSize(String.valueOf(jsonFile.length()));*/
						logger.debug("jsonFile File : {}", jsonFile);
						// json filt to String
						String jsonFileString = FileUtils.readFileToString(jsonFile, "UTF-8");
						logger.debug("Image Json File Desc : {}", jsonFileString);
						JSONObject json = new JSONObject();
						json.put("annotation", new JSONObject(jsonFileString));
						String xml = XML.toString(json);
						imageJsonFileInfoEntity.setImageJsonFileDesc(HtmlUtils.htmlUnescape(URLDecoder.decode(jsonFileString, "UTF-8")));
						imageJsonFileInfoEntity.setImageJsonXmlConvFileDesc(HtmlUtils.htmlUnescape(URLDecoder.decode(xml, "UTF-8")));
						imageJsonFileInfoMapper.update(imageJsonFileInfoEntity);
						this.setTaggingGetInfo(imageFileInfoEntity.getImageFileSq(), jsonFileString, loginIDInSession);
					}
				}
			}

			// 3) Building
			// ########## 8. File move to upload folder ##########
			File destPath = new File(String.format("%s/%s",
					Config.getCommon().getString("IMAGE_PACKAGE_UPLOAD_ROOT_PATH"),
					uploadPath));
			logger.debug("Dest path : {}", destPath);

			// Move
			FileUtils.copyDirectory(workPath, destPath);
			logger.info("Directory is moved!\r\nworkPath : {}\r\ndestPath: {}", workPath, destPath);

			// ########## 7. Clear data ##########
			// ======== delete ========
			boolean isDelete = workPath.delete();
			logger.info("Delete result is [{}] : {}", isDelete, workPath);
		}
		// $Loop
	}

	private void setTaggingGetInfo(String imageFileSq, String jsonDesc, String loginIDInSession) {
		Gson gson = new Gson();
		ImageJsonFileDescEntity descEntity = gson.fromJson(jsonDesc, ImageJsonFileDescEntity.class);
		Map<String, Regions> regions = descEntity.getImage().getRegions();
		Iterator<String> iter = regions.keySet().iterator();
		while (iter.hasNext()) {
			String key =  iter.next();
			Regions region = regions.get(key);

			ImageTaggingGetInfoEntity taggingGetEntity = new ImageTaggingGetInfoEntity();
			taggingGetEntity.setImageFileSq(imageFileSq);
			taggingGetEntity.setFirstImageTaggingDataDicIdSq(region.getRegion_attributes().getTagging_dic_1_depth_id());
			taggingGetEntity.setSecondImageTaggingDataDicIdSq(region.getRegion_attributes().getTagging_dic_2_depth_id());
			taggingGetEntity.setThirdImageTaggingDataDicIdSq(region.getRegion_attributes().getTagging_dic_3_depth_id());
			if( org.apache.commons.lang.StringUtils.equals("rect", region.getShape_attributes().getName()) ) {
				taggingGetEntity.setImageTaggingTypeCd("100");
			} else if( org.apache.commons.lang.StringUtils.equals("polygon", region.getShape_attributes().getName()) ) {
				taggingGetEntity.setImageTaggingTypeCd("200");
			}
			taggingGetEntity.setLoginIDInSession(loginIDInSession);
			imageTaggingGetInfoMapper.insert(taggingGetEntity);
		}
	}

}