package com.lge.mams.management.system.entity;

import java.util.List;

/**
 * 그룹별 권한 관리 Form Entity
 * @version : 1.0
 * @author :  Copyright (c) 2014 by MIRINCOM CORP. All Rights Reserved.
 */
public class GroupAuthFormEntity extends GroupAuthEntity {
	
	private List<ProgramTreeRcsvEntity> programs;

	public List<ProgramTreeRcsvEntity> getPrograms() {
		return programs;
	}

	public void setPrograms(List<ProgramTreeRcsvEntity> programs) {
		this.programs = programs;
	}
}