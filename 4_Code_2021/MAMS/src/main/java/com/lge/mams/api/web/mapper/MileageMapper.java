package com.lge.mams.api.web.mapper;

import java.util.List;

import com.lge.mams.api.web.entity.MileageEntity;

public interface MileageMapper {
	/**
	 * 마일리지 차트 데이터 조회
	 * @Mehtod Name : getChartList
	 * @param entity
	 * @return
	 */
	public List<MileageEntity> getChartList(MileageEntity entity);
	
	/**
	 * 마일리지 경로 데이터 조회
	 * @Mehtod Name : getRouterList
	 * @param entity
	 * @return
	 */
	public List<MileageEntity> getRouterList(MileageEntity entity);
}