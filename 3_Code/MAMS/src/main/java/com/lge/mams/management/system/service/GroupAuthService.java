package com.lge.mams.management.system.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lge.mams.common.web.service.GenericService;
import com.lge.mams.management.system.entity.GroupAuthEntity;
import com.lge.mams.management.system.entity.GroupAuthFormEntity;
import com.lge.mams.management.system.entity.ProgramTreeEntity;
import com.lge.mams.management.system.entity.ProgramTreeRcsvEntity;
import com.lge.mams.management.system.mapper.GroupAuthMapper;

/**
 * 그룹권한 Service
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Service
public class GroupAuthService implements GenericService<GroupAuthEntity> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired private GroupAuthMapper mapper;

	private final String MENU_DELEMITER = " / ";

	/**
	 * (non-Javadoc)
	 * @see GenericService#get(java.lang.Object)
	 */
	@Override
	public GroupAuthEntity get(GroupAuthEntity entity) {
		return mapper.get(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getList(java.lang.Object)
	 */
	@Override
	public List<GroupAuthEntity> getList(GroupAuthEntity entity) {
		int count = this.getCount(entity);
		entity.getPagingValue(count);
		return mapper.getList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getAllList(java.lang.Object)
	 */
	@Override
	public List<GroupAuthEntity> getAllList(GroupAuthEntity entity) {
		return mapper.getAllList(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#getCount(java.lang.Object)
	 */
	@Override
	public Integer getCount(GroupAuthEntity entity) {
		return mapper.count(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#insert(java.lang.Object)
	 */
	@Override
	public GroupAuthEntity insert(GroupAuthEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#update(java.lang.Object)
	 */
	@Override
	public GroupAuthEntity update(GroupAuthEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}

	/**
	 * (non-Javadoc)
	 * @see GenericService#delete(java.lang.Object)
	 */
	@Override
	public int delete(GroupAuthEntity entity) {
		throw new UnsupportedOperationException("Unsupported Operation Exception");
	}

	/**
	 * Delete
	 * @method delete
	 * @param entity
	 * @return
	 */
	public int delete(GroupAuthFormEntity entity) {
		return mapper.delete(entity);
	}

	/**
	 * 권한 메뉴 리스트
	 * @Mehtod Name : getAuthMenu
	 * @param entity
	 * @return
	 */
	public List<ProgramTreeRcsvEntity> getAuthMenu(ProgramTreeRcsvEntity entity) {

		// result
		List<ProgramTreeRcsvEntity> result = new LinkedList<>();

		// get all auth menu
		List<ProgramTreeRcsvEntity> list = mapper.getGroupAuthMenu(entity);

		// Root (Virtual)
		ProgramTreeRcsvEntity rootProgram = new ProgramTreeRcsvEntity();
		rootProgram.setPgmId("1000");	// 1000 : ROOT
		rootProgram.setPgmNm("");		// ROOT
		rootProgram.setLevelNo(0);
		rootProgram.setFullPathNm("Home");

		// sort tree (for recursive)
		if (list != null && list.size() > 0) {
			result.addAll(getSubList(list, rootProgram));
		}

		return result;
	}

	/**
	 * 메뉴 리스트(업데이트용 리스트)
	 * @Mehtod Name : getAuthProgram
	 * @param entity
	 * @return
	 */
	public List<ProgramTreeEntity> getProgramList(ProgramTreeRcsvEntity entity) {

		// result
		List<ProgramTreeEntity> result = new LinkedList<>();

		// get all auth menu
		List<ProgramTreeRcsvEntity> list = mapper.getProgramList(entity);

		// Root (Virtual)
		ProgramTreeRcsvEntity rootProgram = new ProgramTreeRcsvEntity();
		rootProgram.setPgmId("1000");	// 1000 : ROOT
		rootProgram.setPgmNm("");		// ROOT
		rootProgram.setLevelNo(0);
		rootProgram.setFullPathNm("Home");

		// sort tree (for recursive)
		result.addAll(getSubList(list, rootProgram));

		return result;
	}

	/**
	 * 그룹권한 메뉴 리스트(업데이트용 리스트)
	 * @Mehtod Name : getGroupAuthProgramList
	 * @param entity
	 * @return
	 */
	public GroupAuthFormEntity getGroupAuthProgramList(GroupAuthFormEntity entity) {

		// get all auth menu
		ProgramTreeRcsvEntity treeEntity = new ProgramTreeRcsvEntity();
		treeEntity.setAdminGrpId(entity.getAdminGrpId());

		List<ProgramTreeRcsvEntity> list = mapper.getProgramList(treeEntity);
		entity.setPrograms(list);

		// Root (Virtual)
		ProgramTreeRcsvEntity rootProgram = new ProgramTreeRcsvEntity();
		rootProgram.setPgmId("1000");	// 1000 : ROOT
		rootProgram.setPgmNm("");		// ROOT
		rootProgram.setLevelNo(0);
		rootProgram.setFullPathNm("Home");

		// result
		List<ProgramTreeRcsvEntity> sortTree = new LinkedList<>();

		// sort tree (for recursive)
		sortTree.addAll(getSubList(list, rootProgram));

		// re-sort
		List<ProgramTreeRcsvEntity> programs = resortTree(sortTree);

		// set programs
		entity.setPrograms(programs);

		// clear list
		sortTree = null;
		rootProgram = null;
		list = null;
		treeEntity = null;

		return entity;
	}

	/**
	 * Tree 데이터를 순차적으로 재정렬한다.
	 * @method resortTree
	 * @param trees
	 * @return
	 */
	private List<ProgramTreeRcsvEntity> resortTree(List<ProgramTreeRcsvEntity> trees) {

		List<ProgramTreeRcsvEntity> list = new ArrayList<>();
		if (trees != null) {
			for (ProgramTreeRcsvEntity tree : trees) {
				list.add(tree);
				if (tree.getChildCnt() > 0) {
					list.addAll(resortTree(tree.getSubPrograms()));
					tree.setSubPrograms(null);
				}
			}
		}

		return list;
	}

	/**
	 * 권한 메뉴 수정
	 * @Mehtod Name : updateAuthProgram
	 * @param entity
	 * @param authPrograms
	 */
	public void updateAuthProgram(GroupAuthFormEntity entity) {

		logger.debug("<<==== start ====>>");

		// 데이터 초기화
		mapper.delete(entity);
		logger.debug("<<---- delete ---->>");

		if (entity.getPrograms() != null) {
			String adminGrpId = entity.getAdminGrpId();
			String loginIDInSession = entity.getLoginIDInSession();
			// 권한 데이터 넣기
			for (ProgramTreeEntity program : entity.getPrograms()) {

				logger.debug(String.format("PgmId : [%4s], Auth sel [%5s], ins [%5s], upd [%5s], del [%5s]",
						program.getPgmId(),
						program.getAuthSel(),
						program.getAuthIns(),
						program.getAuthUpd(),
						program.getAuthDel()));

				if (StringUtils.isNotEmpty(program.getPgmId())) {
					program.setAdminGrpId(adminGrpId);
					program.setLoginIDInSession(loginIDInSession);
					mapper.insert(program);
				}
			}
		}

		logger.debug("<<==== end ====>>");
	}

	/**
	 * 프로그램(메뉴)를 순회하여 트리형식으로 돌려준다.
	 * Recursive
	 * @method getRecursiveSubList
	 * @param programList
	 * @param upperPgmId
	 * @return
	 */
	private List<ProgramTreeRcsvEntity> getSubList(List<ProgramTreeRcsvEntity> programList, ProgramTreeRcsvEntity parentProgram) {

		List<ProgramTreeRcsvEntity> list = null;

		// ^Loop
		for (int i = programList.size() - 1; i >= 0; i--) {

			ProgramTreeRcsvEntity program = programList.get(i);

			if (parentProgram.getPgmId().equals(program.getUpperPgmId())) {

				if (list == null) { // list 객체가 null 일 경우 List 객체를 생성한다.
					list = new ArrayList<>();
				}

				// level 을 설정한다.
				program.setLevelNo(parentProgram.getLevelNo() + 1);

				// 프로그램 경로를 설정한다.
				String fullPathId = parentProgram.getFullPathId() + MENU_DELEMITER + program.getPgmId();
				program.setFullPathId(fullPathId);

				String fullPathNm = parentProgram.getFullPathNm() + MENU_DELEMITER + program.getPgmNm();
				program.setFullPathNm(fullPathNm);

				// 객체 제거
				programList.remove(i);

				// sub program list (Recursive)
				if (program.getChildCnt() > 0) {
					List<ProgramTreeRcsvEntity> subProgramList = getSubList(programList, program);
					program.setSubPrograms(subProgramList);
				}

				// 사이즈 체크
				int size = programList.size();
				if (i > size) {
					i = size;	// 인덱스 조정
				}

				// add list
				list.add(program);
			}
		}
		// $Loop

		return list;
	}
}