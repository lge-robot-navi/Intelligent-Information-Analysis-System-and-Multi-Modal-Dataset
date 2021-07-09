<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />
<!-- User info -->
<div class="login-info">
	<span> 
		<a href="javascript:void(0);">
			<span>
				${sessionScope.SESSION_USER_NAME} 
			</span>
		</a>
	</span>
</div>
<!-- end user info -->
