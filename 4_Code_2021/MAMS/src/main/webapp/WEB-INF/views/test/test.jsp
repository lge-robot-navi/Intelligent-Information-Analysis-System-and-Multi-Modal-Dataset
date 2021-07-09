<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>MON TEST</title>
</head>

<body>

  CONFIG<br><br>
  CONFIGID :
  <c:out value="${commonConfig.getProperty('CONFIGID') }" /><br><br>
  TUSD_FILES :
  <c:out value="${commonConfig.getProperty('TUSD_FILES') }" /><br>

  테스트입니다.<br><br>

  <c:forEach var="key" items="${commonConfig.getKeys()}">
    <c:out value="${key}" /> :
    <c:out value="${commonConfig.getProperty(key)}" />
    <br>
  </c:forEach>
</body>

</html>