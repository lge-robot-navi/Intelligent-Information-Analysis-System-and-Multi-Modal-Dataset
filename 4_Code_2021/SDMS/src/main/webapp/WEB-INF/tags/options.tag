<%@ tag body-content="scriptless" description="Common Code - Select" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute  name="group" 	required="true" 	rtexprvalue="true" %>
<%@ attribute  name="exclude" 	required="false" 	rtexprvalue="true" %>
<%@ attribute  name="value" 	required="false" 	rtexprvalue="true" %>

<!-- Codes -->
<spring:eval var="codes" expression="@commonCode.getCodeGroup(group)" />

<!-- Create select -->
<c:forEach var="code" items="${codes}">
	<c:if test="${fn:indexOf(exclude, code.codeCd) == -1}">
		<option value="${code.codeCd}" <c:if test="${code.codeCd eq value}">selected='selected'</c:if>>${code.codeNm}</option>
	</c:if>
</c:forEach>
