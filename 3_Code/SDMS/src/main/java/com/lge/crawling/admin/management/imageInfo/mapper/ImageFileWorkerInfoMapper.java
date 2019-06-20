package com.lge.crawling.admin.management.imageInfo.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.lge.crawling.admin.management.imageInfo.entity.ImageFileWorkerInfoEntity;

/**
 * Image File Worker Info Mapper
 * @version : 1.0
 * @author  : Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public interface ImageFileWorkerInfoMapper {

	/**
	 * 조회
	 * @Mehtod Name : get
	 * @param Entity
	 * @return
	 */
	public ImageFileWorkerInfoEntity get(ImageFileWorkerInfoEntity Entity) throws DataAccessException;

	/**
	 * 리스트
	 * @Mehtod Name : list
	 * @param Entity
	 * @return
	 */
	public List<ImageFileWorkerInfoEntity> getList(ImageFileWorkerInfoEntity Entity) throws DataAccessException;

	/**
	 * 전체 리스트
	 * @Mehtod Name : list
	 * @param Entity
	 * @return
	 */
	public List<ImageFileWorkerInfoEntity> getAllList(ImageFileWorkerInfoEntity Entity) throws DataAccessException;

	/**
	 * 카운트
	 * @Mehtod Name : count
	 * @param Entity
	 * @return
	 */
	public int count(ImageFileWorkerInfoEntity Entity) throws DataAccessException;

	/**
	 * 사용자일련번호 생성
	 * @Mehtod Name : insertAdminIdSq
	 * @param Entity
	 * @return
	 */
	public int insertAdminIdSq(ImageFileWorkerInfoEntity Entity) throws DataAccessException;

	/**
	 * 등록
	 * @Mehtod Name : insert
	 * @param Entity
	 * @return
	 */
	public int insert(ImageFileWorkerInfoEntity Entity) throws DataAccessException;

	/**
	 * 수정
	 * @Mehtod Name : update
	 * @param Entity
	 * @return
	 */
	public int update(ImageFileWorkerInfoEntity Entity) throws DataAccessException;

	/**
	 * 삭제
	 * @Mehtod Name : delete
	 * @param Entity
	 * @return
	 */
	public int delete(ImageFileWorkerInfoEntity Entity) throws DataAccessException;
}
