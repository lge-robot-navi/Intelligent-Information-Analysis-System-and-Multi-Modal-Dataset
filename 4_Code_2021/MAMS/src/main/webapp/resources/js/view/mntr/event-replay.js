function createReplay(baseUrl, canvas) {
    var ctx = canvas.getContext("2d");
    var timerId;
    var url = baseUrl;
    var idx = 0;
    var player = {
        start: function (intervalMs, images) {
            if (timerId)
                clearTimeout(timerId);
            timerId = setInterval(function () {
                var img = new Image();
                img.onload = function () {
                    ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
                };
                img.src = encodeURI(url + "?filePath=" + images[idx]);
                idx++;
                if (idx == images.length)
                    player.stop();
            }, intervalMs);
        },
        stop: function () {
            if (timerId)
                clearTimeout(timerId);
        },
    };
    return player;
}
//# sourceMappingURL=event-replay.js.map