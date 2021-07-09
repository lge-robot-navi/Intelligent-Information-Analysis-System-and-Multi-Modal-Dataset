package com.lge.mams.management.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.mams.common.web.service.GenericService;
import com.lge.mams.management.system.entity.ProgramFancytreeEntity;
import com.lge.mams.management.system.mapper.ProgramFancytreeMapper;

/**
 * 프로그램 트리(Fancytree) Service
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Service
public class ProgramFancyreeService implements GenericService<ProgramFancytreeEntity> {
	
	@Autowired private ProgramFancytreeMapper mapper;

	@Override
	public ProgramFancytreeEntity get(ProgramFancytreeEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}

	@Override
	public List<ProgramFancytreeEntity> getList(ProgramFancytreeEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}

	@Override
	public List<ProgramFancytreeEntity> getAllList(ProgramFancytreeEntity entity) {
		return mapper.getAllList(entity);
	}

	@Override
	public Integer getCount(ProgramFancytreeEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}

	@Override
	public ProgramFancytreeEntity insert(ProgramFancytreeEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}

	@Override
	public ProgramFancytreeEntity update(ProgramFancytreeEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}

	@Override
	public int delete(ProgramFancytreeEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}

}
