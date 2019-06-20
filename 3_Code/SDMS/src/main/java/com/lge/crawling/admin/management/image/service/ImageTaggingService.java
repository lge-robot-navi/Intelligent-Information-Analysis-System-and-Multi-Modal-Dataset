package com.lge.crawling.admin.management.image.service;

import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.management.image.entity.ImageTaggingEntity;
import com.lge.crawling.admin.management.image.mapper.ImageTaggingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Image tagging service.
 */
@Service
public class ImageTaggingService implements GenericService<ImageTaggingEntity> {

    @Autowired
    private ImageTaggingMapper mapper;

    @Override
    public ImageTaggingEntity get(ImageTaggingEntity entity) {
        return mapper.get(entity);
    }

    @Override
    public List<ImageTaggingEntity> getList(ImageTaggingEntity entity) {
        int count = this.getCount(entity);
        entity.getPagingValue(count);
        return mapper.getList(entity);
    }

    @Override
    public List<ImageTaggingEntity> getAllList(ImageTaggingEntity entity) {
        return mapper.getAllList(entity);
    }

    @Override
    public Integer getCount(ImageTaggingEntity entity) {
        return mapper.count(entity);
    }

    @Override
    public ImageTaggingEntity insert(ImageTaggingEntity entity) {
        mapper.insert(entity);
        mapper.updateMagnification(entity);
        return entity;
    }

    @Override
    public ImageTaggingEntity update(ImageTaggingEntity entity) {
        mapper.update(entity);
        mapper.updateMagnification(entity);
        return entity;
    }

    @Override
    public int delete(ImageTaggingEntity entity) {
        return mapper.delete(entity);
    }
}
