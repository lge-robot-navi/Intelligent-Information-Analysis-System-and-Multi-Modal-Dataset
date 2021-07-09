"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var app;
function to_datestr(d) {
    return moment(d).format("YYYY-MM-DD HH:mm:ss");
}
$(document).ready(function () {
    app = new Vue({
        el: "#page",
        data: {
            startDt: moment().subtract(1, "days").format("YYYY-MM-DD HH:mm"),
            endDt: moment().format("YYYY-MM-DD HH:mm"),
            config: {
                enableTime: true,
                time_24hr: true,
            },
            playStartDt: "",
            playEndDt: "",
            areaGrpCd: "TA009",
            agentInfos: [{}, {}, {}, {}, {}, {}],
            players: [],
            isplaying: false,
            times: [],
            timesStart: [],
            timesEnd: [],
            imageTime: 0,
            imageTimeStr: "",
            searchResult: false,
            selectStartDt: true,
            selectEndDt: true,
        },
        mounted: function () {
            this.getagentinfo(this.areaGrpCd);
        },
        methods: {
            clickSaveList: function () {
                console.info("click save list");
                var app = this;
                axios
                    .get("/monitoring/mntr/listtimes?start=" + this.startDt + "&end=" + this.endDt + "&area=" + (app.areaGrpCd === "TA009" ? "P" : "G"))
                    .then(function (res) {
                    console.log("data", res.data);
                    app.times = res.data;
                    app.searchResult = true;
                })
                    .catch(function (e) {
                    console.error("E", e);
                });
            },
            clickTime: function (time) {
                console.log("time click", time);
            },
            clickSaveListStart: function () {
                console.info("click save list");
                var app = this;
                axios
                    .get("/monitoring/mntr/listtimes?start=" + this.startDt + "&end=" + this.endDt + "&area=" + (app.areaGrpCd === "TA009" ? "P" : "G"))
                    .then(function (res) {
                    console.log("data", res.data);
                    app.timesStart = res.data;
                })
                    .catch(function (e) {
                    console.error("E", e);
                });
            },
            clickTimeStart: function (time) {
                console.log("time click", time);
                this.startDt = time;
            },
            clickSaveListEnd: function () {
                console.info("click save list");
                var app = this;
                axios
                    .get("/monitoring/mntr/listtimes?start=" + this.startDt + "&end=" + this.endDt + "&area=" + (app.areaGrpCd === "TA009" ? "P" : "G"))
                    .then(function (res) {
                    console.log("data", res.data);
                    app.timesEnd = res.data;
                })
                    .catch(function (e) {
                    console.error("E", e);
                });
            },
            clickTimeEnd: function (time) {
                console.log("time click", time);
                this.endDt = time;
            },
            clickReplay: function () {
                console.info("vue", app);
                console.info("replay...", this.startDt, this.endDt, "OK");
                this.getagentinfo(this.areaGrpCd, this.startPlayers);
                this.isplaying = true;
                this.searchResult = false;
            },
            clickStop: function () {
                console.info("click stop");
                this.players.forEach(function (item) {
                    item.stop();
                });
                this.isplaying = false;
            },
            evtstop: function () {
                this.isplaying = false;
            },
            getStartEnd: function () {
                var app = this;
                axios
                    .get("/monitoring/mntr/getstartend?start=" + this.playStartDt + "&end=" + this.playEndDt + "&area=" + (app.areaGrpCd === "TA009" ? "P" : "G"))
                    .then(function (res) {
                    console.log("startend", res.data);
                    var start = moment(res.data.start, "YYYY-MM-DD HH:mm:ss SSS").valueOf();
                    var end = moment(res.data.end, "YYYY-MM-DD HH:mm:ss SSS").valueOf();
                    if (!start || !end)
                        return;
                    app.players.forEach(function (player) {
                        player.startmin = start;
                        player.endmin = end;
                    });
                })
                    .catch(function (error) {
                    console.error(error);
                });
            },
            startPlayers: function (app) {
                app.players = [];
                console.log("startDt", app.startDt);
                console.log("endDt", app.endDt);
                app.agentInfos.forEach(function (item, idx) {
                    var player = createMntrPlayer();
                    player.play({
                        interval: 300,
                        startmin: moment(app.playStartDt, "YYYY-MM-DD HH:mm").valueOf(),
                        endmin: moment(app.playEndDt, "YYYY-MM-DD HH:mm").valueOf(),
                        area: app.areaGrpCd === "TA009" ? "P" : "G",
                        robotid: (item.info || {}).agentId,
                        canvas: document.getElementById("canvas" + idx),
                        evtstop: function () {
                            app.evtstop();
                        },
                        evtimage: function (time) {
                            if (Math.abs(app.imageTime - time) < 1000) {
                                return;
                            }
                            app.imageTime = time;
                            app.imageTimeStr = moment(app.imageTime).format("YYYY-MM-DD HH:mm:ss");
                            //console.log("imageTimeStr", app.imageTime, app.imageTimeStr);
                        },
                    });
                    app.players.push(player);
                });
                app.getStartEnd();
            },
            getagentinfo: function (cdgrpCd, callback) {
                var app = this;
                axios
                    .get("/monitoring/api/codeInfo?cdgrpCd=" + cdgrpCd)
                    .then(function (res) {
                    res.data.forEach(function (d, idx) {
                        d.info = RJSON.parse(d.codeDs); // console.log(d);
                        app.$set(app.agentInfos, idx, d);
                        // play
                    });
                    //this.agentInfos = res.data;
                    console.log("agentInfos", this.agentInfos);
                    if (callback)
                        callback(app);
                })
                    .catch(function (error) {
                    console.error(error);
                });
            },
            clickSearch: function () {
                console.log("search clicked");
                this.clickSaveList();
                //this.searchResult = !this.searchResult;
                //this.searchResult = true;
            },
            clickSelectStartDt: function (dt) {
                console.log("start date : ", dt);
                this.playStartDt = dt;
            },
            clickSelectEndDt: function (dt) {
                console.log("end date : ", dt);
                this.playEndDt = dt;
            },
        },
        watch: {
            areaGrpCd: function (nv, ov) {
                console.log("area:", nv);
            },
        },
    });
});
//# sourceMappingURL=stat-replay.js.map