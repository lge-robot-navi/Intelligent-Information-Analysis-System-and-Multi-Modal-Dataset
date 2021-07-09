<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="ko" id="extr-page" style="background:url('<c:url value="/resources/bg-images/full-bg.jpg" />');background-position:bottom;">
	<head>
		<meta charset="utf-8">
		<title>LG Sensor Data Management</title>
		<meta name="description" content="Crawling Admin">
		<meta name="author" content="Crawling Admin">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		
		<!-- #CSS Links -->
		<!-- Basic Styles -->
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />">
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/bootstrap/css/font-awesome.min.css" />">

		<!-- #FAVICONS -->
		<link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
		<link rel="icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">

		<!-- SDMS -->
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/bootstrap/css/sdms-production.css" />">

		<!-- #GOOGLE FONT -->
		<!-- <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,300,400,700"> -->

		<!-- #APP SCREEN / ICONS -->
		<!-- Specifying a Webpage Icon for Web Clip 
			 Ref: https://developer.apple.com/library/ios/documentation/AppleApplications/Reference/SafariWebContent/ConfiguringWebApplications/ConfiguringWebApplications.html -->
		<link rel="apple-touch-icon" href="<c:url value="/resources/bootstrap/img/splash/sptouch-icon-iphone.png" />">
		<link rel="apple-touch-icon" sizes="76x76" href="<c:url value="/resources/bootstrap/img/splash/touch-icon-ipad.png" />">
		<link rel="apple-touch-icon" sizes="120x120" href="<c:url value="/resources/bootstrap/img/splash/touch-icon-iphone-retina.png" />">
		<link rel="apple-touch-icon" sizes="152x152" href="<c:url value="/resources/bootstrap/img/splash/touch-icon-ipad-retina.png" />">
		
		<!-- iOS web-app metas : hides Safari UI Components and Changes Status Bar Appearance -->
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		
		<!-- Startup image for web apps -->
		<link rel="apple-touch-startup-image" href="<c:url value="/resources/bootstrap/img/splash/ipad-landscape.png" />" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:landscape)">
		<link rel="apple-touch-startup-image" href="<c:url value="/resources/bootstrap/img/splash/ipad-portrait.png" />" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:portrait)">
		<link rel="apple-touch-startup-image" href="<c:url value="/resources/bootstrap/img/splash/iphone.png" />" media="screen and (max-device-width: 320px)">
		
		<style type="text/css">
			#main { margin-left:; }
			body{background: none !important;background-color: transparent !important;}
			#extr-page #main{background-color: transparent !important;}
			#extr-page #header {height: 53px !important;}

			html, body { height:100%; overflow:hidden }
		</style>
	</head>

	<body class="animated fadeInDown" >
		<header id="header">
			<div id="logo-group" style="background: #f4f4f4 !important; padding:20px">
				<span id="logo"> LG Sensor Data Management </span>
			</div>
		</header>

		<div id="main" role="main" style="height:100%">
			<div class="row" style="padding-top: 200px">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<div class="card card-login align-middle">
						<div class="card-title">
							<h2 class="card-login-title text-center"><i class="fa fa-user txt-color-teal"></i>&nbsp;&nbsp;<spring:message code="common.login" /></h2>
						</div>
						<div class="card-body card-login-body">
							<c:url var="loginURL" value="/login" />
							<form:form id="login-form" method="post" action="${loginURL}" modelAttribute="adminEntity" role="form">
								<div class="form-group">
									<label for="inputEmail">ID</label>
									<spring:message var="id" code="common.id" />
									<form:input path="adminId" maxlength="10" placeholder="${id}" class="form-control" />
									<b class="tooltip tooltip-top-right card-login-alert"><i class="fa fa-user txt-color-teal"></i><spring:message code="common.idCheck" /></b>
								</div>
								<div class="form-group">
									<label for="inputPassword">Password</label>
									<spring:message var="password" code="common.password" />
									<form:password path="adminPw" maxlength="20" placeholder="${password}" class="form-control" />
									<b class="tooltip tooltip-top-right card-login-alert"><i class="fa fa-user txt-color-teal"></i><spring:message code="common.passwdCheck" /></b>
								</div>
								<div class="checkbox">
									<label class="checkbox">
										<input type="checkbox" id="saveId" />
										<i></i><spring:message code="common.saveId" /></label>
								</div>
								<button type="button" onclick="fnSave();" class="btn btn-lg btn-primary btn-block">
									<spring:message code="common.login" />
								</button>
							</form:form>
						</div>
					</div>
				</div>
				<div class="col-md-4"></div>
			</div>
		</div><!--  main -->

		<!--================================================== -->	

		<!--================================================== -->

	    <!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
	    <script src="<c:url value="/resources/bootstrap/js/libs/jquery-2.0.2.min.js" />"></script>
	    <script src="<c:url value="/resources/bootstrap/js/libs/jquery-ui-1.10.3.min.js" />"></script>
	    <script src="<c:url value="/resources/js/jquery/jquery.cookie.js" />"></script>

		<!-- JS TOUCH : include this plugin for mobile drag / drop touch events
		<script src="<c:url value="/resources/bootstrap/js/plugin/jquery-touch/jquery.ui.touch-punch.min.js" />"></script>-->

		<!-- BOOTSTRAP JS -->		
		<script src="<c:url value="/resources/bootstrap/js/bootstrap/bootstrap.min.js" />"></script>

		<!-- JQUERY VALIDATE -->
		<script src="<c:url value="/resources/bootstrap/js/plugin/jquery-validate/jquery.validate.min.js" />"></script>
		<script src="<c:url value="/resources/js/jquery/validate/localization/messages_ko.js" />"></script>

		<!--[if IE 8]>

			<h1>Your browser is out of date, please update your browser by going to www.microsoft.com/download</h1>

		<![endif]-->

		<!-- MAIN APP JS FILE -->
		<%-- <script src="<c:url value="/resources/bootstrap/js/app.min.js" />"></script> --%>
		
		<script type="text/javascript">
		
			jQuery(window).load(function() {
				// Validation
				$("#login-form").validate({
					// Rules for form validation
					rules : {
						adminId : {
							required : true
						},
						adminPw : {
							required : true,
							minlength : 3,
							maxlength : 20
						}
					},

					// Messages for form validation
					messages : {
						adminId : {
							required : '<spring:message code="common.idCheck" />'
						},
						adminPw : {
							required : '<spring:message code="common.passwdCheck" />'
						}
					},

					// Do not change code below
					errorPlacement : function(error, element) {
						error.insertAfter(element.parent());
					}
				});
				
				$.cookie('latestMainPage', null, { expires: -1, path: '/' });
				$.cookie('latestMenu', null, { expires: -1, path: '/' });

				// ID 저장
				var isChecked = $.cookie('SAVE_CHECKED');
				if (isChecked != null && isChecked) {
					$('#saveId').prop('checked', true);
					$('#adminId').val( $.cookie('SAVE_ID') );
				}

				// 엔터키 이벤트
				$('form:first :input').keypress(function(e) {
					if (e.which == 13) {
						fnSave();
					}
				});
			});
			
			function idSave() {
				var id = $('#adminId').val();
				var checked = $('#saveId').prop('checked');

				// ID 저장체크박스에 체크할경우
				if(checked) {
					$.cookie('SAVE_CHECKED', checked, { expires : 30, path: '/' });
					$.cookie('SAVE_ID', id, { expires : 30, path: '/'});
				} else {
					$.cookie('SAVE_CHECKED', null, { expires : -1, path: '/'});
					$.cookie('SAVE_ID', null, { expires : -1, path: '/'});
				}
			}

			// 로그인
			function fnSave() {

				var adminId = $('#adminId');
				adminId.val(adminId.val().trim());	// Trim

				var $form = $('#login-form');
				if ($form.valid()) {
					idSave();
					$form.submit();
				}
			}
			
			// 가입
			function join() {
				location.href = '<c:url value="/member/join" />';
			}
			
			$(document).ready(function(){
				$('#adminId').focus();
			});
		</script>

	</body>
</html>

<!-- Messages -->
<c:if test="${not empty message}">
	<script type="text/javascript">
		alert('${message}');
	</script>
</c:if>
<%
	session.invalidate();
%>