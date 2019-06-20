package com.lge.crawling.admin.management.imageInfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.management.imageInfo.entity.ImageJsonFileInfoEntity;
import com.lge.crawling.admin.management.imageInfo.mapper.ImageJsonFileInfoMapper;

/**
 * Image Json File Info Service
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
@Service
public class ImageJsonFileInfoService implements GenericService<ImageJsonFileInfoEntity> {

	@Autowired private ImageJsonFileInfoMapper mapper;

	/**
	 * (non-Javadoc)
	 * @see GenericService#get(java.lang.Object)
	 */
	@Override
	public ImageJsonFileInfoEntity get(ImageJsonFileInfoEntity entity) {
		return mapper.get(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	@Override
	public List<ImageJsonFileInfoEntity> getAllList(ImageJsonFileInfoEntity entity) {
		return mapper.getAllList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getList(java.lang.Object)
	 */
	@Override
	public List<ImageJsonFileInfoEntity> getList(ImageJsonFileInfoEntity entity) {
		int count = this.getCount(entity);
		entity.getPagingValue(count);
		return mapper.getList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	@Override
	public Integer getCount(ImageJsonFileInfoEntity entity) {
		return mapper.count(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#insert(java.lang.Object)
	 */
	@Override
	public ImageJsonFileInfoEntity insert(ImageJsonFileInfoEntity entity) {
		mapper.insert(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#update(java.lang.Object)
	 */
	@Override
	public ImageJsonFileInfoEntity update(ImageJsonFileInfoEntity entity) {
		mapper.update(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	@Override
	public int delete(ImageJsonFileInfoEntity entity) {
		return mapper.delete(entity);
	}

}