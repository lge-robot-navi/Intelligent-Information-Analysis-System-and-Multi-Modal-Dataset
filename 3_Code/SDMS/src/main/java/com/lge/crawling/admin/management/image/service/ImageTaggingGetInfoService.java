package com.lge.crawling.admin.management.image.service;

import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.management.image.entity.ImageTaggingGetInfoEntity;
import com.lge.crawling.admin.management.image.mapper.ImageTaggingGetInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Image tagging service.
 */
@Service
public class ImageTaggingGetInfoService implements GenericService<ImageTaggingGetInfoEntity> {

    @Autowired
    private ImageTaggingGetInfoMapper mapper;

    @Override
    public ImageTaggingGetInfoEntity get(ImageTaggingGetInfoEntity entity) {
        return mapper.get(entity);
    }

    @Override
    public List<ImageTaggingGetInfoEntity> getList(ImageTaggingGetInfoEntity entity) {
        int count = this.getCount(entity);
        entity.getPagingValue(count);
        return mapper.getList(entity);
    }

    @Override
    public List<ImageTaggingGetInfoEntity> getAllList(ImageTaggingGetInfoEntity entity) {
        return mapper.getAllList(entity);
    }

    @Override
    public Integer getCount(ImageTaggingGetInfoEntity entity) {
        return mapper.count(entity);
    }

    @Override
    public ImageTaggingGetInfoEntity insert(ImageTaggingGetInfoEntity entity) {
        mapper.insert(entity);
        return entity;
    }

    @Override
    public ImageTaggingGetInfoEntity update(ImageTaggingGetInfoEntity entity) {
        mapper.update(entity);
        return entity;
    }

    @Override
    public int delete(ImageTaggingGetInfoEntity entity) {
        return mapper.delete(entity);
    }
}
