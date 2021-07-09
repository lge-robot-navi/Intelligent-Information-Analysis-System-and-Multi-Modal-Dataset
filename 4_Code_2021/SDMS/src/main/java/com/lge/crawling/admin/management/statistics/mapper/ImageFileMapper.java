package com.lge.crawling.admin.management.statistics.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.lge.crawling.admin.management.statistics.entity.ImageFileEntity;

/**
 * Image File Mapper
 * @version : 1.0
 * @author  : Copyright (c) 2015 by UBIVELOX CORP. All Rights Reserved.
 */
public interface ImageFileMapper {

	/**
	 * 조회
	 * @Mehtod Name : get
	 * @param Entity
	 * @return
	 */
	public ImageFileEntity get(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 리스트
	 * @Mehtod Name : list
	 * @param Entity
	 * @return
	 */
	public List<ImageFileEntity> getList(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 전체 리스트
	 * @Mehtod Name : list
	 * @param Entity
	 * @return
	 */
	public List<ImageFileEntity> getAllList(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 카운트
	 * @Mehtod Name : count
	 * @param Entity
	 * @return
	 */
	public int count(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 등록
	 * @Mehtod Name : insert
	 * @param Entity
	 * @return
	 */
	public int insert(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 수정
	 * @Mehtod Name : update
	 * @param Entity
	 * @return
	 */
	public int update(ImageFileEntity Entity) throws DataAccessException;
}
