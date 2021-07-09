import maxios from "axios";
import mvue from "vue";
import mlodash from "lodash";

import * as If from "./if";

declare var axios: typeof maxios;
declare var Vue: typeof mvue;
declare var _: typeof mlodash;
declare var appbody; ///////////////////
declare var topicEtriHil; //////////////////////////////// mntr-event.js (이벤트 처리)
declare var callCommMqttHilApi;

// event handler
function regCollapseEventHandler(len) {
  // console.log('event length', len);

  if (len > 0) {
    for (var i = 0; i < len; i++) {
      $(document).on("show.bs.collapse", "#collapseEvent_" + i, function (e) {
        var target = $("[href='#" + $(this).prop("id") + "']");
        target.removeClass("fa-chevron-circle-down");
        target.addClass("fa-chevron-circle-up");
      });
      $(document).on("shown.bs.collapse", "#collapseEvent_" + i, function (e) {
        var target = $("[href='#" + $(this).prop("id") + "']");
        target.removeClass("fa-chevron-circle-down");
        target.addClass("fa-chevron-circle-up");
      });
      $(document).on("hide.bs.collapse", "#collapseEvent_" + i, function (e) {
        var target = $("[href='#" + $(this).prop("id") + "']");
        target.removeClass("fa-chevron-circle-up");
        target.addClass("fa-chevron-circle-down");
      });
      $(document).on("hidden.bs.collapse", "#collapseEvent_" + i, function (e) {
        var target = $("[href='#" + $(this).prop("id") + "']");
        target.removeClass("fa-chevron-circle-up");
        target.addClass("fa-chevron-circle-down");
      });
    }
  }
}

// 전체삭제 버튼 클릭 시
function alarmClearAll() {
  ($("#clearEvtModal") as any).modal("show");
}

// 전체 이상상황 삭제 버튼 클릭 시 - 모달
function clickClearEvtAll() {
  // 전체 삭제 클릭.
  console.log("click clickClearEvtAll");
  //($("#clearEvtModal") as any).modal("hide");
  //return;
  axios
    .post("/monitoring/agentif/clearAlarmAll", {})
    .then(function (res) {
      // alarm clear는 웹소켓으로 브로드 캐스팅 됨.
      console.log(res);
      alert("삭제하였습니다.");
      ($("#clearEvtModal") as any).modal("hide");
    })
    .catch(function (error) {
      console.error("error", error);
      alert("실패하였습니다.");
      ($("#clearEvtModal") as any).modal("hide");
    });
}

// 전체정상상황 버튼 클릭 시
function clickAllNormalEvent() {
  console.log("전체정상상황...");

  appbody.events.forEach(function (event: If.IfTbEvt) {
    if (event.fbNeed == "Y") {
      clickNormalEvent(event);
    } else {
      appbody.alarmClear(event);
    }
  });
}

// 전체이상상황 버튼 클릭 시
function clickAllAbnormalEvent() {
  console.log("전체이상상황...");

  appbody.events.forEach(function (event: If.IfTbEvt) {
    if (event.fbNeed == "Y") {
      clickAbnormalEvent(event);
    } else {
      appbody.alarmClear(event);
    }
  });
}

// 정상상황 버튼 클릭 시
function clickNormalEvent(evt: If.IfTbEvt) {
  console.log("정상상황...");
  console.log("event", evt);

  var param = {
    topic: getTopic(topicEtriHil),
    eventSn: evt.eventSn,
    body: {
      eventid: evt.timeT,
      agentid: evt.robotId,
      hil: "N", // 잘못된 판정 (이상상황아님)
    },
  };

  callCommMqttHilApi(param);
}

// 이상상황 버튼 클릭 시
function clickAbnormalEvent(evt: If.IfTbEvt) {
  console.log("이상상황...");
  console.log("event", evt);

  var param = {
    topic: getTopic(topicEtriHil),
    eventSn: evt.eventSn,
    body: {
      eventSn: evt.eventSn,
      eventid: evt.timeT,
      agentid: evt.robotId,
      hil: "Y", // 판정이 맞음 (이상상황)
    },
  };

  callCommMqttHilApi(param);
}
