<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title><tiles:insertAttribute name="title" /></title>
		<meta name="description" content="">
		<meta name="author" content="">

		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<!-- Basic Styles -->
		<link rel="stylesheet" type="text/css" media="screen" href="${REQUEST_CONTEXT_PATH}/smartadmin-resources/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${REQUEST_CONTEXT_PATH}/smartadmin-resources/css/font-awesome.min.css">

		<!-- SmartAdmin Styles : Caution! DO NOT change the order -->
		<link rel="stylesheet" type="text/css" media="screen" href="${REQUEST_CONTEXT_PATH}/smartadmin-resources/css/smartadmin-production-plugins.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${REQUEST_CONTEXT_PATH}/smartadmin-resources/css/smartadmin-production.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${REQUEST_CONTEXT_PATH}/smartadmin-resources/css/smartadmin-skins.min.css">

		<!-- SmartAdmin RTL Support  -->
		<link rel="stylesheet" type="text/css" media="screen" href="${REQUEST_CONTEXT_PATH}/smartadmin-resources/css/smartadmin-rtl.min.css">

		<!-- We recommend you use "your_style.css" to override SmartAdmin
				 specific styles this will also ensure you retrain your customization with each SmartAdmin update.
			<link rel="stylesheet" type="text/css" media="screen" href="${REQUEST_CONTEXT_PATH}/smartadmin-resources/css/your_style.css"> -->

		<!-- Demo purpose only: goes with demo.js, you can delete this css when designing your own WebApp -->
		<link rel="stylesheet" type="text/css" media="screen" href="${REQUEST_CONTEXT_PATH}/smartadmin-resources/css/demo.min.css">

		<!-- FAVICONS -->
		<link rel="shortcut icon" href="${REQUEST_CONTEXT_PATH}/resources/images/favicon.ico" type="image/x-icon">
		<link rel="icon" href="${REQUEST_CONTEXT_PATH}/resources/images/favicon.ico" type="image/x-icon">

		<!-- GOOGLE FONT -->
		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,300,400,700">

		<!-- Specifying a Webpage Icon for Web Clip
			 Ref: https://developer.apple.com/library/ios/documentation/AppleApplications/Reference/SafariWebContent/ConfiguringWebApplications/ConfiguringWebApplications.html -->
		<link rel="apple-touch-icon" href="${REQUEST_CONTEXT_PATH}/smartadmin-resources/img/splash/sptouch-icon-iphone.png">
		<link rel="apple-touch-icon" sizes="76x76" href="${REQUEST_CONTEXT_PATH}/smartadmin-resources/img/splash/touch-icon-ipad.png">
		<link rel="apple-touch-icon" sizes="120x120" href="${REQUEST_CONTEXT_PATH}/smartadmin-resources/img/splash/touch-icon-iphone-retina.png">
		<link rel="apple-touch-icon" sizes="152x152" href="${REQUEST_CONTEXT_PATH}/smartadmin-resources/img/splash/touch-icon-ipad-retina.png">

		<!-- iOS web-app metas : hides Safari UI Components and Changes Status Bar Appearance -->
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">

		<!-- Startup image for web apps -->
		<link rel="apple-touch-startup-image" href="${REQUEST_CONTEXT_PATH}/smartadmin-resources/img/splash/ipad-landscape.png" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:landscape)">
		<link rel="apple-touch-startup-image" href="${REQUEST_CONTEXT_PATH}/smartadmin-resources/img/splash/ipad-portrait.png" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:portrait)">
		<link rel="apple-touch-startup-image" href="${REQUEST_CONTEXT_PATH}/smartadmin-resources/img/splash/iphone.png" media="screen and (max-device-width: 320px)">

		<!-- ###### Custom ###### -->
		<link rel="stylesheet" type="text/css" href="${REQUEST_CONTEXT_PATH}/resources/css/default.css" />

		<script>
			var CONTEXT_PATH = "${REQUEST_CONTEXT_PATH}";
		</script>

		<!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
		<script>
			if (!window.jQuery) {
				document.write('<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/libs/jquery-2.1.1.min.js"><\/script>');
			}
		</script>

		<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
		<script>
			if (!window.jQuery.ui) {
				document.write('<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/libs/jquery-ui-1.10.3.min.js"><\/script>');
			}
		</script>
		<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/jquery/jquery.cookie.js"></script>
		<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/jquery/jquery.mask.js"></script>
		<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/common.js"></script>
	</head>

	<!-- #BODY -->
	<body class="">
		<header id="header">
			<div id="logo-group"></div>

			<div class="btn-header transparent pull-right">
				<span> <a href="javascript:window.close();"><i class="glyphicon glyphicon-remove"></i></a> </span>
			</div>
		</header>

		<!-- MAIN PANEL -->
		<div id="main" role="main" class="margin-left-0" style="padding-bottom: 0;">
			<!-- MAIN CONTENT -->
			<div id="content">
				<tiles:insertAttribute name="body" />
			</div>
			<!-- END MAIN CONTENT -->

		</div>
		<!-- END MAIN PANEL -->

		<!--================================================== -->

		<!-- PACE LOADER - turn this on if you want ajax loading to show (caution: uses lots of memory on iDevices)-->
		<script data-pace-options='{ "restartOnRequestAfter": true }' src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/pace/pace.min.js"></script>

		<!-- IMPORTANT: APP CONFIG -->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/app.config.js"></script>

		<!-- JS TOUCH : include this plugin for mobile drag / drop touch events-->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/jquery-touch/jquery.ui.touch-punch.min.js"></script>

		<!-- BOOTSTRAP JS -->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/bootstrap/bootstrap.min.js"></script>

		<!-- CUSTOM NOTIFICATION -->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/notification/SmartNotification.min.js"></script>

		<!-- JARVIS WIDGETS -->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/smartwidgets/jarvis.widget.min.js"></script>

		<!-- EASY PIE CHARTS -->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/easy-pie-chart/jquery.easy-pie-chart.min.js"></script>

		<!-- SPARKLINES -->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/sparkline/jquery.sparkline.min.js"></script>

		<!-- JQUERY VALIDATE -->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/jquery-validate/jquery.validate.min.js"></script>

		<!-- JQUERY MASKED INPUT -->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/masked-input/jquery.maskedinput.min.js"></script>

		<!-- JQUERY SELECT2 INPUT -->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/select2/select2.min.js"></script>

		<!-- JQUERY UI + Bootstrap Slider -->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/bootstrap-slider/bootstrap-slider.min.js"></script>

		<!-- browser msie issue fix -->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/msie-fix/jquery.mb.browser.min.js"></script>

		<!-- FastClick: For mobile devices -->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/fastclick/fastclick.min.js"></script>

		<!-- BOOTSTRAP CLOCKPICKER -->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/clockpicker/clockpicker.min.js"></script>

		<!--[if IE 8]>

		<h1>Your browser is out of date, please update your browser by going to www.microsoft.com/download</h1>

		<![endif]-->

		<!-- MAIN APP JS FILE -->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/app.min.js"></script>

		<!-- ENHANCEMENT PLUGINS : NOT A REQUIREMENT -->

		<!-- PAGE RELATED PLUGIN(S) -->
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/x-editable/moment.min.js"></script>
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/x-editable/jquery.mockjax.min.js"></script>
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/x-editable/x-editable.min.js"></script>

		<!-- #### Custom -->

		<script>
			$(document).ready(function() {
				// DO NOT REMOVE : GLOBAL FUNCTIONS!
				pageSetUp();
			});
		</script>
	</body>
</html>