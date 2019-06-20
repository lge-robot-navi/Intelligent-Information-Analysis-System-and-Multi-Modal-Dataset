package com.lge.crawling.admin.management.sensorData.mapper;

import com.lge.crawling.admin.management.sensorData.entity.SensorDataTaggingEntity;

import java.util.List;

public interface SensorDataTaggingMapper {

    SensorDataTaggingEntity get(SensorDataTaggingEntity entity);
    List<SensorDataTaggingEntity> getList(SensorDataTaggingEntity entity);
    List<SensorDataTaggingEntity> getAllList(SensorDataTaggingEntity entity);
    Integer count(SensorDataTaggingEntity entity);
    int insert(SensorDataTaggingEntity entity);
    int update(SensorDataTaggingEntity entity);
    int delete(SensorDataTaggingEntity entity);
    int updateMagnification(SensorDataTaggingEntity entity);

}
