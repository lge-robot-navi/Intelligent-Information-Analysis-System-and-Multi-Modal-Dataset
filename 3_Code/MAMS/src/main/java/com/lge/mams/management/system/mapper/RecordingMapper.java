package com.lge.mams.management.system.mapper;

import java.util.List;

import com.lge.mams.management.system.entity.RecordingEntity;

/**
 * 공통코드 Mapper
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public interface RecordingMapper {

	/**
	 * 조회
	 * @Mehtod Name : get
	 * @param entity
	 * @return
	 */
	public RecordingEntity get(RecordingEntity entity);

	/**
	 * 리스트
	 * @Mehtod Name : getList
	 * @param entity
	 * @return
	 */
	public List<RecordingEntity> getList(RecordingEntity entity);

	/**
	 * 전체 리스트
	 * @Mehtod Name : getAllList
	 * @param entity
	 * @return
	 */
	public List<RecordingEntity> getAllList(RecordingEntity entity);

	/**
	 * 카운트
	 * @Mehtod Name : count
	 * @param entity
	 * @return
	 */
	public int count(RecordingEntity entity);

	/**
	 * 등록
	 * @Mehtod Name : insert
	 * @param entity
	 * @return
	 */
	public int insert(RecordingEntity entity);

	/**
	 * 수정
	 * @Mehtod Name : update
	 * @param entity
	 * @return
	 */
	public int update(RecordingEntity entity);
}
