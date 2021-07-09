/////////////////////////////////////////////////// mntr-utils.js (유틸)

// 원본함수 (GPS변환)
/*
POSITION_XY convertRobotPostoGPS(const POSITION_XY robotPOS)
{
  POSITION_XY GPS_POS;
  double h11 = -0.111429818;
  double h12 = -0.0853415504;
  double h13 = 36.1190186;
  double h21 = -0.399244487;
  double h22 = -0.305760771;
  double h23 = 129.415985;
  double h31 = -0.00308493176;
  double h32 = -0.00236267666;
  double h33 = 1.0;

  GPS_POS.fX = (h11*robotPOS.fX + h12*robotPOS.fY + h13) / (h31*robotPOS.fX + h32*robotPOS.fY + h33);
  GPS_POS.fY = (h21*robotPOS.fX + h22*robotPOS.fY + h23) / (h31*robotPOS.fX + h32*robotPOS.fY + h33);

  return GPS_POS;
}

struct POSITION_XY
{
  double fX, fY;
};
 */

// waypoint를 GPS로 변경해주는 함수

// function convertRobotPostoGPS(posX, posY) {
//   var gps = {
//     lat: 0,
//     lng: 0,
//   };

//   var h11 = -0.111429818;
//   var h12 = -0.0853415504;
//   var h13 = 36.1190186;
//   var h21 = -0.399244487;
//   var h22 = -0.305760771;
//   var h23 = 129.415985;
//   var h31 = -0.00308493176;
//   var h32 = -0.00236267666;
//   var h33 = 1.0;

//   gps.lat = (h11 * posX + h12 * posY + h13) / (h31 * posX + h32 * posY + h33);
//   gps.lng = (h21 * posX + h22 * posY + h23) / (h31 * posX + h32 * posY + h33);

//   return gps;
// }

function convertRobotPostoGPS(posX, posY) {
  var gps = {
    lat: 0,
    lng: 0,
  };

  if (appbody && appbody.isPohang) {
    // 포항.
    var h11 = -6.7982527038;
    var h12 = -5.9096309158;
    var h13 = 9073.7359567794;
    var h21 = -7.3019538205;
    var h22 = 8.3999369861;
    var h23 = 6059.9926791418;
    var h31 = 0;
    var h32 = 0;
    var h33 = 1.0;

    gps.lat = (h11 * posX + h12 * posY + h13) / 1000000 + 36.11;
    gps.lng = (h21 * posX + h22 * posY + h23) / 1000000 + 129.41;
  } else {
    // 광주.
    // var h11 = -6.7982527038;
    // var h12 = -5.9096309158;
    // var h13 = 9073.7359567794;
    // var h21 = -7.3019538205;
    // var h22 = 8.3999369861;
    // var h23 = 6059.9926791418;
    // var h31 = 0;
    // var h32 = 0;
    // var h33 = 1.0;

    // gps.lat = (h11 * posX + h12 * posY + h13) / 1000000 + 35.2443902;
    // gps.lng = (h21 * posX + h22 * posY + h23) / 1000000 + 126.8355429;
    var lat = -0;
    var lng = 0;
    var x = posX;
    var y = posY;
    var latorig = (35.2443902 * Math.PI) / 180.0;
    var lngorig = (126.8355429 * Math.PI) / 180.0;

    var dist = Math.sqrt(x * x + y * y);
    //var heading = Math.atan2(y, x) - Math.PI / 2.0;
    var heading = -Math.atan2(y, x);
    var R = 6371000;

    lat = Math.asin(Math.sin(latorig) * Math.cos(dist / R) + Math.cos(latorig) * Math.sin(dist / R) * Math.cos(heading));
    lng = lngorig + Math.atan2(Math.sin(heading) * Math.sin(dist / R) * Math.cos(latorig), Math.cos(dist / R) - Math.sin(latorig) * Math.sin(lat));

    gps.lat = (lat * 180.0) / Math.PI;
    gps.lng = (lng * 180.0) / Math.PI;
    // gps.lng = lat * 180.0/ Math.PI
    // gps.lat = lng * 180.0 / Math.PI
  }
  //console.log("posx : " + posX + ", posy : " + posY + ", gps " + JSON.stringify(gps));

  return gps;
}

// function convert_main() {
//     var point = convertRobotPostoGPS(10, 10);
//     console.log('point', point);
// }
//
// convert_main();
function getdt(evtdt) {
  // 20191018-130808.000
  if (!evtdt) return evtdt;
  if (evtdt.length < 15) return evtdt;
  var dt = evtdt;
  return (
    dt.substring(0, 4) +
    "-" +
    dt.substring(4, 6) +
    "-" +
    dt.substring(6, 8) +
    " " +
    dt.substring(9, 11) +
    ":" +
    dt.substring(11, 13) +
    ":" +
    dt.substring(13, 15)
  );
}
