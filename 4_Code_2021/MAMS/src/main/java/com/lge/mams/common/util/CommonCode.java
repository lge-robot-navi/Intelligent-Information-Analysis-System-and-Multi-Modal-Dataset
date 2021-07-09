package com.lge.mams.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.lge.mams.management.system.entity.CodeEntity;
import com.lge.mams.management.system.service.CodeGroupService;
import com.lge.mams.management.system.service.CodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Common Code Spring Bean
 * 공통코드 처리 Spring Bean
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Component
public class CommonCode {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(CommonCode.class);

	private static Map<String, List<CodeEntity>> map;

	@Autowired
    CodeGroupService codeGroupService;
	@Autowired
    CodeService codeService;

	/**
	 * Initialization
	 * 초기화
	 * @Mehtod init
	 */
	@PostConstruct
	public void init() {

		logger.info("INITIALIZE COMMON CODE!!!");

		map = new HashMap<>();

		CodeEntity entity = new CodeEntity();

		// Code List
		List<CodeEntity> codeList = codeService.getListTotal(entity);
		for (CodeEntity code : codeList) {

			if (map.get(code.getCdgrpCd()) == null) {
				map.put(code.getCdgrpCd(), new ArrayList<CodeEntity>());
			}

			List<CodeEntity> cdList = map.get(code.getCdgrpCd());
			cdList.add(code);
		}
	}

	/**
	 * Destructor
	 * 소멸자
	 * @Mehtod destroy
	 */
	@PreDestroy
	public void destroy() {
		logger.info("DESTORY COMMON CODE!!!");
		map.clear();
		map = null;
	}

	/**
	 * Return the common code group data.
	 * 공통코드그룹 데이터를 반환한다.
	 * @method getCodeGroup
	 * @param cdgrpCd
	 * @return
	 */
	public List<CodeEntity> getCodeGroup(String cdgrpCd) {
		return map.get(cdgrpCd);
	}

	/**
	 * Return the common code data.
	 * 공통 코드 데이터를 반환한다.
	 * @Mehtod Name : getCode
	 * @param cdgrpCd
	 * @param cd
	 * @return
	 */
	public String getCode(String cdgrpCd, String cd) {
		List<CodeEntity> codeGroup = map.get(cdgrpCd);
		for (CodeEntity code : codeGroup) {
			if (code.getCodeCd().equals(cd)) {
				return code.getCodeNm();
			}
		}
		return "";
	}

	/**
	 * Return interface code of the common code
	 * 공통코드의 인터페이스코드 데이터를 가져온다.
	 * @method getIfCode
	 * @param cdgrpCd
	 * @param cd
	 * @return
	 */
	public String getIfCode(String cdgrpCd, String cd) {
		List<CodeEntity> codeGroup = map.get(cdgrpCd);
		for (CodeEntity code : codeGroup) {
			if (code.getCodeCd().equals(cd)) {
				return code.getIfCd();
			}
		}
		return "";
	}

	/**
	 * Change common code.
	 * 공통코드 정보를 변경한다.
	 * @Mehtod Name : changeCommonCode
	 * @param cdgrpCd
	 */
	public void changeCommonCode(String cdgrpCd) {
		map.remove(cdgrpCd);

		CodeEntity entity = new CodeEntity();
		entity.setCdgrpCd(cdgrpCd);
		entity.setUseYn("Y");

		map.put(cdgrpCd, codeService.getAllList(entity));
	}
}