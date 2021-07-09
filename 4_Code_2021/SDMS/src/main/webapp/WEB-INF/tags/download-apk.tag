<%@ tag body-content="scriptless" description="NAS data prefix path eco image" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="spring" uri="http://www.springframework.org/tags"
%><%@ attribute  name="fullpath"	required="false" 	rtexprvalue="true"
%><%@ attribute  name="path" 		required="false" 	rtexprvalue="true"
%><%@ attribute  name="file" 		required="false" 	rtexprvalue="true"
%><c:set var="apkDownURL"><spring:eval expression="T(com.lge.crawling.admin.common.Config).getCommon().getString(\"APK_URL\")" />${path}${file}</c:set>
<a href="${apkDownURL}" class="btnty5" target="_blank"><span>APK다운로드</span></a>