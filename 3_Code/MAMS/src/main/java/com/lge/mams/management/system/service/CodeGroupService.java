package com.lge.mams.management.system.service;

import java.util.List;

import com.lge.mams.common.web.service.GenericService;
import com.lge.mams.management.system.entity.CodeGroupEntity;
import com.lge.mams.management.system.mapper.CodeGroupMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 코드 그룹 Service
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Service
public class CodeGroupService implements GenericService<CodeGroupEntity> {

	@Autowired private CodeGroupMapper mapper;

	/**
	 * (non-Javadoc)
	 * @see GenericService#get(java.lang.Object)
	 */
	@Override
	public CodeGroupEntity get(CodeGroupEntity entity) {
		return mapper.get(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getList(java.lang.Object)
	 */
	@Override
	public List<CodeGroupEntity> getList(CodeGroupEntity entity) {
		int count = this.getCount(entity);
		entity.getPagingValue(count);
		return mapper.getList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	@Override
	public List<CodeGroupEntity> getAllList(CodeGroupEntity entity) {
		return mapper.getAllList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	@Override
	public Integer getCount(CodeGroupEntity entity) {
		return mapper.count(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#insert(java.lang.Object)
	 */
	@Override
	public CodeGroupEntity insert(CodeGroupEntity entity) {
		mapper.insert(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#update(java.lang.Object)
	 */
	@Override
	public CodeGroupEntity update(CodeGroupEntity entity) {
		mapper.update(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	@Override
	public int delete(CodeGroupEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}

	/**
	 * 정렬순서 다음 번호를 가져온다.
	 * @Mehtod Name : getNextOrderNo
	 * @param entity
	 * @return
	 */
	public Integer getNextOrderNo(CodeGroupEntity entity) {
		return mapper.getNextOrderNo(entity);
	}
}