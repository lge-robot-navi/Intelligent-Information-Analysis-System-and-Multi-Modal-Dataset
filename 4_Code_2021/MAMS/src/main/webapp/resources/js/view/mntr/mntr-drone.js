/////////////////////////////////////////////////// mntr-drone.js (드론 제어)
// 드론출동
function cmdDroneMove(event) {
    console.log("move");
    console.log("event", event.eventPosX, event.eventPosY);
    var param = {
        topic: getTopic(topicDroneCmd),
        body: {
            cmd: "MOVE",
            lat: Number(event.eventPosX),
            lng: Number(event.eventPosY),
        },
    };
    callCommMqttJsonApi(param);
}
function cmdDroneMovePrepare(event) {
    // 필요시 전송 또는 팝업.
    console.log("drone move prepare");
    // 전송해야 하는 경우.
    // cmdDroneMove(event);
    clickDroneMon();
    if (appbody) {
        $("#dronelat").val(event.eventPosX);
        $("#dronelng").val(event.eventPosY);
    }
    $("#pitch").val(90);
    $("#pitchVal").text("90");
}
function cmdDroneMove2() {
    var evt = {
        eventPosX: $("#dronelat").val(),
        eventPosY: $("#dronelng").val(),
    };
    cmdDroneMove(evt);
}
// 드론복귀
function cmdDroneReturn() {
    console.log("return");
    var param = {
        topic: getTopic(topicDroneCmd),
        body: {
            cmd: "RETURN",
        },
    };
    callCommMqttJsonApi(param);
}
// 비상정지
function cmdDronePause() {
    console.log("pause");
    var param = {
        topic: getTopic(topicDroneCmd),
        body: {
            cmd: "PAUSE",
        },
    };
    callCommMqttJsonApi(param);
}
// 헤딩
function cmdDroneHeading() {
    var hdg = $("#hdg").val();
    console.log("heading", hdg);
    var param = {
        topic: getTopic(topicDroneCmd),
        // body: { cmd: "HEADING", hdg: '' + hdg }
        body: {
            cmd: "HEADING",
            hdg: Number(hdg),
        },
    };
    callCommMqttJsonApi(param);
}
// Gimbal
function cmdDroneGimbal() {
    var pitch = $("#pitch").val();
    var yaw = $("#yaw").val();
    console.log("gimbal");
    console.log("pitch", pitch);
    console.log("yaw", yaw);
    var param = {
        topic: getTopic(topicDroneCmd),
        // body: { cmd: "GIMBAL", pitch: '' + pitch, yaw: '' + yaw }
        body: {
            cmd: "GIMBAL",
            pitch: Number(pitch),
            yaw: Number(yaw),
        },
    };
    callCommMqttJsonApi(param);
}
//# sourceMappingURL=mntr-drone.js.map