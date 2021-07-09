package com.lge.crawling.admin.management.imageInfo.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.lge.crawling.admin.management.imageInfo.entity.ImageFilePackageInfoEntity;

/**
 * Image File Package Info Mapper
 * @version : 1.0
 * @author  : Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public interface ImageFilePackageInfoMapper {

	/**
	 * 조회
	 * @Mehtod Name : get
	 * @param Entity
	 * @return
	 */
	public ImageFilePackageInfoEntity get(ImageFilePackageInfoEntity Entity) throws DataAccessException;

	/**
	 * 리스트
	 * @Mehtod Name : list
	 * @param Entity
	 * @return
	 */
	public List<ImageFilePackageInfoEntity> getList(ImageFilePackageInfoEntity Entity) throws DataAccessException;

	/**
	 * 전체 리스트
	 * @Mehtod Name : list
	 * @param Entity
	 * @return
	 */
	public List<ImageFilePackageInfoEntity> getAllList(ImageFilePackageInfoEntity Entity) throws DataAccessException;

	/**
	 * 이미지 파일 패키지 일련번호 조회
	 * @Mehtod Name : getImageFilePackageSeq
	 * @param Entity
	 * @return
	 */
	public String getImageFilePackageSeq(ImageFilePackageInfoEntity Entity) throws DataAccessException;

	/**
	 * 카운트
	 * @Mehtod Name : count
	 * @param Entity
	 * @return
	 */
	public int count(ImageFilePackageInfoEntity Entity) throws DataAccessException;

	/**
	 * 이미지 파일 패키지 일련번호 생성
	 * @Mehtod Name : insertImageFilePackageIdSq
	 * @param Entity
	 * @return
	 */
	public int insertImageFilePackageIdSq(ImageFilePackageInfoEntity Entity) throws DataAccessException;

	/**
	 * 등록
	 * @Mehtod Name : insert
	 * @param Entity
	 * @return
	 */
	public int insert(ImageFilePackageInfoEntity Entity) throws DataAccessException;

	/**
	 * 수정
	 * @Mehtod Name : update
	 * @param Entity
	 * @return
	 */
	public int update(ImageFilePackageInfoEntity Entity) throws DataAccessException;
}
