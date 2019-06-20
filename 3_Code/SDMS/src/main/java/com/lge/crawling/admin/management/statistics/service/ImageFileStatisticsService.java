package com.lge.crawling.admin.management.statistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.crawling.admin.common.web.service.GenericService;
import com.lge.crawling.admin.management.statistics.entity.ImageFileEntity;
import com.lge.crawling.admin.management.statistics.entity.ImageTaggingDataDicEntity;
import com.lge.crawling.admin.management.statistics.mapper.ImageFileStatisticsMapper;

/**
 * Image File Statistics Service
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
@Service
public class ImageFileStatisticsService implements GenericService<ImageFileEntity> {

	@Autowired private ImageFileStatisticsMapper mapper;

	/**
	 * (non-Javadoc)
	 * @see GenericService#getBarGraphList(java.lang.Object)
	 */
	public List<ImageFileEntity> getBarGraphList(ImageFileEntity entity) {
		int count = this.getBarGraphCnt(entity);
		entity.getPagingValue(count);
		return mapper.getBarGraphList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getBarGraph(java.lang.Object)
	 */
	public List<ImageFileEntity> getBarGraph(ImageFileEntity entity) {
		return mapper.getBarGraph(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getBarGraphCnt(java.lang.Object)
	 */
	public int getBarGraphCnt(ImageFileEntity entity) {
		return mapper.getBarGraphCnt(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getPieGraph(java.lang.Object)
	 */
	public List<ImageFileEntity> getPieGraph(ImageFileEntity entity) {
		return mapper.getPieGraph(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getBarGraphByImageFileTypeCdList(java.lang.Object)
	 */
	public List<ImageFileEntity> getBarGraphByImageFileTypeCdList(ImageFileEntity entity) {
		int count = this.getBarGraphByImageFileTypeCdCnt(entity);
		entity.getPagingValue(count);
		return mapper.getBarGraphByImageFileTypeCdList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getBarGraphByImageFileTypeCd(java.lang.Object)
	 */
	public List<ImageFileEntity> getBarGraphByImageFileTypeCd(ImageFileEntity entity) {
		return mapper.getBarGraphByImageFileTypeCd(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getBarGraphByImageFileTypeCdCnt(java.lang.Object)
	 */
	public int getBarGraphByImageFileTypeCdCnt(ImageFileEntity entity) {
		return mapper.getBarGraphByImageFileTypeCdCnt(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getPieGraphByImageFileTypeCd(java.lang.Object)
	 */
	public List<ImageFileEntity> getPieGraphByImageFileTypeCd(ImageFileEntity entity) {
		return mapper.getPieGraphByImageFileTypeCd(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getBarGraphByImageTaggingTypeCdList(java.lang.Object)
	 */
	public List<ImageFileEntity> getBarGraphByImageTaggingTypeCdList(ImageFileEntity entity) {
		int count = this.getBarGraphByImageTaggingTypeCdCnt(entity);
		entity.getPagingValue(count);
		return mapper.getBarGraphByImageTaggingTypeCdList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getBarGraphByImageTaggingTypeCd(java.lang.Object)
	 */
	public List<ImageFileEntity> getBarGraphByImageTaggingTypeCd(ImageFileEntity entity) {
		return mapper.getBarGraphByImageTaggingTypeCd(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getBarGraphByImageTaggingTypeCdCnt(java.lang.Object)
	 */
	public int getBarGraphByImageTaggingTypeCdCnt(ImageFileEntity entity) {
		return mapper.getBarGraphByImageTaggingTypeCdCnt(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getPieGraphByImageTaggingTypeCd(java.lang.Object)
	 */
	public List<ImageFileEntity> getPieGraphByImageTaggingTypeCd(ImageFileEntity entity) {
		return mapper.getPieGraphByImageTaggingTypeCd(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getBarGraphByLabelList(java.lang.Object)
	 */
	public List<ImageFileEntity> getBarGraphByLabelList(ImageFileEntity entity) {
		int count = this.getBarGraphByLabelCnt(entity);
		entity.getPagingValue(count);
		return mapper.getBarGraphByLabelList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getBarGraphByLabel(java.lang.Object)
	 */
	public List<ImageFileEntity> getBarGraphByLabel(ImageFileEntity entity) {
		return mapper.getBarGraphByLabel(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getBarGraphByLabelCnt(java.lang.Object)
	 */
	public int getBarGraphByLabelCnt(ImageFileEntity entity) {
		return mapper.getBarGraphByLabelCnt(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getPieGraphByLabel(java.lang.Object)
	 */
	public List<ImageFileEntity> getPieGraphByLabel(ImageFileEntity entity) {
		return mapper.getPieGraphByLabel(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getTaggingDicData(java.lang.Object)
	 */
	public List<ImageTaggingDataDicEntity> getTaggingDicData(ImageTaggingDataDicEntity entity) {
		return mapper.getTaggingDicData(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getList(java.lang.Object)
	 */
	@Override
	public List<ImageFileEntity> getList(ImageFileEntity entity) {
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	@Override
	public Integer getCount(ImageFileEntity entity) {
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#insert(java.lang.Object)
	 */
	@Override
	public ImageFileEntity insert(ImageFileEntity entity) {
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#update(java.lang.Object)
	 */
	@Override
	public ImageFileEntity update(ImageFileEntity entity) {
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	@Override
	public int delete(ImageFileEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}

	@Override
	public ImageFileEntity get(ImageFileEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ImageFileEntity> getAllList(ImageFileEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

}