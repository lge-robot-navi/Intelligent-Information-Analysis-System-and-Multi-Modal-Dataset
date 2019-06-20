package com.lge.crawling.admin.management.imageTaggingDic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.management.imageTaggingDic.entity.TaggingDicEntity;
import com.lge.crawling.admin.management.imageTaggingDic.mapper.TaggingDicMapper;

/**
 * Tagging Dictionary Service
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
@Service
public class TaggingDicService implements GenericService<TaggingDicEntity> {

	@Autowired private TaggingDicMapper mapper;

	/**
	 * (non-Javadoc)
	 * @see GenericService#get(java.lang.Object)
	 */
	@Override
	public TaggingDicEntity get(TaggingDicEntity entity) {
		return mapper.get(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	@Override
	public List<TaggingDicEntity> getAllList(TaggingDicEntity entity) {
		return mapper.getAllList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	public List<TaggingDicEntity> getImageDicDownloodList(TaggingDicEntity entity) {
		return mapper.getImageDicDownloodList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	public List<TaggingDicEntity> getImageDicList(TaggingDicEntity entity) {
		int count = this.getImageDicListCount(entity);
		entity.getPagingValue(count);
		return mapper.getImageDicList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getList(java.lang.Object)
	 */
	@Override
	public List<TaggingDicEntity> getList(TaggingDicEntity entity) {
		int count = this.getCount(entity);
		entity.getPagingValue(count);
		return mapper.getList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	@Override
	public Integer getCount(TaggingDicEntity entity) {
		return mapper.count(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	public Integer getImageDicListCount(TaggingDicEntity entity) {
		return mapper.getImageDicListCount(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#insert(java.lang.Object)
	 */
	@Override
	public TaggingDicEntity insert(TaggingDicEntity entity) {
		mapper.insert(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#update(java.lang.Object)
	 */
	@Override
	public TaggingDicEntity update(TaggingDicEntity entity) {
		mapper.update(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	@Override
	public int delete(TaggingDicEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}
}