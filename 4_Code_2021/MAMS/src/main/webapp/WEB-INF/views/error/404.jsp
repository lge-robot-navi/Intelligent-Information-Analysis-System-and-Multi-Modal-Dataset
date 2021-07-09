<%@ page contentType="text/html; charset=utf8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf8" />
  <title>404</title>
  <link type="text/css" rel="stylesheet" href="/css/common.css" />
</head>

<body>
  <table align="center" border="0" width="396" cellpadding="0" cellspacing="0">
    <!-- ////////////// top ///////////////-->
    <tr>
      <td height="180">&nbsp;</td>
    </tr>
    <tr>
      <td>404 Not Found.</td>
    </tr>
    <!-- ////////////// body-end ///////////////-->
  </table>
</body>

</html>
<% response.setStatus(404); %>