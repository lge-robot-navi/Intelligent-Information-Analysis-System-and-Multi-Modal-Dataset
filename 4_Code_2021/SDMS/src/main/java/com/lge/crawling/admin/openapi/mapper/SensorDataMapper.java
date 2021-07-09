package com.lge.crawling.admin.openapi.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.lge.crawling.admin.openapi.entity.SearchEntity;
import com.lge.crawling.admin.openapi.entity.SensorDataEntity;

/**
 * Image File Info Mapper
 * @version : 1.0
 * @author  : Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public interface SensorDataMapper {

	/**
	 * 리스트
	 * @Mehtod Name : list
	 * @param Entity
	 * @return
	 */
	public List<SensorDataEntity> getList(SearchEntity Entity) throws DataAccessException;

	/**
	 * 카운트
	 * @Mehtod Name : count
	 * @param Entity
	 * @return
	 */
	public int count(SearchEntity Entity) throws DataAccessException;
}
