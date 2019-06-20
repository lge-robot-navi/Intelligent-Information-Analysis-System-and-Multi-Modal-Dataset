package com.lge.mams.management.system.mapper;

import java.util.List;

import com.lge.mams.management.system.entity.CodeEntity;

/**
 * 공통코드 Mapper
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public interface CodeMapper {

	/**
	 * 조회
	 * @Mehtod Name : get
	 * @param entity
	 * @return
	 */
	public CodeEntity get(CodeEntity entity);

	/**
	 * 리스트
	 * @Mehtod Name : getList
	 * @param entity
	 * @return
	 */
	public List<CodeEntity> getList(CodeEntity entity);

	/**
	 * 전체 리스트
	 * @Mehtod Name : getAllList
	 * @param entity
	 * @return
	 */
	public List<CodeEntity> getAllList(CodeEntity entity);

	/**
	 * 사용가능코드 전체목록
	 * @Mehtod Name : getListTotal
	 * @param entity
	 * @return
	 */
	public List<CodeEntity> getListTotal(CodeEntity entity);

	/**
	 * 카운트
	 * @Mehtod Name : count
	 * @param entity
	 * @return
	 */
	public int count(CodeEntity entity);

	/**
	 * 등록
	 * @Mehtod Name : insert
	 * @param entity
	 * @return
	 */
	public int insert(CodeEntity entity);

	/**
	 * 수정
	 * @Mehtod Name : update
	 * @param entity
	 * @return
	 */
	public int update(CodeEntity entity);
}
