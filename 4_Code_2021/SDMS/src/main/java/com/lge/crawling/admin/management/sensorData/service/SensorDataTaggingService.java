package com.lge.crawling.admin.management.sensorData.service;

import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.management.sensorData.entity.SensorDataTaggingEntity;
import com.lge.crawling.admin.management.sensorData.mapper.SensorDataTaggingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Image tagging service.
 */
@Service
public class SensorDataTaggingService implements GenericService<SensorDataTaggingEntity> {

    @Autowired
    private SensorDataTaggingMapper mapper;

    @Override
    public SensorDataTaggingEntity get(SensorDataTaggingEntity entity) {
        return mapper.get(entity);
    }

    @Override
    public List<SensorDataTaggingEntity> getList(SensorDataTaggingEntity entity) {
        int count = this.getCount(entity);
        entity.getPagingValue(count);
        return mapper.getList(entity);
    }

    @Override
    public List<SensorDataTaggingEntity> getAllList(SensorDataTaggingEntity entity) {
        return mapper.getAllList(entity);
    }

    @Override
    public Integer getCount(SensorDataTaggingEntity entity) {
        return mapper.count(entity);
    }

    @Override
    public SensorDataTaggingEntity insert(SensorDataTaggingEntity entity) {
        mapper.insert(entity);
        mapper.updateMagnification(entity);
        return entity;
    }

    @Override
    public SensorDataTaggingEntity update(SensorDataTaggingEntity entity) {
        mapper.update(entity);
        mapper.updateMagnification(entity);
        return entity;
    }

    @Override
    public int delete(SensorDataTaggingEntity entity) {
        return mapper.delete(entity);
    }
}
