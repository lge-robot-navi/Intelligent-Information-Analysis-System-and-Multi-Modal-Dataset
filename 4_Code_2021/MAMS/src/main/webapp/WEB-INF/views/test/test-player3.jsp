<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>테스트 페이지 입니다.</title>

  <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
  <script type="text/javascript" src="/monitoring/resources/js/seqplayer.js?ver=002"></script>
  <script>

  </script>

</head>

<body>

  <canvas id="player" width="500" height="300" style="border:1px solid #000000;"></canvas>
  <div style="height:20px;"></div>
  <select id="agentId" style="width:100px;">
    <option>100</option>
    <option>200</option>
    <option>300</option>
    <option>400</option>
    <option>500</option>
    <option>600</option>
  </select>
  <button onclick="startPlayer();">Player Start.</button>
  <button onclick="stopPlayer();">Player Stop.</button>

  <script>
    var player = createSeqPlayer('/monitoring/test/realtimeimage', 'player', '100');
    player.areaCode = 'G';
    player.start(100, -1);


    function startPlayer() {
      player.stop();

      player = createSeqPlayer('/monitoring/test/realtimeimage', 'player', document.getElementById('agentId').value);
      player.start(100, -1);
    }

    function stopPlayer() {
      player.stop();
    }
  </script>

</body>

</html>