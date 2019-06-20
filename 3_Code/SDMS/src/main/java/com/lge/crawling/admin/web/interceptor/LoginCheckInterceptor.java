package com.lge.crawling.admin.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lge.crawling.admin.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lge.crawling.admin.exception.NoAuthorityException;
import com.lge.crawling.admin.exception.NoSessionException;
import com.lge.crawling.admin.management.system.entity.AdminEntity;
import com.lge.crawling.admin.management.system.entity.ProgramTreeRcsvEntity;

/**
 * Login Check Interceptor
 * 로그인을 체크하는 인터셉터
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

	/**
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		boolean result = true;

		logger.info("LOGIN INTERCEPTOR!!!");

		HttpSession session = request.getSession(false);

		if ( session == null ) {
			logger.debug("session is null!!!! -> {}", session);
			HttpServletResponse res = response;
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			res.sendRedirect(request.getContextPath() + "/no-session");

			return false;
		}

		// Login User 객체
		AdminEntity adminEntity = (AdminEntity) session.getAttribute(Constants.SESSION_USER);
		logger.debug("adminEntity : {}", adminEntity);
		logger.debug("session.isNew() : {}", session.isNew());
		logger.debug("(session.getAttribute(Constants.SESSION_USER_PROGRAMS) == null) : {}", (session.getAttribute(Constants.SESSION_USER_PROGRAMS) == null));

		try {
			@SuppressWarnings("unchecked")
			List<ProgramTreeRcsvEntity> menus = (List<ProgramTreeRcsvEntity>) session.getAttribute(Constants.SESSION_USER_PROGRAMS);
			if (!session.isNew() && (menus == null || menus.size() == 0)
					|| adminEntity == null) {
				throw new NoSessionException("세션이 종료되었습니다.");
			}
			logger.debug("session is alive!!!");

			result = true;

			// servlet path
			String servletPath = request.getServletPath();
			logger.debug("getServletPath: {}", servletPath);

			// Program Entity
			ProgramTreeRcsvEntity programEntity = findProgram(menus, servletPath);
			logger.debug("programEntity : {}", programEntity);

			if (programEntity == null || !"Y".equals(programEntity.getAuthSel())) {
				/**
				 * 이 부분 수정이 필요.
				 * 1. NoAuthorityException 등을 구현하여 예외처리를 권장함.
				 * 2. 등록되지 않는 URL 에 대한 처리
				 *
				 *    ex) ajax 등, 메뉴에 전혀 등록되지 않은 URL
				 *        -> 필터 예외처리 등록
				 *        common-interceptor.xml
				 *           <mvc:interceptor>
				 *              <mvc:exclude-mapping path="/dashboard/**" />
				 *              ...
				 *           </mvc:interceptor>
				 *    또는 해당 페이지의 URL 하위로 변경
				 *
				 *    ex) /xyz/json --> /device/beacon/xyz-json 등
				 * 3. 전체 페이지에 대한 테스트 필요함.
				 */
				logger.warn("권한이 존재하지 않습니다.");

				throw new NoAuthorityException();
			}

		} catch (NoAuthorityException nse) {

			HttpServletResponse res = response;
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			res.sendRedirect(request.getContextPath() + "/no-authority");

			result = false;

		} catch (NoSessionException nse) {

			logger.warn("session is dead!!!");

			HttpServletResponse res = response;
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			res.sendRedirect(request.getContextPath() + "/no-session");

			result = false;
		}
		return result;
	}

	/**
	 * URL DS 가 일치하는 메뉴를 찾는다.
	 * @method findProgram
	 * @param programs
	 * @param servletPath
	 * @return
	 */
	private ProgramTreeRcsvEntity findProgram(List<ProgramTreeRcsvEntity> programs, String servletPath) {

		ProgramTreeRcsvEntity result = null;
logger.debug("servletPath=", servletPath);
		for (ProgramTreeRcsvEntity program : programs) {

			logger.debug("auth program : {}", program);

			if (StringUtils.equals(program.getPgmTp(), "U")) {

				boolean isMatch = servletPath.matches(program.getUrlDs() + ".*");
				if (isMatch) {
					logger.debug("is match program : {}", program);
					result = program;
					break;
				}
			}

			// sub program
			if (program.getSubPrograms() != null && program.getSubPrograms().size() > 0) {
				result = findProgram(program.getSubPrograms(), servletPath);
				if (result != null) {
					break;
				}
			}
		}

		return result;
	}
}