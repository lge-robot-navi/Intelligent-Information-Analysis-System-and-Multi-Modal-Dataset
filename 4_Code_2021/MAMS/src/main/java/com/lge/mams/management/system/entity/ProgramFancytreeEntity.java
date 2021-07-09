package com.lge.mams.management.system.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Tree Program
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class ProgramFancytreeEntity extends ProgramEntity {

	// Tree
	@SerializedName("key")
	private String treeId				= "";		/* Tree에서 접근하기 위한 Key */
	private String title				= "";		/* dynatree title */
	private boolean folder				= true;		/* dynatree folder 여부 */
	private boolean lazy				= true;

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb
		.append("[")
		.append("treeId = ").append(this.getTreeId()).append(", ")
		.append("title = ").append(this.getTitle()).append(", ")
		.append("folder = ").append(this.isFolder()).append(", ")
		.append("lazy = ").append(this.isLazy()).append(", ")
		.append("pgmId = ").append(this.getPgmId()).append(", ")
		.append("pgmNm = ").append(this.getPgmNm()).append(", ")
		/*.append("authSel = ").append(this.getAuthSel()).append(", ")
		.append("authIns = ").append(this.getAuthIns()).append(", ")
		.append("authUpd = ").append(this.getAuthUpd()).append(", ")
		.append("authDel = ").append(this.getAuthDel()).append(", ")*/
		.append("urlDs = ").append(this.getUrlDs())
		.append("]\r");

		return sb.toString();
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isFolder() {
		return folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}
}