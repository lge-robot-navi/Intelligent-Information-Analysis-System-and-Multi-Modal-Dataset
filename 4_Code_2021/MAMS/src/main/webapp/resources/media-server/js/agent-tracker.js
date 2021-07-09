/*

*/

String.prototype.repeat = function(num) {
  return new Array(num + 1).join(this);
};

var NUM_OF_AGENT = 10;
var allMoveAgent = null;
var moveAgent = null;
var stopAgent = null;
var agentColor = [];
var agentName = [];

function updateAgentInfo(agentInfoObj) {
	console.log("agent-tracker : " + agentInfoObj);
	for (let i = 0; i < agentInfoObj.length; i++) {
	  agentColor[i] = agentInfoObj[i].color;
	  agentName[i] = agentInfoObj[i].name;	  
	  console.log("name : " + agentInfoObj[i].name);

	  $(`.agent:nth-child(${i+1})`).css('background-color', agentColor[i]);
	  $(`.agent:nth-child(${i+1})`).html("<div style='left: -15px;top: 20px;position: relative'> <center><P>" + agentName[i] + '</center> </div>');
	}
}

function selectObj(x) {
	var str = "";
	for(key in x) {
		str += key+"="+x[key]+"\n";
	}
	alert(str);
	return;
}

function setAgentColor(colorArr) {
	for (let i = 0; i < colorArr.length; i++) {
	  agentColor[i] = colorArr[i];
	}
}

function getRandomColor() {
  var letters = '0123456789ABCDEF';
  var color = '#';
  for (var i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
  }

  return color;
}


$(function init() {
  const $parent = $("#out");
  let agent = `<div class="agent"> </div>`;

  $parent.html(agent.repeat(NUM_OF_AGENT));
  
  let $agent = $(".agent");

  let parentWidth = $parent.width() - $agent.width();
  let parentHeight = $parent.height() - $agent.height();
  console.log(" parentWidth : " + parentWidth + "  parentHeight : " + parentHeight);
  
  function moveAll() {
  	console.log("moveAll");
    for (let i = 0; i < NUM_OF_AGENT; i++) {
      $(`.agent:nth-child(${i+1})`).css('background-color', agentColor[i]);
      $(`.agent:nth-child(${i+1})`).html("<div style='left: -15px;top: 20px;position: relative'> <center><P>" + agentName[i] + '</center> </div>');
    }
    for (let i = 0; i < NUM_OF_AGENT; i++) {
      let x = Math.random() * parentWidth;
      let y = Math.random() * parentHeight;

      //console.log(" x : " + x + "  y : " + y);
      $(`.agent:nth-child(${i+1})`).css('transform', `translate(${x}px, ${y}px)`);
    }
  }
  
  function move(i, x, y) {
	console.log("move agent i: " + i +  " x: " + x + " y: " + y);
  	var agent = $(`.agent:nth-child(${i})`);
	agent.css('background-color', agentColor[i-1]);
	agent.html("<div style='left: -15px;top: 20px;position: relative'> <center><P>" + agentName[i-1] + '</center> </div>');      
	agent.css('transform', `translate(${x}px, ${y}px)`);
    }

  function stop(i) {
	console.log("stop Agent idx:" + i);
	var agent = $(`.agent:nth-child(${i})`);
	var p = agent.position();
	agent.css('background-color', agentColor[i-1]);
	agent.html("<div style='left: -15px;top: 20px;position: relative'> <center><P>" + agentName[i-1] + '</center> </div>');      
	agent.css('transform', `translate(${p.left}px, ${p.top}px)`);
    }    

	allMoveAgent = moveAll
  	moveAgent = move;
  	stopAgent = stop;
  //move();

  //let moveTimer = setInterval(move, 8000);

/*
  $(window).resize(function() {
    clearInterval(moveTimer);
    parentWidth = $parent.width() - $agent.width();
    parentHeight = $parent.height() - $agent.height();
    moveTimer = setInterval(move, 8000);
  });
*/  

});
