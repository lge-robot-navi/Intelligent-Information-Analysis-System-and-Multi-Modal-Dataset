#!/bin/bash


curl http://127.0.0.1:8080/monitoring/agentif/stat -H "Content-Type: application/json; charset=utf-8" -X POST --data @- << REQUEST_BODY
{
	header:{
		robotId:3,
		timestamp:"20190101-101010.333",
		msgId:"agent.event"
	}, 
	data:{
		lat:100,
		lon:120,
		pan:10,
		tilt:10,
		compass:10,
		imuRate:10.1,
		imuAngle:20.1,
		encL:10,
		encR:20,
		velL:30,
		velR:40,
		tv:1111,
		rv:2222
	}
}
REQUEST_BODY

