package com.lge.crawling.admin.management.sensorData.service;

import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.management.sensorData.entity.SensorDataTaggingGetInfoEntity;
import com.lge.crawling.admin.management.sensorData.mapper.SensorDataTaggingGetInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Image tagging service.
 */
@Service
public class SensorDataTaggingGetInfoService implements GenericService<SensorDataTaggingGetInfoEntity> {

    @Autowired
    private SensorDataTaggingGetInfoMapper mapper;

    @Override
    public SensorDataTaggingGetInfoEntity get(SensorDataTaggingGetInfoEntity entity) {
        return mapper.get(entity);
    }

    @Override
    public List<SensorDataTaggingGetInfoEntity> getList(SensorDataTaggingGetInfoEntity entity) {
        int count = this.getCount(entity);
        entity.getPagingValue(count);
        return mapper.getList(entity);
    }

    @Override
    public List<SensorDataTaggingGetInfoEntity> getAllList(SensorDataTaggingGetInfoEntity entity) {
        return mapper.getAllList(entity);
    }

    @Override
    public Integer getCount(SensorDataTaggingGetInfoEntity entity) {
        return mapper.count(entity);
    }

    @Override
    public SensorDataTaggingGetInfoEntity insert(SensorDataTaggingGetInfoEntity entity) {
        mapper.insert(entity);
        return entity;
    }

    @Override
    public SensorDataTaggingGetInfoEntity update(SensorDataTaggingGetInfoEntity entity) {
        mapper.update(entity);
        return entity;
    }

    @Override
    public int delete(SensorDataTaggingGetInfoEntity entity) {
        return mapper.delete(entity);
    }
}
