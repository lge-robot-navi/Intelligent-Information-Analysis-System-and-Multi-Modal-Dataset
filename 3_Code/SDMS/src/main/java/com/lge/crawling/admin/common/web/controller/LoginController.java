package com.lge.crawling.admin.common.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lge.crawling.admin.common.util.CryptoUtil;
import com.lge.crawling.admin.common.util.MessageManage;
import com.lge.crawling.admin.constants.Constants;
import com.lge.crawling.admin.constants.TilesSuffix;
import com.lge.crawling.admin.management.system.entity.AdminEntity;
import com.lge.crawling.admin.management.system.entity.ProgramTreeRcsvEntity;
import com.lge.crawling.admin.management.system.service.AdminService;
import com.lge.crawling.admin.management.system.service.GroupAuthService;

/**
 * Login Controller
 * 로그인 컨트롤러
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
@RequestMapping("")
public class LoginController {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired AdminService service;
	@Autowired
    GroupAuthService groupAuthService;

	/**
	 * 로그인페이지
	 * @Mehtod Name : login
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value="login")
	public String login(@ModelAttribute AdminEntity entity, Model model) {
		return "login";
	}

	/**
	 * 로그인 처리
	 * @Mehtod Name : loginProc
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value="login")
	public String loginProcess(@ModelAttribute AdminEntity entity,
			HttpSession session,
			Model model) {

		String failURL = "login";
		String adminId = entity.getAdminId();
		logger.info("로그인 요청 ID: {}", adminId);

		// 객체 생성
		AdminEntity admin = new AdminEntity();
		admin.setAdminId(adminId);
//		admin.setAdminStatusCd("100");	// 100 : 정상

		// 유저 조회
		try {
			admin = service.get(admin);
		} catch (Exception e) {
			logger.error("Exception : {}", e.getMessage());
			return failURL;
		}
		logger.debug("Admin: {}", admin);

		// 존재하지 않는 유저
		if (admin == null || "9000".equals(admin.getAdminGrpId())) {
			String message = MessageManage.getMessage("user.not-exist");
			logger.warn(message);
			model.addAttribute("message", message);
			return failURL;
		}

		// Encryption
		String encPassword = CryptoUtil.encryptAdminPassword(entity.getAdminId(), entity.getAdminPw());

		// 패스워드 비교
		if (!encPassword.equals(admin.getAdminPw())) {
			String message = MessageManage.getMessage("user.incorrect-password");
			logger.warn(message);
			model.addAttribute("message", message);
			return failURL;
		}

		// 회원상태코드 - 100: 정상
		String adminStatusCd = admin.getAdminStatusCd();
		String adminStatusCdNm = admin.getAdminStatusCdNm();
		logger.debug("adminStatusCd = {}, adminStatusCdNm = {}", adminStatusCd, adminStatusCdNm);
		if (!StringUtils.equals("100", admin.getAdminStatusCd())) {
			String message = MessageManage.getMessage("user.incorrect-status-code", new Object[] { adminStatusCdNm });
			logger.warn(message);
			model.addAttribute("message", message);
			return failURL;
		}

		// 유저 정보 출력
		admin.setAdminPw(null);
		logger.info("######## 로그인 유저 정보 ####");
		logger.info(ToStringBuilder.reflectionToString(admin, ToStringStyle.MULTI_LINE_STYLE));

		// 세션 정보 설정
		session.setAttribute(Constants.SESSION_USER, 			admin);					// 유저 객체
		session.setAttribute(Constants.SESSION_USER_SEQ, 		admin.getAdminIdSq());	// 유저 Sequence
		session.setAttribute(Constants.SESSION_USER_ID, 		admin.getAdminId());	// 유저 ID
		session.setAttribute(Constants.SESSION_USER_CD, 		admin.getAdminCd());	// 관리자구분
		session.setAttribute(Constants.SESSION_USER_NAME, 		admin.getAdminNm());	// 유저명
		session.setAttribute(Constants.SESSION_USER_GRP_ID, 	admin.getAdminGrpId());	// 유저 그룹 ID

		// 권한 메뉴
		ProgramTreeRcsvEntity programRcsvEntity = new ProgramTreeRcsvEntity();
		programRcsvEntity.setAdminGrpId(admin.getAdminGrpId());

		List<ProgramTreeRcsvEntity> menus = groupAuthService.getAuthMenu(programRcsvEntity);
		logger.debug("menus: {}", menus);
		if (menus == null || menus.size() == 0) {
			logger.warn(MessageManage.getMessage("user.auth.not-exist"));
			model.addAttribute("message", MessageManage.getMessage("user.auth.not-exist"));
			return failURL;
		}

		session.setAttribute(Constants.SESSION_USER_PROGRAMS, menus);					// 유저 권한 메뉴

		return "redirect:/main";
	}

	/**
	 * 로그아웃 처리
	 * @Mehtod Name : logoutProcess
	 * @param session
	 * @return
	 */
	@RequestMapping(value="logout")
	public String logoutProcess(HttpSession session) {
		// 세션초기화
		session.invalidate();
		return "redirect:login";
	}
}