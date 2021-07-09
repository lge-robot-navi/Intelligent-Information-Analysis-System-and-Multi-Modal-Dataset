<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>SOUND TEST</title>

  <script src="/monitoring/resources/js/relaxed-json.js"></script>
  <script src="/monitoring/resources/js/lodash.min.js"></script>
  <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
  <script src="https://unpkg.com/vue/dist/vue.js"></script>
  <script src="/monitoring/resources/js/vue-comp.js"></script>

</head>

<body>

  <div style="width: 100%;">
    <div id="audio" style="width: 100%;">
      <table>
        <tr>
          <td>
            <audio ref="audio" :src="audioSrc" @ended="handleEnded" controls="true" @canplay="handleCanPlay"></audio></td>
          <td>
            <div style="width:100%;">
              <av-waveform ref="waveform" ref-link="audio" :key="waveformKey" :canv-width="avwidth"></av-waveform>
            </div>
          </td>
        </tr>
      </table>
    </div>
  </div>


  <script>
    new Vue({
      el: "#audio",
      data: {
        avwidth: 500,
        audioIdx: 1,
        waveformKey: 1,
      },
      computed: {
        audioSrc: function () {
          return "/monitoring/resources/soundtest/sound" + this.audioIdx + ".wav";
        }
      },
      mounted: function () {
        console.log("audio scr:", this.audioSrc);
      },
      methods: {
        handleEnded: function () {
          console.log("ended wav");
          if (this.audioIdx == 3) this.audioIdx = 1;
          else this.audioIdx = this.audioIdx + 1;

        },
        handleCanPlay: function () {
          console.log("can play")
          this.avwidth = this.$refs.waveform.clientWidth;
          this.forceRerender();
          this.$refs.audio.play();
        },
        forceRerender: function () {
          this.$refs.audio.pause();
          this.waveformKey++;
        }
      },
    });
  </script>

</body>

</html>