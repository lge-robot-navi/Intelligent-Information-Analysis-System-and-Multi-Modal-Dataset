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
    var $myquery = jQuery.noConflict(true);
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
  <link rel="stylesheet" type="text/css" href="${REQUEST_CONTEXT_PATH}/resources/css/cia.css" />

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

  <script src="/monitoring/resources/js/relaxed-json.js"></script>
  <script src="/monitoring/resources/js/lodash.min.js"></script>
  <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
  <script src="https://unpkg.com/vue/dist/vue.js"></script>
  <script src="/monitoring/resources/js/vue-comp.js"></script>
  <script src="/monitoring/resources/js/moment.min.js"></script>


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

    .canvas-wrap {
      background: rgb(231, 234, 235)
    }

    canvas {
      border: 1px solid #ccc;
    }

    .start-dt {
      margin: 7px;
    }

    .end-dt {
      margin: 7px;
    }
  </style>

  <title>Multi Agent Monitoring System - STAT Replay</title>
</head>

<body>
  <header>
    <div class="navbar navbar-inverse navbar-fixed-top">
      <!-- <div class="container"> -->
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"></button>
        <a class="navbar-brand" href="/monitoring/mntr/map">Multi Agent Monitoring System - Replay</a>
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
          <h3>에이전트별 재생</h3>
        </div>

        <!-- search -->
        <div class="well search-wrap ">
          <form:form method="get" action="replay" modelAttribute="entity" cssClass="form-inline search-form" role="form" autocomplete="off">
            <fieldset>
              <div class="row">
                <div class="col-sm-10">
                  <div class="row">
                    <div class="col-sm-12">
                      <label style="text-align:right">지역구분</label>
                      <select class="form-control" v-model="areaGrpCd">
                        <option value="TA009" selected>포항</option>
                        <option value="TA010">광주</option>
                      </select>
                      <label style="text-align:right">검색 일시</label>
                      <div class="form-group">

                        <div class="input-group">
                          <flat-pickr v-model="startDt" v-bind:config="config" class="form-control"></flat-pickr>
                        </div>
                        ~
                        <div class="input-group">
                          <flat-pickr v-model="endDt" v-bind:config="config" class="form-control"></flat-pickr>
                        </div>
                      </div>


                      <div class="input-group">
                        <button class="btn btn-primary" type="button" v-on:click="clickSearch">
                          <i class="fa fa-search"></i> 검색
                        </button>
                      </div>
                    </div>
                  </div>
                </div>


              </div>
            </fieldset>
            <fieldset style="margin-top:15px;">
              <div class="row">
                <label style="text-align:right;margin-left:10px;">재생일시</label>
                <input type="text" readonly style="border:1px solid #ccc;padding:5px;" v-model="playStartDt"> ~
                <input type="text" readonly style="border:1px solid #ccc;padding:5px;" v-model="playEndDt">

                <div class="input-group" style="margin-left:10px;">
                  <button type="button" class="btn btn-primary" role="button" v-on:click="clickReplay" v-if="!isplaying">
                    <i class="fa fa-play"></i> 재생
                  </button>
                  <button type="button" class="btn btn-danger" role="button" v-on:click="clickStop" v-if="isplaying">
                    <i class="fa fa-stop"></i> 정지
                  </button>
                </div>
                <div class="input-group" style="margin-left:10px;">
                  재생일시 :
                </div>
                <label style="padding-left:20px;">{{imageTimeStr}}</label>
              </div>
            </fieldset>
            <fieldset v-if="searchResult">
              <template v-if="selectStartDt">
                <div style="font-weight: bold; margin-top:20px;">
                  시작 일시를 선택하세요.
                </div>
                <a href="#" v-for="dt in times" v-on:click="clickSelectStartDt(dt)" class="start-dt">{{dt}}</a>
              </template>
              <template v-if="selectEndDt">
                <div style="font-weight: bold; margin-top:20px;">
                  종료 일시를 선택하세요.
                </div>
                <a href="#" v-for="dt in times" v-on:click="clickSelectEndDt(dt)" class="end-dt">{{dt}}</a>
              </template>
            </fieldset>

          </form:form>
        </div>
        <div class="container-fluid ">
          <div class="row">
            <div class="col-sm-4 " v-for="(item,idx) in agentInfos">
              <div class="card">
                <div class="card-body ">
                  <h5 class="card-title">Agent ID [{{ ((item || {}).info || {} ).agentId}}]</h5>
                  <div class="canvas-wrap">
                    <canvas style="width:100%;" v-bind:id="'canvas' + idx"></canvas>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>

  <script type="text/javascript" src="/monitoring/resources/js/view/mntr/mntr-replayer.js"></script>
  <script type="text/javascript" src="/monitoring/resources/js/view/stat/stat-replay.js"></script>


</body>

</html>