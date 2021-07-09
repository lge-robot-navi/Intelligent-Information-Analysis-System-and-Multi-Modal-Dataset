package com.lge.mams.management.system.entity;

import java.util.List;

/**
 * Program Tree Recursive Entity
 *
 * @version : 1.0
 * @author : Copyright (c) 2014 by MIRINCOM CORP. All Rights Reserved.
 */
public class ProgramTreeRcsvEntity extends ProgramTreeEntity {

	// Sub tree
	private transient List<ProgramTreeRcsvEntity> subPrograms;

	public List<ProgramTreeRcsvEntity> getSubPrograms() {
		return subPrograms;
	}

	public void setSubPrograms(List<ProgramTreeRcsvEntity> subPrograms) {
		this.subPrograms = subPrograms;
	}
}