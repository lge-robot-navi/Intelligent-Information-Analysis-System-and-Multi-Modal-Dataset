package com.lge.crawling.admin.management.system.service;

import java.util.List;

import com.lge.crawling.admin.management.system.mapper.CodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.crawling.admin.common.util.CommonCode;
import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.management.system.entity.CodeEntity;

/**
 * 공통코드 Service
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Service("codeService")
public class CodeService implements GenericService<CodeEntity> {

	@Autowired
	private CodeMapper mapper;

	@Autowired
	private CommonCode commonCode;

	/**
	 * (non-Javadoc)
	 * @see GenericService#get(java.lang.Object)
	 */
	@Override
	public CodeEntity get(CodeEntity entity) {
		entity.setUseYn("");
		return mapper.get(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	@Override
	public List<CodeEntity> getAllList(CodeEntity entity) {
		return mapper.getAllList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getList(java.lang.Object)
	 */
	@Override
	public List<CodeEntity> getList(CodeEntity entity) {
		return mapper.getList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	@Override
	public Integer getCount(CodeEntity entity) {
		return mapper.count(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#insert(java.lang.Object)
	 */
	@Override
	public CodeEntity insert(CodeEntity entity) {
		mapper.insert(entity);
		// 공통코드 변경 값 반영
		commonCode.changeCommonCode(entity.getCdgrpCd());
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#update(java.lang.Object)
	 */
	@Override
	public CodeEntity update(CodeEntity entity) {
		mapper.update(entity);
		// 공통코드 변경 값 반영
		commonCode.changeCommonCode(entity.getCdgrpCd());
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	@Override
	public int delete(CodeEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}

	/**
	 * 공통 코드 그룹 리스트 조회
	 * @Mehtod Name : getListTotal
	 * @param entity
	 * @return
	 */
	public List<CodeEntity> getListTotal(CodeEntity entity) {
		return mapper.getListTotal(entity);
	}
}