<%@ tag body-content="scriptless" description="Common Code - Select" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute  name="group" 	    required="true" 	rtexprvalue="true" %>
<%@ attribute  name="include" 	    required="false" 	rtexprvalue="true" %>
<%@ attribute  name="exclude" 	    required="false" 	rtexprvalue="true" %>
<%@ attribute  name="name" 		    required="true" 	rtexprvalue="true" %>
<%@ attribute  name="id" 		    required="false" 	rtexprvalue="true" %>
<%@ attribute  name="value" 	    required="false" 	rtexprvalue="true" %>
<%@ attribute  name="cssClass" 	    required="false" 	rtexprvalue="false" %>
<%@ attribute  name="cssStyle" 	    required="false" 	rtexprvalue="false" %>
<%@ attribute  name="disabled" 	    required="false" 	rtexprvalue="false" %>
<%@ attribute  name="isServiceCode" required="false" 	rtexprvalue="true" type="java.lang.Boolean" %>

<!-- Codes -->
<c:if test="${!isServiceCode}">
	<spring:eval var="codes" expression="@commonCode.getCodeGroup(group)" />
</c:if>
<c:if test="${isServiceCode}">
	<spring:eval var="codes" expression="@serviceCode.getCodeGroup(group)" />
</c:if>

<!-- Create select -->
<select id="${id}" name="${name}" class="${cssClass}" style="${cssStyle}" <c:if test="${disabled}">disabled="disabled"</c:if>>
	<jsp:doBody></jsp:doBody>
	<c:forEach var="code" items="${codes}">
		<c:choose>
			<c:when test="${code.getClass().getSimpleName() eq 'SvcGroupCodeInfoEntity'}">
				<c:if test="${not empty include && fn:indexOf(include, code.serviceGroupCodeCd) > -1}">
<option value="${code.serviceGroupCodeCd}" <c:if test="${code.serviceGroupCodeCd eq value}">selected='selected'</c:if>>${code.serviceGroupCodeNm}</option>
				</c:if>
				<c:if test="${empty include && fn:indexOf(exclude, code.serviceGroupCodeCd) == -1}">
<option value="${code.serviceGroupCodeCd}" <c:if test="${code.serviceGroupCodeCd eq value}">selected='selected'</c:if>>${code.serviceGroupCodeNm}</option>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="${not empty include && fn:indexOf(include, code.codeCd) > -1}">
<option value="${code.codeCd}" <c:if test="${code.codeCd eq value}">selected='selected'</c:if>>${code.codeNm}</option>
				</c:if>
				<c:if test="${empty include && fn:indexOf(exclude, code.codeCd) == -1}">
<option value="${code.codeCd}" <c:if test="${code.codeCd eq value}">selected='selected'</c:if>>${code.codeNm}</option>
				</c:if>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</select>
