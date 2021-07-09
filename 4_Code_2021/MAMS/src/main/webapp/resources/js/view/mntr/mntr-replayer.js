"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
// 모니터링 실시간 저장 데이터를 replay 하는 플레이어 기능 모듈.
/**
 * 1. player 를 시작한 시점을 가지고 있는다.
 * 2. play할 시작 종료 시간을 보관한다.
 * 3. play할 이미지 정보를 요청한다.(이미지 정보 요청은 지역구분, 시간(분단위까지), 에이전트 구분자를 전송한다.)
 * 4. 수신 받은 이미지 정보를 기준으로 플레이를 시작한다.
 *    플레이는, 시작시점을 기준으로 경과한 시간에 따라, 해당 시간의 영상을 요청한다.
 *    다음 플레이할 이미지를 선택하기 위하여 혀재 플레이하고 있는 index를 보관한다.
 * 5. 이미지 정보를 모두 소진한 경우, 새로운 이미지를 요청한다.
 */
function createMntrPlayer() {
    var player = {
        stop: function () {
            if (this.timerid) {
                clearInterval(this.timerid);
                this.timerid = null;
            }
        },
        evtstop: null,
        canvas: null,
        area: null,
        play: function (config) {
            console.info("player start : ", config);
            this.interval = config.interval; // millisec.
            this.starttime = new Date().getTime();
            this.startmin = config.startmin; //
            this.endmin = config.endmin;
            this.requestmin = 0;
            this.strrequestmin = "";
            this.canvas = config.canvas;
            this.area = config.area;
            this.robotid = config.robotid;
            this.infos = [];
            this.evtstart = config.evtstart;
            this.evtstop = config.evtstop;
            this.evtimage = config.evtimage;
            if (this.timerid) {
                clearInterval(this.timerid);
                this.timerid = null;
            }
            this.fetch();
            this.timerid = setInterval(function () {
                // 최종시간에
                var time = new Date().getTime();
                if (player.isrequesttime(time)) {
                    player.fetch();
                    player.setnextreqmin();
                    if (player.isfinished(time)) {
                        player.stop();
                        if (player.evtstop)
                            player.evtstop();
                    }
                }
                var url = player.getimageurl(time); // 설정된 시간에 싸라
                if (url)
                    player.playimage(url);
                //console.log("interval : ", player.interval, ", starttime : ", player.starttime, ", startmin:", player.startmin, ", endmin:", player.endmin);
            }, this.interval);
        },
        getrequestmin: function () {
            // requestmin과 startmin을 이용하여 시간을 만들어냄.
            var time = this.startmin + this.requestmin;
            return moment(time).format("YYYYMMDDHHmm");
        },
        isfinished: function (time) {
            var elapsed = time - this.starttime;
            var curr = this.startmin + elapsed;
            if (curr > this.endmin) {
                return true;
            }
            return false;
        },
        setnextreqmin: function () {
            this.requestmin += 1000 * 60;
            this.strrequestmin = moment(this.startmin).format("YYYYMMDDHHmm");
        },
        playimage: function (url) {
            // url을 이용하여 이미지 요청을 만들고,요청이 완료된 시점에 이미지를 canvas에 draw처리함.
            //console.log("image url is ", url);
            var img = new Image();
            img.onload = function () {
                var ctx = player.canvas.getContext("2d");
                ctx.drawImage(img, 0, 0, player.canvas.width, player.canvas.height);
            };
            img.src = url;
        },
        isrequesttime: function (time) {
            var elapsed = time - this.starttime;
            //console.log("elapsed", elapsed, ", strreq", this.strrequestmin, "calc", moment(this.startmin + elapsed).format("YYYYMMDDHHmm"));
            if (this.strrequestmin == moment(this.startmin + elapsed).format("YYYYMMDDHHmm"))
                return false;
            return true;
        },
        getimageurl: function (time) {
            // 현재 시간에 맞는 이미지 url을 리턴함.
            var elapsed = time - this.starttime;
            function url(o) {
                return "/monitoring/mntr/mntrimage?timestr=" + o.timestr + "&filename=" + o.image + "&area=" + player.area;
            }
            function noimageurl() {
                return "/monitoring/mntr/noimage";
            }
            while (true) {
                if (this.infos.length < 1) {
                    if (this.evtimage)
                        this.evtimage(elapsed + this.startmin);
                    if (time - this.imagetime > 1000 * 2) {
                        this.imagetime = time;
                        return noimageurl();
                    }
                    return;
                }
                var o = this.infos[0];
                //console.log("o : ", o, url(o));
                var image_elpased = o.time - this.startmin;
                var diff = image_elpased - elapsed;
                //console.log("diff ", diff, o.time, this.startmin);
                //console.log("time ", time, this.starttime, "interval", this.interval);
                if (Math.abs(diff) < this.interval) {
                    this.infos.shift();
                    if (this.evtimage)
                        this.evtimage(o.time);
                    //console.log("url is ", url(o), o.time);
                    this.imagetime = time;
                    return url(o);
                }
                else {
                    if (diff > 0) {
                        // 미래인 경우.
                        if (this.evtimage)
                            this.evtimage(elapsed + this.startmin);
                        return;
                    }
                    // 과거인 경우.
                    this.infos.shift();
                    if (this.evtimage)
                        this.evtimage(elapsed + this.startmin);
                    continue;
                }
            }
        },
        fetch: function () {
            var param = {
                playmin: this.getrequestmin(),
                area: this.area,
                robotid: this.robotid,
            };
            console.log("post", param);
            var app = this;
            axios
                .post("/monitoring/mntr/mntrimageinfo", param)
                .then(function (res) {
                console.log("mtrimage res.ddata :", res.data);
                //console.log("mtrimage infos :", app.infos);
                app.infos = app.infos.concat(res.data.map(function (item) {
                    var toks = item.split(":").map(function (i) {
                        return i.trim();
                    });
                    return {
                        time: moment(toks[0], "YYYYMMDDHHmmss.SSS").valueOf(),
                        timestr: toks[0],
                        image: toks[1],
                    };
                }));
                //console.log("mtrimage af infos :", app.infos);
            })
                .catch(function (e) {
                console.error("E", e);
            });
        },
    };
    return player;
}
// var player = createMntrPlayer();
// player.play({
//   interval: 1000,
//   startmin: moment("201908220000", "YYYYMMDDHHmm").valueOf(),
//   endmin: moment("201908222300", "YYYYMMDDHHmm").valueOf(),
//   area: "P",
//   robotid: 1
// });
//# sourceMappingURL=mntr-replayer.js.map