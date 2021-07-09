package com.lge.mams.management.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.mams.common.web.service.GenericService;
import com.lge.mams.management.system.entity.ProgramEntity;
import com.lge.mams.management.system.mapper.ProgramMapper;

/**
 * 프로그램 Service
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Service
public class ProgramService implements GenericService<ProgramEntity> {

	@Autowired private ProgramMapper mapper;

	/**
	 * (non-Javadoc)
	 * @see GenericService#get(java.lang.Object)
	 */
	@Override
	public ProgramEntity get(ProgramEntity entity) {
		return mapper.get(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	@Override
	public List<ProgramEntity> getAllList(ProgramEntity entity) {
		return mapper.getAllList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getList(java.lang.Object)
	 */
	@Override
	public List<ProgramEntity> getList(ProgramEntity entity) {
		int count = this.getCount(entity);
		entity.getPagingValue(count);
		return mapper.getList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	@Override
	public Integer getCount(ProgramEntity entity) {
		return mapper.count(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#insert(java.lang.Object)
	 */
	@Override
	public ProgramEntity insert(ProgramEntity entity) {
		mapper.insert(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#update(java.lang.Object)
	 */
	@Override
	public ProgramEntity update(ProgramEntity entity) {
		mapper.update(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	@Override
	public int delete(ProgramEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}

	/**
	 * 프로그램 목록 조회 (한번에 리스트 전체 호출)
	 * @Mehtod Name : getProgramsList
	 * @param entity
	 * @return
	 */
	public List<ProgramEntity> getProgramsList(ProgramEntity entity) {
		return mapper.getProgramList(entity);
	}

	/**
	 * 프로그램 목록 조회 (ResultMap 을 이용한 계층형 쿼리)
	 * @Mehtod Name : getPrograms
	 * @param entity
	 * @return
	 */
	public List<ProgramEntity> getPrograms(ProgramEntity entity) {
		return mapper.getPrograms(entity);
	}

	/**
	 * 메인 프로그램 리스트 조회
	 * @Mehtod Name : getMainList
	 * @return
	 */
	public List<ProgramEntity> getMainList() {
		return mapper.getMainList();
	}

	/**
	 * 전체 프로그램 리스트
	 * @Mehtod Name : getListTotal
	 * @param entity
	 * @return List<ProgramEntity>
	 */
	public List<ProgramEntity> getListTotal(ProgramEntity entity) {
		return mapper.getListTotal(entity);
	}

	/**
	 * 프로그램 정렬순서 변경
	 * @Mehtod Name : updateRank
	 * @param entity
	 * @return
	 */
	public int updateRank(ProgramEntity entity, String[] pgmIds, String[] upperPgmIds, String[] rankNos) {
		int cnt = 0;
		for (int i = 0, sz = pgmIds.length; i < sz; i++) {
			entity.setPgmId(pgmIds[i]);
			entity.setUpperPgmId(upperPgmIds[i]);
			entity.setRankNo(rankNos[i]);
			cnt = mapper.updateRank(entity);
		}
		return cnt;
	}
}