/////////////////////////////////////////////////// mntr-agent.js (Agent 상태 처리)
declare var RJSON: any;
declare var MarkerWithLabel;
declare var getAgentMarkerLabel;
declare var droneIcon;
declare var batteryLowIcon;
declare var batteryMiddleIcon;
declare var batteryHighIcon;

declare var map;
declare var transIcon;

// 초기값 셋팅.
function setAgentMarkerInit(data) {
	console.log("에이전트 셋팅",data);
	data.forEach(function(d) {
		d.info = RJSON.parse(d.codeDs); // console.log(d);

		var myLatlng = new google.maps.LatLng(d.info.lat, d.info.lon); // 초기 위치
		var id = d.info.agentId;
		var cssLabel;
		//d.info.markerImage === "robot.png" ? (cssLabel = "robot-labels") : (cssLabel = "cctv-labels");

		// d.marker : agent icon & agent name label
		var markerIcon = {
			url: "/monitoring/resources/images/map/" + d.info.markerImage,
			scaledSize: new google.maps.Size(0, 0),
			origin: new google.maps.Point(0, 0),
			anchor: new google.maps.Point(0, 0),
			labelOrigin: new google.maps.Point(0, 0)
		};

		d.marker = new MarkerWithLabel({
			id: id,
			//icon: "/monitoring/resources/images/map/" + d.info.markerImage,
			icon: markerIcon,
			position: myLatlng,
			// labelContent: appbody.getAgentDisplayName(id),
			labelContent: getLabelContent(d),
			labelAnchor: new google.maps.Point(35, 75), // 상대적위치
			labelClass: cssLabel,
		});
		d.marker.setMap(map);
		
		// robot 아이콘만 배터리 표시
		if (d.info.markerImage === "robot.png") {
			// TODO: 배터리 남은 용량을 받아와 처리해야함. 일단 테스트용으로 처리..
			// var min = 10;
			// var max = 100;
			// var remainBattery = Math.floor(Math.random() * (max - min + 1)) + min;
			var remainBattery = 100; // default value

			//setBatteryMarker(d, remainBattery, d.info.lat, d.info.lon);
			setAgentBattery(d, remainBattery);
		} else if (d.info.markerImage === "drone.png") {
			// 드론 icon & Label
		/*	d.droneMarker = new MarkerWithLabel({
				id: id,
				icon: droneIcon,
				position: myLatlng,
				labelContent: "92%",
				labelAnchor: new google.maps.Point(15, 10), // 상대적위치
				labelClass: "battery-labels",
			});
			d.droneMarker.setMap(map);*/
		}//end else
	});//for each
}//setAgentMarkerInit

/**
	기존의 icon을 제거하고
	Label로 대체한다.
 */

function clearRobotSelection(){
	let menu;
	for(var i=0 ; i< $(".agent-contextmenu").length;i++){
		menu=$(".agent-contextmenu")[i];
		$(menu).css("display","none");
	} //end for
	
	let agent_label;
	for(var i=0 ; i< $(".agent-label").length;i++){
		agent_label=$(".agent-label")[i];
		$(agent_label).css("background","");
	}//end for
}//


let moveCursor = {flag:false,id:""};
function getLabelContent(data) {
	moveCursor.flag = false;
	let content = document.createElement("div");
	let agentBtn = createImgBtn(data);
	//let agentImg = `<img src="/monitoring/resources/images/map/${data.info.markerImage}"/>`
	//`<p class="text-center text-white">${getAgentMarkerLabel(data.info)}</p>`;
	let agentNamePanel = document.createElement("p");
	agentNamePanel.className = "text-center  p-0 m-0 cctv-labels";
	agentNamePanel.innerHTML = getAgentMarkerLabel(data.info);

	if (data.info.markerImage === "drone.png" || data.info.markerImage === "robot.png") {
		//let batteryPanel = `<p class="m-0 p-0 bg bg-primary text-center text-white" id="" style="border-radius: 50%"></p>`;
		content.className = "d-flex"
		let batteryPanel = createBatteryPanel(data);
		let leftDiv = document.createElement("div");
		leftDiv.style.width = "60px";
		leftDiv.className = 'agent-label text-center';
		leftDiv.setAttribute("id", `${data.info.agentId}-div`);
		leftDiv.setAttribute("style", "border-radius:50%");
		content.oncontextmenu = function() {
			clearRobotSelection();
			$(`#contextmenu-${data.info.agentId}`).css("display", "block");
			$(`#${data.info.agentId}-div`).css("background", "radial-gradient( rgba(255,228,0,0.3) ,  rgba(255,255,255,1))")
			//$(`#${data.info.agentId}-btn`).css("background-color", "rgba(250,244,192,0.9)");
		}//oncontextmenu

		leftDiv.appendChild(batteryPanel);
		leftDiv.innerHTML+="<br/>"
		leftDiv.appendChild(agentBtn)
		leftDiv.appendChild(agentNamePanel);
		content.appendChild(leftDiv);
		content.innerHTML += `<ul id="contextmenu-${data.info.agentId}" class="agent-contextmenu" style="height:20px;">
								  <li><a class="p-1" href="javascript:moveBtnEvt('${data.info.agentId}')">이동</a></li>
								</ul>`;
	} else {
		content.appendChild(agentBtn);
		content.appendChild(agentNamePanel);
	}//endelse
	return content;
}//getLabelContent

function createBatteryPanel(data){
	let batteryPanel=document.createElement("label");
	batteryPanel.setAttribute("id", `${data.info.agentId}-battery-info`);
	//batteryPanel.setAttribute("style", "border-radius: 100%");
	batteryPanel.className = "m-0 p-0 border border-white text-center text-white";
	batteryPanel.setAttribute('style',`width:30px; height:16px; background-image: url('${getBatteryIcon(100)}')`);
	batteryPanel.innerHTML="<b>100%</b>";
	return	batteryPanel;
}//createBatteryPanel

function createImgBtn(data){
	let agentBtn = document.createElement("button");
	let agentImg = document.createElement("img");
	agentImg.src = `/monitoring/resources/images/map/${data.info.markerImage}`;
	agentImg.className = "agent-label";
	agentBtn.appendChild(agentImg);
	agentBtn.className = "p-0 text-center agent-label";
	agentBtn.setAttribute("style","width:60px; height:60px;background:none;border:none");//border-radius:50%
	agentBtn.setAttribute("id", `${data.info.agentId}-btn`);
	return agentBtn;
}//createImgBtn

function moveBtnEvt(id: string) {
	moveCursor.id = id;
	$(`#contextmenu-${id}`).css("display","none");
	setMoveAgentCursor(true);
}//move

function setMoveAgentCursor(flag: boolean) {
	moveCursor.flag = flag;
	if (flag) {
		console.log("여기!");
		$("#map").children().first()
			.children().first()
			.children().first()
			.children().last().css("cursor", "crosshair");
	}else {
		moveCursor.id="";
		$("#map").children().first()
			.children().first()
			.children().first()
			.children().last().css("cursor", "auto");
	}//end else
}//setMoveAgentCursor

// marker label 만 업데이트할 수 있는 방법을 못찾음.
// 그래서 batter icon & label maker를 새로 할당.
function resetBatteryMarker(agent, battery, lat, lon) {
	console.log("agent", agent);
	console.log("battery", battery);

	if (agent.markerBattery) {
		agent.markerBattery.setMap(null); // clear.
		// agent.markerBattery = null;
	}//end if
	if (agent.markerBatteryLabel) {
		agent.markerBatteryLabel.setMap(null); // clear.
		// agent.markerBatteryLabel = null;
	}//end if

	//setBatteryMarker(agent, battery, lat, lon);
	setAgentBattery(agent, battery);
}//resetBatteryMarker

function setAgentBattery(agent, battery) {
	$(`#${agent.robotId}-battery-info`).html(`<b>${battery}%</b>`);
	$(`#${agent.robotId}-battery-info`).css('style',`background-image: url('${getBatteryIcon(battery)}')`);
	//$(`#${agent.robotId}-battery-info`).html(`${battery}%`);
}//


function getBatteryIcon(battery){
	var batteryIcon = "";

	if (battery <= 20) batteryIcon = batteryLowIcon;
	else if (battery > 20 && battery <= 60) batteryIcon = batteryMiddleIcon;
	else batteryIcon = batteryHighIcon;
	return batteryIcon;
}//getBatteryImg


// set battery icon & label
function setBatteryMarker(agent, battery, lat, lon) {
	//var myLatlng = new google.maps.LatLng(agent.info.lat, agent.info.lon); // 초기 위치
	var myLatlng = new google.maps.LatLng(lat, lon); // 초기 위치
	var id = agent.robotId;
	var batteryIcon = "";

	if (battery <= 20) batteryIcon = batteryLowIcon;
	else if (battery > 20 && battery <= 60) batteryIcon = batteryMiddleIcon;
	else batteryIcon = batteryHighIcon;

	// label을 image 로 처리함.
	var imgLabel = document.createElement("img");
	imgLabel.src = batteryIcon;

	// battery icon
	agent.markerBattery = new MarkerWithLabel({
		id: id,
		position: myLatlng,
		map: map,
		icon: transIcon, // 투명처리
		labelContent: imgLabel,
		labelAnchor: new google.maps.Point(17, 72),
		labelInForeground: true,
	});
	agent.markerBattery.setMap(map);

	// battery level label
	agent.markerBatteryLabel = new MarkerWithLabel({
		id: id,
		position: myLatlng,
		map: map,
		icon: transIcon, // 투명처리
		labelContent: battery + "%",
		// labelAnchor: new google.maps.Point(-17, 75),
		labelAnchor: new google.maps.Point(16, 74.5),
		labelClass: "battery-labels",
	});
	agent.markerBatteryLabel.setMap(map);
}
