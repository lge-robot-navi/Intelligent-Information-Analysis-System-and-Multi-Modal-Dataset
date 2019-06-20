package com.lge.crawling.admin.management.sensorDataInfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataFileWorkerInfoEntity;
import com.lge.crawling.admin.management.sensorDataInfo.mapper.SensorDataFileWorkerInfoMapper;

/**
 * Image File Worker Info Service
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
@Service
public class SensorDataFileWorkerInfoService implements GenericService<SensorDataFileWorkerInfoEntity> {

	@Autowired private SensorDataFileWorkerInfoMapper mapper;

	/**
	 * (non-Javadoc)
	 * @see GenericService#get(java.lang.Object)
	 */
	@Override
	public SensorDataFileWorkerInfoEntity get(SensorDataFileWorkerInfoEntity entity) {
		return mapper.get(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	@Override
	public List<SensorDataFileWorkerInfoEntity> getAllList(SensorDataFileWorkerInfoEntity entity) {
		return mapper.getAllList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getList(java.lang.Object)
	 */
	@Override
	public List<SensorDataFileWorkerInfoEntity> getList(SensorDataFileWorkerInfoEntity entity) {
		int count = this.getCount(entity);
		entity.getPagingValue(count);
		return mapper.getList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	@Override
	public Integer getCount(SensorDataFileWorkerInfoEntity entity) {
		return mapper.count(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#insert(java.lang.Object)
	 */
	@Override
	public SensorDataFileWorkerInfoEntity insert(SensorDataFileWorkerInfoEntity entity) {
		mapper.insert(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#update(java.lang.Object)
	 */
	@Override
	public SensorDataFileWorkerInfoEntity update(SensorDataFileWorkerInfoEntity entity) {
		mapper.update(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	@Override
	public int delete(SensorDataFileWorkerInfoEntity entity) {
		return mapper.delete(entity);
	}

}