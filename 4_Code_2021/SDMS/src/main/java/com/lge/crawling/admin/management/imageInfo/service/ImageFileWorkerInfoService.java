package com.lge.crawling.admin.management.imageInfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.management.imageInfo.entity.ImageFileWorkerInfoEntity;
import com.lge.crawling.admin.management.imageInfo.mapper.ImageFileWorkerInfoMapper;

/**
 * Image File Worker Info Service
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
@Service
public class ImageFileWorkerInfoService implements GenericService<ImageFileWorkerInfoEntity> {

	@Autowired private ImageFileWorkerInfoMapper mapper;

	/**
	 * (non-Javadoc)
	 * @see GenericService#get(java.lang.Object)
	 */
	@Override
	public ImageFileWorkerInfoEntity get(ImageFileWorkerInfoEntity entity) {
		return mapper.get(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	@Override
	public List<ImageFileWorkerInfoEntity> getAllList(ImageFileWorkerInfoEntity entity) {
		return mapper.getAllList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getList(java.lang.Object)
	 */
	@Override
	public List<ImageFileWorkerInfoEntity> getList(ImageFileWorkerInfoEntity entity) {
		int count = this.getCount(entity);
		entity.getPagingValue(count);
		return mapper.getList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	@Override
	public Integer getCount(ImageFileWorkerInfoEntity entity) {
		return mapper.count(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#insert(java.lang.Object)
	 */
	@Override
	public ImageFileWorkerInfoEntity insert(ImageFileWorkerInfoEntity entity) {
		mapper.insert(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#update(java.lang.Object)
	 */
	@Override
	public ImageFileWorkerInfoEntity update(ImageFileWorkerInfoEntity entity) {
		mapper.update(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	@Override
	public int delete(ImageFileWorkerInfoEntity entity) {
		return mapper.delete(entity);
	}

}