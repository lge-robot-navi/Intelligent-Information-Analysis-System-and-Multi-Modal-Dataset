<%@ tag body-content="scriptless" description="Common Code - Select" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute  name="group" 	required="true" 	rtexprvalue="true" %>
<%@ attribute  name="value" 	required="true" 	rtexprvalue="true" %>
<spring:eval var="codes" expression="@commonCode.getCodeGroup(group)" />
<c:set var="doneLoop" value="false" />
<c:forEach var="item" items="${codes}">
	<c:if test="${not doneLoop}">
		<c:if test="${item.codeCd eq value}">
			${item.codeNm}
			<c:set var="doneLoop" value="true" />
		</c:if>
	</c:if>
</c:forEach>
