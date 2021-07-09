package com.lge.crawling.admin.management.image.mapper;

import com.lge.crawling.admin.management.image.entity.ImageTaggingEntity;

import java.util.List;

public interface ImageTaggingMapper {

    ImageTaggingEntity get(ImageTaggingEntity entity);
    List<ImageTaggingEntity> getList(ImageTaggingEntity entity);
    List<ImageTaggingEntity> getAllList(ImageTaggingEntity entity);
    Integer count(ImageTaggingEntity entity);
    int insert(ImageTaggingEntity entity);
    int update(ImageTaggingEntity entity);
    int delete(ImageTaggingEntity entity);
    int updateMagnification(ImageTaggingEntity entity);

}
