/*
 * (C) Copyright 2014 Kurento (http://kurento.org/)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */

var logtag = "gintoki=>";

//for interaction with agents
var NUM_OF_AGENT = 10;
var AGENT_PUBLIC_IP = "14.35.197.146";	//local server in anyang
//var AGENT_PUBLIC_IP = "175.197.51.229"; //local server in bisangsoft
//var AGENT_PUBLIC_IP = "106.245.234.244";	//local server in daegu
var AWS_PUBLIC_IP = location.host;
//var AGENT_PUBLIC_IP = "192.168.0.3"; //for local network
var PORT_OFFSET = 20;

var bAgentRecording = new Array(true, false, false, false, false, false, false, false, false, false, false);
var bAgentStop = new Array(true, false, false, false, false, false, false, false, false, false, false);

//var ws = new WebSocket('ws://' + AGENT_PUBLIC_IP + ':8081/monitoring/agent');	//for local
//var ws = new WebSocket('ws://' + AWS_PUBLIC_IP + ':52000/monitoring/agent');
var ws = new WebSocket('ws://' + location.host + '/monitoring/agent');
//var ws = new WebSocket('ws://192.168.0.3/monitoring/agent');
console.log(" location.host : " + location.host);

var RECORDING_ENABLE = true;

var RECORDING_PATH = '/recording';
var isStarted = true;
var videoOutput = new Array();
var address = new Array();

var currentSingleViewIP = "";
var idxSelectedAgent = 0;

var pipeline = new Array();
var webRtcPeer = new Array();
var options = new Array();

function getopts(args, opts) {
    var result = opts.default || {};
    args.replace(
        new RegExp("([^?=&]+)(=([^&]*))?", "g"),
        function($0, $1, $2, $3) {
            result[$1] = $3;
        });

    return result;
};

var args = getopts(location.search, {
    default: {
        //ws_uri: 'ws://' + location.hostname + ':8888/kurento',
        ws_uri: 'ws://' + AWS_PUBLIC_IP + ':8888/kurento',
        ice_servers: undefined
    }
});

if (args.ice_servers) {
    console.log(logtag+"Use ICE servers: " + args.ice_servers);
    kurentoUtils.WebRtcPeer.prototype.server.iceServers = JSON.parse(args.ice_servers);
} else {
    console.log(logtag+"Use freeice")
}


window.addEventListener('load', function() {
    console = new Console('console', console);
    //주석처리함.
    //startAll();
    requestToAgent('status');
    startInit();
});

window.onbeforeunload = function() {
    ws.close();
}

function sendMessage(message) {
    var jsonMessage = JSON.stringify(message);
    console.log('Senging message(from brws): ' + jsonMessage + ', state : ' + ws.readyState);
    if (ws.readyState == 3) { //OPEN=1, CLOSING=2, CLOSED:2
        console.log("reconstructed websocket!")
        ws = new WebSocket('ws://' + location.host + '/monitoring/agent');
    }
    ws.send(jsonMessage);
}

function requestToAgent() {
    var message = null
    console.log(logtag+"requestToAgent() arg length : " + arguments.length + ", agent: "+currentSingleViewIP);
    for(var i=0; i<arguments.length; i++) {
    	console.log(logtag+"arg["+i+"] : "+arguments[i]);
    }
    
    if (arguments.length > 1) {
        message = {
            ID: currentSingleViewIP,
            MSG_TYPE: "request",
            DATA_TYPE: arguments[0],
            VALUES: arguments[1]
        };
    } else {
        message = {
            ID: currentSingleViewIP,
            MSG_TYPE: "request",
            DATA_TYPE: arguments[0],

        };
    }
    sendMessage(message);
}

// websocket 서버에 연결되면 연결 메시지를 화면에 출력한다.
ws.onopen = function(e) {
    console.info("websocket connected");
    var message = {
        //ID: "192.168.0.3",//location.hostname,
        ID: location.hostname,
        MSG_TYPE: "notice",
    };
    //sendMessage(message);
};

// websocket 세션이 종료되면 화면에 출력한다.
ws.onclose = function(e) {
    console.info("session closed(brws)");
};

// websocket 에서 수신한 메시지를 화면에 출력한다.
ws.onmessage = function(message) {
    console.info('Received onmessage');
    var parsedMessage = JSON.parse(message.data);
    var strId = parsedMessage.ID;

    var d = new Date();

    console.info('Received message from WebSocket Server(' + d.getSeconds() + 's): ' + message.data);
    var idx = parseInt(strId.substring(strId.lastIndexOf(".") + 1, strId.length) - PORT_OFFSET);
    var values = parsedMessage.VALUES;

    console.info("strId: " + strId + ", agent idx : " + idx + ", msg_type: " + parsedMessage.MSG_TYPE + ", values: " + values);

    if (parsedMessage.MSG_TYPE == 'response') {
        if (values.result == 'ok') {

            //			alert("connect");

            if (!bAgentRecording[idx]) {
                bAgentRecording[idx] = true;

                // 영상 Recording
                //startRecording(idx);
                partialRecording(idx);
                
                // 10분마다 영상을 분할 레코딩함.
                setInterval(function(){
                	requestToAgent('status');
                	partialRecording(idx);
                }, 1000*600);

            }

            //			alert("agent_id: "+idx);
            switch (values.value1) {
                case 'move':
                    break;
                case 'stop':
                    //$.getScript('resources/media-server/js/agent-tracker.js', function() {
                    //$.getScript('/monitoring/resources/media-server/js/agent-tracker.js', function() {
                    //$.getScript('agent-tracker.js', function() {
                    stopAgent(idx);
                    //});
                    break;
                case 'status':
                    $("#vStatus" + idx).text(values.value2);
                    $("#vStatusSolo" + idx).text(values.value2);
                    break;
            }
        }
    } else {
        switch (parsedMessage.DATA_TYPE) {
            case 'location':
                $("#vLocation" + idx).text("x(" + values.x + "), y(" + values.y + ")");
                $("#vLocationSolo" + idx).text("x(" + values.x + "), y(" + values.y + ")");
                //$.getScript('resources/media-server/js/agent-tracker.js', function() {				
                //$.getScript('/monitoring/resources/media-server/js/agent-tracker.js', function() {
                //$.getScript('agent-tracker.js', function() {
                moveAgent(idx, values.x, values.y);
                //});
                break;
            case 'temp_humi':
                $("#vTemp" + idx).text(values.temperature);
                $("#vHumi" + idx).text(values.humidity);
                $("#vTempSolo" + idx).text(values.temperature);
                $("#vHumiSolo" + idx).text(values.humidity);
                break;
            case 'temperature':
                $("#vTemp" + idx).text(values);
                $("#vTempSolo" + idx).text(values);
                break;
            case 'humidity':
                $("#vHumi" + idx).text(values);
                $("#vHumiSolo" + idx).text(values);
                break;
            case 'lidar':
                $("#vLidar" + idx).text(values);
                break;
                //case 'status':
                //	$("#vStatus"+idx).text(values.status);
                //	break;		
            default:
                ;
                onError('Unrecognized message', parsedMessage);
        }
    }
}

// function preSetAgent(agentIp) {
// console.log("preSetAgent agentIp : " + agentIp);
// videoOutput[ipIdx] = document.getElementById('videoOutput'+ipIdx);
// address[ipIdx] = 'rtsp://' + AGENT_PUBLIC_IP + ':1000' + ipIdx + '/unicast';
// }

function preSetAgent(agentInfoList) {
    console.log("preSetAgent() : " + agentInfoList);
    var agentInfo = JSON.parse(agentInfoList);
    var colorArr = new Array();

    for (var i = 0; i < agentInfo.length; i++) {
        var strIp = agentInfo[i].ip;
        var idx = parseInt(strIp.substring(strIp.lastIndexOf(".") + 1, strIp.length) - PORT_OFFSET);
        videoOutput[i + 1] = document.getElementById('videoOutput' + (i + 1));
        colorArr[i] = agentInfo[i].color;
        address[i + 1] = 'rtsp://' + AGENT_PUBLIC_IP + ':' + (10000 + idx) + '/unicast';
        //address[i+1] = 'rtsp://' + '192.168.0.'+ (21+i)+ ':8555/';	//for local network
        console.log("set address : " + address[i + 1]);
    }

    //      $.ajax({
    //          type: "get",
    //          //url: "resources/media-server/js/agent-tracker.js",
    //          url: "agent-tracker.js",
    //          dataType: "script",
    //          success: function(data) { 
    //        	  updateAgentInfo(agentInfo);
    //        	  alert(this.url); },
    //          error: function (data) { alert('NG'); }
    //       });

    //$.getScript('/monitoring/resources/media-server/js/agent-tracker.js', function() {
    //$.getScript('agent-tracker.js', function() {
    updateAgentInfo(agentInfo);
    //});
}


// 분할 레코딩
function partialRecording(idx) {
	
	if(!bAgentStop[idx]) {
		stopRecording(idx);
        startRecording(idx);
	}
}


// 페이지 Load 시
function startInit() {
    isStarted = true;
    console.log("startStreaming!!")

    var startAllButton = document.getElementById('startAll');
    startAllButton.addEventListener('click', startAll);
    var stopAllButton = document.getElementById('stopAll');
    stopAllButton.addEventListener('click', stopAll);

    var startButton = document.getElementById('start');
    startButton.addEventListener('click', start);
    var stopButton = document.getElementById('stop');
    stopButton.addEventListener('click', stop);

    var stopSolo = document.getElementsByClassName("close")[0];
    stopSolo.addEventListener('click', stopSoloView);

    for (var i = 1; i < NUM_OF_AGENT + 1; i++) {

        options[i] = {
            remoteVideo: videoOutput[i],
            useEncodedMedia: true
        };

        webRtcPeer[i] = kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options[i], function(error) {
            if (error) return console.error(error);
        });
        //webRtcPeer[i].generateOffer(onOffer, i);

        (function(m) {
            videoOutput[m].addEventListener('dblclick', function() {
                console.log("videoOutput[i].addEventListener : " + m);
                startSoloView(m);
            });
        })(i);

        console.log(logtag+"address " + address[i] + "  " + videoOutput[i].id);
    }
    
    // Agent별 시작 (Play,Recording)
    function start() {
        var idx = idxSelectedAgent;
        console.log(logtag+"start : " + idx);
        bAgentStop[idx] = false;

        // address[idx] = 'rtsp://' + AGENT_PUBLIC_IP + ':' + (10000 + idx) + '/unicast';

        showSpinner(videoOutput[idx]);
        options[idx] = {
            remoteVideo: videoOutput[idx],
            useEncodedMedia: true
        };
        webRtcPeer[idx] = kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options[idx], function(error) {
            if (error) return console.error(error);
            this.generateOffer(onRecordOffer, idx);
        });
    }
    
    // Agent별 중지 (Play,Recording)
    function stop() {
        var idx = idxSelectedAgent;
        console.log(logtag+"stop : " + idx)
        bAgentStop[idx] = true;
        stopRecording(idx);
    }

    // 전체 시작 (Play,Recording)
    function startAll() {
    	console.log(logtag+"start all agent");
    	for (var i = 1; i < NUM_OF_AGENT + 1; i++) {    		
    		bAgentRecording[i] = false;
    		bAgentStop[i] = false;
    		startRecording(i);
        }
    }
    
    // 전체 중지 (Play,Recording)
    function stopAll() {
        console.log(logtag+"stop all agent");
        isStarted = false;
        // address.disabled = false;
        for (var i = 1; i < NUM_OF_AGENT + 1; i++) {
        	bAgentStop[i] = true;
        	stopRecording(i);
        }
    }
    
    // Agent 별 시작(Play only)
	function startSoloView(idx) {

        console.log(logtag+"startSolo : " + idx);
        /*
         * if(!isStarted) { return; }
         */
        videoOutput[0] = document.getElementById('videoOutput0');
        address[0] = 'rtsp://' + AGENT_PUBLIC_IP + ':' + (10000 + idx) + '/unicast';
        //address[0] = 'rtsp://' + '192.168.0.'+ (20+idx)+ ':8555/';	//for local network

        showSpinner(videoOutput[0]);
        options[0] = {
            remoteVideo: videoOutput[0]
        };
        webRtcPeer[0] = kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options[0], function(error) {
            if (error) return console.error(error);
            this.generateOffer(onPlayOffer, 0);
        });
    }

	// Agent 별 중지(Play only)
    function stopSoloView() {
    	console.log(logtag+"stopSolo : " + idx);
    	
        // address.disabled = false;
        if (webRtcPeer[0]) {
            webRtcPeer[0].dispose();
            webRtcPeer[0] = null;
        }
        if (pipeline[0]) {
            pipeline[0].release();
            pipeline[0] = null;
        }
        hideSpinner(videoOutput[0]);
    }
}

// i번째 agent의 영상을 레코딩
function startRecording(i) {
	
	console.log("start recording: " + i);
	console.log(logtag+"startStreaming!! videoOutput: " + videoOutput[i]);
	showSpinner(videoOutput[i]);

	options[i] = {
            remoteVideo: videoOutput[i],
            useEncodedMedia: true
	};

	webRtcPeer[i] = kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options[i], function(error) {
            if (error) {
            	return console.error(error);
            	console.log(logtag+"kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options["+i+"], function(error) error " + error);
            }
	});
	webRtcPeer[i].generateOffer(onRecordOffer, i);

	console.log(logtag+"address " + address[i] + "  " + videoOutput[i].id);
}

// i번째 agent의 영상 레코딩을 중지
function stopRecording(i) {
    console.log(logtag+"stop recording: " + i)

    if (webRtcPeer[i]) {
        webRtcPeer[i].dispose();
        webRtcPeer[i] = null;
    }
    if (pipeline[i]) {
        pipeline[i].release();
        pipeline[i] = null;
    }
    hideSpinner(videoOutput[i]);
}

// 영상 레코딩 및 DB 저장
function onRecordOffer(error, sdpOffer, func, i) {
    if (error) { 
    	return onError(error);
    	console.log(logtag+"onOffer(error, sdpOffer, func, i) error " + error);
    }
    
    // 저장할 파일명
    var rec_file_name = 'agent' + i + '_' + getTimeString() + '.mp4';
    var rec_file_path = RECORDING_PATH + "/" + getDateString() + "/" + rec_file_name;
    var rec_agent_id = 'agent' + i;

    kurentoClient(args.ws_uri, function(error, client) {
        if (error) {
        	return onError(error);
        	console.log(logtag+"kurentoClient(args.ws_uri, function(error, client) error " + error);
        }

        client.create("MediaPipeline", function(error, p) {
            if (error) {
            	return onError(error);
            	console.log(logtag+"client.create('MediaPipeline', function(error, p) " + error);
            }

            var elements = [{
                    type: 'WebRtcEndpoint',
                    params: {}
                },
                {
                    type: 'PlayerEndpoint',
                    params: {
                        uri: address[i]
                    }
                },
                {
                    type: 'RecorderEndpoint',
                    params: {
                        uri: 'file:///home/lgic' + rec_file_path,
                        mediaProfile: 'MP4_VIDEO_ONLY'
                    }
                }
            ]

            pipeline[i] = p;

            pipeline[i].create(elements, function(error, elements) {
                if (error) {
                	return onError(error);
                	console.log(logtag+"pipeline["+i+"].create(elements, function(error, elements) error " + error);
                }

                var webRtc = elements[0];
                var player = elements[1];
                var recorder = elements[2];
                var idItv;
                setIceCandidateCallbacks(webRtcPeer[i], webRtc, onError);
                console.log("getUri : " + recorder.getUri());
                webRtc.processOffer(sdpOffer, function(error, sdpAnswer) {
                    if (error) {
                    	return onError(error);
                    	console.log(logtag+"webRtc.processOffer(sdpOffer, function(error, sdpAnswer) error " + error);
                    }

                    console.log("sdpOffer");

                    webRtc.gatherCandidates(onError);
                    webRtcPeer[i].processAnswer(sdpAnswer);
                });

                client.connect(player, recorder, function(error) {
                    if (error) {
                    	return onError(error);
                    	console.log(logtag+"client.connect(player, recorder, function(error) error " + error);
                    }

                    client.connect(player, webRtc, function(error) {
                        if (error) {
                        	return onError(error);
                        	console.log(logtag+"client.connect(player, webRtc, function(error) error " + error);
                        }
                        // before unload page
                        window.addEventListener('beforeunload', function(event) {
//                            alert("reload page ");
                            recorder.stop();
                            player.stop();
                            pipeline[i].release();
                            webRtcPeer[i].dispose();
                            videoOutput[i].src = "";
                            hideSpinner(videoOutput[i]);
                        });

                        window.addEventListener('unload', function(event) {
//                            alert("reload page ");
                            recorder.stop();
                            player.stop();
                            pipeline[i].release();
                            webRtcPeer[i].dispose();
                            videoOutput[i].src = "";
                            hideSpinner(videoOutput[i]);
                        });

                        console.log("Connected");
                        //              console.log("I : " + i + ", RECORDING_ENABLE: "+RECORDING_ENABLE);''

                        player.play(function(error) {
                            if (error) { 
                            	console.log(logtag+"play error " + error);
                            	return onError(error);
                            }

                            console.log(logtag+"play: " + i);
                        }); // end player.play(...

                        if (i != 0 && RECORDING_ENABLE) { // except solo View

                            recorder.record(function(error) {
                                if (error) {
                                    console.log(logtag+"record error " + error);
                                    return onError(error);
                                }
                                
                                // DB 저장
                                addRecordData(rec_file_name, rec_file_path, rec_agent_id);
                                
                                console.log(logtag+"recorder ... index : " + i);
                            });

//                            stopAllButton.addEventListener("click", function(event) {
//                                console.log(logtag+"stop record ... index : " + i);
//
//                                recorder.stop();
//                                player.stop();
//                                videoOutput[i].src = "";
//                                clearInterval(idItv);
//                            })
                            
                        }
                    });
                });
            }); // end pipeline[i].create(...
        });
    });
}

// 영상 플레이만
function onPlayOffer(error, sdpOffer, func, i) {
    if (error) { 
    	return onError(error);
    	console.log(logtag+"onOffer(error, sdpOffer, func, i) error " + error);
    }

    kurentoClient(args.ws_uri, function(error, client) {
        if (error) {
        	return onError(error);
        	console.log(logtag+"kurentoClient(args.ws_uri, function(error, client) error " + error);
        }

        client.create("MediaPipeline", function(error, p) {
            if (error) {
            	return onError(error);
            	console.log(logtag+"client.create('MediaPipeline', function(error, p) " + error);
            }

            var elements = [{
                    type: 'WebRtcEndpoint',
                    params: {}
                },
                {
                    type: 'PlayerEndpoint',
                    params: {
                        uri: address[i]
                    }
                }
            ]

            pipeline[i] = p;

            pipeline[i].create(elements, function(error, elements) {
                if (error) {
                	return onError(error);
                	console.log(logtag+"pipeline["+i+"].create(elements, function(error, elements) error " + error);
                }

                var webRtc = elements[0];
                var player = elements[1];
                var idItv;
                setIceCandidateCallbacks(webRtcPeer[i], webRtc, onError);
                
                webRtc.processOffer(sdpOffer, function(error, sdpAnswer) {
                    if (error) {
                    	return onError(error);
                    	console.log(logtag+"webRtc.processOffer(sdpOffer, function(error, sdpAnswer) error " + error);
                    }

                    console.log("sdpOffer");

                    webRtc.gatherCandidates(onError);
                    webRtcPeer[i].processAnswer(sdpAnswer);
                });

                client.connect(player, webRtc, function(error) {
                        if (error) {
                        	return onError(error);
                        	console.log(logtag+"client.connect(player, webRtc, function(error) error " + error);
                        }
                        // before unload page
                        window.addEventListener('beforeunload', function(event) {
//                            alert("reload page ");
                            player.stop();
                            pipeline[i].release();
                            webRtcPeer[i].dispose();
                            videoOutput[i].src = "";
                            hideSpinner(videoOutput[i]);
                        });

                        window.addEventListener('unload', function(event) {
//                            alert("reload page ");
                            player.stop();
                            pipeline[i].release();
                            webRtcPeer[i].dispose();
                            videoOutput[i].src = "";
                            hideSpinner(videoOutput[i]);
                        });

                        player.play(function(error) {
                            if (error) { 
                            	console.log(logtag+"play error " + error);
                            	return onError(error);
                            }

                            console.log(logtag+"solo play: " + i);
                        }); // end player.play(...

                });
            }); // end pipeline[i].create(...
        });
    });
}

// 데이터 저장
function addRecordData(rec_file_name, rec_file_path, rec_agent_id) {
    var recData = {};
    recData["recFileName"] = rec_file_name;
    recData["recFilePath"] = rec_file_path;
    recData["recAgentId"] = rec_agent_id;
    $.ajax({
        type: "POST",
        url: "/monitoring/system/recording/add",
        data: JSON.stringify(recData),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            if (!data['valid']) {
                //alert("Record DB 입력에 실패하였습니다.");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            //alert(JSON.stringify(jqXHR));
        }
    });
}

function getDateString() {
    var d = new Date();
    var dateStr;
    dateStr = d.getFullYear().toString();
    dateStr += leadingZeros((d.getMonth() + 1), 2);
    dateStr += leadingZeros(d.getDate(), 2);
    console.log(dateStr);
    return dateStr;
}

function getTimeString() {
    var d = new Date();
    var timeStr;
    timeStr = d.getFullYear().toString();
    timeStr += leadingZeros((d.getMonth() + 1), 2);
    timeStr += leadingZeros(d.getDate(), 2);
    timeStr += '_' + leadingZeros(d.getHours(), 2);
    timeStr += leadingZeros(d.getMinutes(), 2);
    timeStr += leadingZeros(d.getSeconds(), 2);
    console.log(timeStr);
    return timeStr;
}

function leadingZeros(n, digits) {
    var zero = '';
    n = n.toString();

    if (n.length < digits) {
        for (i = 0; i < digits - n.length; i++)
            zero += '0';
    }
    return zero + n;
}

function setIceCandidateCallbacks(webRtcPeer, webRtcEp, onerror) {
    webRtcPeer.on('icecandidate', function(candidate) {
        console.log("Local candidate:", candidate);

        candidate = kurentoClient.getComplexType('IceCandidate')(candidate);

        webRtcEp.addIceCandidate(candidate, onerror)
    });

    webRtcEp.on('OnIceCandidate', function(event) {
        var candidate = event.candidate;

        console.log("Remote candidate:", candidate);

        webRtcPeer.addIceCandidate(candidate, onerror);
    });
}

function onError(error) {
    if (error) {
        //console.error(error);
        console.log("error:" + error);
        stop();
    }
}

function showSpinner() {
    arguments[0].poster = '/monitoring/resources/media-server/img/transparent-1px.png';
    arguments[0].style.background = "center transparent url('/monitoring/resources/media-server/img/spinner.gif') no-repeat";

}

function hideSpinner() {
    arguments[0].src = '';
    arguments[0].poster = '/monitoring/resources/media-server/img/disconnect.png';
    arguments[0].style.background = '';
}

/**
 * Lightbox utility (to display media pipeline image in a modal dialog)
 */
$(document).delegate('*[data-toggle="lightbox"]', 'click', function(event) {
    event.preventDefault();
    $(this).ekkoLightbox();
});