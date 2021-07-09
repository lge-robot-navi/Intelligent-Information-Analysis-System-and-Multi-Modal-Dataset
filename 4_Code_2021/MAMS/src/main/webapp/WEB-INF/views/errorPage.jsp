<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="ko" id="extr-page">

<head>
  <meta charset="utf-8">
  <title>Multi Agent Monitoring System</title>
  <meta name="description" content="Crawling Admin">
  <meta name="author" content="Crawling Admin">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

  <!-- #CSS Links -->
  <!-- Basic Styles -->
  <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />">
  <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/bootstrap/css/font-awesome.min.css" />">

  <!-- MAMS -->
  <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/bootstrap/css/mams-production.css" />">

  <!-- #FAVICONS -->
  <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
  <link rel="icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">


</head>

<body class="animated fadeInDown">

  <div id="main" role="main">
    <!-- MAIN CONTENT -->
    <div id="content">
      <div class="row">
        <div class="col-xs-12 col-sm-10 col-sm-offset-1 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">

          err: too many session<br />
          msg: 해당 시스템은 레코딩 중복방지를 위해 2개 이상의 세션을 허용하지 않습니다.

        </div><!-- // col -->
      </div><!-- // row -->
    </div><!-- // content -->
  </div><!--  main -->



</body>

</html>