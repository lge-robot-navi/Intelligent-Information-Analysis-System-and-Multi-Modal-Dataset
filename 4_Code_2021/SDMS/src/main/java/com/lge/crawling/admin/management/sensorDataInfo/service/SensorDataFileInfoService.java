package com.lge.crawling.admin.management.sensorDataInfo.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.crawling.admin.common.util.Config;
import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.management.sensorData.entity.SensorDataTaggingEntity;
import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataFileInfoEntity;
import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataFileWorkerInfoEntity;
import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataJsonFileInfoEntity;
import com.lge.crawling.admin.management.sensorDataInfo.mapper.SensorDataFileInfoMapper;
import com.lge.crawling.admin.management.sensorDataInfo.mapper.SensorDataFileWorkerInfoMapper;
import com.lge.crawling.admin.management.sensorDataInfo.mapper.SensorDataJsonFileInfoMapper;

/**
 * Image File Info Service
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
@Service
public class SensorDataFileInfoService implements GenericService<SensorDataFileInfoEntity> {

	@Autowired private SensorDataFileInfoMapper mapper;

	@Autowired private SensorDataFileWorkerInfoMapper sensorDataFileWorkerInfoMapper;

	@Autowired private SensorDataJsonFileInfoMapper sensorDataJsonFileInfoMapper;

	/**
	 * (non-Javadoc)
	 * @see GenericService#get(java.lang.Object)
	 */
	@Override
	public SensorDataFileInfoEntity get(SensorDataFileInfoEntity entity) {
		return mapper.get(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	@Override
	public List<SensorDataFileInfoEntity> getAllList(SensorDataFileInfoEntity entity) {
		return mapper.getAllList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getList(java.lang.Object)
	 */
	@Override
	public List<SensorDataFileInfoEntity> getList(SensorDataFileInfoEntity entity) {
		int count = this.getCount(entity);
		entity.getPagingValue(count);
		return mapper.getList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	@Override
	public Integer getCount(SensorDataFileInfoEntity entity) {
		return mapper.count(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#insert(java.lang.Object)
	 */
	@Override
	public SensorDataFileInfoEntity insert(SensorDataFileInfoEntity entity) {
		mapper.insert(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#update(java.lang.Object)
	 */
	@Override
	public SensorDataFileInfoEntity update(SensorDataFileInfoEntity entity) {
		mapper.update(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	@Override
	public int delete(SensorDataFileInfoEntity entity) {
		return mapper.delete(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	public int deleteImageInfo(SensorDataFileInfoEntity entity) {

		final String rootPath = Config.getCommon().getString("SENSORDATA_PACKAGE_ROOT_PATH");

		int result = 0;

		// 1. 이미지 파일 작업자 정보 삭제
		SensorDataFileWorkerInfoEntity sensorDataFileWorkerInfoEntity = new SensorDataFileWorkerInfoEntity();
		sensorDataFileWorkerInfoEntity.setSensorDataFileSq(entity.getSensorDataFileSq());
		result += sensorDataFileWorkerInfoMapper.delete(sensorDataFileWorkerInfoEntity);

		// 2. 이미지 파일 JSON파일 정보 삭제
		SensorDataJsonFileInfoEntity sensorDataJsonFileInfoEntity = new SensorDataJsonFileInfoEntity();
		sensorDataJsonFileInfoEntity.setSensorDataFileSq(entity.getSensorDataFileSq());

		sensorDataJsonFileInfoEntity = sensorDataJsonFileInfoMapper.get(sensorDataJsonFileInfoEntity);

		if (sensorDataJsonFileInfoEntity != null) {
			File jsonFilePath = new File(String.format("%s/%s", rootPath, sensorDataJsonFileInfoEntity.getSensorDataJsonFilePath()));

			if (jsonFilePath.exists()) {
				jsonFilePath.delete();
			}
			result += sensorDataJsonFileInfoMapper.delete(sensorDataJsonFileInfoEntity);
		}

		// 3. 이미지 파일 정보 삭제
		SensorDataFileInfoEntity sensorDataFileInfoEntity = new SensorDataFileInfoEntity();
		sensorDataFileInfoEntity.setSensorDataFileSq(entity.getSensorDataFileSq());
		sensorDataFileInfoEntity = mapper.get(sensorDataFileInfoEntity);

		if (sensorDataFileInfoEntity != null) {
			File sensorDataFilePath = new File(String.format("%s/%s", rootPath, sensorDataFileInfoEntity.getSensorDataFilePath()));

			if (sensorDataFilePath.exists()) {
				sensorDataFilePath.delete();
			}
			result += mapper.delete(sensorDataFileInfoEntity);
		}

		return result;
	}
	
	
	public List<SensorDataFileInfoEntity> getFileTypeList(SensorDataTaggingEntity entity) {
		return mapper.getFileTypeList(entity);
	}
}