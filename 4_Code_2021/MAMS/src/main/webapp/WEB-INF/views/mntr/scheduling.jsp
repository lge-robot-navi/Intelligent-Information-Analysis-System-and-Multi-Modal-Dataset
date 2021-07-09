<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>

<head>
  <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
  <meta charset="utf-8" />
  <title>스케쥴링 테스트 시뮬레이션</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" crossorigin="anonymous" />
  <link href="${REQUEST_CONTEXT_PATH}/resources/css/scheduling.css" rel="stylesheet" />
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCoz722xBEI9ywaCfWdykSnqnVOo2XRWyQ"></script>
  <script src="https://code.jquery.com/jquery-2.2.4.min.js" crossorigin="anonymous"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/scheduling/jquery.easing.1.3.js"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/scheduling/markerAnimate.js"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/scheduling/SlidingMarker.js"></script>
  <script>
    SlidingMarker.initializeGlobally(); // marker를 global 처리
    var exports = {};
  </script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/scheduling/markerwithlabel.terikon.js"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/scheduling/scheduling.js"></script>
  <script>
    /////////////////////////////////////////////////// map 초기화 및 드론을 이용한 현재 위치 파악
    $(function () {
      initMap();
      addDrone({
        lat: 36.1123,
        lng: 129.3928
      });

      // 클릭시 위경도 위치를 얻기 위해 (드론으로 처리 )
      google.maps.event.addListener(map, "click", function (event) {
        droneMarker.setDuration(duration);
        droneMarker.setEasing(easing);
        droneMarker.setPosition(event.latLng);

        printEvent(event.latLng);
      });
    });
  </script>
</head>

<body>
  <div id="floating-panel">
    <div class="card">
      <div class="card-header text-white bg-dark font-weight-bold txt-scheduling">
        스케쥴링 정보
      </div>
      <div class="card-body txt-scheduling">
        <div class="text-lg-left">스케쥴링 정보</div>
        <div class="custom-control-inline custom-control custom-checkbox">
          <input class="custom-control-input" type="checkbox" id="s_robot1" value="1" onClick="schedulingRouter(1)" />
          <label class="custom-control-label" for="s_robot1">Robot_1</label>
        </div>
        <div class="custom-control-inline custom-control custom-checkbox">
          <input class="custom-control-input" type="checkbox" id="s_robot2" value="2" onClick="schedulingRouter(2)" />
          <label class="custom-control-label" for="s_robot2">Robot_2</label>
        </div>
        <div class="custom-control-inline custom-control custom-checkbox">
          <input class="custom-control-input" type="checkbox" id="s_robot3" value="3" onClick="schedulingRouter(3)" />
          <label class="custom-control-label" for="s_robot3">Robot_3</label>
        </div>
        <div class="text-lg-left pt-3">리스케쥴링 정보</div>
        <div class="custom-control-inline custom-control custom-checkbox">
          <input class="custom-control-input custom-control-input-red" type="checkbox" id="rs_robot1" value="1" onClick="reSchedulingRouter(1)" />
          <label class="custom-control-label" for="rs_robot1">Robot_1</label>
        </div>
        <div class="custom-control-inline custom-control custom-checkbox">
          <input class="custom-control-input custom-control-input-red" type="checkbox" id="rs_robot2" value="2" onClick="reSchedulingRouter(2)" />
          <label class="custom-control-label" for="rs_robot2">Robot_2</label>
        </div>
        <div class="custom-control-inline custom-control custom-checkbox">
          <input class="custom-control-input custom-control-input-red" type="checkbox" id="rs_robot3" value="3" onClick="reSchedulingRouter(3)" />
          <label class="custom-control-label" for="rs_robot3">Robot_3</label>
        </div>
      </div>
    </div>
  </div>

  <div id="floating-panel-others">
    <div class="card">
      <div class="card-header text-white bg-dark font-weight-bold txt-scheduling">
        시뮬레이션
      </div>
      <div class="card-body txt-scheduling">
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="simType" id="scheduling" value="0" checked>
          <label class="form-check-label" for="scheduling" onClick="checkRadioButton()">스케쥴링</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="simType" id="rescheduling" value="1">
          <label class="form-check-label" for="rescheduling" onClick="checkRadioButton()">리스케쥴링</label>
        </div>
        <div class="pt-3"></div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="checkbox" id="robot1" value="1" onClick="actSimulation(1)" />
          <label class="form-check-label" for="robot1">Robot_1</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="checkbox" id="robot2" value="2" onClick="actSimulation(2)" />
          <label class="form-check-label" for="robot2">Robot_2</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="checkbox" id="robot3" value="3" onClick="actSimulation(3)" />
          <label class="form-check-label" for="robot3">Robot_3</label>
        </div>
      </div>
    </div>
  </div>

  <div id="map_canvas"></div>
  <div id="log" class="control"></div>
</body>

</html>