<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>테스트 페이지 입니다.</title>

  <script>
    var agentws = (function () {
      var wrap = {};
      var wspath = 'ws://' + location.host + '/monitoring/agentif';
      var ws = new WebSocket(wspath);
      console.log(" location.host : " + location.host);

      wrap.sendMessage = function sendMessage(msg) {
        var jsonMsg = JSON.stringify(msg);
        console.log('Senging message(from brws): ' + jsonMsg + ', state : ' + ws.readyState);
        if (ws.readyState != 1) { //OPEN=1, CLOSING=2, CLOSED:2
          console.error("reconstructed websocket!")
          return;
        }
        ws.send(jsonMsg);
      }

      ws.onopen = function (e) {
        console.info("websocket connected", e);
      };

      ws.onclose = function (e) {
        console.info("session closed(brws)", e);
      };

      ws.onmessage = function (e) {
        console.info("Message Recv :", e);
        console.info("Message Recv Data :", e.data);

        var parseData = JSON.parse(e.data);

        console.info("id: " + parseData.msgId);
        console.info("robotId: " + parseData.data.robotId);
        console.info("statDt: " + parseData.data.statDt);
        console.info("lat: " + parseData.data.lat);
        console.info("lon: " + parseData.data.lon);
        console.info("tilt: " + parseData.data.tilt);
        console.info("compass: " + parseData.data.compass);
        console.info("imuRate: " + parseData.data.imuRate);
        console.info("imuAngle: " + parseData.data.imuAngle);
        console.info("encL: " + parseData.data.encL);
        console.info("encR: " + parseData.data.encR);
        console.info("velL: " + parseData.data.velL);
        console.info("velR: " + parseData.data.velR);
        console.info("tv: " + parseData.data.tv);
        console.info("rv: " + parseData.data.rv);
      }

      return wrap;
    })();


    function testws() {
      console.log("test ws")
      agentws.sendMessage({
        "aaa": "bbb"
      });
    }
  </script>

</head>

<body>

  <button onclick="testws();">WS 테스트. </button>

</body>

</html>