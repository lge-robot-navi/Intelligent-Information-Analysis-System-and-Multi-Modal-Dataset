var moment = require("moment");
var axios = require("axios");

console.log("test javascript");
function createMntrPlayer() {
  var player = {
    play: function(config) {
      console.info("player start : ", config);
      this.interval = config.interval; // millisec.
      this.starttime = new Date().getTime();
      this.startmin = config.startmin; //
      this.endmin = config.endmin;
      this.requestmin = 0;
      this.canvas = config.canvas;
      this.area = config.area;
      this.robotid = config.robotid;
      this.infos = [];

      if (this.timerid) {
        clearInterval(this.timerid);
        this.timerid = null;
      }
      this.fetch();
      this.timerid = setInterval(function() {
        // 최종시간에
        var time = new Date().getTime();
        if (player.isrequesttime(time)) {
          player.fetch();
          player.setnextreqmin();
        }
        var url = player.getimageurl(); // 설정된 시간에 싸라
        if (url) player.playimage(url);
        console.log("interval : ", player.interval, ", starttime : ", player.starttime, ", startmin:", player.startmin, ", endmin:", player.endmin);
      }, this.interval);
    },
    getrequestmin: function() {
      // requestmin과 startmin을 이용하여 시간을 만들어냄.
      var time = this.startmin + this.requestmin;
      return moment(time).format("YYYYMMDDHHmm");
    },
    setnextreqmin: function() {
      this.requestmin += 1000 * 60;
    },
    playimage: function(url) {
      // url을 이용하여 이미지 요청을 만들고,요청이 완료된 시점에 이미지를 canvas에 draw처리함.
      console.log("image url is ", url);
      var img = new Image();
      img.onload = function() {
        var ctx = this.canvas.getContext("2d");
        ctx.drawImage(img, 0, 0, this.canvas.width, this.canvas.height);
      };

      img.src = url;
    },
    isrequesttime: function(time) {
      var elapsedtime = time - this.starttime;
      if (elapsedtime - this.requestmin > 1000 * 60) {
        return true;
      }
      return false;
    },
    getimageurl: function(time) {
      // 현재 시간에 맞는 이미지 url을 리턴함.
      var elapsed = time - this.starttime;
      function url(o) {
        return "/monitoring/mntr/mntrimage?timestr=" + o.timestr + "&filename=" + o.image;
      }
      while (true) {
        if (this.infos.length < 1) return;
        var o = this.infos[0];
        var image_elpased = o.time - this.startmin;
        var diff = image_elpased - elapsed;
        if (Math.abs(diff) < this.interval) {
          this.infos.shift();
          return url(o);
        } else {
          if (diff > 0) {
            // 미래인 경우.
            return;
          }
          // 과거인 경우.
          this.infos.shift();
          continue;
        }
      }
    },

    fetch: function() {
      var param = {
        playmin: this.getrequestmin(),
        area: this.area,
        robotid: this.robotid
      };
      console.log("post", param);
      axios
        .post("/monitoring/mntr/mntrimageinfo", param)
        .then(function(res) {
          infos = this.infos.concat(
            res.data.map(function(item) {
              var toks = item.split(":").map(function(i) {
                return i.trim();
              });
              return {
                time: moment(toks[0], "YYYYMMDDHHmmss.SSS").valueOf(),
                timestr: toks[0], // YYYYMMDDHHmmss.SSS 의 문자열.
                image: toks[1]
              };
            })
          );
        })
        .catch(function(e) {
          console.error("E", e);
        });
    }
  };

  return player;
}

var player = createMntrPlayer();

player.play({
  interval: 1000,
  startmin: moment("201908220000", "YYYYMMDDHHmm").valueOf(),
  endmin: moment("201908222300", "YYYYMMDDHHmm").valueOf(),
  area: "P",
  robotid: 1
});

//console.log("player : ", player);

var t = moment("201601010101", "YYYYMMDDHHmm");
console.log("time", t, t.valueOf());

console.log("formatted time :", moment(t.valueOf()).format("YYYYMMDDHHmm"));

var line = "20190822194812.400 : agent_01_0014.jpg";
var a = line.split(":").map(function(item) {
  return item.trim();
});
console.log("", a);
t = moment(a[0], "YYYYMMDDHHmmss.SSS");
console.log("t", t);

var fruits = ["Banana", "Orange", "Apple", "Mango"];
console.log("shift", fruits.shift());
console.log("fruits", fruits);
