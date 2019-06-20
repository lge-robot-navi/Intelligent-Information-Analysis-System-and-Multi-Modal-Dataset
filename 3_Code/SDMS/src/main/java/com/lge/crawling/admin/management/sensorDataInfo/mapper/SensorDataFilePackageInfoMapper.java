package com.lge.crawling.admin.management.sensorDataInfo.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataFilePackageInfoEntity;

/**
 * Image File Package Info Mapper
 * @version : 1.0
 * @author  : Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public interface SensorDataFilePackageInfoMapper {

	/**
	 * 조회
	 * @Mehtod Name : get
	 * @param Entity
	 * @return
	 */
	public SensorDataFilePackageInfoEntity get(SensorDataFilePackageInfoEntity Entity) throws DataAccessException;

	/**
	 * 리스트
	 * @Mehtod Name : list
	 * @param Entity
	 * @return
	 */
	public List<SensorDataFilePackageInfoEntity> getList(SensorDataFilePackageInfoEntity Entity) throws DataAccessException;

	/**
	 * 전체 리스트
	 * @Mehtod Name : list
	 * @param Entity
	 * @return
	 */
	public List<SensorDataFilePackageInfoEntity> getAllList(SensorDataFilePackageInfoEntity Entity) throws DataAccessException;

	/**
	 * 이미지 파일 패키지 일련번호 조회
	 * @Mehtod Name : getSensorDataFilePackageSeq
	 * @param Entity
	 * @return
	 */
	public String getSensorDataFilePackageSeq(SensorDataFilePackageInfoEntity Entity) throws DataAccessException;

	/**
	 * 카운트
	 * @Mehtod Name : count
	 * @param Entity
	 * @return
	 */
	public int count(SensorDataFilePackageInfoEntity Entity) throws DataAccessException;

	/**
	 * 이미지 파일 패키지 일련번호 생성
	 * @Mehtod Name : insertSensorDataFilePackageIdSq
	 * @param Entity
	 * @return
	 */
	public int insertSensorDataFilePackageIdSq(SensorDataFilePackageInfoEntity Entity) throws DataAccessException;

	/**
	 * 등록
	 * @Mehtod Name : insert
	 * @param Entity
	 * @return
	 */
	public int insert(SensorDataFilePackageInfoEntity Entity) throws DataAccessException;

	/**
	 * 수정
	 * @Mehtod Name : update
	 * @param Entity
	 * @return
	 */
	public int update(SensorDataFilePackageInfoEntity Entity) throws DataAccessException;
}
