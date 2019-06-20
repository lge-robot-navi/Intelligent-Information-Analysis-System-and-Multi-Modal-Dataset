package com.lge.mams.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.lge.mams.constants.Constants;
import com.lge.mams.management.system.entity.ProgramTreeRcsvEntity;

import org.apache.commons.lang.StringUtils;

/**
 * navigator custom tag
 * 현재 메뉴 위치를 알려주는 Tag
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class NavigatorTag extends TagSupport {

	private static final long serialVersionUID = -3938187414239418008L;

	/** Logger */
	/*private static final Logger logger = LoggerFactory.getLogger(NavigatorTag.class);*/

	@Override
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {

		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpSession session = request.getSession(false);
		List<ProgramTreeRcsvEntity> programs = null;

		if (session.getAttribute(Constants.SESSION_USER_PROGRAMS) == null) {
			return SKIP_BODY;
		} else {
			programs = (List<ProgramTreeRcsvEntity>) session.getAttribute(Constants.SESSION_USER_PROGRAMS);
		}

		String action = (String) request.getAttribute("javax.servlet.forward.request_uri");
		String contextPath = request.getContextPath();

		// 권한메뉴 목록을 순회하여 일치되는 메뉴정보를 가져온다.
		ProgramTreeRcsvEntity program = recursive(programs, action, contextPath);
		if (program == null) {
			return SKIP_BODY;
		}

		StringBuffer sb = new StringBuffer();
		sb.append("<div class='navigator'>");
		sb.append(program.getFullPathNm());
		sb.append("</div>");
		sb.append("<span id='CURRENT_PGM_ID' style='display: none;'>" + program.getPgmId() + "</span>");

		JspWriter out = pageContext.getOut();
		try {
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new JspException(e);
		}
		return EVAL_PAGE;
	}

	/**
	 * 프로그램을 순회하여 해당 메뉴정보를 가져온다.
	 * @method recursive
	 * @param programList
	 * @param action
	 * @param contextPath
	 * @return
	 */
	private ProgramTreeRcsvEntity recursive(List<ProgramTreeRcsvEntity> programList, String action, String contextPath) {

		ProgramTreeRcsvEntity programEntity = null;

		for (ProgramTreeRcsvEntity program : programList) {
			if (StringUtils.equals(program.getPgmTp(), "U")) {
				if (action.matches(contextPath + program.getUrlDs() + ".*")) {
					programEntity = program;
					break;
				}
			}

			if (programEntity == null && program.getSubPrograms() != null && program.getSubPrograms().size() > 0) {
				programEntity = recursive(program.getSubPrograms(), action, contextPath);
				if (programEntity != null) {
					break;
				}
			}
		}

		return programEntity;
	}
}