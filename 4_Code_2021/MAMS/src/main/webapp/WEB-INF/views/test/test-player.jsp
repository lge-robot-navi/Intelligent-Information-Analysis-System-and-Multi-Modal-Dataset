<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>테스트 페이지 입니다.</title>

  <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
  <script>

  </script>

</head>

<body>

  <canvas id="player" width="500" height="300" style="border:1px solid #000000;"></canvas>

  <button onclick="testws();">WS 테스트.</button>

  <script>
    function pad(n, width, z) {
      z = z || '0';
      n = n + '';
      return n.length >= width ? n : new Array(width - n.length + 1)
        .join(z) +
        n;
    }

    var imageNo = 1;
    var canvas = document.getElementById('player');
    var ctx = canvas.getContext("2d");

    setInterval(function () {
      var img = new Image();
      img.onload = function () {
        ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
      };
      img.src = "/monitoring/test/image?agentId=id005&imageNo=" +
        pad(imageNo, 4, '0');
      imageNo++;
      if (imageNo > 150)
        imageNo = 1;

    }, 100);
  </script>

</body>

</html>