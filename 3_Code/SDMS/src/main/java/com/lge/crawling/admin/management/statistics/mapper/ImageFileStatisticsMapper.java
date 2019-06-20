package com.lge.crawling.admin.management.statistics.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.lge.crawling.admin.management.statistics.entity.ImageFileEntity;
import com.lge.crawling.admin.management.statistics.entity.ImageTaggingDataDicEntity;

/**
 * Image File Statistics Mapper
 * @version : 1.0
 * @author  : Copyright (c) 2017 by UBIVELOX CORP. All Rights Reserved.
 */
public interface ImageFileStatisticsMapper {

	/**
	 * 그래프List조회
	 * @Mehtod Name : Bar graph List
	 * @param Entity
	 * @return
	 */
	public List<ImageFileEntity> getBarGraphList(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 그래프조회
	 * @Mehtod Name : Bar graph
	 * @param Entity
	 * @return
	 */
	public List<ImageFileEntity> getBarGraph(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * Bar 그래프 개수
	 * @Mehtod Name : Bar graph Count
	 * @param Entity
	 * @return
	 */
	public int getBarGraphCnt(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * Pie 그래프조회
	 * @Mehtod Name : Pie graph
	 * @param Entity
	 * @return
	 */
	public List<ImageFileEntity> getPieGraph(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 이미지 파일 형식별 Bar 그래프 통계 List
	 * @Mehtod Name : Bar graph By Image File Type Code List
	 * @param Entity
	 * @return
	 */
	public List<ImageFileEntity> getBarGraphByImageFileTypeCdList(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 이미지 파일 형식별 Bar 그래프 통계
	 * @Mehtod Name : Bar graph By Image File Type Code
	 * @param Entity
	 * @return
	 */
	public List<ImageFileEntity> getBarGraphByImageFileTypeCd(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 이미지 파일 형식별 Bar 그래프 통계 개수
	 * @Mehtod Name : Bar graph By Image File Type Code Count
	 * @param Entity
	 * @return
	 */
	public int getBarGraphByImageFileTypeCdCnt(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 이미지 파일 형식별 Pie 그래프 통계
	 * @Mehtod Name : Pie graph By Image File Type
	 * @param Entity
	 * @return
	 */
	public List<ImageFileEntity> getPieGraphByImageFileTypeCd(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 이미지 태깅 프로퍼티 타입별 Bar 그래프 통계 List
	 * @Mehtod Name : Bar graph By Image Tagging Type Code List
	 * @param Entity
	 * @return
	 */
	public List<ImageFileEntity> getBarGraphByImageTaggingTypeCdList(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 이미지 태깅 프로퍼티 타입별 Bar 그래프 통계
	 * @Mehtod Name : Bar graph By Image Tagging Type Code
	 * @param Entity
	 * @return
	 */
	public List<ImageFileEntity> getBarGraphByImageTaggingTypeCd(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 이미지 태깅 프로퍼티 타입별 Bar 그래프 통계 개수
	 * @Mehtod Name : Bar graph By Image Tagging Type Code Count
	 * @param Entity
	 * @return
	 */
	public int getBarGraphByImageTaggingTypeCdCnt(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 이미지 태깅 프로퍼티 타입별 Pie 그래프 통계
	 * @Mehtod Name : Pie graph By Image Tagging Type Code
	 * @param Entity
	 * @return
	 */
	public List<ImageFileEntity> getPieGraphByImageTaggingTypeCd(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 이미지 태깅 라벨별 Bar 그래프 통계 List
	 * @Mehtod Name : Bar graph By Image Tagging Type Code List
	 * @param Entity
	 * @return
	 */
	public List<ImageFileEntity> getBarGraphByLabelList(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 이미지 태깅 라벨별 Bar 그래프 통계
	 * @Mehtod Name : Bar graph By Image Tagging Type Code
	 * @param Entity
	 * @return
	 */
	public List<ImageFileEntity> getBarGraphByLabel(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 이미지 태깅 라벨별 Bar 그래프 통계 개수
	 * @Mehtod Name : Bar graph By Image Tagging Type Code Count
	 * @param Entity
	 * @return
	 */
	public int getBarGraphByLabelCnt(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 이미지 태깅 라벨별 Pie 그래프 통계
	 * @Mehtod Name : Pie graph By Image Tagging Type Code
	 * @param Entity
	 * @return
	 */
	public List<ImageFileEntity> getPieGraphByLabel(ImageFileEntity Entity) throws DataAccessException;

	/**
	 * 태깅 데이터 사전 정보 조회
	 * @Mehtod Name : Get Tagging Data Dictionary Data
	 * @param Entity
	 * @return
	 */
	public List<ImageTaggingDataDicEntity> getTaggingDicData(ImageTaggingDataDicEntity Entity) throws DataAccessException;
}
