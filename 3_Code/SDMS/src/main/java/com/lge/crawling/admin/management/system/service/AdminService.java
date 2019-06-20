package com.lge.crawling.admin.management.system.service;

import java.text.DecimalFormat;
import java.util.List;

import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.management.system.entity.AdminEntity;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.crawling.admin.management.system.mapper.AdminMapper;

/**
 * Admin(관리자) Service
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Service
public class AdminService implements GenericService<AdminEntity> {

	@Autowired private AdminMapper mapper;

	/**
	 * (non-Javadoc)
	 * @see GenericService#get(java.lang.Object)
	 */
	@Override
	public AdminEntity get(AdminEntity entity) {
		return mapper.get(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	@Override
	public List<AdminEntity> getAllList(AdminEntity entity) {
		return mapper.getAllList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getList(java.lang.Object)
	 */
	@Override
	public List<AdminEntity> getList(AdminEntity entity) {
		int count = this.getCount(entity);
		entity.getPagingValue(count);
		return mapper.getList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	@Override
	public Integer getCount(AdminEntity entity) {
		return mapper.count(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#insert(java.lang.Object)
	 */
	@Override
	public AdminEntity insert(AdminEntity entity) {		
		mapper.insertAdminIdSq(entity);
		entity.setAdminIdSq("A" + StringUtils.leftPad(entity.getAdminIdSq(), 9, "0"));
		mapper.insert(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#update(java.lang.Object)
	 */
	@Override
	public AdminEntity update(AdminEntity entity) {
		mapper.update(entity);
		return entity;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	@Override
	public int delete(AdminEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}

}