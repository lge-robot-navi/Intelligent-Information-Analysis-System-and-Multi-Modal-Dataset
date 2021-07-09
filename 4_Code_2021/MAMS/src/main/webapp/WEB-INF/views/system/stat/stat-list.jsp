<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="custom" uri="/tags/custom-taglib"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page trimDirectiveWhitespaces="true"%>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />

  <script src="${REQUEST_CONTEXT_PATH}/resources/jquery-ui-1.12.1.custom/external/jquery/jquery.js"></script>
  <script type="text/javascript">
    var $myquery = jQuery.noConflict();
  </script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/jquery-ui-1.12.1.custom/jquery-ui.js"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/jquery-ui-1.12.1.custom/jquery-ui-my.js?v001"></script>
  <link rel="stylesheet" href="${REQUEST_CONTEXT_PATH}/resources/jquery-ui-1.12.1.custom/jquery-ui.css?v001">

  <!-- Basic Styles -->
  <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />">
  <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/bootstrap/css/font-awesome.min.css" />">

  <!-- FAVICONS -->
  <link rel="shortcut icon" href="${REQUEST_CONTEXT_PATH}/resources/images/favicon.ico" type="image/x-icon">
  <link rel="icon" href="${REQUEST_CONTEXT_PATH}/resources/images/favicon.ico" type="image/x-icon">

  <!-- GOOGLE FONT -->
  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,300,400,700">

  <!-- ###### Custom ###### -->
  <link rel="stylesheet" type="text/css" href="${REQUEST_CONTEXT_PATH}/resources/css/default.css" />

  <link rel="stylesheet" type="text/css" media="screen"
    href="<c:url value="/resources/media-server/bower_components/ekko-lightbox/dist/ekko-lightbox.min.css" />">

  <script>
    var CONTEXT_PATH = "${REQUEST_CONTEXT_PATH}";
    var exports = {};
  </script>

  <script src="https://code.jquery.com/jquery-2.0.2.min.js" integrity="sha256-TZWGoHXwgqBP1AF4SZxHIBKzUdtMGk0hCQegiR99itk=" crossorigin="anonymous">
  </script>
  <script src="https://code.jquery.com/ui/1.10.3/jquery-ui.min.js" integrity="sha256-lnH4vnCtlKU2LmD0ZW1dU7ohTTKrcKP50WA9fa350cE="
    crossorigin="anonymous"></script>
  <link rel="stylesheet" href="${REQUEST_CONTEXT_PATH}/resources/js/jquery/themes/base/jquery-ui.css">


  <script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/jquery/jquery.cookie.js"></script>
  <script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/jquery/jquery.form.js"></script>
  <script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/jquery/jquery.mask.js"></script>
  <script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/jquery/jquery.blockUI.js"></script>
  <script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/jquery/jquery.fileDownload.js"></script>
  <script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/spin.js"></script>
  <script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/common.js"></script>
  <script type="text/javascript" src="<c:url value="/resources/media-server/bower_components/ekko-lightbox/dist/ekko-lightbox.min.js" />"></script>


  <style type="text/css">
    /* The Modal (background) */
    .modal {
      display: none;
      /* Hidden by default */
      position: fixed;
      /* Stay in place */
      z-index: 9999;
      /* Sit on top */
      padding-top: 200px;
      /* Location of the box */
      left: 0;
      top: 0;
      width: 100%;
      /* Full width */
      height: 100%;
      /* Full height */
      overflow: auto;
      /* Enable scroll if needed */
      background-color: rgb(0, 0, 0);
      /* Fallback color */
      background-color: rgba(0, 0, 0, 0.4);
      /* Black w/ opacity */
    }

    /* Modal Content */
    .modal-content {
      background-color: #fefefe;
      margin: 0 auto;
      box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
      border: 1px solid #888;
      width: 680px;
      /* height: 720px; */
    }

    .modal-body {
      padding-top: 10px;
      padding-bottom: 10px;
    }

    .modal-header {
      padding: 2px 16px;
      background-color: #5cb85c;
      color: white;
    }

    .modal-footer {
      padding-bottom: 10px;
      padding-top: 10px;
      background-color: #5cb85c;
      color: white;
    }

    /* The Close Button */
    .close {
      color: #aaaaaa;
      float: right;
      font-size: 28px;
      font-weight: bold;
    }

    .close:hover,
    .close:focus {
      color: #000;
      text-decoration: none;
      cursor: pointer;
    }
  </style>

  <title>Multi Agent Monitoring System</title>
</head>

<body>
  <header>
    <div class="navbar navbar-inverse navbar-fixed-top">
      <!-- <div class="container"> -->
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"></button>
        <a class="navbar-brand" href="/monitoring/mntr/map">Multi Agent Monitoring System - 상태조회</a>
      </div>
      <div style="float: right; padding: 15px">
        <a href="/monitoring/logout"><span color="white">Logout</span></a>
        <div></div>
      </div>
    </div>
  </header>

  <div id="page">
    <div id="content">
      <header> </header>

      <aside>
        <div id="out"></div>
      </aside>

      <section>
        <div style="height: 50px"></div>

        <div style="text-align: center">
          <h3>에이전트별 상태조회</h3>
        </div>

        <!-- search -->
        <div class="well search-wrap">
          <form:form method="get" action="list" modelAttribute="abstractPage" cssClass="form-inline search-form" role="form" autocomplete="off">
            <fieldset>
              <div class="row">
                <div class="col-sm-10">
                  <div class="row">
                    <div class="col-sm-12">
                      <label>상태 일시</label>
                      <div class="form-group">
                        <div class="input-group startDt">
                          <form:input path="startDt" readonly="false" cssClass="form-control" />
                          <span class="input-group-addon" @click="showStartDt();"><i class="fa fa-calendar"></i></span>
                        </div>
                        ~
                        <div class="input-group">
                          <form:input path="endDt" readonly="false" cssClass="form-control" />
                          <span class="input-group-addon" @click="showEndDt();"><i class="fa fa-calendar"></i></span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="col-sm-2 text-center search_btn_wrap">
                  <button type="submit" class="btn btn-default btn-primary" role="button">
                    <i class="fa fa-search"></i> 조회
                  </button>
                </div>
              </div>
            </fieldset>
          </form:form>
        </div>

        <div class="table">
          <table class="table table-striped table-bordered table-hover">
            <thead>
              <tr>
                <th>SEQ</th>
                <th>지역구분</th>
                <th>일시</th>
                <th>Compass</th>
                <th>ENC L</th>
                <th>ENC R</th>
                <th>IMU Angle</th>
                <th>IMU Rate</th>
                <th>Lat</th>
                <th>Lng</th>
                <th>Pan</th>
                <th>Robot ID</th>
                <th>RV</th>
                <th>Tilt</th>
                <th>TV</th>
                <th>Vel L</th>
                <th>Vel R</th>
              </tr>
            </thead>

            <tbody>
              <c:if test="${empty list}">
                <tr>
                  <td colspan="18">
                    <spring:message code="common.dataIsEmpty" />
                  </td>
                </tr>
              </c:if>
              <c:forEach var="item" items="${list}">
                <tr data-toggle="lightbox">
                  <td>${item.statSn}</td>
                  <td>${item.areaCode}</td>
                  <td>${item.statDt}</td>
                  <td>${item.compass}</td>
                  <td>${item.encL}</td>
                  <td>${item.encR}</td>
                  <td>${item.imuAngle}</td>
                  <td>${item.imuRate}</td>
                  <td>${item.lat}</td>
                  <td>${item.lon}</td>
                  <td>${item.pan}</td>
                  <td>${item.robotId}</td>
                  <td>${item.rv}</td>
                  <td>${item.tilt}</td>
                  <td>${item.tv}</td>
                  <td>${item.velL}</td>
                  <td>${item.velR}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
        <jsp:include page="/include/paging.jsp" flush="true" />

      </section>
    </div>
  </div>


  <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
  <script src="https://unpkg.com/vue/dist/vue.js"></script>
  <script src="/monitoring/resources/js/relaxed-json.js"></script>




  <script>
    $myquery(document).ready(function () {


    });

    var app;
    $(document).ready(function () {
      app = new Vue({
        el: '#page',
        mounted: function () {
          setTimeout(function () {
            $myquery("#startDt").datepicker({
              dateFormat: 'yy-mm-dd'
            });
            $myquery("#endDt").datepicker({
              dateFormat: 'yy-mm-dd'
            });
          }, 100);
        },
        methods: {
          showStartDt: function () {
            $myquery("#startDt").datepicker('show');
          },
          showEndDt: function () {
            $myquery("#endDt").datepicker('show');
          }
        }
      });
    });
  </script>
</body>

</html>