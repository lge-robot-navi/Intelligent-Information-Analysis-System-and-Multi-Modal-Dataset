package com.lge.mams.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lge.mams.constants.Constants;
import com.lge.mams.management.system.entity.ProgramTreeRcsvEntity;

/**
 * check authority custom tag
 * 권한체크 custom tag
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@SuppressWarnings("serial")
public class AuthorityTag extends TagSupport {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(AuthorityTag.class);

	private String type = null;
	private String authUrl = null;

	public String getType() { return type; }
	public String getAuthUrl() { return authUrl; }
	public void setType(String type) { this.type = type; }
	public void setAuthUrl(String authUrl) { this.authUrl = authUrl; }

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

		String action;
		String contextPath = request.getContextPath();
		if (StringUtils.isEmpty(authUrl)) {
			action = (String) request.getAttribute("javax.servlet.forward.request_uri");
		} else {
			action = contextPath + authUrl;
		}

		ProgramTreeRcsvEntity program = findProgram(programs, action, contextPath);
		logger.debug("Match Result Program : {}", program);

		if (program != null) {
			if (!type.equals("CREATE")
					&& !type.equals("READ")
					&& !type.equals("UPDATE")
					&& !type.equals("DELETE")) {
				throw new JspException("attribute type의 값으로는 CREATE, READ, UPDATE, DELETE만이 사용가능합니다.");
			}

			//logger.debug("type : {}", type);
			//logger.debug("sub  : {}", ToStringBuilder.reflectionToString(sub,ToStringStyle.MULTI_LINE_STYLE));

			if ((type.equals("CREATE") && !"Y".equals(program.getAuthIns()))
					|| (type.equals("READ") && !"Y".equals(program.getAuthSel()))
					|| (type.equals("UPDATE") && !"Y".equals(program.getAuthUpd()))
					|| (type.equals("DELETE") && !"Y".equals(program.getAuthDel()))) {
				return SKIP_BODY;
			}

			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	/**
	 * 권한 정보가 일치하는 메뉴를 찾는다.
	 * @method findProgram
	 * @param programs
	 * @param action
	 * @param contextPath
	 * @return
	 */
	private ProgramTreeRcsvEntity findProgram(List<ProgramTreeRcsvEntity> programs, String action, String contextPath) {

		ProgramTreeRcsvEntity result = null;

		for (ProgramTreeRcsvEntity program : programs) {

			logger.debug("auth program : {}", program);

			if (StringUtils.equals(program.getPgmTp(), "U")) {
				String dsURL = contextPath + program.getUrlDs();
//				logger.debug("action : {}, dsURL : {}", action, dsURL);

				boolean isMatch = action.matches(dsURL + ".*");
//				logger.debug("{} - isMatch : {}", program.getPgmNm(), isMatch);

				if (isMatch) {
					logger.debug("is match program : {}", program);
					result = program;
					break;
				}
			}

			// sub program
			if (program.getSubPrograms() != null && program.getSubPrograms().size() > 0) {
				result = findProgram(program.getSubPrograms(), action, contextPath);
				if (result != null) {
					break;
				}
			}
		}

		return result;
	}
}
