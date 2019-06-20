package com.lge.mams.web.interceptor;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lge.mams.common.util.Config;


/**
 * AclInterceptor
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class AclInterceptor extends HandlerInterceptorAdapter {
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(AclInterceptor.class);

    /**
     * (non-Javadoc)
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

        String remoteIp = req.getRemoteAddr();
        //logger.debug("Remote-IP : " + remoteIp);

        // ACL 기능 사용 여부 (Y:사용, N:미사용)
        String aclUse = Config.getCommon().getString("ACL.USE");

        // ACL 기능 사용 여부 체크
        if (StringUtils.equals("N", aclUse)) {

            //logger.debug("IP 체크 사용안함");

        } else {

            logger.debug("IP 체크 사용");

            if (!this.getIPAccRight(remoteIp)) {

                logger.debug("사용불가 IP");

                res.setStatus(403);
                res.setCharacterEncoding("UTF-8");
                res.setContentType("text/html; charset=UTF-8");
                res.addHeader("User-agent", req.getHeader("User-agent"));
                PrintWriter pw = res.getWriter();
                pw.print("403 Forbidden");

                return false;
            }
        }

        return true;
    }

    /**
     * (non-Javadoc)
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }


    /**
     * 현재 들어온 아이피가 허용된 아이피인지 체크
     * @method getIPAccRight
     * @param ip
     * @return true:사용가능, false:사용불가
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public boolean getIPAccRight(String ip) throws Exception {

        boolean accRslt = false;
        try {
            List stok = Config.getCommon().getList("ACL.IP_LIST");
            for (int i = 0, tokenCnt = stok.size(); i < tokenCnt; i++) {

                String tokenValue = (String) stok.get(i);
                // logger.debug("ACCEPT USE IP LIST [" + i + "] : " + tokenValue);

                // 전체 IP 비교일 경우
                if (ip.equals(tokenValue)) {
                    logger.debug("사용가능 IP");
                    accRslt = true;
                    break;
                }

                // C클래스 IP 대역일 경우
                if (tokenValue.lastIndexOf("#") != -1) {

                    String accIP = ip.substring(0, ip.lastIndexOf("."));								// 접근 IP C클래스 대역만 추출
                    String accTokenValue = tokenValue.substring(0, tokenValue.lastIndexOf("#") - 1);	// 접급 가능 C클래스 대역만 추출
                    if (accIP.equals(accTokenValue)) {
                        logger.debug("사용가능 IP");
                        accRslt = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            //logger.error(e.getMessage(), e);
            throw e;
        }

        return accRslt;
    }
}
