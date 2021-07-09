"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var robotRouter = []; // 로봇 스케쥴 경로
var robotReRouter = []; // 로봇 리스케쥴 경로
var duration = 1000; // 애니메이션 지속시간
var easing = "easeInOutQuint"; // 애니메이션 타입
var oldSchedulingPath = []; // schedule data
var newSchedulingPath = []; // reschedule data
// 이동 경로 추가 - schedule
function addRouter(id, path, color) {
    if (isEmpty(path))
        return;
    // 스케쥴 경로를 점선으로 표시
    var lineSymbol = {
        path: "M 0,-1 0,1",
        strokeOpacity: 1,
        strokeColor: color,
        scale: 4,
    };
    robotRouter[id] = new google.maps.Polyline({
        path: path,
        icons: [{ icon: lineSymbol, offset: "0", repeat: "20px" }],
        //strokeColor: "#ff0000",
        strokeColor: "#ccc",
        strokeOpacity: 0.5,
        strokeWeight: 3,
        map: map,
    });
    robotRouter[id].setMap(map);
}
// 이동 경로 추가 - reschedule
function addReRouter(id, path, color) {
    if (isEmpty(path))
        return;
    robotReRouter[id] = new google.maps.Polyline({
        path: path,
        strokeColor: color,
        strokeOpacity: 0.7,
        strokeWeight: 3,
        map: map,
    });
    robotReRouter[id].setMap(map);
}
// 이동 경로 삭제 - schedule
function removeRouter(id) {
    robotRouter[id].setMap(null);
    robotRouter[id] = undefined;
}
// 이동 경로 삭제 - reschedule
function removeReRouter(id) {
    robotReRouter[id].setMap(null);
    robotReRouter[id] = undefined;
}
// 스케쥴 경로 추가/삭제
function schedulingRouter(id) {
    if ($("#robot_old_" + id).is(":checked")) {
        console.log("여기===>add", id);
        if (robotRouter[id] === undefined)
            addRouter(id, oldSchedulingPath[id], "#005ef8");
    }
    else {
        console.log("여기===>remove", id);
        if (robotRouter[id] !== undefined)
            removeRouter(id);
    }
}
// 리스케쥴 경로 추가/삭제
function reSchedulingRouter(id) {
    if ($("#robot_new_" + id).is(":checked")) {
        console.log("여기===>add", id);
        if (robotReRouter[id] === undefined)
            addReRouter(id, newSchedulingPath[id], "#ff0000");
    }
    else {
        console.log("여기===>remove", id);
        if (robotReRouter[id] !== undefined)
            removeReRouter(id);
    }
}
// 스케쥴 경로 추가/삭제 (전체)
function schedulingRouterAll() {
    if ($("#robot_old_all").is(":checked")) {
        $('input:checkbox[name="robot_old_id"]').each(function (idx, item) {
            var $id = $(item).data("agent_id");
            console.log("[schedulingRouterAll] agent id : ", $id);
            item.checked = true; //checked 처리
            schedulingRouter($id);
        });
    }
    else {
        $('input:checkbox[name="robot_old_id"]').each(function (idx, item) {
            var $id = $(item).data("agent_id");
            console.log("[schedulingRouterAll] agent id : ", $id);
            item.checked = false; //checked 처리
            schedulingRouter($id);
        });
    }
}
// 리스케쥴 경로 추가/삭제 (전체)
function reSchedulingRouterAll() {
    if ($("#robot_new_all").is(":checked")) {
        $('input:checkbox[name="robot_new_id"]').each(function (idx, item) {
            var $id = $(item).data("agent_id");
            console.log("[reSchedulingRouterAll] agent id : ", $id);
            item.checked = true; //checked 처리
            reSchedulingRouter($id);
        });
    }
    else {
        $('input:checkbox[name="robot_new_id"]').each(function (idx, item) {
            var $id = $(item).data("agent_id");
            console.log("[reSchedulingRouterAll] agent id : ", $id);
            item.checked = false; //checked 처리
            reSchedulingRouter($id);
        });
    }
}
// schedule data set
function setOldSchedulingPath() {
    console.log("set old schedule path");
    axios
        .get("/monitoring/api/mqtt/mqttoldschedule")
        .then(function (res) {
        //console.log("res.data", res.data);
        //console.log("res.data.robots", res.data.robots);
        if (!isEmpty(res.data)) {
            (res.data.robots || []).forEach(function (item) {
                // waypoint를 GPS로 변경
                //console.log("old robot", item);
                var gpsInfo = item.wayPoints.map(function (point) {
                    //console.log("old point", point);
                    return convertRobotPostoGPS(point.posx, point.posy);
                });
                // oldSchedulingPath 값 셋팅
                oldSchedulingPath[item.robotId] = gpsInfo;
            });
        }
    })
        .catch(function (error) {
        console.error(error);
    });
}
// reschedule data set
function setNewSchedulingPath() {
    console.log("set new schedule path");
    axios
        .get("/monitoring/api/mqtt/mqttnewschedule")
        .then(function (res) {
        if (!isEmpty(res.data)) {
            (res.data.robots || []).forEach(function (item) {
                // waypoint를 GPS로 변경
                //console.log("new robot", item);
                var gpsInfo = item.wayPoints.map(function (point) {
                    //console.log("new point", point);
                    return convertRobotPostoGPS(point.posx, point.posy);
                });
                // newSchedulingPath 값 셋팅
                newSchedulingPath[item.robotId] = gpsInfo;
            });
        }
    })
        .catch(function (error) {
        console.error(error);
    });
}
function cmdReScheduleExecute(data) {
    // rescheduling data가 들어온 경우, schedule, reschedule path를 재설정함.
    setOldSchedulingPath();
    setNewSchedulingPath();
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
/////////////////////////////////////////////////////////// 시뮬레이션 경로 샘플 데이터
// oldSchedulingPath[1] = [
//   {lat: 36.1195, lng: 129.4155},
//   {lat: 36.1194, lng: 129.4154},
//   {lat: 36.1193, lng: 129.4153},
//   {lat: 36.1192, lng: 129.4154},
//   {lat: 36.1192, lng: 129.4155},
//   {lat: 36.1191, lng: 129.4156},
//   {lat: 36.1191, lng: 129.4157},
//   {lat: 36.1192, lng: 129.4158},
//   {lat: 36.1193, lng: 129.4159},
// ];
// oldSchedulingPath[2] = [
//   {lat: 36.1190, lng: 129.4158},
//   {lat: 36.1187, lng: 129.4162},
//   {lat: 36.1184, lng: 129.4159},
//   {lat: 36.1187, lng: 129.4155},
//   {lat: 36.1190, lng: 129.4158},
// ];
//
// newSchedulingPath[1] = [
//   {lat: 36.1195, lng: 129.4155},
//   {lat: 36.1194, lng: 129.4154},
//   {lat: 36.1193, lng: 129.4153},
//   {lat: 36.1192, lng: 129.4154},
//   {lat: 36.1192, lng: 129.4155},
//   {lat: 36.1191, lng: 129.4156},
//   {lat: 36.1191, lng: 129.4157},
//   {lat: 36.1192, lng: 129.4158},
//   {lat: 36.1193, lng: 129.4159},
//   {lat: 36.1192, lng: 129.4158},
//   {lat: 36.1191, lng: 129.4157},
//   {lat: 36.1191, lng: 129.4156},
//   {lat: 36.11903, lng: 129.4158},
//   {lat: 36.11894, lng: 129.4156},
//   {lat: 36.11920, lng: 129.4152},
//   {lat: 36.11907, lng: 129.4151},
//   {lat: 36.11888, lng: 129.4154},
//   {lat: 36.11898, lng: 129.4155},
// ];
// newSchedulingPath[2] = [
//   {lat: 36.1190, lng: 129.4158},
//   {lat: 36.1187, lng: 129.4162},
//   {lat: 36.1184, lng: 129.4159},
//   {lat: 36.1187, lng: 129.4155},
//   {lat: 36.1190, lng: 129.4158},
//   {lat: 36.1186, lng: 129.4163},
//   {lat: 36.1185, lng: 129.4164},
//   {lat: 36.1185, lng: 129.4165},
//   {lat: 36.1186, lng: 129.4166},
//   {lat: 36.1187, lng: 129.4166},
//   {lat: 36.1188, lng: 129.4165},
//   {lat: 36.1189, lng: 129.4166},
//   {lat: 36.1188, lng: 129.4167},
//   {lat: 36.1188, lng: 129.4168},
//   {lat: 36.1188, lng: 129.4169},
//   {lat: 36.1189, lng: 129.4170},
//   {lat: 36.1189, lng: 129.4169},
//   {lat: 36.1190, lng: 129.4168},
//   {lat: 36.1191, lng: 129.4168},
//   {lat: 36.1191, lng: 129.4169},
// ];
//# sourceMappingURL=mntr-scheduling.js.map