package com.lge.crawling.admin.management.system.mapper;

import java.util.List;

import com.lge.crawling.admin.management.system.entity.ProgramFancytreeEntity;

public interface ProgramFancytreeMapper {

	/**
	 * 전체 리스트
	 * @Mehtod Name : getAllList
	 * @param entity
	 * @return
	 */
	public List<ProgramFancytreeEntity> getAllList(ProgramFancytreeEntity entity);
}
