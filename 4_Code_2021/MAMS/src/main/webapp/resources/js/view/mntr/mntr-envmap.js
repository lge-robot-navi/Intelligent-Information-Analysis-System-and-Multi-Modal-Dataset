"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var envmapstat = {
    loc: "",
    mapid: "",
    place: "",
    elapsedsec: 0,
};
function setEnvMapOptions(id, place) {
    envmapstat.place = place;
    var loc = place === "포항" ? "ph" : "gw";
    envmapstat.loc = loc;
    envmapstat.mapid = id;
    console.log("id", id);
    if (id === "mapheight") {
        $("#maplegend").attr("src", "/monitoring/resources/images/legend-height.png");
    }
    else if (id === "maptemperature") {
        $("#maplegend").attr("src", "/monitoring/resources/images/legend-temperature.png");
    }
    else {
        //$("#maplegend").removeAttr("src");
        $("#maplegend").attr("src", "/monitoring/resources/images/transparent.png");
    }
    // 각 옵션에 대해 서버로 호출만 해주고, 서버에서 처리된 이미지를 받아와서 보여줌.
    getEnvMapOptions(loc, id + "=1");
}
function getEnvMap(place) {
    axios
        .get("/monitoring/mqtt/getenvmap?width=500&height=350&location=" + place, {
        responseType: "blob",
    })
        .then(function (res) {
        // console.log(res);
        var reader = new window.FileReader();
        reader.readAsDataURL(res.data);
        reader.onload = function () {
            $("#envmapImg").attr("src", reader.result);
        };
    })
        .catch(function (error) {
        console.error(error);
    });
}
function getEnvMapOptions(loc, options) {
    axios
        .get("/monitoring/mqtt/getenvmap?width=500&height=350&location=" + loc + "&" + options, {
        responseType: "blob",
    })
        .then(function (res) {
        // console.log(res);
        var reader = new window.FileReader();
        reader.readAsDataURL(res.data);
        reader.onload = function () {
            $("#envmapImg").attr("src", reader.result);
        };
    })
        .catch(function (error) {
        console.error(error);
    });
}
setInterval(function () {
    if (!appbody)
        return;
    if (!appbody.autorefresh)
        return;
    if (!envmapstat.mapid)
        return;
    if (!appbody.autorefreshinterval)
        return;
    envmapstat.elapsedsec = envmapstat.elapsedsec + 1;
    if (envmapstat.elapsedsec > appbody.autorefreshinterval) {
        console.log("auto refresh envmap ", envmapstat);
        setEnvMapOptions(envmapstat.mapid, envmapstat.place);
        envmapstat.elapsedsec = 0;
    }
}, 1000);
//# sourceMappingURL=mntr-envmap.js.map