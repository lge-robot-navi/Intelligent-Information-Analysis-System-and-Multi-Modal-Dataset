package com.lge.crawling.admin.management.system.mapper;

import java.util.List;

import com.lge.crawling.admin.management.system.entity.AdminGroupEntity;

/**
 * 관리자 그룹 Mapper
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public interface AdminGroupMapper {

	/**
	 * 조회
	 * @Mehtod Name : get
	 * @param entity
	 * @return
	 */
	public AdminGroupEntity get(AdminGroupEntity entity);

	/**
	 * 리스트
	 * @Mehtod Name : getList
	 * @param entity
	 * @return
	 */
	public List<AdminGroupEntity> getList(AdminGroupEntity entity);

	/**
	 * 전체 리스트
	 * @Mehtod Name : getAllList
	 * @param entity
	 * @return
	 */
	public List<AdminGroupEntity> getAllList(AdminGroupEntity entity);

	/**
	 * 카운트
	 * @Mehtod Name : count
	 * @param entity
	 * @return
	 */
	public int count(AdminGroupEntity entity);

	/**
	 * 등록
	 * @Mehtod Name : insert
	 * @param entity
	 * @return
	 */
	public int insert(AdminGroupEntity entity);

	/**
	 * 수정
	 * @Mehtod Name : update
	 * @param entity
	 * @return
	 */
	public int update(AdminGroupEntity entity);
}
