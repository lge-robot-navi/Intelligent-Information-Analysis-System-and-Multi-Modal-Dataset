package com.lge.crawling.admin.management.system.mapper;

import java.util.List;

import com.lge.crawling.admin.management.system.entity.ProgramEntity;

/**
 * Program Mapper
 * @version : 1.0
 * @author :  Copyright (c) 2014 by MIRINCOM CORP. All Rights Reserved.
 */
public interface ProgramMapper {

	/**
	 * 조회
	 * @Mehtod Name : get
	 * @param entity
	 * @return
	 */
	public ProgramEntity get(ProgramEntity entity);

	/**
	 * 리스트
	 * @Mehtod Name : getList
	 * @param entity
	 * @return
	 */
	public List<ProgramEntity> getList(ProgramEntity entity);

	/**
	 * 전체 리스트
	 * @Mehtod Name : getAllList
	 * @param entity
	 * @return
	 */
	public List<ProgramEntity> getAllList(ProgramEntity entity);

	/**
	 * 카운트
	 * @Mehtod Name : count
	 * @param entity
	 * @return
	 */
	public int count(ProgramEntity entity);

	/**
	 * 등록
	 * @Mehtod Name : insert
	 * @param entity
	 * @return
	 */
	public int insert(ProgramEntity entity);

	/**
	 * 수정
	 * @Mehtod Name : update
	 * @param entity
	 * @return
	 */
	public int update(ProgramEntity entity);

	/**
	 * 프로그램 목록 조회 (한번에 리스트 전체 호출)
	 * @Mehtod Name : getProgramList
	 * @param entity
	 * @return
	 */
	public List<ProgramEntity> getProgramList(ProgramEntity entity);

	/**
	 * 프로그램 목록 조회 (ResultMap 을 이용한 계층형 쿼리)
	 * @Mehtod Name : getPrograms
	 * @param entity
	 * @return
	 */
	public List<ProgramEntity> getPrograms(ProgramEntity entity);

	/**
	 * 메인 프로그램 리스트 조회
	 * @Mehtod Name : getMainList
	 * @return
	 */
	public List<ProgramEntity> getMainList();

	/**
	 * 전체 프로그램 리스트
	 * @Mehtod Name : getListTotal
	 * @param entity
	 * @return
	 */
	public List<ProgramEntity> getListTotal(ProgramEntity entity);

	/**
	 * 프로그램 정렬순서 변경
	 * @Mehtod Name : updateRank
	 * @param entity
	 * @return
	 */
	public int updateRank(ProgramEntity entity);
}
