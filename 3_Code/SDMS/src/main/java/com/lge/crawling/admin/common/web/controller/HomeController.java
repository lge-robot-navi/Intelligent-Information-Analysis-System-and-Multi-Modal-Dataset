package com.lge.crawling.admin.common.web.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lge.crawling.admin.constants.Constants;
import com.lge.crawling.admin.constants.TilesSuffix;
import com.lge.crawling.admin.management.system.entity.ProgramTreeRcsvEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Home Controller
 * 메인 홈 컨트롤러
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
@RequestMapping("")
public class HomeController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(HomeController.class);

	
	@RequestMapping("/wsmain")
	public String wsmain() {
		return "websocket/wsmain";
	}
	
	/**
	 * Home
	 * @Mehtod Name : login
	 * @return
	 */
	@RequestMapping("")
	public String home() {
		return "redirect:login";
	}

	/**
	 * Home Profile
	 * @Mehtod Name : profile
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value="profile")
	public String profile() {
		return "profile" +  TilesSuffix.POPUP;
	}

	/**
	 * 메인 페이지
	 * @Mehtod Name : main
	 * @return
	 */
	@RequestMapping("main")
	public String main(HttpSession session, HttpServletResponse response) {

		if ( session == null ) {
			return "redirect:login";
		}

		@SuppressWarnings("unchecked")
		List<ProgramTreeRcsvEntity> menus = (List<ProgramTreeRcsvEntity>) session.getAttribute(Constants.SESSION_USER_PROGRAMS);
		if ( menus == null || menus.size() == 0 ) {
			return "redirect:login";
		}

		// find my-page url
		ProgramTreeRcsvEntity myPageProgram = findProgram(menus, "/system/my-page/");
		if (myPageProgram != null) {

			// Pgm ID
			String pgmId = "PGM_ID_" + myPageProgram.getPgmId();

			// Home Menu URL
			session.setAttribute(Constants.MY_PAGE_PGM_ID, pgmId);
		}

		// find first menu url
		ProgramTreeRcsvEntity program = findProgram(menus);
		if (program != null) {

			// Pgm ID
			String pgmId = "PGM_ID_" + program.getPgmId();

			// Cookie
			Cookie cookie = new Cookie("latestMenu", pgmId);
			cookie.setPath("/");
			response.addCookie(cookie);

			// Home Menu URL
			session.setAttribute(Constants.HOME_PGM_ID, pgmId);

			return "redirect:" + program.getUrlDs();
		}

//		return "redirect:/dashboard";
		return "redirect:/";
	}

	/**
	 * no session
	 * @Mehtod Name : noSession
	 * @return
	 */
	@RequestMapping("no-session")
	public String noSession() {
		return "/error/no-session";
	}

	/**
	 * no authority
	 * @method noAuthority
	 * @return
	 */
	@RequestMapping("no-authority")
	public String noAuthority() {
		return "/error/no-authority";
	}

	/**
	 * 프로그램을 순환조회하여 프로그램 타입이 URL 인 최초 데이터를 돌려준다.
	 * @method findProgram
	 * @param programs
	 * @return
	 */
	private ProgramTreeRcsvEntity findProgram(List<ProgramTreeRcsvEntity> programs) {

		ProgramTreeRcsvEntity programEntity = null;

		for (ProgramTreeRcsvEntity program : programs) {
			if ("U".equals(program.getPgmTp())) {
				programEntity = program;
				break;
			}

			if (program.getSubPrograms() != null && program.getSubPrograms().size() > 0) {
				programEntity = findProgram(program.getSubPrograms());
				if (programEntity != null) {
					break;
				}
			}
		}

		return programEntity;
	}

	/**
	 * 프로그램을 순환조회하여 프로그램 타입이 URL 인 최초 데이터를 돌려준다.
	 * @method findProgram
	 * @param programs
	 * @return
	 */
	private ProgramTreeRcsvEntity findProgram(List<ProgramTreeRcsvEntity> programs, String dsUrl) {

		ProgramTreeRcsvEntity programEntity = null;

		for (ProgramTreeRcsvEntity program : programs) {
			if ("U".equals(program.getPgmTp())) {
				if (program.getUrlDs().equals(dsUrl)) {
					programEntity = program;
					break;
				}
			}

			if (program.getSubPrograms() != null && program.getSubPrograms().size() > 0) {
				programEntity = findProgram(program.getSubPrograms(), dsUrl);
				if (programEntity != null) {
					break;
				}
			}
		}

		return programEntity;
	}
}
