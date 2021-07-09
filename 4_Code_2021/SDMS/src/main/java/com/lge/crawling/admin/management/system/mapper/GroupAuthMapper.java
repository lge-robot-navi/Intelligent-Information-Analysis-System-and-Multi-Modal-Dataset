package com.lge.crawling.admin.management.system.mapper;

import java.util.List;

import com.lge.crawling.admin.management.system.entity.GroupAuthEntity;
import com.lge.crawling.admin.management.system.entity.ProgramTreeEntity;
import com.lge.crawling.admin.management.system.entity.GroupAuthFormEntity;
import com.lge.crawling.admin.management.system.entity.ProgramTreeRcsvEntity;

/**
 * 그룹권한 Mapper
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public interface GroupAuthMapper {

	/**
	 * 조회
	 * @Mehtod Name : get
	 * @param entity
	 * @return
	 */
	public GroupAuthEntity get(GroupAuthEntity entity);

	/**
	 * 리스트
	 * @Mehtod Name : getList
	 * @param entity
	 * @return
	 */
	public List<GroupAuthEntity> getList(GroupAuthEntity entity);

	/**
	 * 전체 리스트
	 * @Mehtod Name : getAllList
	 * @param entity
	 * @return
	 */
	public List<GroupAuthEntity> getAllList(GroupAuthEntity entity);

	/**
	 * 카운트
	 * @Mehtod Name : count
	 * @param entity
	 * @return
	 */
	public int count(GroupAuthEntity entity);

	/**
	 * 등록
	 * @Mehtod Name : insert
	 * @param entity
	 * @return
	 */
	public int insert(ProgramTreeEntity entity);

	/**
	 * 삭제
	 * @Mehtod Name : delete
	 * @param entity
	 * @return
	 */
	public int delete(GroupAuthFormEntity entity);

	/**
	 * 그룹권한 메뉴 리스트
	 * @Mehtod Name : getAuthMenu
	 * @param entity
	 * @return
	 */
	public List<ProgramTreeRcsvEntity> getGroupAuthMenu(ProgramTreeRcsvEntity entity);

	/**
	 * 메뉴(프로그램) 리스트 (업데이트용 리스트)
	 * @Mehtod Name : getProgramList
	 * @param entity
	 * @return
	 */
	public List<ProgramTreeRcsvEntity> getProgramList(ProgramTreeRcsvEntity entity);
}
