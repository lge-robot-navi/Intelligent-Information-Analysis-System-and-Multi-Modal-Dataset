console.log("test2");
var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");
var img = new Image();
img.onload = function () {
    console.log("onload.");
    ctx.drawImage(img, 0, 0); // Or at whatever offset you like
    ctx.fillStyle = "blue";
    for (var i = 1; i <= 10; i++) {
        var alpha = i * 0.1;
        ctx.globalAlpha = alpha;
        ctx.fillRect(50 * i, 20, 40, 40);
    }
    ctx.fillStyle = "red";
    for (var i = 1; i <= 10; i++) {
        var alpha = i * 0.1;
        ctx.globalAlpha = alpha;
        ctx.fillRect(50 * i + 10, 20, 40, 40);
    }
};
img.src = "/monitoring/resources/images/pohang-map.jpg";
//# sourceMappingURL=mntr-img-map.js.map