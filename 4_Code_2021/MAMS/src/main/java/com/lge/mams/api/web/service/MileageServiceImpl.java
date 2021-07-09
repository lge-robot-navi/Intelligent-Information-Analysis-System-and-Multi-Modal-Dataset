package com.lge.mams.api.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.mams.api.web.entity.MileageEntity;
import com.lge.mams.api.web.mapper.MileageMapper;


@Service
public class MileageServiceImpl implements MileageService{

	// 마일리지 Mapper
	@Autowired
	MileageMapper mileageMapper;
	
	@Override
	public List<MileageEntity> getChartInfo(MileageEntity entity) {
		// TODO Auto-generated method stub
		return mileageMapper.getChartList(entity);
	}
	
	@Override
	public List<MileageEntity> getRouterInfo(MileageEntity entity) {
		// TODO Auto-generated method stub
		return mileageMapper.getRouterList(entity);
	}

	
	
}//class
