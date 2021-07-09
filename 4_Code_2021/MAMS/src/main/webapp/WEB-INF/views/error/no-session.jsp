<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />
<%
	session.invalidate();
%>
<script type="text/javascript">
  alert("<spring:message code='alert.session' />");
  if (opener != null) {
    opener.top.location.href = "${REQUEST_CONTEXT_PATH}";
    window.close();
  } else {
    top.location.href = "${REQUEST_CONTEXT_PATH}/";
  }
</script>