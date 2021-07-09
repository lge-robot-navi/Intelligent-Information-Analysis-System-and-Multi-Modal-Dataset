package com.lge.crawling.admin.management.system.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.lge.crawling.admin.management.system.entity.CodeGroupEntity;

/**
 * 코드 그룹 Mapper
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public interface CodeGroupMapper {

	/**
	 * 조회
	 * @Mehtod Name : get
	 * @param entity
	 * @return
	 */
	public CodeGroupEntity get(CodeGroupEntity entity);

	/**
	 * 리스트 (페이징)
	 * @Mehtod Name : getList
	 * @param entity
	 * @return
	 */
	public List<CodeGroupEntity> getList(CodeGroupEntity entity);

	/**
	 * 전체 리스트
	 * @Mehtod Name : getAllList
	 * @param entity
	 * @return
	 */
	public List<CodeGroupEntity> getAllList(CodeGroupEntity entity);

	/**
	 * 카운트
	 * @Mehtod Name : count
	 * @param entity
	 * @return
	 */
	public int count(CodeGroupEntity entity);

	/**
	 * 등록
	 * @Mehtod Name : insert
	 * @param entity
	 * @return
	 */
	public int insert(CodeGroupEntity entity);

	/**
	 * 수정
	 * @Mehtod Name : update
	 * @param entity
	 * @return
	 */
	public int update(CodeGroupEntity entity);

	/**
	 * 코드그룹의 다음 정렬번호순서를 가져온다.
	 * @Mehtod Name : getNextOrderNo
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public int getNextOrderNo(CodeGroupEntity entity);
}
