package com.lge.crawling.admin.openapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.crawling.admin.openapi.entity.SearchEntity;
import com.lge.crawling.admin.openapi.entity.SensorDataEntity;
import com.lge.crawling.admin.openapi.mapper.SensorDataMapper;

@Service
public class SensorDataService  {

	@Autowired private SensorDataMapper mapper;

	public List<SensorDataEntity> getList(SearchEntity entity) {
		int count = this.getCount(entity);
		entity.setPage(entity.getPageno()); // 페이지번호
		entity.setPageSize(entity.getCount()); // 목록수
		entity.getPagingValue(count);
		return mapper.getList(entity);
	}

	public Integer getCount(SearchEntity entity) {
		return mapper.count(entity);
	}
}