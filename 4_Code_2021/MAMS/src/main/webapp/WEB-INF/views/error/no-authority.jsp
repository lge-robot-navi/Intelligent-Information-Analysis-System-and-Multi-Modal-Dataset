<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />
<html>

<head>
  <title>
    <spring:message code='alert.noAuthoriy' />
  </title>
</head>

<body>
  <h1>
    <spring:message code='alert.noAuthoriy' />
  </h1>

  <a href="javascript:prevPage();">Prev page</a>

  <script type="text/javascript">
    alert("<spring:message code='alert.noAuthoriy' />");

    function prevPage() {
      //var referrerUrl = document.referrer;
      history.go(-1);
    }
  </script>
</body>

</html>