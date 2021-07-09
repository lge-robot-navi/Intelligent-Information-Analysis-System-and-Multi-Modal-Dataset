<%@ tag body-content="scriptless" description="Common Code - Select" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute  name="group" 	required="true" 	rtexprvalue="true" %>
<%@ attribute  name="exclude" 	required="false" 	rtexprvalue="false" %>
<%@ attribute  name="name" 		required="true" 	rtexprvalue="true" %>
<%@ attribute  name="id" 		required="false" 	rtexprvalue="true" %>
<%@ attribute  name="value" 	required="false" 	rtexprvalue="true" %>
<%@ attribute  name="cssClass" 	required="false" 	rtexprvalue="false" %>
<%@ attribute  name="cssStyle" 	required="false" 	rtexprvalue="false" %>

<!-- Codes -->
<spring:eval var="codes" expression="@commonCode.getCodeGroup(group)" />

<!-- Create checkbox -->
<c:forEach var="code" items="${codes}" varStatus="codeStatus">
	<c:if test="${fn:indexOf(exclude, code.codeCd) == -1}">
		<c:set var="indexId" value="${name}_${codeStatus.index}"></c:set>
		<c:set var="isChecked" value="${empty value ? (codeStatus.index == 0) : (value eq code.codeCd)}" />
		<input type="radio" id="${indexId}" name="${name}" class="${cssClass}" style="${cssStyle}" value="${code.codeCd}"
			${isChecked ? ' checked=\"checked\" ' : ''}
			/>
		<label for="${indexId}">${code.codeNm}</label>
	</c:if>
</c:forEach>
