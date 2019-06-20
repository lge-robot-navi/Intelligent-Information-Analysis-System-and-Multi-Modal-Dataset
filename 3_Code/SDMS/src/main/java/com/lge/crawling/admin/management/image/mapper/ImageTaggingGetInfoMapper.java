package com.lge.crawling.admin.management.image.mapper;

import com.lge.crawling.admin.management.image.entity.ImageTaggingGetInfoEntity;

import java.util.List;

public interface ImageTaggingGetInfoMapper {

    ImageTaggingGetInfoEntity get(ImageTaggingGetInfoEntity entity);
    List<ImageTaggingGetInfoEntity> getList(ImageTaggingGetInfoEntity entity);
    List<ImageTaggingGetInfoEntity> getAllList(ImageTaggingGetInfoEntity entity);
    Integer count(ImageTaggingGetInfoEntity entity);
    int insert(ImageTaggingGetInfoEntity entity);
    int update(ImageTaggingGetInfoEntity entity);
    int delete(ImageTaggingGetInfoEntity entity);

}
