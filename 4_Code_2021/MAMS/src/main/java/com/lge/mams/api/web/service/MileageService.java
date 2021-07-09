package com.lge.mams.api.web.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.lge.mams.api.web.entity.MileageEntity;

@Service
public interface MileageService {
	public List<MileageEntity> getChartInfo(MileageEntity entity);
	public List<MileageEntity> getRouterInfo(MileageEntity entity);
}
