<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />

  <!-- FAVICONS -->
  <link rel="shortcut icon" href="${REQUEST_CONTEXT_PATH}/resources/images/favicon.ico" type="image/x-icon">
  <link rel="icon" href="${REQUEST_CONTEXT_PATH}/resources/images/favicon.ico" type="image/x-icon">


  <link rel="stylesheet" type="text/css" media="screen"
    href="<c:url value="/resources/media-server/bower_components/bootstrap/dist/css/bootstrap.min.css" />">
  <link rel="stylesheet" type="text/css" media="screen"
    href="<c:url value="/resources/media-server/bower_components/ekko-lightbox/dist/ekko-lightbox.min.css" />">
  <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/media-server/css/monitoring.css" />">
  <link rel="stylesheet" type="text/css" href="<c:url value="/resources/media-server/css/agent-tracker.css" />">

  <script type="text/javascript" src="<c:url value="/resources/media-server/bower_components/jquery/dist/jquery.min.js" />"></script>
  <script type="text/javascript" src="<c:url value="/resources/media-server/bower_components/ekko-lightbox/dist/ekko-lightbox.min.js" />"></script>
  <script type="text/javascript" src="<c:url value="/resources/media-server/bower_components/adapter.js/adapter.js" />"></script>

  <script type="text/javascript" src="<c:url value="/resources/media-server/js/console.js" />"></script>
  <script type="text/javascript" src="<c:url value="/resources/media-server/bower_components/kurento-client/js/kurento-client.js" />"></script>
  <script type="text/javascript" src="<c:url value="/resources/media-server/bower_components/kurento-utils/js/kurento-utils.js" />"></script>

  <script type="text/javascript" src="<c:url value="/resources/media-server/js/agent-tracker.js" />"></script>
  <script type="text/javascript" src="<c:url value="/resources/media-server/js/index.js?ver=3.5" />"></script>

  <title>Multi Agent Monitoring System</title>
</head>

<body>
  <header>
    <div class="navbar navbar-inverse navbar-fixed-top">
      <!-- <div class="container"> -->
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"></button>
        <a class="navbar-brand" href="./">Multi Agent Monitoring System</a>
      </div>
      <div style="float:right;padding:15px"><a href="logout"><span color="white">Logout</span></a>
        <div>
          <!-- </div> -->
        </div>

        <!-- style -->
        <style>

        </style>
        <!-- end style -->

  </header>

  <div id="page">
    <div id="content">
      <header>
        <input type="hidden" id="cntSession" value="${cntSession}" />
      </header>

      <aside>
        <div id="out">
        </div>
      </aside>

      <section>
        <script>
          // 세션 카운트를 계산해 중복 레코딩을 방지함.
          var cntSession = $("#cntSession").val();

          for (var i = 1; i < NUM_OF_AGENT + 1; i++) {
            document.write("<div id='outlineBox" + i + "' class='outlineBox'>");
            document.write("<div id='videoArea" + i + "' class='videoArea'>");
            document.write("<video id='videoOutput" + i + "' class='videoOutput' autoplay poster=");
            document.write("'<c:url value='/resources/media-server/img/disconnect.png' />' ");
            document.write("ondblclick='show(" + i + ")'" + "onclick='selectAgent(" + i + ")'");
            document.write("</video>");
            document.write("</div>");
            document.write("<div id='agentInfo" + i + "' class='agentInfo'><P>");

            document.write("<table class='tAgentInfo'>");
            document.write("<tr><td>Status : " + "<em id='vStatus" + i + "'>-</em>" + "</td></tr>");
            document.write("<tr><td>Location : " + "<em id='vLocation" + i + "'>-</em>" + "</td></tr>");
            document.write("<tr><td>Temperature : " + "<em id='vTemp" + i + "'>-</em>" + " &#8451</td></tr>");
            document.write("<tr><td>Humidity : " + "<em id='vHumi" + i + "'>-</em>" + " &#37</td></tr>");
            document.write("</table>");

            document.write("</div>");
            document.write("</div>");
            if (i == 5) document.write("</br></br></br></br></br></br></br></br>");


          }

          //updateAgentInfo('${agentInfoList}');
          preSetAgent('${agentInfoList}');



          function show(i) {
            // var videoArea = document.getElementById("videoArea"+i);
            // var videoOutput = document.getElementById("videoOutput"+i);
            // var videoSoloArea = document.getElementById("videoSoloArea");
            // videoArea.style.width = "640px";
            // videoOutput.style.width = "640px";
            // videoArea.style.height = "480px";
            // videoOutput.style.height = "480px";

            // 추가한 tempDivAgentInfo 삭제
            $('#tempDivAgentInfo').remove();

            var modal = document.getElementById('modalSingleWin');
            var span = document.getElementById('singleClose');
            span.onclick = function () {
              modal.style.display = "none";
            }

            modal.style.display = "block";
            var obj = $ {
              agentInfoList
            };

            //console.log("agentList:" + JSON.stringify(obj));

            console.log("show i : " + obj[i].name);
            document.getElementById("agentTitle").innerHTML = obj[i - 1].name;
            currentSingleViewIP = obj[i - 1].ip;

            var appendStr = '';
            appendStr += '<div id="tempDivAgentInfo">';
            appendStr += "<table class='tAgentInfo'>";
            appendStr += "<tr><td>Status : " + "<em id='vStatusSolo" + i + "'>-</em>" + "</td></tr>";
            appendStr += "<tr><td>Location : " + "<em id='vLocationSolo" + i + "'>-</em>" + "</td></tr>";
            appendStr += "<tr><td>Temperature : " + "<em id='vTempSolo" + i + "'>-</em>" + " &#8451</td></tr>";
            appendStr += "<tr><td>Humidity : " + "<em id='vHumiSolo" + i + "'>-</em>" + " &#37</td></tr>";
            appendStr += "</table>";
            appendStr += '</div>';

            // Agent정보추가에 추가
            $('#statusArea').append(appendStr);
          }

          function selectAgent(i) {
            var agentId = document.getElementById("sAgtId")
            var obj = $ {
              agentInfoList
            };
            idxSelectedAgent = i;
            console.log("selected Agent : " + idxSelectedAgent);

            agentId.innerHTML = "Agent : " + i;
            agentId.style.backgroundColor = obj[i - 1].color;
            currentSingleViewIP = obj[i - 1].ip;
            for (var j = 1; j < NUM_OF_AGENT + 1; j++) {


              if (i == j) {
                document.getElementById("videoOutput" + j).style.border = "3px solid #ff9009";
                $(".agent:nth-child(" + j + ")").css('border', "2px solid #ff9009");
                $(".agent:nth-child(" + j + ")").css('width', "26px");
                $(".agent:nth-child(" + j + ")").css('height', "26px");
              } else {
                document.getElementById("videoOutput" + j).style.border = "1px solid black";
                $(".agent:nth-child(" + j + ")").css('border', "0px");
                $(".agent:nth-child(" + j + ")").css('width', "20px");
                $(".agent:nth-child(" + j + ")").css('height', "20px");
              }
            }
            //"border: 1px solid black";
          }
        </script>
      </section>

    </div>
  </div>

  <script>
    function setup() {
      var modal = document.getElementById('modalSetupWin');
      var span = document.getElementById('setupClose');
      span.onclick = function () {
        modal.style.display = "none";
      }

      modal.style.display = "block";
    }

    function setupOk() {
      var modal = document.getElementById('modalSetupWin');
      modal.style.display = "none";
      var agentInfoObj = ajaxListModel();
      updateAgentInfo(agentInfoObj);
      //var test = $("#formId").find("input:text").map(function(){ return $(this).val(); }).get().join("");
      //console.log("mjchoi test = " + test);
      //var formData = JSON.stringify($("#form").serialize());

    }

    function setupCancel() {
      var modal = document.getElementById('modalSetupWin');
      modal.style.display = "none";
    }

    function ajaxListModel() {
      var inputs = $("#form :input");
      var o = {};
      var obj = $.map(inputs, function (n, i) {
        if (n.name == 'no') o = {};
        o[n.name] = $(n).val();

        if (n.name == 'type') {
          return o;
        }
      });

      var agentInfoData = JSON.stringify(obj);
      $.ajax({
        type: 'GET', // method
        url: 'agent-info',
        async: 'true', // true
        data: {
          "obj": agentInfoData
        },
        processData: true,
        contentType: 'application/json',

        success: function (data, status, xhr) {
          console.log("success data : " + data);
        },
        error: function (error) {
          console.log("error", error);
        }
      });

      return obj;
    }
  </script>

  <div id="modalSetupWin" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
      <div class="modal-header">
        <span id="setupClose" class="close">&times;</span>
        <h2>Agent Setup</h2>
      </div>
      <div id="agentList" class="container">
        <P>
          <form id="form" class="form-horizontal">

            <c:forEach var="item" items="${agentInfoList}" varStatus="status">
              <div class="form-group">
                <div>
                  <input type="hidden" id="no" name="no" value=${item.no}>
                </div>
                <label class="control-label col-sm-1" for="name">Name</label>
                <div class="col-sm-2">
                  <input type="text" class="form-control" id="name" placeholder="" name="name" value=${item.name}>
                </div>

                <label class="control-label col-sm-1" for="ip">IP</label>
                <div class="col-sm-2">
                  <input type="text" class="form-control" id="ip" placeholder="" name="ip" value=${item.ip}>
                </div>

                <label class="control-label col-sm-1" for="color">Color</label>
                <div class="col-sm-1">
                  <input type="color" id="color" name="color" value=${item.color}>
                </div>

                <label class="control-label col-sm-1" for="type">Type</label>
                <div class="col-sm-1">
                  <select id="selectType" name="type">
                    <option value="camera" ${item.type=='camera' ? 'selected="selected"' : '' }">camera</option>
                    <option value="robot" ${item.type=='robot' ? 'selected="selected"' : '' }">robot</option>
                    <option value="terminal" ${item.type=='terminal' ? 'selected="selected"' : '' }">terminal</option>
                    <option value="etc" ${item.type=='etc' ? 'selected="selected"' : '' }">etc</option>
                  </select>
                </div>
              </div>
            </c:forEach>
            <!--     
			    <div class="form-group">        
			      <div class="col-sm-offset-2 col-sm-10">
			        <button type="submit" class="btn btn-primary">Submit</button>
			      </div>
			    </div>
			 	-->
            <div class="modal-footer" id="btnArea" style="text-align:center">
              <div id="btnArea">
                <a id="okBtn" href="#" class="btn btn-success" onClick="setupOk();">Ok</a>
                <a id="cancelBtn" href="#" class="btn btn-primary" onClick="setupCancel();">Cancel</a>
              </div>
            </div>

          </form>
          </script>
      </div>

    </div>
  </div>
  <!-- Modal Window -->
  <div id="modalSingleWin" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
      <div class="modal-header">
        <span id="singleClose" class="close">&times;</span>
        <h2 id="agentTitle"></h2>
      </div>
      <div class="modal-body">
        <div id='videoSoloArea'>
          <video id="videoOutput0" controls="yes" autoplay width="640px" height="480px"
            poster="<c:url value="/resources/media-server/img/disconnect.png" />"> </video>
        </div>
        <div id="statusArea" style="background:#808080"></div>
        <div style="clear:both:"></div>


      </div>
      <div class="modal-footer" id="btnArea" style="text-align:center">
        <a id="chagngeCamera" href="#" class="btn btn-primary" onclick="requestToAgent('command', 'changecam');">Change Camera</a>
        <!-- <a id="replay" href="#" class="btn btn-primary">Replay</a> -->
        <a id="checkAgent" href="#" class="btn btn-info" onClick="requestToAgent('status')">Check Agent</a>
        <a id="moveAgent" href="#" class="btn btn-info" onclick="requestToAgent('command', 'move');">Move Agent</a>
        <a id="stopAgent" href="#" class="btn btn-info" onclick="requestToAgent('command', 'stop');">Stop Agent</a>
      </div>
    </div>
  </div>

  <footer>
    <div class="foot-fixed-bottom" style="margin-top:50px;">
      <div class="col-lg-6" style="text-align:right">
        <a id="startAll" href="#" class="btn btn-success">
          <span class="glyphicon glyphicon-play"></span>All Connect</a>
        <a id="stopAll" href="#" class="btn btn-danger">
          <span class="glyphicon glyphicon-stop"></span>All Disconnect</a>
        <a id="config" href="#" class="btn btn-warning" onClick="setup();">
          <span class="glyphicon glyphicon-wrench"></span> Setup</a>
        <a id="replay" href="/monitoring/system/recording/list" target="_blank" class="btn btn-primary">Replay</a>
      </div>

      <div class="col-lg-5" style="text-align:left; margin-left:60px">
        <span id="sAgtId" style="font-size: 18px; margin:20px; border:1px solid black;border-radius: 10px;
						 padding:6px;padding-left:12px;padding-right:12px;background-color: gray">
          Agent : &nbsp&nbsp</span>
        <a id="start" href="#" class="btn btn-success">
          <span class="glyphicon glyphicon-play"></span> Connect</a>
        <a id="stop" href="#" class="btn btn-danger">
          <span class="glyphicon glyphicon-stop"></span> Disconnect</a>
        <a id="moveAgent" href="#" class="btn btn-info" onClick="requestToAgent('command', 'move');">
          <span class="glyphicon glyphicon-road"></span> Move</a>
        <a id="stopAgent" href="#" class="btn btn-danger" onClick="requestToAgent('command', 'stop');">
          <span class="glyphicon glyphicon-stop"></span> Stop</a>
      </div>



      <!--  
					<div class="col-lg-12">
						<label class="control-label" for="console">Console</label>
						<div id="console"></div>
					</div>
					-->
    </div>

  </footer>
  <!-- </div> -->

</body>

</html>