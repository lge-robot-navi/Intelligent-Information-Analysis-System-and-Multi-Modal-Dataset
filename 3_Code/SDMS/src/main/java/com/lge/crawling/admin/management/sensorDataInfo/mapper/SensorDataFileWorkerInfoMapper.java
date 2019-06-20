package com.lge.crawling.admin.management.sensorDataInfo.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataFileWorkerInfoEntity;

/**
 * Image File Worker Info Mapper
 * @version : 1.0
 * @author  : Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public interface SensorDataFileWorkerInfoMapper {

	/**
	 * 조회
	 * @Mehtod Name : get
	 * @param Entity
	 * @return
	 */
	public SensorDataFileWorkerInfoEntity get(SensorDataFileWorkerInfoEntity Entity) throws DataAccessException;

	/**
	 * 리스트
	 * @Mehtod Name : list
	 * @param Entity
	 * @return
	 */
	public List<SensorDataFileWorkerInfoEntity> getList(SensorDataFileWorkerInfoEntity Entity) throws DataAccessException;

	/**
	 * 전체 리스트
	 * @Mehtod Name : list
	 * @param Entity
	 * @return
	 */
	public List<SensorDataFileWorkerInfoEntity> getAllList(SensorDataFileWorkerInfoEntity Entity) throws DataAccessException;

	/**
	 * 카운트
	 * @Mehtod Name : count
	 * @param Entity
	 * @return
	 */
	public int count(SensorDataFileWorkerInfoEntity Entity) throws DataAccessException;

	/**
	 * 사용자일련번호 생성
	 * @Mehtod Name : insertAdminIdSq
	 * @param Entity
	 * @return
	 */
	public int insertAdminIdSq(SensorDataFileWorkerInfoEntity Entity) throws DataAccessException;

	/**
	 * 등록
	 * @Mehtod Name : insert
	 * @param Entity
	 * @return
	 */
	public int insert(SensorDataFileWorkerInfoEntity Entity) throws DataAccessException;

	/**
	 * 수정
	 * @Mehtod Name : update
	 * @param Entity
	 * @return
	 */
	public int update(SensorDataFileWorkerInfoEntity Entity) throws DataAccessException;

	/**
	 * 삭제
	 * @Mehtod Name : delete
	 * @param Entity
	 * @return
	 */
	public int delete(SensorDataFileWorkerInfoEntity Entity) throws DataAccessException;
}
