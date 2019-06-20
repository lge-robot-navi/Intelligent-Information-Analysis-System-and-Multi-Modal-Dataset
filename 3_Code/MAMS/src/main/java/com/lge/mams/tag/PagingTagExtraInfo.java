package com.lge.mams.tag;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * support for custom paging tag
 * custum paging tag 지원을 위한 클래스
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class PagingTagExtraInfo extends TagExtraInfo {

	@Override
	public VariableInfo[] getVariableInfo(TagData data) {
		String name = (String) data.getAttribute("name");
		VariableInfo info = new VariableInfo(name, "com.lge.mams.tag.PagingTag", true, VariableInfo.NESTED);
		VariableInfo[] variables = new VariableInfo[1];
		variables[0] = info;
		return variables;
	}
}