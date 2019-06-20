package com.lge.crawling.admin.management.sensorDataInfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataJsonFileInfoEntity;
import com.lge.crawling.admin.management.sensorDataInfo.mapper.SensorDataJsonFileInfoMapper;

/**
 * Image Json File Info Service
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
@Service
public class SensorDataJsonFileInfoService implements GenericService<SensorDataJsonFileInfoEntity> {

	@Autowired private SensorDataJsonFileInfoMapper mapper;

	/**
	 * (non-Javadoc)
	 * @see GenericService#get(java.lang.Object)
	 */
	@Override
	public SensorDataJsonFileInfoEntity get(SensorDataJsonFileInfoEntity entity) {
		return mapper.get(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	@Override
	public List<SensorDataJsonFileInfoEntity> getAllList(SensorDataJsonFileInfoEntity entity) {
		return mapper.getAllList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getList(java.lang.Object)
	 */
	@Override
	public List<SensorDataJsonFileInfoEntity> getList(SensorDataJsonFileInfoEntity entity) {
		int count = this.getCount(entity);
		entity.getPagingValue(count);
		return mapper.getList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	@Override
	public Integer getCount(SensorDataJsonFileInfoEntity entity) {
		return mapper.count(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#insert(java.lang.Object)
	 */
	@Override
	public SensorDataJsonFileInfoEntity insert(SensorDataJsonFileInfoEntity entity) {
		mapper.insert(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#update(java.lang.Object)
	 */
	@Override
	public SensorDataJsonFileInfoEntity update(SensorDataJsonFileInfoEntity entity) {
		mapper.update(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	@Override
	public int delete(SensorDataJsonFileInfoEntity entity) {
		return mapper.delete(entity);
	}

}