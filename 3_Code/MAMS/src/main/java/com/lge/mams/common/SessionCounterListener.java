package com.lge.mams.common;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionCounterListener implements HttpSessionListener, HttpSessionAttributeListener {
 
	 private static final Logger logger = LoggerFactory.getLogger(SessionCounterListener.class);
	
     private static int totalActiveSessions;
 
     public static int getTotalActiveSession(){
           return totalActiveSessions;
     }
 
    @Override
    public void sessionCreated(HttpSessionEvent event) {
           totalActiveSessions++;
           System.out.println("sessionCreated - add one session into counter");	
           
           //세션이 만들어질 때 호출
           HttpSession session = event.getSession(); //request에서 얻는 session과 동일한 객체
           session.getServletContext().log(session.getId() + " 세션생성 " + ", 접속자수 : " + totalActiveSessions);
    }
 
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
           totalActiveSessions--;
           if(totalActiveSessions < 0)
        	   totalActiveSessions = 0;
           
           System.out.println("sessionDestroyed - deduct one session from counter");	
           
           HttpSession session = event.getSession();
           session.getServletContext().log(session.getId() + " 세션소멸 " + ", 접속자수 : " + totalActiveSessions);
    }	
    
 // This method will be automatically called when session attribute added
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {      
        
    	// 로그인 시 session.setAttribute 를 통해 해당 함수가 호출됨.
    	logger.debug(">>>>>>>>>>>>>>> Attribute Added <<<<<<<<<<<<<<<");
    	logger.debug("Attribute Name:" + event.getName());
    	logger.debug("Attribute Value:" + event.getValue());
    }
    // This method will be automatically called when session attribute removed
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {     
    	// 로그아웃 시 session.removeAttribute 를 통해 해당 함수가 호출됨.
    	logger.debug(">>>>>>>>>>>>>>> Attribute Removed <<<<<<<<<<<<<<<");
    }
    // This method will be automatically called when session attribute replace
    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {    
    	logger.debug(">>>>>>>>>>>>>>> Attribute Replaced <<<<<<<<<<<<<<<");
    	logger.debug("Attribute Name:" + event.getName());
    	logger.debug("Attribute Old Value:" + event.getValue());
    }
}