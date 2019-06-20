package com.lge.crawling.admin.management.sensorData.mapper;

import com.lge.crawling.admin.management.sensorData.entity.SensorDataTaggingGetInfoEntity;

import java.util.List;

public interface SensorDataTaggingGetInfoMapper {

    SensorDataTaggingGetInfoEntity get(SensorDataTaggingGetInfoEntity entity);
    List<SensorDataTaggingGetInfoEntity> getList(SensorDataTaggingGetInfoEntity entity);
    List<SensorDataTaggingGetInfoEntity> getAllList(SensorDataTaggingGetInfoEntity entity);
    Integer count(SensorDataTaggingGetInfoEntity entity);
    int insert(SensorDataTaggingGetInfoEntity entity);
    int update(SensorDataTaggingGetInfoEntity entity);
    int delete(SensorDataTaggingGetInfoEntity entity);

}
