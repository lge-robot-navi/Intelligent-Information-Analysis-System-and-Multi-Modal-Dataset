console.log("test");
// function get_drone_quality(url: string) {
//   // http://223.171.32.36:8080/stream?topic=/tracknet/compressed&type=mjpeg&aquality=20&width=1920&height=1080
//   if (!url.includes("?")) return;
//   const urls = url.split("?");
//   if (!urls[1].includes("&")) return;
//   const kv = urls[1].split("&").find((ele: string) => ele.startsWith("aquality"));
//   if (kv) {
//     if (!kv.includes("=")) return;
//     return kv.split("=")[1];
//   }
// }
// function set_drone_quality(url: string, val: string) {
//   let newurl: string = "";
//   if (!url.includes("?")) return url;
//   const urls = url.split("?");
//   if (!urls[1].includes("&")) return url;
//   const kvs = urls[1].split("&");
//   const nkvs = kvs.map((ele: string) => {
//     if (ele.startsWith("aquality")) {
//       return "aquality=" + val;
//     } else {
//       return ele;
//     }
//   });
//   newurl = urls[0] + "?" + nkvs.join("&");
//   return newurl;
// }
// console.log(get_drone_quality("http://223.171.32.36:8080/stream?topic=/tracknet/compressed&type=mjpeg&aquality=20&width=1920&height=1080"));
// console.log(set_drone_quality("http://223.171.32.36:8080/stream?topic=/tracknet/compressed&type=mjpeg&aquality=20&width=1920&height=1080", "21"));
//# sourceMappingURL=test.js.map