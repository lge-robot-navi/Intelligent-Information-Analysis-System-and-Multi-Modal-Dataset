package com.lge.mams.test;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("test")
public class Test2Controller {
	
	@InitBinder
    public void initBinding(WebDataBinder b){

    }
	
	
	@RequestMapping(method = RequestMethod.GET, value="b")
	public String login(HttpSession session) {
		
		
		return "test";
	}
}
