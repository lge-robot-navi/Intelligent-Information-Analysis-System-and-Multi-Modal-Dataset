package com.lge.mams.management.system.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.lge.mams.management.system.entity.AdminEntity;

/**
 * Admin Mapper
 * @version : 1.0
 * @author  : Copyright (c) 2015 by UBIVELOX CORP. All Rights Reserved.
 */
public interface AdminMapper {

	/**
	 * 조회
	 * @Mehtod Name : get
	 * @param Entity
	 * @return
	 */
	public AdminEntity get(AdminEntity Entity) throws DataAccessException;

	/**
	 * 리스트
	 * @Mehtod Name : list
	 * @param Entity
	 * @return
	 */
	public List<AdminEntity> getList(AdminEntity Entity) throws DataAccessException;

	/**
	 * 전체 리스트
	 * @Mehtod Name : list
	 * @param Entity
	 * @return
	 */
	public List<AdminEntity> getAllList(AdminEntity Entity) throws DataAccessException;

	/**
	 * 카운트
	 * @Mehtod Name : count
	 * @param Entity
	 * @return
	 */
	public int count(AdminEntity Entity) throws DataAccessException;

	/**
	 * 사용자일련번호 생성
	 * @Mehtod Name : insertAdminIdSq
	 * @param Entity
	 * @return
	 */
	public int insertAdminIdSq(AdminEntity Entity) throws DataAccessException;
	
	/**
	 * 등록
	 * @Mehtod Name : insert
	 * @param Entity
	 * @return
	 */
	public int insert(AdminEntity Entity) throws DataAccessException;

	/**
	 * 수정
	 * @Mehtod Name : update
	 * @param Entity
	 * @return
	 */
	public int update(AdminEntity Entity) throws DataAccessException;
}
