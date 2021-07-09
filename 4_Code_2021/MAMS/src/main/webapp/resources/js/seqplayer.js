/**
 * 
 */

function createSeqPlayerDevice(baseUrl, canvas, agentId) {
  function pad(n, width, z) {
    z = z || '0';
    n = n + '';
    return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
  }

  //console.log(">>>canvas", canvas[0]);

  var ctx = canvas.getContext("2d");
  //var ctx = canvas ? canvas[0].getContext("2d") : null;

  var timerId;

  var url = baseUrl;

  var imageNo = 1;

  var player = {
    areaCode: 'P',
    agentId: agentId,
    start: function (intervalMs, maxImageNo) {
      player.agentId = canvas.getAttribute("data-agent-id");
      console.log("player.agentId", player.agentId);
      if (timerId) clearTimeout(timerId);
      timerId = setInterval(function () {
        var img = new Image();
        img.onload = function () {
          ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
          if (player.drawCallback) player.drawCallback(img);
        };

        img.src = url + "?agentId=" + player.agentId + "&areaCode=" + player.areaCode + "&imageNo=" + pad(imageNo, 4, '0');
        imageNo++;
        if (maxImageNo > 0) {
          if (imageNo > maxImageNo) imageNo = 1;
        }

      }, intervalMs);
    },
    stop: function () {
      if (timerId) clearTimeout(timerId);
    },
    addDrawCallback: function (callback) {
      player.drawCallback = callback;
    },
    clearDrawCallback: function () {
      delete player.drawCallback;
    }
  }


  return player;
}

// 테스트용. 시퀀스 이미지 플레이어. 
function createSeqImagePlayer(baseUrl, canvas, agentId, startImageNo) {
  function pad(n, width, z) {
    z = z || '0';
    n = n + '';
    return n.length >= width ? n : new Array(width - n.length + 1)
      .join(z) +
      n;
  }

  var ctx = canvas.getContext("2d");

  var timerId;

  var url = baseUrl;

  var imageNo = 1;
  if (startImageNo) imageNo = startImageNo;

  var player = {
    agentId: agentId,
    imgBase: agentId,
    start: function (intervalMs, maxImageNo) {
      if (maxImageNo <= 0) maxImageNo = 59
      if (timerId) clearTimeout(timerId);
      timerId = setInterval(function () {
        var img = new Image();
        img.onload = function () {
          ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
          if (player.drawCallback) player.drawCallback(img);
        };

        img.src = url + "?agentId=" + player.imgBase + "&imageNo=" + pad(imageNo, 4, '0');
        imageNo++;
        if (maxImageNo > 0) {
          if (imageNo > maxImageNo)
            imageNo = 1;
        }

      }, 300);
    },
    stop: function () {
      if (timerId) clearTimeout(timerId);
    },
    addDrawCallback: function (callback) {
      player.drawCallback = callback;
    },
    clearDrawCallback: function () {
      delete player.drawCallback;
    }
  }


  return player;
}



function createSeqPlayer(baseUrl, canvasId, agentId) {
  var canvas = document.getElementById(canvasId);
  return createSeqPlayerDevice(baseUrl, canvas, agentId);
}