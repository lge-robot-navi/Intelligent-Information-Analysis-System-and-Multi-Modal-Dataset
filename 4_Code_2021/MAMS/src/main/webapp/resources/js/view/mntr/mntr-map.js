"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (_) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
Object.defineProperty(exports, "__esModule", { value: true });
var agentws = (function () {
    var wrap = { sendMessage: null };
    var wspath = "ws://" + location.host + "/monitoring/agentif";
    var ws = new WebSocket(wspath);
    console.log(" location.host : " + location.host);
    wrap.sendMessage = function (msg) {
        var jsonMsg = JSON.stringify(msg);
        console.log("Senging message(from brws): " + jsonMsg + ", state : " + ws.readyState);
        if (ws.readyState != 1) {
            //OPEN=1, CLOSING=2, CLOSED:2
            console.error("reconstructed websocket!");
            return;
        }
        ws.send(jsonMsg);
    };
    ws.onopen = function (e) {
        console.info("websocket connected", e);
    };
    ws.onclose = function (e) {
        console.info("session closed", e);
    };
    ws.onmessage = function (e) {
        console.info("Message Recv :", e);
        console.info("Message Recv Data :", e.data);
        var parseData = JSON.parse(e.data);
        appsearch.agentStatus = JSON.parse(e.data);
        /*
        Message Recv Data : {"msgId":"STAT","data":{"statSn":71,"robotId":4,"statDt":"20190101-191010.333","lat":36.09114,"lon":129.3337,"pan":10.0,"tilt":10.0,"compass":10.0,"imuRate":10.1,"imuAngle":20.1,"encL":10,"encR":20,"velL":30,"velR":40,"tv":1111.0,"rv":2222.0}}
        Message Recv Data : {"msgId":"EVENT","data":{"eventSn":39,"eventDt":"20190401-191010.333","robotId":4,"deviceId":100,"abnormalId":20,"eventTimestamp":"20190401-191010.333","eventPosX":100.0,"eventPosY":200.0,"confirmYn":"N"}}
        */
        if (pushLogJson)
            pushLogJson(parseData);
        if (parseData.msgId === "STAT") {
            marker_stat(parseData.data);
        }
        else if (parseData.msgId === "EVENT") {
            marker_event(parseData.data);
        }
        else if (parseData.msgId === "CLEAREVENT") {
            console.log("clear event : " + parseData.data.eventSn);
            if (appbody)
                appbody.removeAlarm(parseData.data.eventSn);
        }
        else if (parseData.msgId === "MQTTAGENTS") {
            // mqtt status 업데이트.
            // console.log(JSON.parse(parseData.data));
            // console.log("data", parseData.data);
            (parseData.data || []).forEach(function (ele) {
                console.log("mqtt stat", ele);
                var location = appbody.isPohang ? "ph" : "gw";
                // 동일지역에 대해서 처리하도록 한다. ( 실제 수신되는 것은, 포항 광주 모두 수신되고 있음.)
                if (location === ele.location) {
                    if (appbody)
                        appbody.setHealthy(ele.agentId, ele.isHealthy);
                }
                // const healthy = appbody.ledkey % 2 == 0;
                // console.log("healthy", healthy);
                // if (appbody) appbody.setHealthy(ele.agentId, healthy);
            });
            if (appbody)
                appbody.ledkey = appbody.ledkey + 1;
            console.log("ledkey is ", appbody.ledkey);
        }
        else if (parseData.msgId === "SCHEDULERES") {
            //
            console.log("schedule data is : ", parseData.data, JSON.parse(parseData.data));
            cmdReScheduleExecute(JSON.parse(parseData.data));
        }
        else if (parseData.msgId === "CLEAREVENTALL") {
            console.log("clear event all");
            if (appbody)
                appbody.removeAlarmAll();
        }
        else if (parseData.msgId === "VOICEINFO") {
            console.log("voice info : " + parseData.data);
            // if (audiocomp) {
            //   var d = parseData.data;
            //   if (appbody.isPohang && d.area != "P") {
            //     console.log("diff area " + d.area);
            //     return;
            //   }
            //   if (!appbody.isPohang && d.area != "G") {
            //     console.log("diff area " + d.area);
            //     return;
            //   }
            //   audiocomp.play(d.agentId, d.url);
            // }
        }
    };
    return wrap;
})();
function pushLogJson(obj) {
    if (!appbody)
        return;
    if (!appbody.logShow)
        return;
    var msg = JSON.stringify(obj, null, 4);
    if (pushLog)
        pushLog(msg);
}
var agentInfos = [];
var oldAgentInfos = [];
var oldAgents = [];
var placeInfos = [];
var map, map_mileage;
function initMap() {
    setTimeout(function () {
        initMap2();
        initMapMileage("init");
    }, 500);
}
window.initMap = initMap; // 이게 없으면 아래의 google map 에서 찾지 못함.
google.maps.event.addDomListener(window, "load", initMap);
/********** 구글맵 초기화 **********/
function initMap2() {
    console.log("initMap called.");
    var locInit = {
        lat: 36.091,
        lng: 129.3332,
    };
    map = new google.maps.Map(document.getElementById("map"), {
        zoom: 19,
        center: locInit,
        //mapTypeId: google.maps.MapTypeId.ROADMAP
        mapTypeId: google.maps.MapTypeId.SATELLITE,
    });
    // initOverlayMap(map);
    google.maps.event.addListener(map, "click", function (event) {
        var latitude = event.latLng.lat();
        var longitude = event.latLng.lng();
        console.log(latitude + ", " + longitude);
        clearRobotSelection();
        moveAgent(getAreaNm(), parseFloat(latitude), parseFloat(longitude));
        setMoveAgentCursor(false);
        pushLatlon({
            lat: parseFloat(latitude).toFixed(6),
            lon: parseFloat(longitude).toFixed(6),
        });
    });
    axios
        .get("/monitoring/api/codeInfo?cdgrpCd=TA008")
        .then(function (res) {
        console.log(res);
        res.data.forEach(function (d) {
            d.info = RJSON.parse(d.codeDs);
        });
        placeInfos = res.data; // 100 포항, 200 광주.
        doClick("TA009"); // 디폴트 포함.
    })
        .catch(function (error) {
        console.error(error);
    });
    axios
        .get("/monitoring/agentif/alarm")
        .then(function (res) {
        console.log(res);
        res.data.forEach(function (d) {
            marker_event(d);
        });
        appbody.addCollapseEvents(); // collapse event handler
    })
        .catch(function (error) {
        console.error(error);
    });
} // Stat 변경 시 호출됨
function initMapMileage(loc) {
    var setLocation = {};
    var locInitPh = {
        lat: 36.118922,
        lng: 129.416156
    };
    var locInitGw = {
        lat: 35.2445,
        lng: 126.835003
    };
    if (loc === "ph") {
        console.log("initMapMileage set ph.");
        setLocation = locInitPh;
    }
    else if (loc === "gw") {
        console.log("initMapMileage set gw.");
        setLocation = locInitGw;
    }
    else {
        console.log("initMapMileage set init.");
        setLocation = locInitPh;
    }
    map_mileage = new google.maps.Map(document.getElementById("map_mileage"), {
        zoom: 18,
        center: setLocation,
        //mapTypeId: google.maps.MapTypeId.ROADMAP,
        mapTypeId: google.maps.MapTypeId.SATELLITE,
    });
}
function marker_stat(data) {
    console.info(data);
    if (!appbody)
        return;
    var agent = agentInfos.find(function (e) {
        return e.info.agentId == data.robotId && e.info.areaCode == data.areaCode;
    });
    if (agent) {
        console.log("find agent.");
        resetBatteryMarker(agent, data.battery, data.lat, data.lon); // battery reset
        agent.marker.setPosition({
            lat: data.lat,
            lng: data.lon,
        });
        agent.markerBattery.setPosition({
            lat: data.lat,
            lng: data.lon,
        });
        agent.markerBatteryLabel.setPosition({
            lat: data.lat,
            lng: data.lon,
        });
    }
    if (appbody)
        appbody.addAgentData(data);
} // Event 발생 시 호출됨
function marker_event(evt) {
    var pos = {
        lat: evt.eventPosX,
        lng: evt.eventPosY,
    }; // 임시
    console.log("event : ", evt);
    var image = "siren.gif";
    if (appbody) {
        var info = appbody.getEventInfo(evt.abnormalId);
        if (info.info)
            image = info.info.image;
    }
    evt.marker = new google.maps.Marker({
        //id: evt.eventSn,
        position: pos,
        // map: map,
        // label: label,
        icon: {
            url: "".concat(REQUEST_CONTEXT_PATH, "/resources/images/map/") + image,
            labelOrigin: new google.maps.Point(42, 2),
        },
        label: {
            text: "[" + evt.eventSn + "]",
            color: "#C70E20",
            fontWeight: "bold", // animation: google.maps.Animation.DROP
        },
    });
    evt.marker.setMap(map);
    if (appbody)
        appbody.addEvent(evt);
} // Event 발생 시 marker animation
function marker_animation(marker) {
    if (marker.getAnimation() !== null) {
        marker.setAnimation(null);
    }
    else {
        marker.setAnimation(google.maps.Animation.BOUNCE);
    }
}
/*
* agent 목록 JSON형태. initLat, initLon, agentId, fixed, markerImage
* event 목록 JSON형태. ( 위치는 어떻게 환산할 것인지.) abnormalId, abnormalImage
*/
var appbody;
var appnav;
var appset;
var appsearch;
var player = [];
var selplayer;
var selagent;
var mileageRouter = [];
var moveControllerJson = { id: 0, run: 0, lin: 0.0, ang: 0.0, speed: 2.0 }; // speed 제어 미사용으로 기본값 2.0을 고정하여 사용중.
var batteryHighIcon = "/monitoring/resources/images/ic_battery_high.png"; // 배터리 아이콘 (high)
var batteryMiddleIcon = "/monitoring/resources/images/ic_battery_middle.png"; // 배터리 아이콘 (middle)
var batteryLowIcon = "/monitoring/resources/images/ic_battery_low.png"; // 배터리 아이콘 (low)
var transIcon = "/monitoring/resources/images/map/trans_marker.png"; // 투명 아이콘
var droneIcon = "/monitoring/resources/images/map/drone.png"; // 드론 아이콘
function drawCallback(img) {
    var canvas = document.getElementById("playerBig");
    var ctx = canvas.getContext("2d");
    ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
}
/********** player big **********/
$("#playerModal").on("show.bs.modal", function (e) {
    console.log("show ===> robot ID : ", selagent);
    if (selagent > 9) {
        console.log("moveController disable ===> ", selagent);
        $('#moveController').css('display', 'none');
    }
    else {
        console.log("moveController enable ===> ", selagent);
        if (appbody.isPlaying)
            $('#moveController').css('display', 'block');
        $('#moveCtlRobotId').attr('value', selagent);
        $('#robotLin').attr('value', moveControllerJson.lin);
        $('#robotAng').attr('value', moveControllerJson.ang);
        window.addEventListener('keydown', moveKeyDownEvent, false);
        window.addEventListener('keyup', moveKeyUpEvent, false);
    }
});
$("#playerModal").on("shown.bs.modal", function (e) {
    console.log("shown");
    //if (audiocomp) audiocomp.stop = false;
    if (selplayer) {
        var canvas = document.getElementById("playerBig");
        var ctx = canvas.getContext("2d");
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        selplayer.addDrawCallback(drawCallback);
    }
    //if (audiocomp) audiocomp.resize();
});
$("#playerModal").on("hide.bs.modal", function (e) {
    console.log("hide");
    moveControllerJson.run = 0;
    moveControllerJson.lin = 0;
    moveControllerJson.ang = 0;
    window.removeEventListener('keydown', moveKeyDownEvent, false);
    window.removeEventListener('keyup', moveKeyUpEvent, false);
});
$("#playerModal").on("hidden.bs.modal", function (e) {
    console.log("hidden");
    if (selplayer)
        selplayer.clearDrawCallback();
    //if (audiocomp) (document.getElementById("audioCtl") as HTMLAudioElement).pause();
});
// TODO: 지도맵에 특정 위치를 마우스로 클릭할 때, 특정 위치로 로봇을 이동 시키는 기능 구현 시 사용 예정.
function moveAgent(area, lat, lng) {
    if (moveCursor.flag == true) {
        var param = {
            area: area,
            robotId: moveCursor.id,
            lat: lat,
            lng: lng
        }; //param
        console.log(param);
        axios
            .get("/monitoring/mqtt/move/control?" + $.param(param))
            .then(function (res) {
            alert("통신완료 : topic만 정하면 clear");
        }).catch(function (error) {
            console.error(error);
        });
    } //end if
} //moveAgent
function floatMinMaxCheck(value) {
    if (value < -2.0)
        return false;
    if (value > 2.0)
        return false;
    return true;
}
function sendMoveRobotData() {
    console.log("move back-end send JSON ===> ", moveControllerJson);
    axios
        .post("/monitoring/mqtt/movecommand", moveControllerJson)
        .then(function (res) {
        console.log("move command res success", res);
    })
        .catch(function (e) {
        console.error("error", e);
    });
}
function moveKeyDownEvent(e) {
    switch (e.keyCode) {
        case 38: // up
            moveup1();
            break;
        case 40: // down
            movedown1();
            break;
        case 37: // left
            moveleft1();
            break;
        case 39: // right
            moveright1();
            break;
        case 32: // space
            movereset1();
            break;
        default:
            break;
    }
}
function moveKeyUpEvent(e) {
    switch (e.keyCode) {
        case 38: // up
            moveup2();
            break;
        case 40: // down
            movedown2();
            break;
        case 37: // left
            moveleft2();
            break;
        case 39: // right
            moveright2();
            break;
        case 32: // space
            movereset2();
            break;
        default:
            break;
    }
}
// 로봇 수동 제어 명령 : lin_vel (전진 이동)
function moveup1() {
    $('#moveStyleUp').removeClass('bi-arrow-up-circle-fill');
    $('#moveStyleUp').addClass('bi-arrow-up-circle');
    var value = parseFloat((moveControllerJson.lin + 0.1).toFixed(1));
    if (floatMinMaxCheck(value)) {
        moveControllerJson.id = selagent;
        moveControllerJson.run = 1;
        moveControllerJson.lin = value;
        $('#robotLin').attr('value', moveControllerJson.lin);
        $('#robotLin').css('color', 'black');
        sendMoveRobotData();
    }
    else {
        $('#robotLin').attr('value', moveControllerJson.lin + '(MAX)');
        $('#robotLin').css('color', 'red');
    }
}
function moveup2() {
    $('#moveStyleUp').removeClass('bi-arrow-up-circle');
    $('#moveStyleUp').addClass('bi-arrow-up-circle-fill');
}
$('#moveLinVelUp').mousedown(function () {
    moveup1();
});
$('#moveLinVelUp').mouseup(function () {
    moveup2();
});
// 로봇 수동 제어 명령 : lin_vel (후진 이동)
function movedown1() {
    $('#moveStyleDown').removeClass('bi-arrow-down-circle-fill');
    $('#moveStyleDown').addClass('bi-arrow-down-circle');
    var value = parseFloat((moveControllerJson.lin - 0.1).toFixed(1));
    if (floatMinMaxCheck(value)) {
        moveControllerJson.lin = value;
        $('#robotLin').attr('value', moveControllerJson.lin);
        $('#robotLin').css('color', 'black');
        sendMoveRobotData();
    }
    else {
        $('#robotLin').attr('value', moveControllerJson.lin + '(MIN)');
        $('#robotLin').css('color', 'red');
    }
}
function movedown2() {
    $('#moveStyleDown').removeClass('bi-arrow-down-circle');
    $('#moveStyleDown').addClass('bi-arrow-down-circle-fill');
}
$('#moveLinVelDown').mousedown(function () {
    movedown1();
});
$('#moveLinVelDown').mouseup(function () {
    movedown2();
});
// 로봇 수동 제어 명령 : ang_vel (좌측 회전)
function moveleft1() {
    $('#moveStyleLeft').removeClass('bi-arrow-left-circle-fill');
    $('#moveStyleLeft').addClass('bi-arrow-left-circle');
    var value = parseFloat((moveControllerJson.ang - 0.1).toFixed(1));
    if (floatMinMaxCheck(value)) {
        moveControllerJson.ang = value;
        $('#robotAng').attr('value', moveControllerJson.ang);
        $('#robotAng').css('color', 'black');
        sendMoveRobotData();
    }
    else {
        $('#robotAng').attr('value', moveControllerJson.ang + '(MIN)');
        $('#robotAng').css('color', 'red');
    }
}
function moveleft2() {
    $('#moveStyleLeft').removeClass('bi-arrow-left-circle');
    $('#moveStyleLeft').addClass('bi-arrow-left-circle-fill');
}
$('#moveAngVelLeft').mousedown(function () {
    moveleft1();
});
$('#moveAngVelLeft').mouseup(function () {
    moveleft2();
});
// 로봇 수동 제어 명령 : ang_vel (우측 회전)
function moveright1() {
    $('#moveStyleRight').removeClass('bi-arrow-right-circle-fill');
    $('#moveStyleRight').addClass('bi-arrow-right-circle');
    var value = parseFloat((moveControllerJson.ang + 0.1).toFixed(1));
    if (floatMinMaxCheck(value)) {
        moveControllerJson.ang = value;
        $('#robotAng').attr('value', moveControllerJson.ang);
        $('#robotAng').css('color', 'black');
        sendMoveRobotData();
    }
    else {
        $('#robotAng').attr('value', moveControllerJson.ang + '(MAX)');
        $('#robotAng').css('color', 'red');
    }
}
function moveright2() {
    $('#moveStyleRight').removeClass('bi-arrow-right-circle');
    $('#moveStyleRight').addClass('bi-arrow-right-circle-fill');
}
$('#moveAngVelRight').mousedown(function () {
    moveright1();
});
$('#moveAngVelRight').mouseup(function () {
    moveright2();
});
// 로봇 수동 제어 명령 : lin_vel, ang_vel 값 Reset
function movereset1() {
    $('#moveStyleReset').removeClass('bi-square-fill');
    $('#moveStyleReset').addClass('bi-square');
    moveControllerJson.lin = 0.0;
    moveControllerJson.ang = 0.0;
    $('#robotLin').attr('value', moveControllerJson.lin);
    $('#robotAng').attr('value', moveControllerJson.ang);
    $('#robotLin').css('color', 'black');
    $('#robotAng').css('color', 'black');
    sendMoveRobotData();
}
function movereset2() {
    $('#moveStyleReset').removeClass('bi-square');
    $('#moveStyleReset').addClass('bi-square-fill');
}
$('#moveLinAngReset').mousedown(function () {
    movereset1();
});
$('#moveLinAngReset').mouseup(function () {
    movereset2();
});
/********** event replay **********/
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
function getlocstr(ispohang) {
    if (ispohang)
        return "/pohang";
    return "/gwangju";
}
function clickSettings() {
    console.log("click settings");
    appset.getSettings();
    $("#settingsModal").modal("show");
    appnav.btnSetClicked = true;
}
$("#settingsModal").on("hidden.bs.modal", function (e) {
    appnav.btnSetClicked = false;
    console.log("settings hidden");
});
function clickAgentAutoSearch() {
    console.log("click agent auto search");
    appnav.btnAutoSearchClicked = true;
    $("#appSearchModal").modal("show");
    appsearch.loadingPage();
    appsearch.getAgentSettingList();
    setTimeout(function () {
        $('#getAgentStatus').empty();
        appsearch.getAgentAutoSearch();
    }, 3000);
}
$("#appSearchModal").on("hidden.bs.modal", function (e) {
    console.log("agent auto search hidden");
    appnav.btnAutoSearchClicked = false;
    $('#getAgentSettings').empty();
    $('#getAgentStatus').empty();
});
$("#droneMonModal").on("hidden.bs.modal", function (e) {
    console.log("dronemon hidden");
    $("#streamDroneTmp").remove();
    appnav.btnDroneMonClicked = false;
});
function reStartDroneMon() {
    // 삭제후, 기동.
    $("#streamDroneTmp").remove();
    var url;
    axios
        .get("/monitoring/api/codeInfo?cdgrpCd=TA012")
        .then(function (res) {
        console.log("res", res);
        res.data.forEach(function (d) {
            if (appnav.place === "포항" && d.codeCd === "001") {
                url = d.codeDs;
                $("#droneQuality").data("place", "ph");
            }
            else if (appnav.place === "광주" && d.codeCd === "002") {
                url = d.codeDs;
                $("#droneQuality").data("place", "gw");
            }
        });
        console.log("url", url);
        var quality = get_drone_quality(url);
        $("#droneQuality").val(quality);
        $("#droneQuality").data("url", url);
        //$("#streamDrone").append('<div id="streamDroneTmp"><img src="' + url + '"></div>');
        $("#streamDrone").append('<div id="streamDroneTmp"><img src="' + url + '" width="912" height="513" style="border:solid 1px #ccc"></div>');
    })
        .catch(function (error) {
        console.error(error);
    });
}
function clickDroneMon() {
    console.log("dronemon show");
    $("#droneMonModal").modal("show");
    $("#pitch").val(90);
    $("#pitchVal").text("90");
    // $("#streamDrone").append('<div id="streamDroneTmp"><img src="http://223.171.32.237:8080/stream?topic=/tracknet/compressed&type=mjpeg&quality=10&width=640&height=480"></div>')
    reStartDroneMon();
    appnav.btnDroneMonClicked = true;
}
function cmdApplyDroneQuality() {
    console.log("cmdApplyDroneQuality click");
    // 1. 데이터를 저장.
    var qual = parseInt("" + $("#droneQuality").val());
    console.log("qual", qual, "url", $("#droneQuality").data("url"));
    if (isNaN(qual)) {
        alert("드론스트리밍 quality에 숫자를 입력하여 주십시오");
        return;
    }
    if (qual < 1 || qual > 50) {
        alert("드론스트리밍 quality를 1 ~ 50 으로 입력하여 주십시오");
        return;
    }
    var place = $("#droneQuality").data("place");
    var url = $("#droneQuality").data("url");
    console.log("url before ", url, ", qual", qual);
    var oldqual = parseInt(get_drone_quality(url));
    if (oldqual === qual) {
        alert("영상품질이 이전 설정과 동일합니다.");
        return;
    }
    url = set_drone_quality(url, qual);
    var param = { dronUrlPohang: "", dronUrlGwangju: "" };
    if (place === "ph") {
        param.dronUrlPohang = url;
    }
    else if (place === "gw") {
        param.dronUrlGwangju = url;
    }
    else {
        console.error("place is not unknown", place);
        return;
    }
    axios
        .post("/monitoring/api/drone/urls", param)
        .then(function (res) {
        // 정상저장하였으면,
        // 2. reStartDroneMon 호출.
        reStartDroneMon();
    })
        .catch(function (e) {
        console.error("error", e);
    });
}
function get_drone_quality(url) {
    // 포항적용 URL
    // http://223.171.32.36:8080/stream?topic=/tracknet/compressed&type=mjpeg&quality=5&width=912&height=513
    //
    // http://223.171.32.36:8080/stream?topic=/tracknet/compressed&type=mjpeg&quality=20&width=1920&height=1080
    if (!url.includes("?"))
        return;
    var urls = url.split("?");
    if (!urls[1].includes("&"))
        return;
    var kv = urls[1].split("&").find(function (ele) { return ele.startsWith("quality"); });
    if (kv) {
        if (!kv.includes("="))
            return;
        return kv.split("=")[1];
    }
}
function set_drone_quality(url, val) {
    var newurl = "";
    if (!url.includes("?"))
        return url;
    var urls = url.split("?");
    if (!urls[1].includes("&"))
        return url;
    var kvs = urls[1].split("&");
    var nkvs = kvs.map(function (ele) {
        if (ele.startsWith("quality")) {
            return "quality=" + val;
        }
        else {
            return ele;
        }
    });
    newurl = urls[0] + "?" + nkvs.join("&");
    return newurl;
}
$(document).ready(function () {
    appset = new Vue({
        el: "#appSettingsModal",
        data: {
            settings: [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}],
            setsend: [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}],
            config: {},
            dronUrlGwangju: "",
            dronUrlPohang: "",
        },
        beforeMount: function () {
            this.initSettings();
        },
        methods: {
            initSettings: function () {
                this.settings = [];
                for (var i = 0; i < 10; i++) {
                    this.settings.push({
                        ison: false,
                    });
                }
            },
            getAgentId: function (id) {
                if (!appbody)
                    return;
                return appbody.getAgentDisplayName(id);
            },
            clickSave: function () {
                console.log("save...");
                this.saveSettings();
                this.saveConfig();
                this.$refs.close.click();
            },
            getSettings: function () {
                var app = this;
                this.initSettings();
                var url = "/monitoring/api/settings" + getlocstr(appbody.isPohang);
                axios
                    .get("/monitoring/api/drone/urls")
                    .then(function (res) {
                    console.log("/monitoring/api/drone/urls res ", res.data);
                    app.dronUrlGwangju = res.data.dronUrlGwangju;
                    app.dronUrlPohang = res.data.dronUrlPohang;
                    return axios.get(url);
                })
                    .then(function (res) {
                    var infos = agentInfos
                        .map(function (item, i) {
                        return {
                            idx: i,
                            agentId: (item.info || {}).agentId,
                        };
                    })
                        .filter(function (item, i) {
                        return i < 10;
                    }); // app.settings = res.data;
                    infos.forEach(function (item) {
                        app.settings[item.idx].agentId = item.agentId;
                    });
                    res.data.list.forEach(function (item, idx) {
                        (app.settings.find(function (elem) {
                            return elem.agentId == item.agentId;
                        }) || {}).ison = item.ison;
                    });
                })
                    .catch(function (e) {
                    return console.log("E", e);
                });
                this.getSetSend();
                this.getConfigInfo();
            },
            getSetSend: function () {
                var app = this;
                var url = "/monitoring/api/agents/get" + getlocstr(appbody.isPohang);
                axios
                    .get(url)
                    .then(function (res) {
                    console.log("setsend res.data : ", res.data);
                    res.data.forEach(function (item, i) {
                        if (i > 10)
                            return;
                        app.setsend.splice(i, 1, item);
                    });
                })
                    .catch(function (e) {
                    console.log("E", e);
                });
            },
            getConfigInfo: function () {
                var app = this;
                axios
                    .get("/monitoring/test/config")
                    .then(function (res) {
                    console.log("config", res.data);
                    app.config = res.data;
                    console.log("config", app.config);
                })
                    .catch(function (ex) {
                    console.error("E", ex);
                });
            },
            getSendAgentId: function (rid) {
                var o = this.setsend[rid];
                if (!o.robotId)
                    return "Robot ID : [ ]";
                return "Robot ID : [" + o.robotId + "]";
            },
            saveConfig: function () {
                axios
                    .get("/monitoring/test/config?type=udp&val=" + this.config.udpLog)
                    .then(function (res) { })
                    .catch(function (ex) {
                    console.error("E", ex);
                });
                axios
                    .get("/monitoring/test/config?type=stat&val=" + this.config.statLog)
                    .then(function (res) { })
                    .catch(function (ex) {
                    console.error("E", ex);
                });
                axios
                    .get("/monitoring/test/config?type=event&val=" + this.config.eventLog)
                    .then(function (res) { })
                    .catch(function (ex) {
                    console.error("E", ex);
                });
            },
            saveSettings: function () {
                console.log("settings", this.settings);
                var app = this;
                var url = "/monitoring/api/settings" + getlocstr(appbody.isPohang);
                axios
                    .post("/monitoring/api/drone/urls", { dronUrlPohang: app.dronUrlPohang, dronUrlGwangju: app.dronUrlGwangju })
                    .then(function (res) {
                    return axios.post(url, {
                        list: app.settings,
                    });
                })
                    .then(function (res) {
                    console.log("result is", res);
                    if (res.data.resultCode == "OK") {
                        // alert("저장하였습니다.")
                        app.saveSendSettings();
                    }
                    else {
                        alert("실패하였습니다.");
                    }
                })
                    .catch(function (e) {
                    alert("실패하였습니다.");
                    console.log("e", e);
                }); // this.saveSendSettings();
            },
            saveSendSettings: function () {
                console.log("setsend", this.setsend);
                var app = this;
                var url = "/monitoring/api/agents/set" + getlocstr(appbody.isPohang);
                axios
                    .post(url, this.setsend)
                    .then(function (res) {
                    console.log("result is", res);
                    if (res.data.resultCode == "OK") {
                        //alert("저장하였습니다.");
                        console.info("저장하였습니다.");
                    }
                    else {
                        alert("실패하였습니다.");
                        console.error("실패하였습니다");
                    }
                })
                    .catch(function (e) {
                    alert("실패하였습니다.");
                    console.error("e", e);
                });
            },
        },
    });
    appbody = new Vue({
        el: "#appBody",
        data: {
            ledkey: 1,
            alerts: [],
            alertid: 1,
            schedulingShow: false,
            logShow: false,
            latlonShow: false,
            envmapShow: true,
            logs: [],
            latlons: [],
            agents: [],
            datas: [],
            events: [],
            eventsShowCnt: 10,
            isPohang: true,
            saveInitAgentPh: [],
            saveInitAgentGw: [],
            eventInfos: [],
            isPlaying: false,
            renderAlarmComponent: true,
            autorefresh: false,
            autorefreshinterval: 5,
            keyEvents: 1,
            alertTitleStyle: {
                background: "",
                color: "",
            },
            agentStats: [],
            numOfMovingAgent: 0,
            dronelat: 0,
            dronelng: 0,
            chartMileage: "",
            dataMileage: [],
            appSearchFixedBtn: false,
        },
        mounted: function () {
            this.$refs.player.forEach(function (item, idx) {
                player[idx] = createSeqPlayerDevice("/monitoring/test/realtimeimage", item);
            });
            this.startTimer();
        },
        watch: {
            isPohang: function (val, oval) {
                var cdgrpCd;
                if (val)
                    cdgrpCd = "TA009";
                else
                    cdgrpCd = "TA010";
                var app = this;
                this.setupAgents(app, cdgrpCd);
            },
        },
        computed: {
            logMsg: function () {
                return this.logs.join("\n");
            },
            logLatlon: function () {
                return this.latlons.join("\n");
            },
            alarmCnt: function () {
                return this.events.length;
            },
        },
        created: function () {
            // 광주.(agent 목록)
            this.setupAgents(this, "TA009");
            var app = this;
            axios
                .get("/monitoring/api/codeInfo?cdgrpCd=TA011")
                .then(function (res) {
                console.log(res);
                var idx = 0;
                res.data.forEach(function (d) {
                    d.info = RJSON.parse(d.codeDs);
                    app.eventInfos.push(d);
                });
            })
                .catch(function (error) {
                console.error(error);
            });
        },
        methods: {
            startTimer: function () {
                var app = this;
                setInterval(function () {
                    // console.log("timer.3");
                    if (app.alarmCnt) {
                        if (app.alertTitleStyle.background == "red") {
                            app.alertTitleStyle.background = "rgb(204,229,255)";
                            app.alertTitleStyle.color = "rgb(0,64,133)";
                        }
                        else {
                            app.alertTitleStyle.background = "red";
                            app.alertTitleStyle.color = "white";
                        }
                    }
                    else {
                        app.alertTitleStyle.background = "rgb(204,229,255)";
                        app.alertTitleStyle.color = "rgb(0,64,133)";
                    }
                }, 1000);
            },
            addalert: function (msg) {
                var app = this;
                this.alertid = this.alertid + 1;
                var id = this.alertid;
                this.alerts.push({ id: id, msg: msg, type: "error" });
                setTimeout(function () {
                    app.alerts = app.alerts.filter(function (ele) {
                        return ele.id != id;
                    });
                }, 3000);
            },
            addinfo: function (msg) {
                var app = this;
                this.alertid = this.alertid + 1;
                var id = this.alertid;
                this.alerts.push({ id: id, msg: msg, type: "info" });
                setTimeout(function () {
                    app.alerts = app.alerts.filter(function (ele) {
                        return ele.id != id;
                    });
                }, 3000);
            },
            getAreaName: function (evt) {
                if (evt.areaCode == "P")
                    return "포항";
                if (evt.areaCode == "G")
                    return "광주";
                return "";
            },
            getPos: function (v) {
                if (!v)
                    return "";
                return parseFloat(v).toFixed(5);
            },
            startPlayers: function () {
                this.isPlaying = true;
                var areaCode = this.isPohang ? "P" : "G";
                player.forEach(function (p) {
                    if (p) {
                        p.areaCode = areaCode;
                        p.start(200, -1);
                    }
                });
                $('#moveController').css('display', 'block');
            },
            stopPlayers: function stopPlayers() {
                this.isPlaying = false;
                player.forEach(function (p) {
                    if (p) {
                        p.stop();
                    }
                });
                $('#moveController').css('display', 'none');
            },
            clickPlay: function () {
                if (this.isPlaying)
                    this.stopPlayers();
                else
                    this.startPlayers();
            },
            clickEventItem: function (evt, idx) {
                //const app = this;
                // events: [] as Array<If.IfTbEvt>,
                //app.events[idx].expand = !app.events[idx].expand;
                evt.expand = !evt.expand;
            },
            viewBigPlayer: function (idx) {
                selplayer = player[idx];
                selagent = agentInfos[idx].info.agentId;
                $("#playerModal").modal("show");
            },
            setupAgents: function (app, cdgrpCd) {
                var place = cdgrpCd == "TA009" ? "ph" : "gw";
                app.datas.splice(0, app.datas.length);
                app.agents.splice(0, app.agents.length);
                app.agentStats.splice(0, app.agentStats.length);
                //app.events.splice(0,app.events.length);
                axios
                    .get("/monitoring/api/mqtt/mqttagentstats")
                    .then(function (res) {
                    res.data.forEach(function (item) {
                        if (item.location === place && (item.agentType === "ROBOT" || item.agentType === "FIXEDROBOT")) {
                            app.agentStats.push(item);
                        }
                    });
                    return axios.get("/monitoring/api/codeInfo?cdgrpCd=" + cdgrpCd);
                })
                    .then(function (res) {
                    console.log(res);
                    appsearch.agentSettings = res.data.slice();
                    var idx = 0;
                    res.data.forEach(function (d) {
                        d.info = RJSON.parse(d.codeDs);
                        app.agents.push(d);
                        oldAgents.push(d);
                        var p = player[idx];
                        if (p) {
                            p.agentId = d.info.agentId;
                            if (appbody.isPohang)
                                p.areaCode = "P";
                            else
                                p.areaCode = "G";
                            // 상태정보 추가
                            var statInfo = app.agentStats.find(function (item) {
                                return item.agentId === p.agentId;
                            });
                            d.info.isHealthy = statInfo.isHealthy;
                        }
                        idx++;
                    });
                    // 이동형 Agent Count
                    app.numOfMovingAgent = res.data.filter(function (it) {
                        var json = RJSON.parse(it.codeDs);
                        if (json.fixed === "N")
                            return it;
                    }).length;
                })
                    .catch(function (error) {
                    console.error(error);
                });
            },
            setHealthy: function (robotId, isHealthy) {
                var app = this;
                app.agents
                    .filter(function (ele) {
                    return ele.info.agentId == robotId;
                })
                    .forEach(function (ele) {
                    if (isHealthy)
                        app.isHealthy.push(robotId);
                    ele.info.isHealthy = isHealthy;
                });
            },
            getAgent: function (idx) {
                var arr = this.agents;
                if (idx >= arr.length)
                    return {};
                return arr[idx];
            },
            getAgentById: function (id) {
                var arr = this.agents;
                if (id >= arr.length)
                    return {};
                var o = arr.find(function (ele) {
                    if (ele.info && ele.info.agentId == id)
                        return true;
                    return false;
                });
                if (o)
                    return o;
                return {};
            },
            getAgentCnt: function (onlyRobot) {
                if (this.appSearchFixedBtn) {
                    return appsearch.agent_checked.length;
                }
                else {
                    if (onlyRobot) {
                        return this.agents.filter(function (agent) { return agent.info.markerImage === "robot.png"; }).length || 10;
                    }
                    else {
                        return this.agents.length || 20;
                    }
                }
            },
            doScheduleBattery: function () {
                console.log("doScheduleBattery");
                var app = this;
                var arr = app.agents
                    .filter(function (ele) {
                    return ele.chkScheduleInit;
                })
                    .map(function (ele) {
                    return "ids=" + ele.info.agentId;
                });
                console.log("schedule battery", arr);
                if (arr.length != 1) {
                    alert("하나의 Agent를 선택하여 주십시오");
                    return;
                }
                // lowbat hom 호출하고나서, 배터리 스케쥴 실행.
                app.doHomingLowbat();
                axios
                    .get("/monitoring/mqtt/schedulebattery/" + getAreaNm() + "?" + arr.join("&"))
                    .then(function (res) {
                    console.log("res is ", res.data);
                    app.addinfo("처리하였습니다");
                })
                    .catch(function (e) {
                    console.error("E", e);
                    app.addalert("실패하였습니다");
                });
            },
            doHomingLowbat: function () {
                this.doHoming("LOWBAT");
            },
            doHomingFailure: function () {
                this.doHoming("FAILURE");
            },
            doHoming: function (reason) {
                console.log("doHoming");
                var app = this;
                var arr = this.agents
                    .filter(function (ele) {
                    return ele.chkScheduleInit;
                })
                    .map(function (ele) {
                    return "ids=" + ele.info.agentId;
                });
                console.log("doHoming", arr);
                /* if (arr.length !== 1) {
                  alert("하나의 Agent를 선택하여 주십시오");
                  return;
                } */
                if (arr.length < 1) {
                    alert("하나 이상의 Agent를 선택하여 주십시오");
                    return;
                }
                axios
                    .get("/monitoring/mqtt/homing/" + getAreaNm() + "/" + reason + "?" + arr.join("&"))
                    .then(function (res) {
                    console.log("res is ", res.data);
                    app.addinfo("처리하였습니다");
                })
                    .catch(function (e) {
                    console.error("E", e);
                    app.addalert("실패하였습니다");
                });
            },
            doMoveTo: function (reason, eventSn) {
                var app = this;
                axios
                    .get("/monitoring/mqtt/moveto/" + getAreaNm() + "/" + reason + "?eventSn=" + eventSn)
                    .then(function (res) {
                    console.log("res is ", res.data);
                    app.addinfo("처리하였습니다");
                })
                    .catch(function (e) {
                    console.error("E", e);
                    app.addalert("실패하였습니다");
                });
            },
            doMoveToFixed: function (reason, eventSn) {
                console.log("Move TO Fixed");
                var app = this;
                axios
                    .get("/monitoring/mqtt/movetofixed/" + getAreaNm() + "/" + reason + "?eventSn=" + eventSn)
                    .then(function (res) {
                    console.log("res is ", res.data);
                    app.addinfo("처리하였습니다");
                })
                    .catch(function (e) {
                    console.error("E", e);
                    app.addalert("실패하였습니다");
                });
            },
            cmdReScheduleAbnormal: function (evt) {
                var _a;
                console.log("cmdReScheduleAbnormal", evt);
                console.log("agent ", this.getAgentById(evt.robotId));
                var agent = this.getAgentById(evt.robotId);
                var app = this;
                var isFixed = ((_a = agent === null || agent === void 0 ? void 0 : agent.info) === null || _a === void 0 ? void 0 : _a.fixed) === "Y" ? true : false;
                var param = "ids=" + evt.robotId + "&eventSn=" + evt.eventSn;
                console.log("scheduleabnormal", param, "isFixed", isFixed);
                // move to 날리고 나서, abnormal.
                if (isFixed)
                    app.doMoveToFixed("GOTO", evt.eventSn);
                else
                    app.doMoveTo("GOTO", evt.eventSn);
                var url = "/monitoring/mqtt/scheduleabnormal/";
                if (isFixed)
                    url = "/monitoring/mqtt/scheduleabnormalfixed/";
                axios
                    .get(url + getAreaNm() + "?" + param)
                    .then(function (res) {
                    console.log("res is ", res.data);
                    app.addinfo("처리하였습니다");
                })
                    .catch(function (e) {
                    console.error("E", e);
                    app.addalert("실패하였습니다");
                });
            },
            doScheduleInit: function () {
                var app = this;
                if (getAreaNm() === "ph")
                    app.saveInitAgentPh = [];
                else
                    app.saveInitAgentGw = [];
                var arr = this.agents
                    .filter(function (ele) {
                    return ele.chkScheduleInit;
                })
                    .map(function (ele) {
                    if (getAreaNm() === "ph")
                        app.saveInitAgentPh.push(ele.info.agentId);
                    else
                        app.saveInitAgentGw.push(ele.info.agentId);
                    return "ids=" + ele.info.agentId;
                });
                console.log("schedule init", arr);
                if (arr.length < 1) {
                    alert("하나 이상의 Agent를 선택하여 주십시오");
                    return;
                }
                axios
                    .get("/monitoring/mqtt/scheduleinit/" + getAreaNm() + "?" + arr.join("&"))
                    .then(function (res) {
                    console.log("res is ", res.data);
                    app.addinfo("처리하였습니다");
                })
                    .catch(function (e) {
                    console.error("E", e);
                    app.addalert("실패하였습니다");
                });
            },
            clickChkScheduleInit: function (event, id) {
                console.log("event", event, this.agents);
                var agent = this.getAgent(id);
                if (agent) {
                    agent.chkScheduleInit = event.target.checked;
                    console.log("agent", agent);
                }
            },
            doScheduleStop: function () {
                var app = this;
                var arr = this.agents
                    .filter(function (ele) {
                    return ele.chkScheduleInit;
                })
                    .map(function (ele) {
                    return "ids=" + ele.info.agentId;
                });
                console.log("doScheduleStop", arr);
                if (arr.length < 1) {
                    alert("하나 이상의 Agent를 선택하여 주십시오");
                    return;
                }
                axios
                    .get("/monitoring/mqtt/schedulestop/" + getAreaNm() + "?" + arr.join("&"))
                    .then(function (res) {
                    console.log("res is ", res.data);
                    app.addinfo("처리하였습니다");
                })
                    .catch(function (e) {
                    console.error("E", e);
                    app.addalert("실패하였습니다");
                });
            },
            doScheduleReturn: function () {
                var app = this;
                var arr = this.agents
                    .filter(function (ele) {
                    return ele.chkScheduleInit;
                })
                    .map(function (ele) {
                    return "ids=" + ele.info.agentId;
                });
                console.log("doScheduleReturn", arr);
                if (arr.length < 1) {
                    alert("하나 이상의 Agent를 선택하여 주십시오");
                    return;
                }
                axios
                    .get("/monitoring/mqtt/schedulereturn/" + getAreaNm() + "?" + arr.join("&"))
                    .then(function (res) {
                    console.log("res is ", res.data);
                    app.addinfo("처리하였습니다");
                })
                    .catch(function (e) {
                    console.error("E", e);
                    app.addalert("실패하였습니다");
                });
            },
            getAgentId: function (idx) {
                var agent = this.getAgent(idx); // return '[' + agent.info.agentId + "] " + agent.codeNm;
                if (agent && agent.info) {
                    return agent.info.agentId;
                }
                return "";
            },
            getAgentDisplayName: function (idx) {
                var agent = this.getAgent(idx); // return '[' + agent.info.agentId + "] " + agent.codeNm;
                if (agent && agent.info) {
                    return getAgentMarkerLabel(agent.info);
                }
                return "";
            },
            getAgentIsHealthy: function (idx) {
                var agent = this.getAgent(idx);
                if (agent && agent.info)
                    return agent.info.isHealthy;
                return false;
            },
            getData: function (idx) {
                var datas = this.datas;
                var agent = this.getAgent(idx);
                if (agent.info) {
                    var f = datas.find(function (e) {
                        if (e.robotId == agent.info.agentId && e.areaCode == agent.info.areaCode)
                            return true;
                        return false;
                    });
                    if (f)
                        return f;
                }
                return {};
            },
            addAgentData: function (data) {
                var datas = this.datas;
                var idx = datas.findIndex(function (e) {
                    if (e.robotId == data.robotId && e.areaCode == data.areaCode)
                        return true;
                    return false;
                });
                if (idx < 0) {
                    datas.push(data);
                }
                else {
                    this.$set(datas, idx, data);
                }
            },
            alarmReplay: function (evt) {
                console.log("alarm replay", evt);
                // 이벤트 목록을 수집.
                var images;
                eventReplay = createReplay("/monitoring/mntr/evtimage", document.getElementById("eventPlayer"));
                axios
                    .get("/monitoring/mntr/evtinfo/" + evt.eventSn)
                    .then(function (res) {
                    console.log("res", res);
                    images = res.data;
                    if (images.length < 1) {
                        alert("이벤트에 이미지가 존재하지 않습니다.");
                        return;
                    }
                    $("#eventReplayModal").modal("show");
                    eventReplay.start(200, images);
                })
                    .catch(function (ex) {
                    console.error("E", ex);
                });
            },
            alarmClear: function (evt) {
                var app = this;
                axios
                    .post("/monitoring/agentif/clearAlarm", {
                    eventSn: evt.eventSn,
                })
                    .then(function (res) {
                    // alarm clear는 웹소켓으로 브로드 캐스팅 됨.
                    console.log(res);
                })
                    .catch(function (error) {
                    console.error(error);
                });
            },
            forceRenderAlarmComponent: function () {
                this.renderAlarmComponent = false;
                var app = this;
                this.$nextTick(function () {
                    app.renderAlarmComponent = true;
                });
                this.keyEvents++;
            },
            removeMarkerAlarm: function (evt) {
                evt.marker.setMap(null);
                this.events.splice(this.events.findIndex(function (e) {
                    return e.eventSn == evt.eventSn;
                }), 1);
            },
            removeAlarm: function (sn) {
                var evt = this.events.find(function (e) {
                    return e.eventSn == sn;
                });
                if (evt) {
                    this.removeMarkerAlarm(evt);
                    this.forceRenderAlarmComponent();
                }
            },
            removeAlarmAll: function () {
                console.log("removeAlarmAll");
                var app = this;
                var evts = _.clone(this.events);
                _.forEach(evts, function (ele) {
                    console.log("event : ", ele);
                    app.removeMarkerAlarm(ele);
                });
                this.forceRenderAlarmComponent();
                this.events = [];
            },
            addEvent: function (evt) {
                this.events.push(evt);
                this.forceRenderAlarmComponent();
            },
            getEventInfo: function (abnormalId) {
                var info = this.eventInfos.find(function (e) {
                    return e.codeCd == abnormalId;
                });
                if (info)
                    return info;
                return {
                    codeNm: abnormalId,
                };
            },
            addCollapseEvents: function () {
                regCollapseEventHandler(this.alarmCnt);
            },
        },
    });
    appnav = new Vue({
        el: "#appNav",
        data: {
            place: "포항",
            btnSchedulingClicked: false,
            btnDroneMonClicked: false,
            btnSetClicked: false,
            btnLogClicked: false,
            btnLatLonClicked: false,
            btnEnvMapClicked: true,
            btnMileageClicked: false,
            btnAutoSearchClicked: false,
        },
    });
    appsearch = new Vue({
        el: "#agentAutoSearchModal",
        data: {
            agentSettings: {},
            agentStatus: [],
            isPohang: true,
            agent_checked: [],
            clickFixedBtn: false,
            maxRobotId: 9,
            newAgents: [],
            newAgentInfos: [],
        },
        methods: {
            getAgentSettingList: function () {
                this.agentSettings.forEach(function (item) {
                    var agent = item.info;
                    var li = document.createElement("li");
                    var type = (agent.fixed === "N" ? "이동형" : "고정형");
                    var addValue = type + "(Agent ID : " + agent.agentId + ")";
                    var textNode = document.createTextNode(addValue);
                    li.style.marginBottom = "10px";
                    li.appendChild(textNode);
                    document.getElementById('getAgentSettings').appendChild(li);
                });
                document.getElementById('getAgentSettings').style.paddingLeft = "20px";
            },
            getAgentAutoSearch: function () {
                var areaCode = this.agentSettings.map(function (item) { return item = item.info.areaCode; });
                var status = this.agentStatus.data.slice();
                var loc = areaCode[0] === "P" ? "ph" : "gw";
                // 테스트 코드
                var healthyOk = [0, 2, 4, 6];
                healthyOk.forEach(function (ele) {
                    status[ele].isHealthy = true;
                });
                status.map(function (item, index) {
                    if (loc === item.location) {
                        console.log("status ===> ", index, item.agentId, item.agentType, item.isHealthy);
                        var agentId = item.agentId;
                        console.log("status agentId ===> ", agentId);
                        var input = document.createElement("input");
                        input.setAttribute("type", "checkbox");
                        input.style.marginRight = "10px";
                        input.checked = (item.isHealthy ? true : false);
                        var nameLabel = document.createElement("label");
                        var statLabel = document.createElement("label");
                        var br = document.createElement("br");
                        var type = (item.agentType === "ROBOT" ? "이동형" : "고정형");
                        var addName = " " + type + "(Agent ID : " + item.agentId + ") : ";
                        var addStatus = (item.isHealthy === true ? "정상" : "불량");
                        var nameNode = document.createTextNode(addName);
                        var statusNode = document.createTextNode(addStatus);
                        nameLabel.appendChild(nameNode);
                        statLabel.appendChild(statusNode);
                        nameLabel.setAttribute("for", item.agentId);
                        input.style.marginBottom = "10px";
                        nameLabel.style.marginRight = "10px";
                        nameLabel.style.marginBottom = "10px";
                        statLabel.style.color = item.isHealthy === true ? "blue" : "red";
                        statLabel.style.marginBottom = "10px";
                        input.setAttribute("id", item.agentId);
                        document.getElementById('getAgentStatus').appendChild(input);
                        document.getElementById('getAgentStatus').appendChild(nameLabel);
                        document.getElementById('getAgentStatus').appendChild(statLabel);
                        document.getElementById('getAgentStatus').appendChild(br);
                    }
                });
            },
            loadingPage: function () {
                var img = document.createElement("img");
                img.setAttribute("src", "/monitoring/resources/images/loading/loading1.gif");
                img.style.paddingLeft = "23px";
                document.getElementById('getAgentStatus').appendChild(img);
            },
            clickFixed: function () {
                var _this = this;
                this.agent_checked = [];
                var check = document.getElementById("getAgentStatus").getElementsByTagName("input");
                for (var i = 0; i < check.length; i++) {
                    if (check[i].checked === true) {
                        this.agent_checked.push(check[i].id);
                    }
                }
                // 메인화면 변경        
                console.log("appSearchFixedBtn ===> ", appbody.appSearchFixedBtn);
                appbody.appSearchFixedBtn = true;
                var moveCount = 0;
                this.newAgents = [];
                this.newAgentInfos = [];
                this.agent_checked.forEach(function (ele) {
                    if (ele <= _this.maxRobotId)
                        moveCount++;
                });
                appbody.numOfMovingAgent = moveCount;
                if (!appbody.appSearchFixedBtn) {
                    this.agent_checked.forEach(function (item) {
                        appbody.agents.forEach(function (ele) {
                            if (Number(item) === ele.info.agentId)
                                _this.newAgents.push(ele);
                        });
                    });
                    this.agent_checked.forEach(function (item) {
                        agentInfos.forEach(function (ele) {
                            if (Number(item) === ele.info.agentId)
                                _this.newAgentInfos.push(ele);
                        });
                    });
                }
                else {
                    this.agent_checked.forEach(function (item) {
                        oldAgents.forEach(function (ele) {
                            if (Number(item) === ele.info.agentId)
                                _this.newAgents.push(ele);
                        });
                    });
                    this.agent_checked.forEach(function (item) {
                        oldAgentInfos.forEach(function (ele) {
                            if (Number(item) === ele.info.agentId)
                                _this.newAgentInfos.push(ele);
                        });
                    });
                }
                appbody.agents = [];
                agentInfos = [];
                appbody.agents = this.newAgents.slice();
                agentInfos = this.newAgentInfos.slice();
                console.log("appbody.agents ===> ", appbody.agents);
                console.log("agentInfos ===> ", agentInfos);
                $('#schedulingInfo').load(location.href + '#schedulingInfo');
                $('#collapseAgent').load(location.href + '#collapseAgent');
            }
        }
    });
    google.charts.load('current', { packages: ['corechart', 'bar'] });
});
function doClick(cdgrpCd) {
    initOverlayMap(map, getAreaNm(), moveAgent);
    // set schedule, reschedule path (화면 로드시 스케쥴링 데이터를 받아와 셋팅함)
    setOldSchedulingPath();
    setNewSchedulingPath();
    cdgrpCd === "TA009" ? getEnvMap("ph") : getEnvMap("gw"); // 환경맵표기
    // 환경맵 선택 초기화
    $('input:radio[name="envmap"]').each(function (index) {
        // console.log('index', index);
        this.checked = false; //checked 처리
    });
    axios
        .get("/monitoring/api/codeInfo?cdgrpCd=" + cdgrpCd)
        .then(function (res) {
        // 위치 이동.
        var placeCode = cdgrpCd == "TA009" ? "100" : "200";
        var place = placeInfos.find(function (e) {
            return e.codeCd == placeCode;
        });
        map.setCenter({
            lat: place.info.lat,
            lng: place.info.lon,
        });
        // marker clear
        agentInfos.map(function (e) {
            if (e.marker) {
                e.marker.setMap(null); // clear.
                e.marker = null;
            }
            if (e.markerBattery) {
                e.markerBattery.setMap(null); // clear.
                e.markerBattery = null;
            }
            if (e.markerBatteryLabel) {
                e.markerBatteryLabel.setMap(null); // clear.
                e.markerBatteryLabel = null;
            }
            if (e.droneMarker) {
                e.droneMarker.setMap(null); // clear.
                e.droneMarker = null;
            }
        });
        // Agent Marker 초기값 셋팅.
        setAgentMarkerInit(res.data);
        agentInfos = res.data;
        oldAgentInfos = res.data;
    })
        .catch(function (error) {
        console.error(error);
    });
}
function clickPohang() {
    if (appbody.isPohang) {
        console.log("already 포항");
        return;
    }
    console.log("포항");
    appnav.place = "포항";
    appbody.isPohang = true;
    doClick("TA009");
}
function clickGwangju() {
    if (!appbody.isPohang) {
        console.log("already 광주");
        return;
    }
    console.log("광주");
    appnav.place = "광주";
    appbody.isPohang = false;
    doClick("TA010");
}
function clickViewLog() {
    if (appbody) {
        appbody.logShow = !appbody.logShow;
        appnav.btnLogClicked = appbody.logShow;
    }
}
function clickViewEnvMap() {
    if (appbody) {
        appbody.envmapShow = !appbody.envmapShow;
        appnav.btnEnvMapClicked = appbody.envmapShow;
        if (appbody.envmapShow) {
            getEnvMap(appbody.isPohang ? "ph" : "gw");
        }
    }
}
function clickViewMileage() {
    console.log("mileage show");
    $("#mileageMonModal").modal("show");
    appnav.btnMileageClicked = true;
    var loc = appbody.isPohang ? "ph" : "gw";
    setMileageMap(loc);
    $('#locationMileage').val(loc).prop("selected", true);
    appbody.agentStats.map(function (item) {
        if (item.agentType === "ROBOT") {
            $('input:checkbox[id="mileageAgent' + item.agentId + '"]').prop('checked', true);
        }
    });
    var now = new Date();
    var month = now.getMonth() + 1;
    var day = now.getDate();
    var yyyy = now.getFullYear();
    var mm = (month < 10) ? '0' + month : month;
    var dd = (day < 10) ? '0' + day : day;
    var today = yyyy + "-" + mm + "-" + dd;
    console.log("today ===> ", today);
    $('#mileageStartDate').val(today);
    $('#mileageEndDate').val(today);
    axios
        .get("/monitoring/mntr/mileageinfo?start=" + today + "&end=" + today)
        .then(function (res) {
        console.log("chart data load", res.data);
        appbody.dataMileage = res.data.slice();
        var count = mileageAgentCount();
        drawViewMileage(count);
    })
        .catch(function (error) {
        console.error("getMileageInfo error ===> ", error);
    });
}
$('#locationMileage').on("change", function () {
    var val = $(this).val();
    console.log("mileage map change ===>", val);
});
$("#mileageMonModal").on("hidden.bs.modal", function () {
    console.log("mileage hidden");
    $('#mileageStartDate').val('');
    $('#mileageEndDate').val('');
    $('#chart_mileage').empty();
    $('#selectAgentList').find('input').prop('checked', false);
    appnav.btnMileageClicked = false;
});
function drawViewMileage(agents) {
    console.log("mileage draw");
    var chartData = [];
    var agentInfo = { id: [], color: [] };
    $('#chart_mileage').empty();
    var colors = [
        "#4B0082",
        "#008000",
        "#0000FF",
        "#1E90FF",
        "#00CED1",
        "#E9967A",
        "#FF8C00",
        "#FF7F50",
        "#FF69B4" // Robot_9 Color(HotPink)
    ];
    var data = appbody.dataMileage;
    if (agents.length > 0) {
        if (typeof appbody.dataMileage[0] === "undefined" || appbody.dataMileage[0] === "") {
            console.log("data null");
            setChartLayout("chart-loading");
            return;
        }
        agents.map(function (item, idx) {
            if (idx === 0)
                chartData.push(["RobotID", "Mileage", { role: "annotation" }, { role: "style" }]);
            chartData.push(["Robot_" + item, data[item - 1], data[item - 1], colors[item - 1]]);
            agentInfo.id.push(item);
            agentInfo.color.push(colors[item - 1]);
        });
    }
    else {
        setChartLayout("zero-agents");
        return;
    }
    var drawData = google.visualization.arrayToDataTable(chartData);
    var col = drawData.getColumnRange(1);
    console.log("column range min/max ===> ", col.min, col.max);
    var _max = Math.ceil(col.max);
    var options = {
        width: 600,
        hAxis: {
            title: "마일리지(Km)",
            minValue: 0,
            viewWindow: {
                min: 0,
                max: col.max === 0 ? 10 : (_max % 2 === 1 ? _max + 1 : _max)
            }
        },
        chartArea: { top: 30 },
        legend: { position: "none" },
        // 차트가 뿌려질때 실행될 애니메이션 효과
        animation: {
            startup: true,
            duration: 1000,
            easing: "linear"
        }
    };
    appbody.chartMileage = new google.visualization.BarChart(document.getElementById("chart_mileage"));
    appbody.chartMileage.draw(drawData, options);
    google.visualization.events.addListener(appbody.chartMileage, 'select', function () {
        var selection = appbody.chartMileage.getSelection();
        var select = selection[0].row;
        var id = agentInfo.id[select];
        var color = agentInfo.color[select];
        setChartLayout("map-loading");
        pathDraw(id, color);
    });
}
function isEmpty(value) {
    if (typeof value === "string") {
        return value === null || value === undefined || value === "" ? true : false;
    }
    else if (typeof value === "object") {
        return value === null || value === undefined || Object.keys(value).length === 0 ? true : false;
    }
    else {
        return value === null || value === undefined ? true : false;
    }
}
function pathDraw(id, color) {
    return __awaiter(this, void 0, void 0, function () {
        var start, end, res;
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    console.log("pathDraw ===> ", id, color);
                    start = $('#mileageStartDate').val();
                    end = $('#mileageEndDate').val();
                    return [4 /*yield*/, axios.get("/monitoring/mntr/mileagerouter?id=" + id + "&" + "start=" + start + "&" + "end=" + end)];
                case 1:
                    res = _a.sent();
                    pathLoad[id] = (res.data).slice(undefined);
                    addMileageRouter(id, pathLoad[id], color);
                    appbody.chartMileage.setSelection(null);
                    return [2 /*return*/];
            }
        });
    });
}
// 이동 경로 추가 - mileage path add
function addMileageRouter(id, paths, color) {
    console.log("addMileageRouter ===>", id, paths, color);
    if (isEmpty(paths))
        return;
    removeMileageRouter();
    paths.forEach(function (path) {
        var circle = new google.maps.Circle({
            strokeColor: color,
            strokeOpacity: 1,
            strokeWeight: 2,
            fillColor: color,
            fillOpacity: 1,
            map: map_mileage,
            center: path,
            radius: 1,
        });
        mileageRouter.push(circle);
    });
    $('#map_loading').empty();
    mileageRouter.forEach(function (item) { return item.setMap(map_mileage); });
}
// 이동 경로 삭제 - mileage path remove
function removeMileageRouter() {
    mileageRouter.forEach(function (item) { return item.setMap(null); });
    mileageRouter = [];
}
$('input[name="mileageDateIn"]').on("change", function () {
    var oldStartDt, oldEndDt;
    var mileageStartDt = $('#mileageStartDate').val();
    var mileageEndDt = $('#mileageEndDate').val();
    if (mileageStartDt !== "" && mileageEndDt !== "") {
        if (mileageStartDt !== oldStartDt || mileageEndDt !== oldEndDt) {
            appbody.dataMileage = [];
            if (appbody.dataMileage[0] === "" || mileageAgentCount().length !== 0) {
                setChartLayout("chart-loading");
            }
            axios
                .get("/monitoring/mntr/mileageinfo?start=" + mileageStartDt + "&end=" + mileageEndDt)
                .then(function (res) {
                console.log("chart data load", res.data);
                appbody.dataMileage = res.data.slice();
                var count = mileageAgentCount();
                drawViewMileage(count);
            })
                .catch(function (error) {
                console.error("getMileageInfo error ===> ", error);
            });
        }
    }
    oldStartDt = mileageStartDt;
    oldEndDt = mileageEndDt;
});
function mileageAgentCount() {
    var agents = [];
    $('#selectAgentList').find('input').map(function (idx, ele) {
        if (idx !== 0 && ele.checked)
            agents.push(ele.value);
    });
    return agents;
}
function setMileageMap(loc) {
    console.log("setMileageMap ===> ", loc);
    initMapMileage(loc);
}
$('input:checkbox[id="mileageAgentAll"]').on("change", function () {
    if ($('input:checkbox[id="mileageAgentAll"]').is(":checked") === true) {
        $('input:checkbox[name="selectAgentItem"]').prop("checked", true);
    }
    else {
        $('input:checkbox[name="selectAgentItem"]').prop("checked", false);
    }
    var count = mileageAgentCount();
    drawViewMileage(count);
});
$('input:checkbox[name="selectAgentItem"]').on("change", function (e) {
    console.log("selectAgentItem event ===> ", e.target.id);
    var count = mileageAgentCount();
    drawViewMileage(count);
});
function setChartLayout(cmd) {
    if (cmd === "chart-loading") {
        $('#chart_mileage').empty();
        $('#chart_mileage').append('<img src="/monitoring/resources/images/loading/loading1.gif" style="padding-left: 140px; padding-top: 100px;">');
    }
    else if (cmd === "map-loading") {
        console.log("map_loading");
        $('#map_loading').append('<img src="/monitoring/resources/images/loading/loading3.gif" style="padding-left: 140px; padding-top: 100px;">');
    }
    else if (cmd === "zero-agents") {
        $('#chart_mileage').empty();
        $('#chart_mileage').append('<h4 style="padding-left: 100px; padding-top: 250px;">선택된 이동형 에이전트가 없습니다.</h4>');
    }
}
function clickViewLatlon() {
    if (appbody) {
        appbody.latlonShow = !appbody.latlonShow;
        appnav.btnLatLonClicked = appbody.latlonShow;
    }
}
function clickViewScheduling() {
    if (appbody) {
        appbody.schedulingShow = !appbody.schedulingShow;
        appnav.btnSchedulingClicked = appbody.schedulingShow;
    }
    if (!appnav.btnSchedulingClicked) {
        appbody.agents.filter(function (ele) {
            if (ele.chkScheduleInit)
                ele.chkScheduleInit = false;
        });
    }
}
function pushLog(msg) {
    if (!appbody)
        return;
    if (!appbody.logShow)
        return;
    appbody.logs.push(msg);
    var maxline = 1000;
    if (appbody.logs.length > maxline) {
        appbody.logs.splice(0, appbody.logs.length - maxline);
    }
}
function pushLatlon(o) {
    if (!appbody)
        return;
    if (!appbody.latlonShow)
        return;
    appbody.latlons.push(JSON.stringify(o).replace(/\"/gi, " "));
}
function getAreaNm() {
    if (appnav.place === "포항") {
        return "ph";
    }
    else if (appnav.place === "광주") {
        return "gw";
    }
}
function getAgentMarkerLabel(agent) {
    var agentLabel;
    if (agent.fixed === "N") {
        // 이동형
        agentLabel = "Robot_" + agent.agentId;
    }
    else if (agent.fixed === "Y") {
        // 고정형
        // agent id는 유일해야 하므로,
        // cctv agent id를 보이는 것만 강제로 변경.
        // cctv agent id가 10, 11, 12, ... 로 고정되어야 함.
        agentLabel = "Agent(F)_" + agent.agentId;
    }
    else {
        // B: both
        agentLabel = "Drone";
    }
    return agentLabel;
}
var hdgSlider = $("#hdg");
hdgSlider.on("input", function () {
    $("#hdgVal").text(hdgSlider.val());
});
var pitchSlider = $("#pitch");
pitchSlider.on("input", function () {
    $("#pitchVal").text(pitchSlider.val());
});
var yawSlider = $("#yaw");
yawSlider.on("input", function () {
    $("#yawVal").text(yawSlider.val());
});
/********** 마일리지 로봇 이동 경로 테스트용 샘플 데이터 **********/
var pathLoad = { 1: [], 2: [], 3: [], 4: [], 5: [], 6: [], 7: [], 8: [], 9: [] };
var robotPath = { 1: [], 2: [], 3: [], 4: [], 5: [], 6: [], 7: [], 8: [], 9: [] };
/*robotPath[1] = [
  {lat: 36.1195, lng: 129.4155},
  {lat: 36.1194, lng: 129.4154},
  {lat: 36.1193, lng: 129.4153},
  {lat: 36.1192, lng: 129.4154},
  {lat: 36.1192, lng: 129.4155},
  {lat: 36.1191, lng: 129.4156},
  {lat: 36.1191, lng: 129.4157},
  {lat: 36.1192, lng: 129.4158},
  {lat: 36.1193, lng: 129.4159},
  {lat: 36.1192, lng: 129.4158},
  {lat: 36.1191, lng: 129.4157},
  {lat: 36.1191, lng: 129.4156},
  {lat: 36.11903, lng: 129.4158},
  {lat: 36.11894, lng: 129.4156},
  {lat: 36.11920, lng: 129.4152},
  {lat: 36.11907, lng: 129.4151},
  {lat: 36.11888, lng: 129.4154},
  {lat: 36.11898, lng: 129.4155},
];
robotPath[2] = [
  {lat: 36.1190, lng: 129.4158},
  {lat: 36.1187, lng: 129.4162},
  {lat: 36.1184, lng: 129.4159},
  {lat: 36.1187, lng: 129.4155},
  {lat: 36.1190, lng: 129.4158},
  {lat: 36.1186, lng: 129.4163},
  {lat: 36.1185, lng: 129.4164},
  {lat: 36.1185, lng: 129.4165},
  {lat: 36.1186, lng: 129.4166},
  {lat: 36.1187, lng: 129.4166},
  {lat: 36.1188, lng: 129.4165},
  {lat: 36.1189, lng: 129.4166},
  {lat: 36.1188, lng: 129.4167},
  {lat: 36.1188, lng: 129.4168},
  {lat: 36.1188, lng: 129.4169},
  {lat: 36.1189, lng: 129.4170},
  {lat: 36.1189, lng: 129.4169},
  {lat: 36.1190, lng: 129.4168},
  {lat: 36.1191, lng: 129.4168},
  {lat: 36.1191, lng: 129.4169},
];*/
/*pathLoad[1] = [
  {lat: 36.1195, lng: 129.4155},
  {lat: 36.1194, lng: 129.4154},
  {lat: 36.1193, lng: 129.4153},
  {lat: 36.1192, lng: 129.4154}
];

pathLoad[2] = [
  {lat: 36.1192, lng: 129.4155},
  {lat: 36.1191, lng: 129.4156},
  {lat: 36.1191, lng: 129.4157},
  {lat: 36.1192, lng: 129.4158}
];

pathLoad[3] = [
  {lat: 36.1193, lng: 129.4159},
  {lat: 36.1192, lng: 129.4158},
  {lat: 36.1191, lng: 129.4157},
  {lat: 36.1191, lng: 129.4156}
];

pathLoad[4] = [
  {lat: 36.11903, lng: 129.4158},
  {lat: 36.11894, lng: 129.4156},
  {lat: 36.11920, lng: 129.4152}
];

pathLoad[5] = [
  {lat: 36.11907, lng: 129.4151},
  {lat: 36.11888, lng: 129.4154},
  {lat: 36.11898, lng: 129.4155}
];

pathLoad[6] = [
  {lat: 36.1190, lng: 129.4158},
  {lat: 36.1187, lng: 129.4162},
  {lat: 36.1184, lng: 129.4159},
  {lat: 36.1187, lng: 129.4155},
  {lat: 36.1190, lng: 129.4158}
];

pathLoad[7] = [
  {lat: 36.1186, lng: 129.4163},
  {lat: 36.1185, lng: 129.4164},
  {lat: 36.1185, lng: 129.4165},
  {lat: 36.1186, lng: 129.4166},
  {lat: 36.1187, lng: 129.4166}
];

pathLoad[8] = [
  {lat: 36.1188, lng: 129.4165},
  {lat: 36.1189, lng: 129.4166},
  {lat: 36.1188, lng: 129.4167},
  {lat: 36.1188, lng: 129.4168},
  {lat: 36.1188, lng: 129.4169}
];

pathLoad[9] = [
  {lat: 36.1189, lng: 129.4170},
  {lat: 36.1189, lng: 129.4169},
  {lat: 36.1190, lng: 129.4168},
  {lat: 36.1191, lng: 129.4168},
  {lat: 36.1191, lng: 129.4169}
];*/
//# sourceMappingURL=mntr-map.js.map