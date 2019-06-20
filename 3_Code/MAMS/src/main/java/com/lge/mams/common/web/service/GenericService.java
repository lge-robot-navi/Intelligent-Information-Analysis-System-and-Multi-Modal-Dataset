package com.lge.mams.common.web.service;

import java.util.List;

/**
 * Generic Service Interface
 * 최상위 서비스 인터페이스
 * 기본 CRUD 를 정의.
 * 모든 서비스 인터페이스는 GenericService<T> 상속 권장.
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public interface GenericService<T> {

	/**
	 * find
	 * 조회
	 * @Mehtod Name : get
	 * @param entity
	 * @return
	 */
	T get(T entity);

	/**
	 * find with paging
	 * 페이징 리스트
	 * @Mehtod Name : getList
	 * @param entity
	 * @return
	 */
	List<T> getList(T entity);

	/**
	 * find all
	 * 전체 리스트
	 * @Mehtod Name : getList
	 * @param entity
	 * @return
	 */
	List<T> getAllList(T entity);

	/**
	 * count
	 * 카운트
	 * @Mehtod Name : getCount
	 * @param entity
	 * @return
	 */
	Integer getCount(T entity);

	/**
	 * insert
	 * 등록
	 * @Mehtod Name : insert
	 * @param entity
	 * @return
	 */
	T insert(T entity);

	/**
	 * update
	 * 수정
	 * @Mehtod Name : update
	 * @param entity
	 * @return
	 */
	T update(T entity);

	/**
	 * delete
	 * 삭제
	 * @Mehtod Name : delete
	 * @param entity
	 */
	int delete(T entity);
}