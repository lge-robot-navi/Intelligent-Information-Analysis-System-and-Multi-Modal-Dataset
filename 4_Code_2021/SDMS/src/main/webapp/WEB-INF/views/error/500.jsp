<%@ page contentType="text/html; charset=utf8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<title>500</title>
</head>
<body>
	<h1>Exception</h1>
	<h2>Message:</h2>
	<h3>${exception.message}</h3>
</body>
</html>
<% response.setStatus(500); %>