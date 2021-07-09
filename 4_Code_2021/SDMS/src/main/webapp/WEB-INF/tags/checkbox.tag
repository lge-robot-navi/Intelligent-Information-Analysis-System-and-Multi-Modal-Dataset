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

<!-- Values -->
<c:set var="values" value="${fn:split(value, ',') }" />

<!-- Create checkbox -->
<c:forEach var="code" items="${codes}" varStatus="codeStatus">
	<c:if test="${fn:indexOf(exclude, code.codeCd) == -1}">
		<c:set var="indexId" value="${name}_${codeStatus.index}"></c:set>
		<input type="checkbox" id="${indexId}" name="${name}" class="${cssClass}" style="${cssStyle}" value="${code.codeCd}"
			<c:forEach var="val" items="${values}">
				${val eq code.codeCd ? ' checked=\"checked\" ' : ''}
			</c:forEach>
		/>
		<label for="${indexId}">${code.codeNm}</label>
	</c:if>
</c:forEach>
