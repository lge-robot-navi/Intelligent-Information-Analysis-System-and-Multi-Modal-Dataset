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
		<meta name="author" content="Mirincom">

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
		<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/jquery/jquery.form.js"></script>
		<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/jquery/jquery.mask.js"></script>
		<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/jquery/jquery.blockUI.js"></script>
		<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/jquery/jquery.fileDownload.js"></script>
		<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/spin.js"></script>
		<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/common.js"></script>
	</head>

	<!-- #BODY -->
	<body class="">

		<!-- HEADER -->
		<header id="header">
			<div id="logo-group">
				<!-- PLACE YOUR LOGO HERE -->
				<span id="logo"> <a href="${REQUEST_CONTEXT_PATH}/main">Sensor Data Management</a> </span>
				<!-- END LOGO PLACEHOLDER -->
			</div>

			<!-- pulled right: nav area -->
			<div class="pull-right">

				<!-- collapse menu button -->
				<div id="hide-menu" class="btn-header pull-right">
					<span> <a href="javascript:void(0);" data-action="toggleMenu" title="Collapse Menu"><i class="fa fa-reorder"></i></a> </span>
				</div>
				<!-- end collapse menu -->

				<!-- #MOBILE -->
				<!-- Top menu profile link : this shows only when top menu is active -->
				<ul id="mobile-profile-img" class="header-dropdown-list hidden-xs padding-5">
					<li class="">
						<a href="#" class="dropdown-toggle no-margin userdropdown" data-toggle="dropdown">
							<img src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/img/avatars/sunny.png" alt="John Doe" class="online" />
						</a>
						<ul class="dropdown-menu pull-right">
							<li>
								<a href="javascript:void(0);" class="padding-10 padding-top-0 padding-bottom-0"><i class="fa fa-cog"></i> Setting</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="profile.html" class="padding-10 padding-top-0 padding-bottom-0"> <i class="fa fa-user"></i> <u>P</u>rofile</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="javascript:void(0);" class="padding-10 padding-top-0 padding-bottom-0" data-action="toggleShortcut"><i class="fa fa-arrow-down"></i> <u>S</u>hortcut</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="javascript:void(0);" class="padding-10 padding-top-0 padding-bottom-0" data-action="launchFullscreen"><i class="fa fa-arrows-alt"></i> Full <u>S</u>creen</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="login.html" class="padding-10 padding-top-5 padding-bottom-5" data-action="userLogout"><i class="fa fa-sign-out fa-lg"></i> <strong><u>L</u>ogout</strong></a>
							</li>
						</ul>
					</li>
				</ul>

				<!-- logout button -->
				<div id="logout" class="btn-header transparent pull-right">
					<span> <a href="${REQUEST_CONTEXT_PATH}/logout" title="Sign Out" data-action="userLogout" data-logout-msg="<spring:message code='alert.logout'/>"><i class="fa fa-sign-out"></i></a> </span>
				</div>
				<!-- end logout button -->

				<!-- search mobile button (this is hidden till mobile view port) -->
				<div id="search-mobile" class="btn-header transparent pull-right">
					<span> <a href="javascript:void(0)" title="Search"><i class="fa fa-search"></i></a> </span>
				</div>
				<!-- end search mobile button -->

				<!-- fullscreen button -->
				<div id="fullscreen" class="btn-header transparent pull-right">
					<span> <a href="javascript:void(0);" data-action="launchFullscreen" title="Full Screen"><i class="fa fa-arrows-alt"></i></a> </span>
				</div>
				<!-- end fullscreen button -->

				<!-- profile button -->
				<div id="profile" class="btn-header transparent pull-right">
					<span> <a href="javascript:void(0);" title="profile" onclick="javascript:openPopup('${REQUEST_CONTEXT_PATH}/profile','Profile','800','600')"><i class="fa fa-user"></i></a> </span>
				</div>
				<!-- end profile button -->

				<!-- multiple lang dropdown : find all flags in the flags page -->
				<%-- <ul class="header-dropdown-list hidden-xs">
					<li>
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/img/blank.gif" class="flag flag-us" alt="United States"> <span> English (US) </span> <i class="fa fa-angle-down"></i> </a>
						<ul class="dropdown-menu pull-right">
							<li class="active">
								<a href="javascript:void(0);"><img src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/img/blank.gif" class="flag flag-us" alt="United States"> English (US)</a>
							</li>
							<li>
								<a href="javascript:void(0);"><img src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/img/blank.gif" class="flag flag-kr" alt="Korea"> 한국어</a>
							</li>
						</ul>
					</li>
				</ul> --%>
				<!-- end multiple lang -->

			</div>
			<!-- end pulled right: nav area -->

		</header>
		<!-- END HEADER -->

		<!-- Left panel : Navigation area -->
		<!-- Note: This width of the aside area can be adjusted through LESS variables -->
		<aside id="left-panel">
			<tiles:insertAttribute name="header" />

			<tiles:insertAttribute name="left" />

			<span class="minifyme" data-action="minifyMenu">
				<i class="fa fa-arrow-circle-left hit"></i>
			</span>
		</aside>
		<!-- END NAVIGATION -->

		<!-- MAIN PANEL -->
		<div id="main" role="main">

			<!-- RIBBON -->
			<div id="ribbon">

				<span class="ribbon-button-alignment">
					<span id="refresh" class="btn btn-ribbon" data-action="resetWidgets" data-title="refresh"  rel="tooltip" data-placement="bottom" data-original-title="<i class='text-warning fa fa-warning'></i> Warning! This will reset all your widget settings." data-html="true">
						<i class="fa fa-refresh"></i>
					</span>
				</span>

				<!-- breadcrumb -->
				<ol class="breadcrumb">
					<custom:navigator />
				</ol>
				<!-- end breadcrumb -->

				<!-- You can also add more buttons to the
				ribbon for further usability

				Example below:

				<span class="ribbon-button-alignment pull-right">
				<span id="search" class="btn btn-ribbon hidden-xs" data-title="search"><i class="fa-grid"></i> Change Grid</span>
				<span id="add" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa-plus"></i> Add</span>
				<span id="search" class="btn btn-ribbon" data-title="search"><i class="fa-search"></i> <span class="hidden-mobile">Search</span></span>
				</span> -->

			</div>
			<!-- END RIBBON -->

			<!-- MAIN CONTENT -->
			<div id="content">
				<tiles:insertAttribute name="body" />
			</div>
			<!-- END MAIN CONTENT -->

		</div>
		<!-- END MAIN PANEL -->

		<!-- PAGE FOOTER -->
		<div class="page-footer">
			<div class="row">
				<div class="col-xs-12 col-sm-6">
					<span class="txt-color-white">LG | Copyright © LG Image Crawling. All Rights Reserved.</span>
				</div>
			</div>
		</div>
		<!-- END PAGE FOOTER -->

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
		<%-- <script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/chartjs/chart.min.js"></script> --%>
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/chartjs/Chart.bundle.js"></script>
		<%-- <script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/chartjs/chartjs-plugin-zoom.min.js"></script> --%>
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/chartjs/chartjs-plugin-zoom.js"></script>
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/chartjs/hammer.min.js"></script>
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/bootstrap-duallistbox/jquery.bootstrap-duallistbox.min.js"></script>
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/fuelux/wizard/wizard.min.js"></script>
		<script src="${REQUEST_CONTEXT_PATH}/smartadmin-resources/js/plugin/bootstrap-tags/bootstrap-tagsinput.min.js"></script>

		<!-- #### Custom -->

		<script>
			$(document).ready(function() {
				// DO NOT REMOVE : GLOBAL FUNCTIONS!
				pageSetUp();
			});

			function openPopup(popupUrl, popupName, w, h) {
		         leftPosition = (screen.width) ? (screen.width-w)/2 : 0;
		         topPosition = (screen.height) ? (screen.height-h)/2 : 0;
		         option = 'height='+h+',width='+w+',top='+topPosition+',left='+leftPosition+',scrollbars=no,menubar=no,titlebar=no,toolbar=no,resizable=no';
		         window.open(popupUrl, popupName, option);
			}
		</script>

		<div id="loading-div"></div>

	</body>
</html>