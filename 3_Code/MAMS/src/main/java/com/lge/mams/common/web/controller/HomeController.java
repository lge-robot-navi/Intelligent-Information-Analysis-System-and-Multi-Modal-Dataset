package com.lge.mams.common.web.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lge.mams.common.SessionCounterListener;
import com.lge.mams.common.util.HttpSessionUtils;
import com.lge.mams.constants.Constants;
import com.lge.mams.constants.TilesSuffix;
import com.lge.mams.management.system.entity.ProgramTreeRcsvEntity;

import net.sf.json.JSONArray;

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

	/**
	 * Home
	 * @Mehtod Name : login
	 * @return
	 */
	@RequestMapping("")
	public String home(HttpSession session) {
		
		if(!HttpSessionUtils.isLoginUser(session)) {
    		return "redirect:/login";
		} 
		
		return "redirect:main";
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

//		if ( session == null ) {
//			return "redirect:login";
//		}
		
		if(!HttpSessionUtils.isLoginUser(session)) {
    		return "redirect:/login";
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
			logger.info("pgmId {} ", pgmId);
			logger.info("getUrlDs {} ", program.getUrlDs());
			return "redirect:" + "/index";
		}

//		return "redirect:/dashboard";
		return "redirect:/";
	}

	//mjchoi - delete after debugging.
	@SuppressWarnings({ "null", "unchecked" })
	@RequestMapping("index")
	public String index(HttpSession session, HttpServletRequest request, Model model) {
		
		if(!HttpSessionUtils.isLoginUser(session)) {
    		return "redirect:/login";
		} 
		
		int cntSession = SessionCounterListener.getTotalActiveSession();
		model.addAttribute("cntSession", cntSession);
		
		
		// 접속 제한 (중복레코딩 방지) - 일단 주석처리 해놓음.
//		if(cntSession > 1) {
//			return "redirect:/toomany-session";
//		}
		
		String settingsFilePath =  request.getServletContext().getRealPath("/WEB-INF/views");
		List<Object> agentInfoList = new ArrayList<Object>();
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = null;
		try {
			logger.info("load settingsFilePath {}", settingsFilePath);
			// FILE 객체로 읽는 경우
			map = (Map)mapper.readValue(new File(settingsFilePath + "/settings.json"), Map.class);

			for ( String key : map.keySet() )
			{
				System.out.println("agentInfo key = " + key);
				int idx = 0;
				for (Iterator localIterator = ((List)map.get(key)).iterator(); localIterator.hasNext(); )
				{
					idx++;
					Object obj = localIterator.next();
					
					agentInfoList.add(obj);
					
//					model.addAttribute("agentIp_" + idx,  ((Map)obj).get("ip").toString());
//					model.addAttribute("agentIdx_" + idx,  ((Map)obj).get("idx").toString());
//					System.out.println("ip = " + ((Map)obj).get("ip").toString() + ", idx = " + ((Map)obj).get("idx").toString());
				}
				System.out.println(agentInfoList.toString());
				model.addAttribute("agentInfoList", JSONArray.fromObject(agentInfoList));;
			}
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		//return "/index";
		return "/test-map";
	}
	
	@ResponseBody 
	@RequestMapping(value="agent-info", method=RequestMethod.GET, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String list_model(HttpServletRequest request){
	//public List<String> list_model(@ModelAttribute("agentInfoData") AgentInfoData agentInfoData, HttpServletRequest request){ 
		String settingsFilePath =  request.getServletContext().getRealPath("/WEB-INF/views");
		List<String> response = new ArrayList<String>();
		
		String str = request.getParameter("obj");
		str = str.replaceAll("&quot;", "\"");
		str = "{\"agentInfo\":" + str + "}";
		logger.info("save settingsFilePath {}", settingsFilePath);
		logger.info("agentInfo {}", str);

		try {
			FileWriter file = new FileWriter(settingsFilePath + "/settings.json");
			file.write(str);
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
			return "fail"; 
		}
		return "success"; 
	} 

	
	/**
	 * no session
	 * @Mehtod Name : noSession
	 * @return
	 */
	@RequestMapping("toomany-session")
	public String tooManySession() {
		return "/errorPage";
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
