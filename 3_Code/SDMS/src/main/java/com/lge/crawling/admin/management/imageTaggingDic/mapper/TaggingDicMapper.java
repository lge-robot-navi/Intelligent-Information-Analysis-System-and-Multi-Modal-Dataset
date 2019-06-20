package com.lge.crawling.admin.management.imageTaggingDic.mapper;

import java.util.List;

import com.lge.crawling.admin.management.imageTaggingDic.entity.TaggingDicEntity;

/**
 * Tagging Dictionary Mapper
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public interface TaggingDicMapper {

	/**
	 * 조회
	 * @Mehtod Name : get
	 * @param entity
	 * @return
	 */
	public TaggingDicEntity get(TaggingDicEntity entity);

	/**
	 * 리스트
	 * @Mehtod Name : getList
	 * @param entity
	 * @return
	 */
	public List<TaggingDicEntity> getList(TaggingDicEntity entity);

	/**
	 * 전체 리스트
	 * @Mehtod Name : getAllList
	 * @param entity
	 * @return
	 */
	public List<TaggingDicEntity> getAllList(TaggingDicEntity entity);

	/**
	 * 이미지 파일 태깅 사전 다운로드 리스트 조회
	 * @Mehtod Name : getImageDicDownloodList
	 * @param entity
	 * @return
	 */
	public List<TaggingDicEntity> getImageDicDownloodList(TaggingDicEntity entity);

	/**
	 * 이미지 사전 리스트 조회
	 * @Mehtod Name : getImageDicList
	 * @param entity
	 * @return
	 */
	public List<TaggingDicEntity> getImageDicList(TaggingDicEntity entity);

	/**
	 * 카운트
	 * @Mehtod Name : count
	 * @param entity
	 * @return
	 */
	public int count(TaggingDicEntity entity);

	/**
	 * 이미지 사전 리스트 카운트
	 * @Mehtod Name : getImageDicListCount
	 * @param entity
	 * @return
	 */
	public int getImageDicListCount(TaggingDicEntity entity);

	/**
	 * 등록
	 * @Mehtod Name : insert
	 * @param entity
	 * @return
	 */
	public int insert(TaggingDicEntity entity);

	/**
	 * 수정
	 * @Mehtod Name : update
	 * @param entity
	 * @return
	 */
	public int update(TaggingDicEntity entity);
}
