<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title>MQTT</title>

  <link rel="shortcut icon" href="${REQUEST_CONTEXT_PATH}/resources/images/favicon.ico" type="image/x-icon">
  <link rel="icon" href="${REQUEST_CONTEXT_PATH}/resources/images/favicon.ico" type="image/x-icon">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <link href="/monitoring/resources/css/mqtt.css" rel="stylesheet" />
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
    crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
    integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <script src="/monitoring/resources/bootstrap-4.3.1/js/bootstrap.min.js"></script>
  <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
  <script src="https://unpkg.com/vue/dist/vue.min.js"></script>
  <script src="/monitoring/resources/js/relaxed-json.js"></script>
  <script src="/monitoring/resources/js/lodash.min.js"></script>
  <script src="//cdn.jsdelivr.net/npm/ramda@0.25/dist/ramda.min.js"></script>

  <script>
    var REQUEST_CONTEXT_PATH = '${REQUEST_CONTEXT_PATH}';
    var exports = {};
  </script>
  <script src="/monitoring/resources/js/view/mqtt/mqtt-test.js"></script>
  <style type="text/css">
    * {
      box-sizing: border-box;
      border-collapse: collapse;
    }

    .testred {
      border: 1px solid red !important;
    }

    .testblue {
      border: 1px solid blue !important;
    }

    .btnwidth {
      min-width: 100px;
    }

    .w100 {
      width: 100px;
    }

    .h10 {
      height: 10px;
    }

    .group {
      background-color: azure;
    }
  </style>
</head>

<body>
  <div id="app">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <a class="navbar-brand" href="#">MQTT TEST</a>
      <button type="button" class="m-1 btn btn-sm btn-primary btnwidth" @click="showDronePanel = !showDronePanel">드론제어</button>
      <button type="button" class="m-1 btn btn-sm btn-primary btnwidth" @click="showTopicTestPanel = !showTopicTestPanel">토픽테스트</button>
      <button type="button" class="m-1 btn btn-sm btn-primary btnwidth" @click="showSchedulingPanel = !showSchedulingPanel">스케쥴링</button>
    </nav>
    <div class="container-fluid " style="height:calc(100vh - 100px);">
      <div class='row ' style="height:100%;">
        <div class='col-sm-6' style="height:100%;overflow-y:scroll">
          <div class="m-2 p-2 group rounded">
            <div class="d-flex align-items-center">
              <h5 class="flex-grow-1">지역구분</h5>지역(ph or gw) <input v-model="loc" class="w100" />
            </div>
          </div>
          <div class="m-2 p-2 group rounded" v-if="showDronePanel">
            <div class="d-flex align-items-center">
              <h5 class="flex-grow-1">드론 제어</h5>
            </div>

            <div>
              <button type="button" class="btn btn-primary btnwidth" @click="clickMove">출동</button>위도
              <input v-model="lat" class="w100" /> 경도
              <input v-model="lng" class="w100" />
            </div>
            <div class="h10"></div>
            <div>
              <button type="button" class="btn btn-primary btnwidth" @click="clickReturn">복귀</button>
            </div>

            <div class="h10"></div>
            <div>
              <button type="button" class="btn btn-info btnwidth" @click="clickHeading">헤딩</button> 헤딩 <input v-model="hdg" class="w100" />
            </div>
            <div class="h10"></div>
            <div>
              <button type="button" class="btn btn-success btnwidth" @click="clickGimbal">GIMBAL</button> pitch <input v-model="pitch" class="w100" />
              yaw
              <input v-model="yaw" class="w100" />
            </div>
            <div class="h10"></div>
            <div>
              <button type="button" class="btn btn-danger btnwidth" @click="clickPause">PAUSE</button>
            </div>
          </div>

          <div class="m-2 p-2 group rounded" v-if="showTopicTestPanel">
            <div class="d-flex align-items-center">
              <h5 class="flex-grow-1">TOPIC 테스트</h5>
            </div>
            <div>
              topic : <input type="text" v-model="testtopic" style="min-width:300px;" />
            </div>
            <div>
              샘플데이터 :
              <button type="button" class="m-1 btn btn-sm btn-info btnwidth" @click="sampleScheduleInit">초기스케쥴</button>
              <button type="button" class="m-1 btn btn-sm btn-info btnwidth" @click="sampleScheduleLowBat">LOWBAT스케쥴</button>
              <button type="button" class="m-1 btn btn-sm btn-info btnwidth" @click="sampleScheduleAbnormal">ABNORMAL스케쥴</button>
              <button type="button" class="m-1 btn btn-sm btn-info btnwidth" @click="sampleScheduleRes">스케쥴링응답1</button>
              <button type="button" class="m-1 btn btn-sm btn-info btnwidth" @click="sampleScheduleRes2">스케쥴링응답2</button>
              <button type="button" class="m-1 btn btn-sm btn-info btnwidth" @click="sampleRobotStat">ROBOT 상태(STAT)</button>
              <button type="button" class="m-1 btn btn-sm btn-info btnwidth" @click="sampleEvent">EVENT</button>
            </div>
            <textarea class="form-control" v-model="topicdata" rows="20"></textarea>
            <button type="button" class="btn btn-success btnwidth" @click="clickSendTopicData">전송</button>
          </div>

          <div class="m-2 p-2 group rounded" v-if="showSchedulingPanel">
            <div class="d-flex align-items-center">
              <h5 class="flex-grow-1">리스케쥴링 테스트</h5>
            </div>
            <div>
              <p>리스케쥴링 데이터를 입력 후 "전송"버튼 클릭 시 새로운 Path가 적용되고, 메인화면 스케쥴링 정보에서 Old(이전 Path), New(변경 Path)를 확인하실 수 있습니다.</p>
            </div>
            <textarea class="form-control" v-model="rescheduling" rows="10"></textarea>
            <button type="button" class="btn btn-success btnwidth" @click="clickRescheduling">전송</button>
          </div>

        </div>

        <div class='col-sm-6 d-flex flex-column'>
          <div class="d-flex align-items-center">
            <h6 class="flex-grow-1">LOG </h6>
            <input class="flex-grow-0" type="checkbox" id="chklockscroll" v-model="lockscroll" /><label class="flex-grow-0 d-inline-block p-1 m-1"
              for="chklockscroll">Lock
              Scroll</label>
            <button type="button" class="btn btn-sm btn-info" @click="clickClearLog">로그삭제</button>
          </div>

          <textarea id="logmsg" class="flex-grow-1" readonly>{{logMsg}}</textarea>
        </div>
      </div>
    </div>
  </div>
</body>

</html>