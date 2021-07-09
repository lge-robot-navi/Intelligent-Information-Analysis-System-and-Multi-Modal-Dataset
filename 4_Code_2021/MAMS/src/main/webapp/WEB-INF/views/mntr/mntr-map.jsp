<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>

<head>
  <title>모니터링 메인</title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <script src="${REQUEST_CONTEXT_PATH}/resources/jquery-ui-1.12.1.custom/external/jquery/jquery.js"></script>
  <script type="text/javascript">
    var $myquery = jQuery.noConflict();
  </script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/jquery-ui-1.12.1.custom/jquery-ui.js"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/jquery-ui-1.12.1.custom/jquery-ui-my.js?v001"></script>
  <link rel="stylesheet" href="${REQUEST_CONTEXT_PATH}/resources/jquery-ui-1.12.1.custom/jquery-ui.css?v001">

  <!-- FAVICONS -->
  <link rel="shortcut icon" href="${REQUEST_CONTEXT_PATH}/resources/images/favicon.ico" type="image/x-icon">
  <link rel="icon" href="${REQUEST_CONTEXT_PATH}/resources/images/favicon.ico" type="image/x-icon">
  <link href="${REQUEST_CONTEXT_PATH}/resources/css/scheduling.css" rel="stylesheet" />
  <link href="${REQUEST_CONTEXT_PATH}/resources/css/heart-beat.css" rel="stylesheet" />
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="/monitoring/resources/css/cia.css" />
  <!-- Bootstrap Font Icon CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
  <style type="text/css">
    html body {
      overflow: hidden
    }

    .google-map {
      width: 100%;
      /*height: 94vh;*/
      /* height: 60vh; */
    }

    .env-map {
      width: 100%;
    }

    .env-map .options {
      font-size: 14px;
    }

    .event-layer {
      margin: 0px;
      padding: 15px;
      background: #fff3cd;
    }

    #wrapenvmap img {
      object-fit: fill;
      width: 100%;
      height: 100%;
    }

    .btn-circle {
      width: 30px;
      height: 30px;
      padding: 6px 0px;
      border-radius: 15px;
      text-align: center;
      font-size: 12px;
      line-height: 1.42857;
    }

    .btn-wide-circle {
      height: 30px;
      padding: 6px 0px;
      border-radius: 15px;
      text-align: center;
      font-size: 12px;
      line-height: 1.42857;
    }
    
    .agent-contextmenu {
   	  display:none;
	  width: 100px;
	  margin: 0;
	  padding: 0;
	  background: #FFFFFF;
	  border-radius: 5px;
	  list-style: none;
	  box-shadow:
	    0 15px 35px rgba(50,50,90,0.1),
	    0 5px 15px rgba(0,0,0,0.07);
	  overflow: hidden;
	  z-index: 999999;
	}
	
	.agent-contextmenu li {
	  border-left: 3px solid transparent;
	  transition: ease .2s;
	}
	
	.agent-contextmenu li a {
	  display: block;
	  padding: 10px;
	  color: #B0BEC5;
	  text-decoration: none;
	  transition: ease .2s;
	}
	
	.agent-contextmenu li:hover {
	  background: #007BFF;
	  border-left: 3px solid #17a2b8;
	}
	
	.agent-contextmenu li:hover a {
	  color: #FFFFFF;
	}
	
	.agent-label{
		border: none;
	    outline:none;
	    background:none;
	}
	.agent-label:focus{
		border: none;
	    outline:none;
	    background:none;
	}

    /* map에 drawing 시 사각형이 생기는 문제로 주석 처리함 */
    /*canvas {*/
    /*border: 1px solid #ccff33;*/
    /*}*/
  </style>
  <!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" -->
  <!-- crossorigin="anonymous"></script> -->
  <!-- jquery easing 문제로 jquery를 slim에서 normal로 변경함 -->
  <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous">
  </script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
    integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <script src="/monitoring/resources/bootstrap-4.3.1/js/bootstrap.min.js"></script>
  <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
  <script src="https://unpkg.com/vue/dist/vue.min.js"></script>
  <script src="/monitoring/resources/js/relaxed-json.js"></script>
  <script src="/monitoring/resources/js/lodash.min.js"></script>
  <script src="//cdn.jsdelivr.net/npm/ramda@0.25/dist/ramda.min.js"></script>
  <script type="text/javascript" src="/monitoring/resources/js/seqplayer.js?ver=007"></script>
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

  <script src="/monitoring/resources/js/vue-comp.js"></script>

  <script>
    var REQUEST_CONTEXT_PATH = '${REQUEST_CONTEXT_PATH}'
    var exports = {};
  </script>

</head>

<body style='overflow-y:hidden'>

  <nav class="navbar navbar-expand-lg navbar-dark bg-primary" id="appNav">
    <a class="navbar-brand" href="#">Multi Agent Monitoring System ({{place}})</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
      aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item active"><a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a></li>
        <li class="nav-item dropdown"><a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false"> 장소({{place}}) </a>
          <div class="dropdown-menu" aria-labelledby="navbarDropdown">
            <a class="dropdown-item" href="#" onclick="clickPohang();">포항</a> <a class="dropdown-item" href="#" onclick="clickGwangju();">광주</a>
          </div>
        </li>
      </ul>
      <div class="form-inline my-2 my-lg-0">
        <span class="text-white mr-3">{{place}}</span>
        <!-- <a href="mntr/scheduling" target="_blank" class="btn btn-success my-2 my-sm-0">스케쥴링 시뮬레이션</a>&nbsp; -->
        <button :class="{ 'btn-warning': btnEnvMapClicked }" class="btn btn-info my-2 my-sm-0" onclick="clickViewEnvMap();">환경맵</button>&nbsp;
        <button :class="{ 'btn-warning': btnSchedulingClicked }" class="btn btn-info my-2 my-sm-0" onclick="clickViewScheduling();">
          스케쥴링 정보</button>&nbsp;
        <button :class="{ 'btn-warning': btnLatLonClicked }" class="btn btn-info my-2 my-sm-0" onclick="clickViewLatlon();">위경도</button>&nbsp;
        <button :class="{ 'btn-warning': btnLogClicked }" class="btn btn-info my-2 my-sm-0" onclick="clickViewLog();">Logging</button>&nbsp;
        <button :class="{ 'btn-warning': btnMileageClicked }" class="btn btn-info my-2 my-sm-0" onclick="clickViewMileage();">마일리지</button>&nbsp;
        <button :class="{ 'btn-warning': btnDroneMonClicked }" class="btn btn-info my-2 my-sm-0" onclick="clickDroneMon();">드론관제</button>&nbsp;
        <button :class="{ 'btn-warning': btnSetClicked }" class="btn btn-info my-2 my-sm-0" onclick="clickSettings();">일반 설정</button>&nbsp;
        <button :class="{ 'btn-warning': btnAutoSearchClicked }" class="btn btn-info my-2 my-sm-0" onclick="clickAgentAutoSearch();">에이전트 자동 검색</button>&nbsp;
        <a href="/monitoring/app/agent/settings" target="mntr_agent_settings" class="btn btn-info my-2 my-sm-0">에이전트 설정</a>&nbsp;
        <a href="/monitoring/logout" target="mntr_logout" class="btn btn-info my-2 my-sm-0">로그아웃</a>
      </div>
    </div>
  </nav>

  <div class="row " id="appBody">
    <template v-if="alerts.length > 0">
      <div style="position:absolute;left:0;top:0;width:100%;">

        <template v-for="ele in alerts">
          <template v-if="ele.type =='info'">
            <div class="alert alert-primary" style="position:static; text-align:center" role="alert">{{ele.msg}}
            </div>
          </template>
          <template v-if="ele.type =='error'">
            <div class="alert alert-primary" role="alert" style="position:static; background-color: red;color:white; text-align:center">{{ele.msg}}
            </div>
          </template>
        </template>

      </div>
    </template>
    <div class="col-sm d-flex flex-column " style="padding-right:0;background:#d7d7d7;">
      <div id="map" class="google-map" style="flex: 3 3 auto"></div>
      <div id="envmap" v-if="envmapShow" class="env-map  d-flex flex-row" :key="envmapShow">
        <div class="text-white bg-secondary m-1" style="width:100%;max-height:350px">
          <div class="d-flex no-gutters">
            <div class="flex-grow-0" id="wrapenvmap">
              <img id="envmapImg" style="width:500px;height:350px" />
            </div>
            <div style="flex:0 0 70px">
              <img id="maplegend" style="width:100%;height:100%;" src="/monitoring/resources/images/transparent.png" />
            </div>
            <div class="p-1 flex-grow-1">
              <div class="p-3 options flex-grow-0">
                <div class="d-flex">
                  <h6 class="card-title flex-grow-0">자동갱신 설정</h6>
                </div>
                <div class="pl-3">
                  <input type="checkbox" id="chkautorefresh" v-model="autorefresh">
                  <label class="pl-2" for="chkautorefresh">자동갱신</label>
                  <span v-if="autorefresh">
                    <label class="pl-2" for="autorefreshinterval">갱신주기</label>
                    <select v-model="autorefreshinterval" id="autorefreshinterval">
                      <option value="1">1 초</option>
                      <option value="2">2 초</option>
                      <option value="3">3 초</option>
                      <option value="4">4 초</option>
                      <option value="5">5 초</option>
                      <option value="6">6 초</option>
                      <option value="7">7 초</option>
                      <option value="8">8 초</option>
                      <option value="9">9 초</option>
                      <option value="10">10 초</option>
                    </select>
                  </span>
                </div>
                <div class="pl-3 pb-3">
                </div>
                <h6 class="card-title">수색영역보기</h6>
                <div class="pl-3 pb-3">
                  <input type="radio" id="envmap1" name="envmap" @click="setEnvMapOptions('mapsearch', appnav.place)">
                  <label for="envmap1">수색완료영역</label><br />
                </div>
                <h6 class="card-title">환경맵상태보기</h6>
                <div class="pl-3">
                  <input type="radio" id="envmap3" name="envmap" @click="setEnvMapOptions('mapheight', appnav.place)">
                  <label for="envmap3">높이지도</label><br />
                  <input type="radio" id="envmap4" name="envmap" @click="setEnvMapOptions('maptemperature', appnav.place)">
                  <label for="envmap4">온도지도</label><br />
                  <input type="radio" id="envmap5" name="envmap" @click="setEnvMapOptions('mapobjprob', appnav.place)">
                  <label for="envmap5">사람,차량 존재확률지도</label><br />
                  <input type="radio" id="envmap6" name="envmap" @click="setEnvMapOptions('mapheightprob', appnav.place)">
                  <label for="envmap6">장애물 확률지도</label>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
    <!-- 스크롤
    padding값이 없으면 스크롤이 생기지 않음.
    오른쪽 padding 값에 따라 스크롤 크기 조절 가능.
    5px시: 화살표 안보이게 할 수 있으나 너무 작음.
    15px:  기본 스크롤로 보임.
    -->
    <div class="col-sm" style="padding-left:0;padding-right:15px;background:#f0f0f0;">
      <div class="col" style='overflow-y: auto; height: calc(100vh - 57px);'>

        <template v-if="logShow">
          <div style="height: 20px"></div>
          <div class="alert alert-primary" role="alert">로그
            <button class="float-right btn btn-sm btn-secondary btn-circle ml-2" @click="clickViewLog">X</button>
            <button class="float-right btn btn-sm btn-success" @click="logs = []">CLEAR</button>
          </div>
          <div class="row" style="margin: 2px;">
            <textarea class="col-sm-12" style="height: 200px;">{{logMsg}}</textarea>
          </div>
        </template>
        <template v-if="latlonShow">
          <div style="height: 20px"></div>
          <div class="alert alert-primary" role="alert">위경도
            <button class="float-right btn btn-sm btn-secondary btn-circle ml-2" @click="clickViewLatlon">X</button>
            <button class="float-right btn btn-sm btn-success" @click="latlons = []">초기화</button>
          </div>
          <div class="row" style="margin: 2px;">
            <textarea class="col-sm-12" style="height: 200px;">{{logLatlon}}</textarea>
          </div>
        </template>
        <template v-if="schedulingShow">
          <div style="height: 20px"></div>
          <div id="schedulingInfo" class="alert alert-primary" role="alert">
            <div style="width: 100%" class="d-flex align-content-center">
              <span class="flex-grow-1"> 스케쥴링 정보</span>
              <button class="btn btn-sm btn-secondary btn-circle mb-1" @click="clickViewScheduling">X</button>
            </div>
            <div style="width: 100%">
              <div class="card">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-12">
                      <div class="alert alert-secondary " role="alert">
                        <button class="btn btn-sm btn-success" @click="doScheduleInit">초기스케쥴링 전송</button>
                        <button class="btn btn-sm btn-warning" @click="doScheduleBattery">리스케쥴링(배터리)</button>
                        <button class="btn btn-sm btn-danger" @click="doScheduleStop">긴급정지</button>
                        <button class="btn btn-sm btn-info" @click="doScheduleReturn">복귀(충전대)</button>
                        <!-- <button class="btn btn-sm btn-info" @click="doHomingLowbat">HOMING(LOWBAT)</button>
                        <button class="btn btn-sm btn-info" @click="doHomingFailure">HOMING(FAILURE)</button> -->

                        <div style="width:100%;font-size: small;">
                          <ol class="mb-0 mt-2">
                            <li>초기 스케쥴링 : 초기 스케쥴링에 사용하실 Robot을 선택하고 나서, 초기 스케쥴링 전송 버튼을 클릭하여 주십시오.</li>
                            <li>리스케쥴링(배터리) : 배터리부족 Robot을 선택하고 나서, 리스케쥴링(배터리) 버튼을 클릭하여 주십시오.</li>
                            <li>긴급정지 : 이동 중인 Robot을 선택하고 나서, 긴급정지 버튼을 클릭하여 주십시오.</li>
                            <li>복귀(충전대) : 충전대로 복귀 시킬 Robot을 선택한 뒤, 복귀(충전대) 버튼을 클릭하여 주십시오.</li>
                          </ol>
                        </div>
                      </div>
                    </div>

                    <template v-for="i in numOfMovingAgent">
                      <div class="col-sm-4">
                        <div class="custom-control-inline custom-control custom-checkbox">
                          <input class="custom-control-input" type="checkbox" :id="'robot_init_'+i" :value="getAgent(i-1).chkScheduleInit"
                            @input="clickChkScheduleInit($event, i-1)" />
                          <label class="custom-control-label" :for="'robot_init_'+i">{{getAgentDisplayName(i-1)}}</label>
                        </div>
                      </div>
                      <template v-if="(i) % 3 === 0 && i !== numOfMovingAgent">
                        <div class="col-sm-12 pb-2"></div>
                      </template>
                    </template>
                    
                  </div>
                </div>
              </div>
            </div>
            <div style="height:5px;width:100%;"></div>

            <div style="width: 100%">
              <div class="card">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-12">
                      <div class="alert alert-secondary" role="alert">
                        <span class="pr-3"><b>전체</b></span>
                        <span class="custom-control-inline custom-control custom-checkbox">
                          <input class="custom-control-input" type="checkbox" id="robot_old_all" @click="schedulingRouterAll()" />
                          <label class="custom-control-label" for="robot_old_all">Old</label>
                        </span>
                        <span class="custom-control-inline custom-control custom-checkbox">
                          <input class="custom-control-input custom-control-input-red" type="checkbox" id="robot_new_all"
                            @click="reSchedulingRouterAll()" />
                          <label class="custom-control-label" for="robot_new_all">New</label>
                        </span>
                      </div>
                    </div>
                    <template v-for="i in numOfMovingAgent">
                      <div class="col-sm-4">
                        <div><b>{{getAgentDisplayName(i-1)}}</b></div>
                        <div class="custom-control-inline custom-control custom-checkbox">
                          <input class="custom-control-input" type="checkbox" name="robot_old_id" :id="'robot_old_'+getAgentId(i-1)"
                          	@click="schedulingRouter(getAgentId(i-1))" :data-agent_id="getAgentId(i-1)" />
                          <label class="custom-control-label" :for="'robot_old_'+getAgentId(i-1)">Old</label>
                        </div>
                        <div class="custom-control-inline custom-control custom-checkbox">
                          <input class="custom-control-input custom-control-input-red" type="checkbox" name="robot_new_id" :id="'robot_new_'+getAgentId(i-1)"
                            @click="reSchedulingRouter(getAgentId(i-1))" :data-agent_id="getAgentId(i-1)" />
                          <label class="custom-control-label" :for="'robot_new_'+getAgentId(i-1)">New</label>
                        </div>
                      </div>
                      <template v-if="(i) % 3 === 0 && i !== numOfMovingAgent">
                        <div class="col-sm-12 pb-2"></div>
                      </template>
                    </template>
                  </div>
                </div>
              </div>
            </div>
        </template>

        <div style="height: 10px"></div>

        <div class="alert alert-primary" role="alert">
          실시간 로봇 정보
          <button :class="{btn:true,'btn-success':!isPlaying, 'btn-warning':isPlaying, 'btn-sm':true}"
            @click="clickPlay">{{isPlaying?'모니터링 중지':'모니터링 시작'}}</button>
          <button type="button" class="btn btn-secondary btn-sm dropdown-toggle float-right" data-toggle="collapse" data-target="#collapseAgent"
            aria-expanded="false" aria-controls="collapseAgent">
          </button>
          <div class="float-right" style="width:10px;height:10px;"></div>
          <a href="/monitoring/system/stat/replay" target="mntr_stat_replay" class="btn btn-info btn-sm float-right">Replay</a>
          <div class="float-right" style="width:10px;height:10px;"></div>
          <a href="/monitoring/system/stat/list" target="mntr_stat_list" class="btn btn-primary btn-sm float-right">조회</a>
        </div>
        <div class="collapse show" id="collapseAgent">
          <div class="row px-2">
            <template v-for="i in getAgentCnt()">
              <!-- <div class="col-sm-4" style="padding-left: 1pt;padding-right: 1pt;"> -->
              <div class="col-sm-4 px-2">
                <div class="ml-4 text-center position-relative">
                
                  <span>{{getAgentDisplayName(i-1)}}</span>
              
                  <template v-if="getAgentIsHealthy(i-1)">
                    <div class="mt-1 mr-3 led-green float-right" :key="ledkey"></div> <!-- live -->
                  </template>
                  <template v-else>
                    <div class="mt-1 mr-3 led-red float-right" :key="ledkey"></div>
                  </template>
              
                </div>
                <div class="card">
                  <canvas ref="player" :data-agent-id="getAgentId(i-1)" class="card-img-top" width="640" height="480" @click="viewBigPlayer(i-1);">
                  </canvas>
                  <!-- <div class="card-body"> -->
                  <!-- <h5 class="card-title">{{getAgentDisplayName(i-1)}}</h5> -->
                  <!-- <p class="card-text"> -->
                  <!-- <b>lat</b>: {{getPos(getData(i-1).lat)}}, <b>lng</b>: {{getPos(getData(i-1).lon)}} -->
                  <!-- </p> -->
                  <!-- </div> -->
                </div>
              </div>
              <template v-if="(i) % 3 ===0 && i !== getAgentCnt()">
                <div class="col-sm-12 pb-1"></div>
              </template>
            </template>
          </div>
        </div>
        <div style="height: 20px"></div>
        <div class="alert alert-primary align-middle d-flex" role="alert" v-bind:style="alertTitleStyle">
          <div class="flex-grow-1">
            이상 상황 발생 ({{ alarmCnt }})
            &nbsp;&nbsp;이벤트 표시수&nbsp;<input v-model="eventsShowCnt" style="width:50px;" />
          </div>
          <div style="width:10px;height:10px;"></div>
          <a id="replay" href="/monitoring/system/event/list" target="mntr_event_list" class="btn btn-primary btn-sm float-right">조회화면</a>

          <div style="width:10px;height:10px;"></div>
          <button type="button" class="btn btn-success btn-sm" @click="clickAllNormalEvent">
            전체정상상황
          </button>
          <div style="width:10px;height:10px;"></div>
          <button type="button" class="btn btn-warning btn-sm" @click="clickAllAbnormalEvent">
            전체이상상황
          </button>
          <div style="width:10px;height:10px;"></div>
          <button type="button" class="btn btn-danger btn-sm " @click="alarmClearAll()">
            전체 삭제
          </button>
        </div>
        <div id="event_list"></div>
        <div :key="keyEvents" style="max-height: 350px;overflow:auto;">
          <template v-if="renderAlarmComponent " v-for="(event,i) in (events||[]).slice(0,eventsShowCnt+1)" :key="event.eventSn">
            <div v-if="i < eventsShowCnt" class="alert alert-warning fade show d-flex align-items-center" role="alert"
              style="background:#fae100; margin:0">
              <div class="flex-grow-1">
                <!-- [상태] -->
                <template v-if="event.status == 1">
                  <div class="d-inline-block mt-1 mr-3 led-warn" title="1 : caution"></div>
                </template>
                <template v-else-if="event.status == 2">
                  <div class="d-inline-block mt-1 mr-3 led-red" title="2 : warning"></div>
                </template>
                <strong>[{{event.eventSn}}]{{getAreaName(event)}}
                  {{ ( (getAgentById(event.robotId).info||{}).fixed =="Y"?"Agent(F)":"Robot") + "_" + event.robotId }}</strong>
                {{ getEventInfo(event.abnormalId).codeNm  }}
                <br />
                <span style="margin-left:40px;">{{getdt(event.eventDt)}}</span>
              </div>
              <div class="align-self-center justify-content-end flex-grow-0 ">
                <template v-if="event.fbNeed == 'Y'">
                  <button class="btn btn-sm btn-success btn-circle mr-2" title="피드백 필요" style="cursor:default;">Y</button>
                </template>
                <template v-else>
                  <button class="btn btn-sm btn-secondary btn-circle mr-2" title="피드백 필요 없음" style="cursor:default;">N</button>
                </template>
              </div>
              <div class="align-self-center justify-content-end flex-grow-0 ">
                <button type="button" class="btn btn-primary btn-sm mr-2" @click="alarmReplay(event)" style="height:30px;width:60px;">
                  보기
                </button>
              </div>
              <i class="fa fa-chevron-circle-down flex-grid-0 justify-content-end" data-toggle="collapse" :href="'#collapseEvent_'+i"
                @click="clickEventItem(event, i)" style="margin:0px 0px 0px 0px;cursor:pointer;font-size:30px"></i>
            </div>

            <div v-if="i < eventsShowCnt" class="collapse" :id="'collapseEvent_'+i" :class="{show:event.expand}">
              <div class="event-layer d-flex">
                <div class="flex-grow-1">
                  <!-- <span style="color:blue">
                    {{event.status}}
                  </span> -->
                  [위치]
                  <span style="color:blue">위도: {{ parseFloat(event.eventPosX).toFixed(5) }} 경도: {{ parseFloat(event.eventPosY).toFixed(5) }}</span>
                </div>


                <template v-if="event.fbNeed =='Y'">
                  <button type="button" class="btn btn-warning btn-sm" @click="clickAbnormalEvent(event)">
                    이상상황
                  </button>
                  <div class="p-1"></div>
                  <button type=" button" class="btn btn-success btn-sm" @click="clickNormalEvent(event)">
                    정상상황
                  </button>
                </template>
                <template v-else>
                  <button type="button" class="btn btn-success btn-sm" @click="alarmClear(event)">
                    확인
                  </button>
                </template>

                <div class="p-1"></div>
                <button type="button" class="btn btn-info btn-sm" @click="cmdReScheduleAbnormal(event)">
                  리스케쥴링
                </button>
                <div class="p-1"></div>
                <button type="button" class="btn btn-danger btn-sm" @click="cmdDroneMovePrepare(event)">
                  드론관제
                </button>

              </div>
            </div>

            <div v-if="i==eventsShowCnt">
              {{ (events.length -eventsShowCnt) + " 개의 이상상황이 더 있습니다."}}
            </div>
          </template>
        </div>
        <div style="height: 40px"></div>
      </div>
    </div>
  </div>

  <style>
    .modal-dialog {
      width: 1000px;
      max-width: 1000px;
      margin: 30px auto;
    }

    table.paleBlueRows td,
    table.paleBlueRows th {
      padding: 3px 2px;
      border: 1px solid #ccc;
    }

    table.paleBlueRows td {
      font-size: 15px;
      padding: 12px 12px;
    }

    table.paleBlueRows th {
      font-size: 15px;
      font-weight: bold;
      color: #000;
      background-color: #cfdff2;
      text-align: center;
    }
    
  </style>

  <!-- player 상세보기 -->
  <div class="modal fade" id="playerModal" tabindex="-1" role="dialog" aria-labelledby="playerModalTitle" aria-hidden="true" style="overflow: hidden;">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="palyerModalTitle">상세 보기</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body row justify-content-center align-items-end">
          <canvas id="playerBig" width="640" height="480"></canvas>
          <div id="moveController" style="display: none;">
            <div id="viewMoveValue" style="width: fit-content; margin-left: 50px; margin-bottom: 10px;">
              <div class="card bg-light">
                <div class="card-body" style="padding: 10px;">
                  <div class="row">
                    <div class="col-sm-12 d-flex">
                      <div class="d-flex flex-column flex-grow-1">
                        <div class="d-flex" style="margin-bottom: 5px;">
                          <div class="flex-grow-0" style="margin-right: 6px;">Agent ID</div>
                          <input id="moveCtlRobotId" style="width: 65px;" class="d-inline-block flex-grow-2 mx-1 text-center" readonly>
                        </div>
                        <div class="d-flex" style="margin-bottom: 5px;">
                          <div class="flex-grow-0">상하 이동</div>
                          <input id="robotLin" style="width: 65px;" class="d-inline-block flex-grow-2 mx-1 text-center" readonly>
                        </div>
                        <div class="d-flex" style="margin-bottom: 5px;">
                          <div class="flex-grow-0">좌우 회전</div>
                          <input id="robotAng" style="width: 65px;" class="d-inline-block flex-grow-2 mx-1 text-center" readonly>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div>
              <div id="controlMoveValue" class="text-center"
                style="border-radius: 25%; background-color: lightgray; margin-left: 50px; margin-bottom: 10px;">
                <button style="border: none; background: unset; outline: none;" id="moveLinVelUp">
                  <i id="moveStyleUp" class="bi bi-arrow-up-circle-fill h1"></i>
                </button><br>
                <button style="border: none; background: unset; outline: none;" id="moveAngVelLeft">
                  <i id="moveStyleLeft" class="bi bi-arrow-left-circle-fill h1"></i>
                </button>
                <button style="border: none; background: unset; outline: none;" id="moveLinAngReset">
                  <i id="moveStyleReset" class="bi bi-square-fill h1"></i>
                </button>
                <button style="border: none; background: unset; outline: none;" id="moveAngVelRight">
                  <i id="moveStyleRight" class="bi bi-arrow-right-circle-fill h1"></i>
                </button><br>
                <button style="border: none; background: unset; outline: none;" id="moveLinVelDown">
                  <i id="moveStyleDown" class="bi bi-arrow-down-circle-fill h1"></i>
                </button>
              </div>
            </div>
          </div>
          <!-- <div style="border-radius: 50%; width: 70%; background-color: lightgray;">
            <i class="bi bi-arrow-up-circle h1"></i><br>
            <i class="bi bi-arrow-left-circle h1"></i>
            <i class="bi bi-square h1"></i>
            <i class="bi bi-arrow-right-circle h1"></i><br>
            <i class="bi bi-arrow-down-circle h1"></i>
          </div> -->
          <div style="width: 100%;">
            <!--
            <div id="audio" style="width: 100%;">
              <table>
                <colgroup>
                  <col width="20%">
                  <col width="80%">
                </colgroup>
                <tr>
                  <td>
                    <audio ref="audio" :src="audioSrc" @ended="handleEnded" controls="true" @canplay="handleCanPlay" id="audioCtl"></audio>
                  </td>
                  <td>
                    <div style="width:100%;" ref="waveform">
                      <av-waveform ref-link="audio" :key="waveformKey" :canv-width="avwidth"></av-waveform>
                    </div>
                  </td>
                </tr>
              </table>
            </div>
            -->
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
          <!-- <button onclick="testplay()">테스트</button> -->
        </div>
      </div>
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
        <div class="modal-body text-center">
          <canvas id="eventPlayer" width="640" height="480" style="width: 640px; height: 480px;"></canvas>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
        </div>
      </div>
    </div>
  </div>
  
  <!-- 로봇 이동거리 마일리지 다이알로그 -->
  <div class="modal fade" id="mileageMonModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="width:1600px;max-width:1600px;min-width:900px;" id="appMileageModal">
      <div class="modal-content" style="height: 800px;">
        <div class="modal-header">
          <h5 class="modal-title">로봇 이동거리 마일리지 통계</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" style="padding-top: 0px;">
          <div>
            <div id="optionMileage">
              <div id="mileageDates" class="row justify-content-around alert alert-info" role="alert" style="padding: 10px 10px 5px 10px;">
                <div id="selectDate">
                  <label><b>조회 기간</b></label>
                  <input name="mileageDateIn" id="mileageStartDate" class="d-inline-block flex-grow-1 mx-1" type="date" /> ~
                  <input name="mileageDateIn" id="mileageEndDate" class="d-inline-block flex-grow-1 mx-1" type="date" />
                </div>
                <div id="selectAgentList">
                  <b style="margin-right: 10px">로봇 선택</b>
                    <c:forEach var="i" begin="0" end="9" step="1">
                      <c:if test="${i eq 0}">
                        <input name="selectAgentAll" type="checkbox" id="mileageAgentAll" value="${i}">
                        <label style="margin-right: 10px" for="mileageAgentAll">ALL</label>
                      </c:if>
                      <c:if test="${i ne 0}">
                        <input name="selectAgentItem" type="checkbox" id="mileageAgent${i}" value="${i}">
                        <label style="margin-right: 10px" for="mileageAgent${i}">Robot_<c:out value="${i}" /></label>
                      </c:if>
                    </c:forEach>
                </div>
              </div>
            </div>

            <div class="row" id="renderChartAndMap" style="height: 580px; padding: 0px 15px;">
              <div id="chart_mileage" style="width: 39%; margin-right: 15px;"></div>
              <div style="position: absolute; top: 80px; right: 80px; z-index: 1;">
                <select id="locationMileage" style="width: 80px; height: 40px; font-size: 18px;"
                        onchange="setMileageMap(this.value)">
                  <option value="ph" selected="selected">포항</option>
                  <option value="gw">광주</option>
                </select>
              </div>
    	      <div id="map_mileage" style="width: 60%;"></div>
    	      
    	      <div id="map_loading" style="position: absolute; right: 360px; top: 120px; z-index: 1;"></div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal" ref="close">닫기</button>
        </div>
      </div>
    </div>
  </div>

  <!-- 드론 모니터링 다이알로그 -->
  <div class="modal fade" id="droneMonModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="width:1300px;max-width:1300px;min-width:900px;" id="appDroneMonModal">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">드론 실시간 모니터링</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body text-center">
          <!-- 드론 실시간 영상 -->
          <!-- 참고: 나중에 아이피 마지막 237이 29로 바뀔 예정임 -->
          <div class="row">
            <div class="col-md-9">
              <div id="streamDrone" class="mt-4"></div>
            </div>
            <div class="col-md-3">
              <div>
                <button class="btn btn-primary btn-sm float-left" onclick="cmdDroneReturn()">드론복귀</button>
                <button class="btn btn-danger btn-sm float-right" onclick="cmdDronePause()">비상정지</button>
              </div>
              <div class="pt-5">
                <div class="card bg-light">
                  <div class="card-body">
                    <div class="row">
                      <div class="col-sm-12 d-flex">
                        <div class="d-flex flex-column flex-grow-1">
                          <div class="d-flex">
                            <div class="flex-grow-0">위도</div>
                            <input id="dronelat" style="width:80px" class="d-inline-block flex-grow-1 mx-1" />
                          </div>
                          <div class="d-flex">
                            <div class="flex-grow-0">경도</div>
                            <input id="dronelng" style="width:80px" class="d-inline-block flex-grow-1 mx-1" />
                          </div>
                        </div>
                        <div class="d-flex flex-grow-0 ">
                          <button class="btn btn-primary btn-sm" style="width:48px;" onclick="cmdDroneMove2()">드론출동</button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="pt-2">
                <div class="card bg-light">
                  <div class="card-body">
                    <div class="row">
                      <div class="col-md-4 float-left" style="color:blue">0</div>
                      <div class="col-md-4"><span id="hdgVal" name="hdgVal" style="color:red">0</span></div>
                      <div class="col-md-4 float-right" style="color:blue">360</div>
                    </div>
                    <input id="hdg" name="hdg" type="range" value="0" min="0" max="360" style="width:100%">
                    <div>
                      헤딩
                      <button class="btn btn-success btn-sm float-right" onclick="cmdDroneHeading()">전송</button>
                    </div>
                  </div>
                </div>
              </div>
              <div class="pt-2">
                <div class="card bg-light">
                  <div class="card-body">
                    pitch
                    <div class="row">
                      <div class="col-md-4 float-left" style="color:blue">-45</div>
                      <div class="col-md-4"><span id="pitchVal" name="pitchVal" style="color:red">30</span></div>
                      <div class="col-md-4 float-right" style="color:blue">135</div>
                    </div>
                    <input id="pitch" name="pitch" type="range" value="30" min="-45" max="135" style="width:100%">
                    yaw
                    <div class="row">
                      <div class="col-md-4 float-left" style="color:blue">-330</div>
                      <div class="col-md-4"><span id="yawVal" name="yawVal" style="color:red">0</span></div>
                      <div class="col-md-4 float-right" style="color:blue">330</div>
                    </div>
                    <input id="yaw" name="yaw" type="range" value="0" min="-330" max="330" style="width:100%">
                    <div>
                      pitch & yaw
                      <button class="btn btn-success btn-sm float-right" onclick="cmdDroneGimbal()">전송</button>
                    </div>
                  </div>
                </div>
              </div>

              <div class="pt-2">
                <div class="card bg-light">
                  <div class="card-body">
                    <div class="row">
                      <div class="col-sm-12 d-flex">
                        <div class="d-flex flex-column flex-grow-1">
                          <div class="d-flex">
                            <div class="flex-grow-0">드론영상품질</div>
                            <input id="droneQuality" style="width:40px" class="d-inline-block flex-grow-1 mx-1" />
                            <button class="btn btn-primary btn-sm" onclick="cmdApplyDroneQuality()">적용</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal" ref="close">닫기</button>
        </div>
      </div>
    </div>
  </div>

	<style>
      .fieldsetSearch {
        border-width: 2px;
        border-style: groove;
        border-color: threedface;
        border-image: initial;
        padding-block-end: 10px;
        padding-block-start: 5.6px;
        padding-inline-end: 12px;
        padding-inline-start: 12px;
      }
      .legendSearch {
      	background-color: white;
      	color: black;
      	padding: 3px 6px;
      	text-align: center;
      	width: auto;
      	font-size: large;
      }
    </style>

	<!-- 에이전트 자동 검색 다이알로그 -->
  <div class="modal fade" id="appSearchModal" tabindex="-1" role="dialog" aria-labelledby="searchModalTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="min-width: 810px; width: 810px; max-width: 810px;" id="agentAutoSearchModal">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="searchModalTitle">에이전트 자동 검색</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="row modal-body">
          <div class="col">
            <fieldset class="fieldsetSearch">
              <legend class="legendSearch font-weight-bold">에이전트 설정 리스트</legend>
              <div style="height: 350px;">
                <ul id="getAgentSettings"></ul>
              </div>
            </fieldset>
          </div>
          <div class="col">
            <fieldset class="fieldsetSearch">
              <legend class="legendSearch font-weight-bold">에이전트 상태 및 설정</legend>
              <div id="getAgentStatus" style="height: 350px;"></div>
            </fieldset>
          </div>
        </div>
        <div class="modal-footer">
          <label style="margin-right: 40px;">화면에 표시할 에이전트를 선택하시고, [적용] 버튼을 클릭하시면 화면에 반영 됩니다.</label>
          <button type="button" class="btn btn-primary" v-on:click="clickFixed" data-dismiss="modal" ref="close">적용</button>
          <button type="button" class="btn btn-danger" data-dismiss="modal" ref="close">취소</button>
        </div>
      </div>
    </div>
  </div>
  
  <!-- 설정 다이알로그 -->
  <div class="modal fade" id="settingsModal" tabindex="-1" role="dialog" aria-labelledby="settingsModalTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="min-width:800px;width:800px;max-width:800px;" id="appSettingsModal">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="settingsModalTitle">설정</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <table class="paleBlueRows">
            <colgroup>
              <col width="250px" />
              <col width="650px" />
            </colgroup>
            <tr>
              <th> 실시간 모니터링 저장 여부
              </th>
              <td>
                <div>
                  <input type="checkbox" id="saveEvtImgAgent1" v-model="settings[0].ison"> <label for="saveEvtImgAgent1">{{getAgentId(0)}}</label>
                  <input type="checkbox" id="saveEvtImgAgent2" v-model="settings[1].ison"> <label for="saveEvtImgAgent2">{{getAgentId(1)}}</label>
                  <input type="checkbox" id="saveEvtImgAgent3" v-model="settings[2].ison"> <label for="saveEvtImgAgent3">{{getAgentId(2)}}</label>
                </div>
                <div>
                  <input type="checkbox" id="saveEvtImgAgent4" v-model="settings[3].ison"> <label for="saveEvtImgAgent4">{{getAgentId(3)}}</label>
                  <input type="checkbox" id="saveEvtImgAgent5" v-model="settings[4].ison"> <label for="saveEvtImgAgent5">{{getAgentId(4)}}</label>
                  <input type="checkbox" id="saveEvtImgAgent6" v-model="settings[5].ison"> <label for="saveEvtImgAgent6">{{getAgentId(5)}}</label>
                </div>
                <div>
                  <input type="checkbox" id="saveEvtImgAgent7" v-model="settings[6].ison"> <label for="saveEvtImgAgent7">{{getAgentId(6)}}</label>
                  <input type="checkbox" id="saveEvtImgAgent8" v-model="settings[7].ison"> <label for="saveEvtImgAgent8">{{getAgentId(7)}}</label>
                  <input type="checkbox" id="saveEvtImgAgent9" v-model="settings[8].ison"> <label for="saveEvtImgAgent9">{{getAgentId(8)}}</label>
                </div>
                <!--  드론은 실시간 이미지 저장 불가. <div>
                  <input type="checkbox" id="saveEvtImgAgent10" v-model="settings[9].ison"> <label for="saveEvtImgAgent10">드론</label>
                </div> -->
              </td>
            </tr>

            <tr>
              <th> 실시간이미지 전송여부
              </th>
              <td>
                <div>
                  <input type="checkbox" id="sendEvtImgAgent1" v-model="setsend[0].stop"> <label for="sendEvtImgAgent1">{{getAgentId(0)}}</label>
                  <input type="checkbox" id="sendEvtImgAgent2" v-model="setsend[1].stop"> <label for="sendEvtImgAgent2">{{getAgentId(1)}}</label>
                  <input type="checkbox" id="sendEvtImgAgent3" v-model="setsend[2].stop"> <label for="sendEvtImgAgent3">{{getAgentId(2)}}</label>
                </div>
                <div>
                  <input type="checkbox" id="sendEvtImgAgent4" v-model="setsend[3].stop"> <label for="sendEvtImgAgent4">{{getAgentId(3)}}</label>
                  <input type="checkbox" id="sendEvtImgAgent5" v-model="setsend[4].stop"> <label for="sendEvtImgAgent5">{{getAgentId(4)}}</label>
                  <input type="checkbox" id="sendEvtImgAgent6" v-model="setsend[5].stop"> <label for="sendEvtImgAgent6">{{getAgentId(5)}}</label>
                </div>
                <div>
                  <input type="checkbox" id="sendEvtImgAgent7" v-model="setsend[6].stop"> <label for="sendEvtImgAgent7">{{getAgentId(6)}}</label>
                  <input type="checkbox" id="sendEvtImgAgent8" v-model="setsend[7].stop"> <label for="sendEvtImgAgent8">{{getAgentId(7)}}</label>
                  <input type="checkbox" id="sendEvtImgAgent9" v-model="setsend[8].stop"> <label for="sendEvtImgAgent9">{{getAgentId(8)}}</label>
                </div>
                <!-- 드론은 처리 불가.  
                <div>
                  <input type="checkbox" id="sendEvtImgAgent10" v-model="setsend[9].stop"> <label for="sendEvtImgAgent10">드론</label>
                </div>
                 -->
              </td>
            </tr>
            <tr>
              <th>서버 로그 설정</th>
              <td>
                <input type="checkbox" id="udpLog" v-model="config.udpLog"> <label for="udpLog">UDP LOG</label>
                <input type="checkbox" id="statLog" v-model="config.statLog"> <label for="statLog">상태정보</label>
                <input type="checkbox" id="eventLog" v-model="config.eventLog"> <label for="eventLog">이벤트정보</label>
              </td>
            </tr>
            <tr>
              <th rowspan="2">드론URL설정</th>
              <td>
                <input type="text" id="dronUrlPohang" v-model="dronUrlPohang" style="width:100%;">
              </td>
            </tr>
            <tr>
              <td>
                <input type="text" id="dronUrlGwangju" v-model="dronUrlGwangju" style="width:100%;">
              </td>
            </tr>
          </table>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" v-on:click="clickSave">저장</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal" ref="close">닫기</button>
        </div>
      </div>
    </div>
  </div>

  <!-- 전체 이상상황 해지-->
  <div class="modal fade" id="clearEvtModal" tabindex=" -1" role="dialog" aria-labelledby="clearEvtModalTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="width:400px;max-width:350px;">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="clearEvtModalTitle">전체 이상상황 삭제</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          전체 이상상황을 삭제 하시겠습니까?
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" onclick="clickClearEvtAll()">전체 이상상황 삭제</button> &nbsp;
          <button type="button" class="btn btn-secondary" data-dismiss="modal" ref="close">닫 기</button>
        </div>
      </div>
    </div>
  </div>

  <script type="text/javascript" src="/monitoring/resources/js/view/mntr/event-replay.js"></script>
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCoz722xBEI9ywaCfWdykSnqnVOo2XRWyQ"></script>
  <script type="text/javascript" src="/monitoring/resources/js/view/mntr/mntr-map.js"></script>
  <script type="text/javascript" src="/monitoring/resources/js/view/mntr/mntr-map-overlay.js"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/view/mntr/mntr-mqtt.js"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/scheduling/jquery.easing.1.3.js"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/scheduling/markerAnimate.js"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/scheduling/SlidingMarker.js"></script>
  <script>
    SlidingMarker.initializeGlobally(); // marker를 global 처리
  </script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/scheduling/markerwithlabel.terikon.js"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/view/mntr/mntr-scheduling.js"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/view/mntr/mntr-utils.js"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/view/mntr/mntr-envmap.js"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/view/mntr/mntr-agent.js"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/view/mntr/mntr-event.js"></script>
  <script src="${REQUEST_CONTEXT_PATH}/resources/js/view/mntr/mntr-drone.js"></script>
</body>

</html>