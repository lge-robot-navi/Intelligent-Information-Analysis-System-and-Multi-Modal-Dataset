"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var topicDroneCmd = "/drone/cmder"; // Drone Control
var topicEtriHil = "/etri/hil"; // Human in Loop Feedback
// common
function callCommMqttJsonApi(param) {
    console.log("json", param);
    axios
        .post("/monitoring/api/mqtt/publishjson", param)
        .then(function (res) {
        console.log(res);
        appbody.addinfo("처리하였습니다(" + param.topic + ")");
    })
        .catch(function (error) {
        console.error(error);
        appbody.addalert("실패하였습니다(" + (error || {}).message + ")");
    });
}
function callCommMqttHilApi(param) {
    console.log("json", param);
    axios
        .post("/monitoring/api/mqtt/hiljson", param)
        .then(function (res) {
        console.log(res);
        appbody.addinfo("처리하였습니다(" + param.topic + ")");
    })
        .catch(function (error) {
        console.error(error);
        appbody.addalert("실패하였습니다(" + (error || {}).message + ")");
    });
}
// get topic
function getTopic(topic) {
    return "/mams/" + getAreaNm() + topic;
}
//# sourceMappingURL=mntr-mqtt.js.map