package com.lge.crawling.admin.management.imageInfo.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.crawling.admin.common.util.Config;
import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.management.imageInfo.entity.ImageFileInfoEntity;
import com.lge.crawling.admin.management.imageInfo.entity.ImageFileWorkerInfoEntity;
import com.lge.crawling.admin.management.imageInfo.entity.ImageJsonFileInfoEntity;
import com.lge.crawling.admin.management.imageInfo.mapper.ImageFileInfoMapper;
import com.lge.crawling.admin.management.imageInfo.mapper.ImageFileWorkerInfoMapper;
import com.lge.crawling.admin.management.imageInfo.mapper.ImageJsonFileInfoMapper;

/**
 * Image File Info Service
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
@Service
public class ImageFileInfoService implements GenericService<ImageFileInfoEntity> {

	@Autowired private ImageFileInfoMapper mapper;

	@Autowired private ImageFileWorkerInfoMapper imageFileWorkerInfoMapper;

	@Autowired private ImageJsonFileInfoMapper imageJsonFileInfoMapper;

	/**
	 * (non-Javadoc)
	 * @see GenericService#get(java.lang.Object)
	 */
	@Override
	public ImageFileInfoEntity get(ImageFileInfoEntity entity) {
		return mapper.get(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	@Override
	public List<ImageFileInfoEntity> getAllList(ImageFileInfoEntity entity) {
		return mapper.getAllList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getList(java.lang.Object)
	 */
	@Override
	public List<ImageFileInfoEntity> getList(ImageFileInfoEntity entity) {
		int count = this.getCount(entity);
		entity.getPagingValue(count);
		return mapper.getList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	@Override
	public Integer getCount(ImageFileInfoEntity entity) {
		return mapper.count(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#insert(java.lang.Object)
	 */
	@Override
	public ImageFileInfoEntity insert(ImageFileInfoEntity entity) {
		mapper.insert(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#update(java.lang.Object)
	 */
	@Override
	public ImageFileInfoEntity update(ImageFileInfoEntity entity) {
		mapper.update(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	@Override
	public int delete(ImageFileInfoEntity entity) {
		return mapper.delete(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	public int deleteImageInfo(ImageFileInfoEntity entity) {

		final String rootPath = Config.getCommon().getString("IMAGE_PACKAGE_ROOT_PATH");

		int result = 0;

		// 1. 이미지 파일 작업자 정보 삭제
		ImageFileWorkerInfoEntity imageFileWorkerInfoEntity = new ImageFileWorkerInfoEntity();
		imageFileWorkerInfoEntity.setImageFileSq(entity.getImageFileSq());
		result += imageFileWorkerInfoMapper.delete(imageFileWorkerInfoEntity);

		// 2. 이미지 파일 JSON파일 정보 삭제
		ImageJsonFileInfoEntity imageJsonFileInfoEntity = new ImageJsonFileInfoEntity();
		imageJsonFileInfoEntity.setImageFileSq(entity.getImageFileSq());

		imageJsonFileInfoEntity = imageJsonFileInfoMapper.get(imageJsonFileInfoEntity);

		if (imageJsonFileInfoEntity != null) {
			File jsonFilePath = new File(String.format("%s/%s", rootPath, imageJsonFileInfoEntity.getImageJsonFilePath()));

			if (jsonFilePath.exists()) {
				jsonFilePath.delete();
			}
			result += imageJsonFileInfoMapper.delete(imageJsonFileInfoEntity);
		}

		// 3. 이미지 파일 정보 삭제
		ImageFileInfoEntity imageFileInfoEntity = new ImageFileInfoEntity();
		imageFileInfoEntity.setImageFileSq(entity.getImageFileSq());
		imageFileInfoEntity = mapper.get(imageFileInfoEntity);

		if (imageFileInfoEntity != null) {
			File imageFilePath = new File(String.format("%s/%s", rootPath, imageFileInfoEntity.getImageFilePath()));

			if (imageFilePath.exists()) {
				imageFilePath.delete();
			}
			result += mapper.delete(imageFileInfoEntity);
		}

		return result;
	}
}