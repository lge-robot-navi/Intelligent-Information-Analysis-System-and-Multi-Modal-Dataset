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
        <a class="navbar-brand" href="/monitoring/mntr/map">Multi Agent Monitoring System - Event List</a>
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
          <h3>에이전트별 이상상황 이력조회</h3>
        </div>

        <!-- search -->
        <div class="well search-wrap">
          <form:form method="get" action="list" modelAttribute="abstractPage" cssClass="form-inline search-form" role="form" autocomplete="off"
            ref="submit">
            <fieldset>
              <div class="row">
                <div class="col-sm-10">
                  <div class="row">
                    <div class="col-sm-12">
                      <label>이상상황 발생일시</label>
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
                <th>Abnormal ID</th>
                <th>Abnormal</th>
                <th>Device ID</th>
                <th>발생 일시</th>
                <th>POS X</th>
                <th>POS Y</th>
                <th>ROBOT ID</th>
                <th>Replay</th>
                <th>확인여부</th>
              </tr>
            </thead>

            <tbody>
              <c:if test="${empty list}">
                <tr>
                  <td colspan="11">
                    <spring:message code="common.dataIsEmpty" />
                  </td>
                </tr>
              </c:if>
              <c:forEach var="item" items="${list}">
                <tr data-toggle="lightbox">
                  <td>${item.eventSn}</td>
                  <td>${item.areaCode}
                    <c:if test="${item.areaCode == 'P'}">
                      (포항)
                    </c:if>
                    <c:if test="${item.areaCode == 'G'}">
                      (광주)
                    </c:if>
                  </td>
                  <td>${item.abnormalId}</td>
                  <td>
                    <c:if test="${not empty item.abnormal}">
                      ${item.abnormal.codeNm}
                    </c:if>
                  </td>
                  <td>${item.deviceId}</td>
                  <td>${item.eventDt}</td>
                  <td>${item.eventPosX}</td>
                  <td>${item.eventPosY}</td>
                  <td>${item.robotId}</td>

                  <td style="padding:0px;">
                    <button type="button" class="btn btn-default" role="button" @click="clickReplayItem(${item.eventSn})" style="margin:0px;">
                      <i class="fa fa-replay"> </i> 재생
                    </button>
                  </td>

                  <c:if test="${item.confirmYn eq 'Y'}">
                    <td class="success"><span class="success">확인</span></td>
                  </c:if>
                  <c:if test="${item.confirmYn eq 'N' or item.confirmYn eq ''}">
                    <td class="danger"><span @click="alarmClear(${item.eventSn});" class="danger">미확인</span></td>
                  </c:if>

                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
        <jsp:include page="/include/paging.jsp" flush="true" />

      </section>
    </div>
  </div>

  <!-- 이벤트 다시보기 -->
  <div class="modal fade" id="eventReplayModal" tabindex="-1" role="dialog" aria-labelledby="eventReplayModalTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="eventReplayModalTitle">이벤트 다시 보기</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <canvas id="eventPlayer" style="width: 100%; height: 100%;"></canvas>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal">닫기</button>
        </div>
      </div>
    </div>
  </div>

  <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
  <script src="https://unpkg.com/vue/dist/vue.js"></script>
  <script src="/monitoring/resources/js/relaxed-json.js"></script>

  <script type="text/javascript" src="/monitoring/resources/js/view/mntr/event-replay.js"></script>


  <script>
    // event replay.
    var eventReplay;
    $("#eventReplayModal").on("show.bs.modal", function (e) {
      console.log("event replay show");
    });
    $("#eventReplayModal").on("shown.bs.modal", function (e) {
      console.log("event replay shown");
    });

    $("#eventReplayModal").on("hide.bs.modal", function (e) {
      if (eventReplay) {
        eventReplay.stop();
        eventReplay = null;
      }
      console.log("event replay hide");
    });
    $("#eventReplayModal").on("hidden.bs.modal", function (e) {
      console.log("event replay hidden");
    });

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
          },
          alarmClear: function (sn) {
            if (!confirm("이상상황을 확인처리 하시겠습니가?")) {
              return;
            }
            var app = this;
            axios
              .post("/monitoring/agentif/clearAlarm", {
                eventSn: sn
              })
              .then(function (res) {
                // alarm clear는 웹소켓으로 브로드 캐스팅 됨.
                console.log(res);
                app.$refs.submit.submit();
              }).catch(function (error) {
                console.error(error);
              });
          },
          clickReplayItem: function (sn) {
            console.info("replay sn is :" + sn);
            var images;
            eventReplay = createReplay("/monitoring/mntr/evtimage", document.getElementById("eventPlayer"));
            axios
              .get("/monitoring/mntr/evtinfo/" + sn)
              .then(function (res) {
                console.log("res", res);
                images = res.data;
                if (images.length < 1) {
                  alert("이벤트에 이미지가 존재하지 않습니다.");
                  return;
                }
                $("#eventReplayModal").modal("show");

                eventReplay.start(200, images);
              }).catch(function (ex) {
                console.error("E", ex);
              });
          }
        }
      });
    });
  </script>
</body>

</html>