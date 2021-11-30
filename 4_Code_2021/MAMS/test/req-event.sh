#!/bin/bash


curl http://127.0.0.1:8080/monitoring/agentif/event -H "Content-Type: application/json; charset=utf-8" -X POST --data @- << REQUEST_BODY
{
	header:{
		robotId:4,
		timestamp:"20190401-101010.333",
		msgId:"agent.event"
	}, 
	data:{
		deviceId:100,
		abnormalId:20,
		eventTimestamp:"20190401-101010.333",
		eventPosX:100,
		eventPosY:200
	}
}
REQUEST_BODY

