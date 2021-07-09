/////////////////////////////////////////////////// scheduling.js (로봇 스케쥴링 처리)
var map;
var robotMarker = []; // 로봇 아이콘
var batteryMarker = []; // 배터리 아이콘
var robotRouter = []; // 로봇 스케쥴 경로
var robotReRouter = []; // 로봇 리스케쥴 경로
var robotTimer = []; // 시뮬레이션 타이머
// 참고, 맵 확대/축소 시 간격이 맞지 않음. 다른 표현방식 고려.
var gapLat = 0.002; // 로봇아이콘과 배터리아이콘의 간격 (상하)
var gapLng = 0; // 로봇아이콘과 배터리아이콘의 간격 (좌우)
var duration = 1000; // 애니메이션 지속시간
var easing = "easeInOutQuint"; // 애니메이션 타입
var robotIcon = "/monitoring/resources/images/map/robot.png"; // 로봇 아이콘
var droneIcon = "/monitoring/resources/images/map/drone.png"; // 드론 아이콘
var transIcon = "/monitoring/resources/images/map/trans_marker.png"; // 투명 아이콘
var batteryHighIcon = "/monitoring/resources/images/ic_battery_high.png"; // 배터리 아이콘 (high)
var batteryMiddleIcon = "/monitoring/resources/images/ic_battery_middle.png"; // 배터리 아이콘 (middle)
var batteryLowIcon = "/monitoring/resources/images/ic_battery_low.png"; // 배터리 아이콘 (low)

// 맵 초기화
function initMap() {
  var mapLatlng = new google.maps.LatLng(36.1187, 129.4136); // 맵 초기 위치
  var mapOptions = {
    zoom: 15,
    center: mapLatlng,
    mapTypeId: google.maps.MapTypeId.ROADMAP,
  };
  map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
}

// 로봇 추가
function addAgent(id, pos) {
  var initLat = pos.lat;
  var initLng = pos.lng;

  var myLatlng = new google.maps.LatLng(initLat, initLng); // 초기 위치

  // TODO: 데이터를 받아서 처리. (배터리 용량)
  var batteryIcon;
  if (id === 1) batteryIcon = batteryLowIcon;
  else if (id === 2) batteryIcon = batteryHighIcon;
  else if (id === 3) batteryIcon = batteryMiddleIcon;

  robotMarker[id] = new MarkerWithLabel({
    icon: robotIcon,
    position: myLatlng,
    labelContent: "Robot_" + id,
    labelAnchor: new google.maps.Point(30, 0), // 상대적위치
    labelClass: "robot-labels",
  });

  // label을 image 로 처리함.
  var imgLabel = document.createElement("img");
  imgLabel.src = batteryIcon;

  batteryMarker[id] = new MarkerWithLabel({
    position: new google.maps.LatLng(initLat, initLng),
    map: map,
    icon: transIcon, // 투명처리
    labelContent: imgLabel,
    labelAnchor: new google.maps.Point(17, 72),
    labelInForeground: true,
  });

  // batteryMarker[id] = new MarkerWithLabel({
  //   icon: batteryIcon,
  //   position: new google.maps.LatLng(initLat + gapLat, initLng + gapLng),
  // });

  robotMarker[id].setMap(map);
  batteryMarker[id].setMap(map);
}

// 로봇 삭제
function removeAgent(id) {
  robotMarker[id].setMap(null);
  batteryMarker[id].setMap(null);
}

// 드론 추가
function addDrone(pos) {
  var initLat = pos.lat;
  var initLng = pos.lng;

  var myLatlng = new google.maps.LatLng(initLat, initLng); // 초기 위치

  droneMarker = new MarkerWithLabel({
    icon: droneIcon,
    position: myLatlng,
    labelContent: "Drone",
    labelAnchor: new google.maps.Point(30, 0), // 상대적위치
    labelClass: "drone-labels",
  });

  droneMarker.setMap(map);
}

// 이동 경로 추가 (polyline) - schedule, reschedule
function addRouter(id, path, color) {
  // 스케쥴 경로를 점선으로 표시
  var lineSymbol = {
    path: "M 0,-1 0,1",
    strokeOpacity: 1,
    strokeColor: color,
    scale: 4,
  };
  robotRouter[id] = new google.maps.Polyline({
    path: path,
    icons: [
      {
        icon: lineSymbol,
        offset: "0",
        repeat: "20px",
      },
    ],
    strokeColor: "#ff0000",
    strokeOpacity: 1.0,
    strokeOpacity: 0,
    strokeWeight: 3,
    map: map,
  });
  robotRouter[id].setMap(map);
}

// 이동 경로 추가 (polyline) - schedule, reschedule
function addReRouter(id, path, color) {
  robotReRouter[id] = new google.maps.Polyline({
    path: path,
    strokeColor: color,
    strokeOpacity: 1.0,
    strokeWeight: 4,
    map: map,
  });
  robotReRouter[id].setMap(map);
}

// 이동 경로 삭제 (polyline) - schedule, reschedule
function removeRouter(id) {
  robotRouter[id].setMap(null);
}

// 이동 경로 삭제 (polyline) - schedule, reschedule
function removeReRouter(id) {
  robotReRouter[id].setMap(null);
}

// 로봇 이동 시뮬레이션 시작
function startSimulation(id, path) {
  var count = -1;
  var inverse = true;
  robotTimer[id] = setInterval(function () {
    if (inverse) {
      if (count < path.length - 1) {
        count++;
      } else {
        inverse = false;
      }
    } else {
      if (count > 0) {
        count--;
      } else {
        inverse = true;
      }
    }

    var robotLat = path[count].lat;
    var robotLng = path[count].lng;
    var robotLatLng = new google.maps.LatLng(robotLat, robotLng);

    robotMarker[id].setDuration(duration);
    robotMarker[id].setEasing(easing);
    robotMarker[id].setPosition(robotLatLng);

    // var batteryLat = path[count].lat + gapLat;
    // var batteryLng = path[count].lng + gapLng;
    // var batterLatLng = new google.maps.LatLng(batteryLat, batteryLng);

    batteryMarker[id].setDuration(duration);
    batteryMarker[id].setEasing(easing);
    batteryMarker[id].setPosition(robotLatLng);
  }, 1000);
}

// 로봇 이동 시뮬레이션 시작
function startSimulationContinue(id, path) {
  var count = -1;
  robotTimer[id] = setInterval(function () {
    if (count < path.length - 1) {
      count++;
    } else {
      count = 0;
    }

    var robotLat = path[count].lat;
    var robotLng = path[count].lng;
    var robotLatLng = new google.maps.LatLng(robotLat, robotLng);

    robotMarker[id].setDuration(duration);
    robotMarker[id].setEasing(easing);
    robotMarker[id].setPosition(robotLatLng);

    // var batteryLat = path[count].lat + gapLat;
    // var batteryLng = path[count].lng + gapLng;
    // var batterLatLng = new google.maps.LatLng(batteryLat, batteryLng);

    batteryMarker[id].setDuration(duration);
    batteryMarker[id].setEasing(easing);
    batteryMarker[id].setPosition(robotLatLng);
  }, 1000);
}

// 시뮬레이션 종료
function stopSimulation(id) {
  clearInterval(robotTimer[id]);
}

// 로그 출력
function printEvent(pos) {
  console.log("pos: " + pos);
  var $log = $("#log");
  $log.html($log.html() + "현재위치: " + pos + "<br/>");
  $log.scrollTop($log[0].scrollHeight); // 입력 후 스크롤을 맨 밑으로 내림
}

// 로봇, 스케쥴 경로 추가/삭제
function schedulingRouter(id) {
  if ($("input:checkbox[id='s_robot" + id + "']").is(":checked")) {
    console.log("add", id);
    if (id === 1) {
      addRouter(id, path1[0], "#ff0000");
    } else if (id === 2) {
      addRouter(id, path2[0], "#005ef8");
    } else if (id === 3) {
      addRouter(id, path3[0], "#6cb33e");
    }
  } else {
    console.log("remove", id);
    removeRouter(id);
  }
}

// 로봇, 리스케쥴 경로 추가/삭제
function reSchedulingRouter(id) {
  if ($("input:checkbox[id='rs_robot" + id + "']").is(":checked")) {
    console.log("add", id);
    if (id === 1) {
      addReRouter(id, path1[1], "#ff0000");
    } else if (id === 2) {
      addReRouter(id, path2[1], "#005ef8");
    } else if (id === 3) {
      addReRouter(id, path3[1], "#6cb33e");
    }
  } else {
    console.log("remove", id);
    removeReRouter(id);
  }
}

// 시뮬레이션 동작
function actSimulation(id) {
  var simType = $("input[name='simType']:checked").val();
  console.log("simType", simType);

  if ($("input:checkbox[id='robot" + id + "']").is(":checked")) {
    console.log("start", id);
    if (id === 1) {
      addAgent(id, path1[simType][0]);
      startSimulation(id, path1[simType]);
    } else if (id === 2) {
      addAgent(id, path2[simType][0]);
      startSimulation(id, path2[simType]);
    } else if (id === 3) {
      addAgent(id, path3[simType][0]);
      startSimulationContinue(id, path3[simType]);
    }
  } else {
    console.log("stop", id);
    removeAgent(id);
    stopSimulation(id);
  }
}

// radio button check 시 - agent 제거, 시뮬레이션 중지, checkbox 체크 해제
function checkRadioButton() {
  for (var i = 1; i <= 3; i++) {
    if (robotMarker[i] !== undefined) {
      removeAgent(i);
      stopSimulation(i);
      $("input:checkbox[id='robot" + i + "']").prop("checked", false);
    }
  }
}

/////////////////////////////////////////////////////////// 시뮬레이션 경로 데이터

var path1 = [];
var path2 = [];
var path3 = [];

path1[0] = [
  { lat: 36.1187, lng: 129.4136 },
  { lat: 36.1173, lng: 129.4129 },
  { lat: 36.1162, lng: 129.412 },
  { lat: 36.1167, lng: 129.4102 },
  { lat: 36.1182, lng: 129.4087 },
  { lat: 36.12, lng: 129.4064 },
  { lat: 36.1222, lng: 129.4049 },
  { lat: 36.1246, lng: 129.4035 },
  { lat: 36.1275, lng: 129.4027 },
  { lat: 36.1281, lng: 129.4003 },
];
path2[0] = [
  { lat: 36.114, lng: 129.4202 },
  { lat: 36.1122, lng: 129.42 },
  { lat: 36.1104, lng: 129.4199 },
  { lat: 36.1087, lng: 129.4199 },
  { lat: 36.1072, lng: 129.42 },
  { lat: 36.1054, lng: 129.42 },
  { lat: 36.1037, lng: 129.4196 },
  { lat: 36.1021, lng: 129.419 },
];
path3[0] = [
  { lat: 36.1126, lng: 129.4091 },
  { lat: 36.1121, lng: 129.4112 },
  { lat: 36.1115, lng: 129.414 },
  { lat: 36.1109, lng: 129.4161 },
  { lat: 36.1104, lng: 129.418 },
  { lat: 36.1103, lng: 129.4199 },
  { lat: 36.1115, lng: 129.4212 },
  { lat: 36.1141, lng: 129.4204 },
  { lat: 36.1151, lng: 129.4187 },
  { lat: 36.1162, lng: 129.4169 },
  { lat: 36.1175, lng: 129.4151 },
  { lat: 36.1183, lng: 129.4136 },
  { lat: 36.117, lng: 129.4128 },
  { lat: 36.1157, lng: 129.4114 },
  { lat: 36.1144, lng: 129.4103 },
  { lat: 36.1131, lng: 129.4096 },
];

path1[1] = [
  { lat: 36.1096, lng: 129.4268 },
  { lat: 36.1097, lng: 129.4288 },
  { lat: 36.1098, lng: 129.4312 },
  { lat: 36.1098, lng: 129.4331 },
  { lat: 36.108, lng: 129.4328 },
  { lat: 36.1061, lng: 129.4329 },
  { lat: 36.1042, lng: 129.4329 },
  { lat: 36.1022, lng: 129.4329 },
  { lat: 36.1007, lng: 129.4329 },
];
path2[1] = [
  { lat: 36.1183, lng: 129.4393 },
  { lat: 36.1167, lng: 129.4412 },
  { lat: 36.1157, lng: 129.4426 },
  { lat: 36.1143, lng: 129.4443 },
  { lat: 36.1128, lng: 129.446 },
  { lat: 36.1113, lng: 129.4478 },
  { lat: 36.1097, lng: 129.45 },
  { lat: 36.1079, lng: 129.452 },
  { lat: 36.1064, lng: 129.4538 },
  { lat: 36.1052, lng: 129.4553 },
];
path3[1] = [
  { lat: 36.1109, lng: 129.3975 },
  { lat: 36.1103, lng: 129.3992 },
  { lat: 36.1097, lng: 129.4007 },
  { lat: 36.1079, lng: 129.3994 },
  { lat: 36.1067, lng: 129.3986 },
  { lat: 36.1067, lng: 129.3969 },
  { lat: 36.1071, lng: 129.3952 },
  { lat: 36.108, lng: 129.3939 },
  { lat: 36.1091, lng: 129.3956 },
  { lat: 36.1105, lng: 129.3969 },
  { lat: 36.1109, lng: 129.3975 },
];
