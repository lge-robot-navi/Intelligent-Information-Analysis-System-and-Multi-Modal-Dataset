package com.lge.mams.mqtt.web;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lge.mams.agentif.model.AgentResult;
import com.lge.mams.agentif.service.AgentManager;
import com.lge.mams.jpa.impl.TbEventInfoRepository;
import com.lge.mams.jpa.model.TbAgentStat;
import com.lge.mams.jpa.model.TbEventInfo;
import com.lge.mams.mqtt.MqttHandler;
import com.lge.mams.mqtt.envmap.MqttEnvMapImageBuilder;
import com.lge.mams.mqtt.envmap.MqttImgReqInfo;
import com.lge.mams.mqtt.scheduler.Robot;
import com.lge.mams.mqtt.scheduler.ScheduleReq;
import com.lge.mams.mqtt.stat.MdlMqttStat;
import com.lge.mams.mqtt.stat.MqttAgentStatManager;
import com.lge.mams.util.JsonUtil;
import com.lge.mams.util.StrUtil;

@Controller
@RequestMapping("mqtt")
public class MqttController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	TbEventInfoRepository repoEventInfo;

	@Autowired
	MqttAgentStatManager mqttAgents;

	@RequestMapping(method = RequestMethod.GET, value = "test")
	public String mqtt_test() {

		return "mqtt/mqtt-test";
	}

	@RequestMapping(value = "testimage", method = RequestMethod.GET)
	public void testimage(HttpServletResponse response) throws IOException {
		BufferedImage img = new BufferedImage(1000, 500, BufferedImage.TYPE_INT_ARGB);

		// Grab the graphics object off the image
		Graphics2D graphics = img.createGraphics();
		Color color;
		color = new Color(255, 255, 255);
		graphics.fill(new Rectangle(0, 0, 1000, 500));

		color = new Color(250, 50, 50, 50);
		Stroke stroke = new BasicStroke(1f);

		// graphics.setStroke(stroke);
		graphics.setPaint(color);

		graphics.fill(new Rectangle(0, 0, 100, 10));

		response.setContentType(MediaType.IMAGE_PNG_VALUE);

		ImageIO.write(img, "png", response.getOutputStream());

	}

	@RequestMapping(value = "testimage2", method = RequestMethod.GET)
	public void testimage2(HttpServletResponse response) throws IOException {
		MqttImgReqInfo info = new MqttImgReqInfo();
		BufferedImage img = mapImageBuilder.build(info);

		response.setContentType(MediaType.IMAGE_PNG_VALUE);

		ImageIO.write(img, "png", response.getOutputStream());

	}

	@Autowired
	MqttEnvMapImageBuilder mapImageBuilder;

	@RequestMapping(value = "getenvmap", method = RequestMethod.GET)
	public void getenvmap(MqttImgReqInfo info, HttpServletResponse response) throws IOException {
		BufferedImage img = mapImageBuilder.build(info);

		response.setContentType(MediaType.IMAGE_PNG_VALUE);

		ImageIO.write(img, "png", response.getOutputStream());
	}

	/**
	 * 요청 파라미터 1. 포항/광주. : 베이스 이미지 결정. 2. width, height : 이미지의 크기 결정. 3. 각 옵션 값. :
	 * 수신된 데이터 중에서 어떤 영역들을 overlay 할지를 결정하는 옵션.
	 */

	@Autowired
	AgentManager agentMgr;

	@RequestMapping(method = RequestMethod.GET, value = "homing/{loc}/{reason}")
	@ResponseBody
	public AgentResult homing(@PathVariable String loc, @PathVariable String reason,
			@RequestParam("ids") ArrayList<Integer> ids) {

		logger.info("ids : {}", JsonUtil.pretty(ids));

		Integer id = ids.get(0);

		String topic = "/mams/" + loc + "/cloud/move_to";
		Map map = new LinkedHashMap<String, Object>();

		map.put("agentId", id);
		map.put("reason", reason); // reason : LOWBAT, FAILURE

		publish(topic, map);

		return AgentResult.success("처리하였습니다");
	}

	/**
	 * @param robotId
	 * @param lat Lateral(가로)
	 * @param lng Longitudinal(세로)
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "move/control")
	@ResponseBody
	public AgentResult moveTo(String area, String robotId,double lat,double lng) {
		String topic="/test/dhkim-move-control";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("area", area);
		map.put("robotId", robotId);
		map.put("lat", lat);
		map.put("lng", lng);
		publish(topic, map);
		return AgentResult.success("처리하였습니다");
	}//
	@RequestMapping(method = RequestMethod.GET, value = "moveto/{loc}/{reason}")
	@ResponseBody
	public AgentResult moveto(@PathVariable String loc, @PathVariable String reason,
			@RequestParam("eventSn") Long eventSn) {

		logger.info("eventSn : {}", eventSn);

		TbEventInfo evt = repoEventInfo.findOne(eventSn);

		String topic = "/mams/" + loc + "/cloud/move_to";
		Map map = new LinkedHashMap<String, Object>();

		if ("LOWBAT".equals(reason) || "FAILURE".equals(reason)) {
			map.put("posx", 0); // reason : LOWBAT, FAILURE
			map.put("posy", 0); // reason : LOWBAT, FAILURE
		} else {
			map.put("posx", evt.getEventWayX());
			map.put("posy", evt.getEventWayY());
		}

		map.put("agentId", evt.getRobotId());
		map.put("reason", reason); // reason : LOWBAT, FAILURE

		publish(topic, map);

		return AgentResult.success("처리하였습니다");
	}

	public double calcDist(double x1, double y1, double x2, double y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

	boolean isHealthy(List<MdlMqttStat> list, int robotid) {
		// List<MdlMqttStat> list = mqttAgents.getStatList2();
		MdlMqttStat stat = list.stream().filter(ele -> ele.agentId == robotid && ele.isHealthy).findAny().orElse(null);
		if (stat == null)
			return false;
		return true;

	}

	int getNearRobotId(TbEventInfo evt) {

		List<MdlMqttStat> list = mqttAgents.getStatList2();

		String location = "";
		if (StrUtil.isEqual("P", evt.getAreaCode())) {
			location = "ph";
		}
		if (StrUtil.isEqual("G", evt.getAreaCode())) {
			location = "gw";
		}

		final String floc = location;

		List<MdlMqttStat> agents = list.stream().filter(ele -> {
			return ele.isHealthy && !ele.fixed && ele.stat != null && StrUtil.isEqual(ele.location, floc);
		}).collect(Collectors.toList());

		agents.stream().forEach(ele -> {
			ele.dist = calcDist(evt.getEventWayX(), evt.getEventWayY(), ele.stat.posx, ele.stat.posy);
			logger.info("calc dist : {}", JsonUtil.pretty(ele));
		});
		MdlMqttStat agent = agents.stream().min((a, b) -> {
			if (a.dist < b.dist)
				return -1;
			if (a.dist > b.dist)
				return 1;
			return 0;
		}).orElse(null);

		if (agent == null) {
			logger.info("not found near robotid : {}", JsonUtil.pretty(evt));
			return -1;
		}

		logger.info("near agent : {}, evt : {}", JsonUtil.pretty(agent), JsonUtil.pretty(evt));
		return agent.agentId;
	}

	@RequestMapping(method = RequestMethod.GET, value = "movetofixed/{loc}/{reason}")
	@ResponseBody
	public AgentResult movetofixed(@PathVariable String loc, @PathVariable String reason,
			@RequestParam("eventSn") Long eventSn) {

		/**
		 * 가장 가까운 이동형 로봇을 찾아서, 그로봇을 이벤트의 위치로.
		 */
		logger.info("movetofixed eventSn : {}", eventSn);

		TbEventInfo evt = repoEventInfo.findOne(eventSn);

		String topic = "/mams/" + loc + "/cloud/move_to";
		Map map = new LinkedHashMap<String, Object>();

		if ("LOWBAT".equals(reason) || "FAILURE".equals(reason)) {
			map.put("posx", 0); // reason : LOWBAT, FAILURE
			map.put("posy", 0); // reason : LOWBAT, FAILURE
		} else {
			map.put("posx", evt.getEventWayX());
			map.put("posy", evt.getEventWayY());
		}

		// 이동형 최근접로봇을 찾을 것.
		int nearrobotid = getNearRobotId(evt);
		if (nearrobotid < 0) {
			nearrobotid = evt.getRobotId();
		}

		map.put("agentId", nearrobotid);
		map.put("reason", reason); // reason : LOWBAT, FAILURE

		publish(topic, map);

		return AgentResult.success("처리하였습니다");
	}

	@RequestMapping(method = RequestMethod.GET, value = "scheduleinit/{loc}")
	@ResponseBody
	public AgentResult scheduleinit(@PathVariable String loc, @RequestParam("ids") ArrayList<Integer> ids)
			throws Exception {

		logger.info("scheduleinit ids : {}", JsonUtil.pretty(ids));
		
		// 하나 이상의 로봇이 있어야 함.
		if (ids.size() < 1) {
			throw new Exception("하나 이상의 ROBOT을 선택해야 합니다.");
		}

		ScheduleReq req = new ScheduleReq();
		List<Robot> list = new ArrayList<Robot>();

		for (Integer ele : ids) {
			Robot itm = new Robot();
			itm.posx = 0;
			itm.posy = 0;
			itm.robotId = ele;
			itm.curTargetx = 0;
			itm.curTargety = 0;
			itm.prevTargetTheta = 0;
			itm.prevTargetx = 0;
			itm.prevTargety = 0;
			list.add(itm);
		}
		req.robots = list;
		req.reqType = "INIT";
		req.agentId = 0;
		req.posx = 0;
		req.posy = 0;

		publishScheduleReq(loc, req);

		return AgentResult.success("처리하였습니다");
	}

	// 로봇 긴급 정지 명령
	@RequestMapping(method = RequestMethod.GET, value = "schedulestop/{loc}")
	@ResponseBody
	public AgentResult schedulestop(@PathVariable String loc, @RequestParam("ids") ArrayList<Integer> ids)
			throws Exception {

		logger.info("schedulestop ids : {}", JsonUtil.pretty(ids));
		
		// 하나 이상의 로봇이 있어야 함.
		if (ids.size() < 1) {
			throw new Exception("하나 이상의 ROBOT을 선택해야 합니다.");
		}

		publishScheduleStop(ids);

		return AgentResult.success("처리하였습니다");
	}
	
	// 로봇 충전대 복귀 명령
	@RequestMapping(method = RequestMethod.GET, value = "schedulereturn/{loc}")
	@ResponseBody
	public AgentResult schedulereturn(@PathVariable String loc, @RequestParam("ids") ArrayList<Integer> ids)
			throws Exception {
		
		logger.info("schedulereturn ids : {}", JsonUtil.pretty(ids));
		
		// 하나 이상의 로봇이 있어야 함.
		if (ids.size() < 1) {
			throw new Exception("하나 이상의 ROBOT을 선택해야 합니다.");
		}
		
		publishScheduleReturn(ids);
		
		return AgentResult.success("처리하였습니다");
	}
	
	// 로봇 수동 제어 명령
	@RequestMapping(method = RequestMethod.POST, value = "movecommand")
	@ResponseBody
	public void sendMoveCommand(@RequestBody JSONObject jObject) {
		publishMoveCommand(jObject);
	}

	@RequestMapping(method = RequestMethod.GET, value = "scheduleabnormal/{loc}")
	@ResponseBody
	public AgentResult scheduleabnormal(@PathVariable String loc, @RequestParam("ids") ArrayList<Integer> ids,
			@RequestParam("eventSn") Long eventSn) throws Exception {

		logger.info("scheduleabnormal ids : {}", JsonUtil.pretty(ids));

		// 이상상황 id 에 대해서 하나가 있어야 함.
		if (ids.size() != 1) {
			throw new Exception("하나의 ROBOT을 선택해야 합니다.");
		}
		int robotid = ids.get(0);

		List<TbAgentStat> agentlist;
		if ("ph".equals(loc)) {
			agentlist = agentMgr.phlist();
		} else {
			agentlist = agentMgr.gwlist();
		}

		ScheduleReq req = new ScheduleReq();
		List<Robot> list = new ArrayList<Robot>();

		TbEventInfo evt = repoEventInfo.findOne(eventSn);

		List<MdlMqttStat> statlist = mqttAgents.getStatList2();
		agentlist.forEach(ele -> {
			if (!isHealthy(statlist, ele.robotId))
				return;
			Robot itm = new Robot();
			itm.posx = ele.posx;
			itm.posy = ele.posy;
			itm.robotId = ele.robotId;
			itm.curTargetx = ele.curTargetx;
			itm.curTargety = ele.curTargety;
			itm.curTargetTheta = ele.curTargetTheta;
			itm.prevTargetTheta = ele.prevTargetTheta;
			itm.prevTargetx = ele.prevTargetx;
			itm.prevTargety = ele.prevTargety;

			if (ele.robotId == robotid) {
				//
				req.agentId = robotid;
				if (req.agentId != evt.getRobotId()) {
					logger.error("agent different : {} != {}", req.agentId, evt.getRobotId());
				}
				req.posx = evt.getEventWayX();
				req.posy = evt.getEventWayY();
//				req.posx = ele.posx;
//				req.posy = ele.posy;
			} else {
				list.add(itm);
			}
		});

		req.robots = list;
		req.reqType = "ABNORMAL";

		publishScheduleReq(loc, req);

		return AgentResult.success("처리하였습니다");
	}

	@RequestMapping(method = RequestMethod.GET, value = "scheduleabnormalfixed/{loc}")
	@ResponseBody
	public AgentResult scheduleabnormalfixed(@PathVariable String loc, @RequestParam("ids") ArrayList<Integer> ids,
			@RequestParam("eventSn") Long eventSn) throws Exception {

		logger.info("scheduleabnormalfixed : ids : {}", JsonUtil.pretty(ids));

		// 이상상황 id 에 대해서 하나가 있어야 함.
		if (ids.size() != 1) {
			throw new Exception("하나의 ROBOT을 선택해야 합니다.");
		}
		int robotid = ids.get(0);

		List<TbAgentStat> agentlist;
		if ("ph".equals(loc)) {
			agentlist = agentMgr.phlist();
		} else {
			agentlist = agentMgr.gwlist();
		}

		ScheduleReq req = new ScheduleReq();
		List<Robot> list = new ArrayList<Robot>();

		TbEventInfo evt = repoEventInfo.findOne(eventSn);

		int nearrobotid = getNearRobotId(evt);
		if (nearrobotid < 0) {
			nearrobotid = evt.getRobotId();
		}

		final int fnearrobotid = nearrobotid;

		List<MdlMqttStat> statlist = mqttAgents.getStatList2();
		agentlist.forEach(ele -> {

			if (!isHealthy(statlist, ele.robotId))
				return;

			Robot itm = new Robot();
			itm.posx = ele.posx;
			itm.posy = ele.posy;
			itm.robotId = ele.robotId;
			itm.curTargetx = ele.curTargetx;
			itm.curTargety = ele.curTargety;
			itm.curTargetTheta = ele.curTargetTheta;
			itm.prevTargetTheta = ele.prevTargetTheta;
			itm.prevTargetx = ele.prevTargetx;
			itm.prevTargety = ele.prevTargety;

			if (ele.robotId == fnearrobotid) {
				//
				req.agentId = fnearrobotid;
				if (req.agentId != evt.getRobotId()) {
					logger.error("agent different : {} != {}", req.agentId, evt.getRobotId());
				}
				req.posx = evt.getEventWayX();
				req.posy = evt.getEventWayY();
//				req.posx = ele.posx;
//				req.posy = ele.posy;
			} else {
				list.add(itm);
			}
		});

		req.robots = list;
		req.reqType = "ABNORMAL";

		publishScheduleReq(loc, req);

		return AgentResult.success("처리하였습니다");
	}

	@RequestMapping(method = RequestMethod.GET, value = "schedulebattery/{loc}")
	@ResponseBody
	public AgentResult schedulebattery(@PathVariable String loc, @RequestParam("ids") ArrayList<Integer> ids)
			throws Exception {

		logger.info("ids : {}", JsonUtil.pretty(ids));

		if (ids.size() != 1) {
			throw new Exception("하나의 ROBOT을 선택해야 합니다.");
		}
		
		int robotid = ids.get(0);

		List<TbAgentStat> agentlist;
		if ("ph".equals(loc)) agentlist = agentMgr.phlist();
		else agentlist = agentMgr.gwlist();
		
		ScheduleReq req = new ScheduleReq();
		List<Robot> list = new ArrayList<Robot>();
		List<MdlMqttStat> statlist = mqttAgents.getStatList2();

		agentlist.forEach(ele -> {
			if (!isHealthy(statlist, ele.robotId)) return;
			Robot itm = new Robot();
			itm.posx = ele.posx;
			itm.posy = ele.posy;
			itm.robotId = ele.robotId;
			itm.curTargetx = ele.curTargetx;
			itm.curTargety = ele.curTargety;
			itm.curTargetTheta = ele.curTargetTheta;
			itm.prevTargetTheta = ele.prevTargetTheta;
			itm.prevTargetx = ele.prevTargetx;
			itm.prevTargety = ele.prevTargety;

			if (ele.robotId == robotid) {
				req.agentId = robotid;
				req.posx = ele.posx;
				req.posy = ele.posy;
			} else {
				// low battery 인 경우는, low battery agent 는 포함시키지 않음.
				list.add(itm);
			}
		});

		req.robots = list;
		req.reqType = "LOWBAT";

		publishScheduleReq(loc, req);

		return AgentResult.success("처리하였습니다");
	}

	@RequestMapping(method = RequestMethod.GET, value = "schedulereq/{loc}")
	@ResponseBody
	public AgentResult schedulereq(@PathVariable String loc) {

		if ("ph".equals(loc)) {
			// 포항.
			ScheduleReq req = new ScheduleReq();
			List<Robot> list = new ArrayList<Robot>();
			List<TbAgentStat> agentlist = agentMgr.phlist();
			for (TbAgentStat ele : agentlist) {
				Robot itm = new Robot();
				itm.posx = ele.posx;
				itm.posy = ele.posy;
				itm.robotId = ele.robotId;
				list.add(itm);
			}
			req.robots = list;
			req.reqType = "ABNORMAL"; // ABNORMAL , LOWBAT, INIT

			publishScheduleReq(loc, req);

		} else if ("gw".equals(loc)) {
			// 광주
			ScheduleReq req = new ScheduleReq();
			List<Robot> list = new ArrayList<Robot>();
			List<TbAgentStat> agentlist = agentMgr.gwlist();
			for (TbAgentStat ele : agentlist) {
				Robot itm = new Robot();
				itm.posx = ele.posx;
				itm.posy = ele.posy;
				itm.robotId = ele.robotId;
				list.add(itm);
			}
			req.robots = list;
			req.reqType = "ABNORMAL"; // ABNORMAL , LOWBAT, INIT

			publishScheduleReq(loc, req);
		} else {
			logger.error("not found location " + loc);
		}

		return AgentResult.success("처리하였습니다");
	}

	@Autowired
	MqttHandler mqtt;

	void publishScheduleReq(String loc, ScheduleReq req) {
		String topic = "/mams/" + loc + "/scheduler/req";
		String jsonmsg = JsonUtil.pretty(req);

		logger.info("topic : {}, msg :  {}", topic, jsonmsg);
		mqtt.publish(topic, jsonmsg);
	}

	void publish(String topic, Object req) {
		String jsonmsg = JsonUtil.pretty(req);
		logger.info("topic : {}, msg :  {}", topic, jsonmsg);
		mqtt.publish(topic, jsonmsg);
	}

	void publishScheduleStop(ArrayList<Integer> ids) {
		String topic;
		int cmd = 0;	// STOP Command
		byte[] byteArray = new byte[4];
		// 빅 엔디안 변환
		//byteArray[0] = (byte)(cmd >> 24);
		//byteArray[1] = (byte)(cmd >> 16);
		//byteArray[2] = (byte)(cmd >> 8);
		//byteArray[3] = (byte)(cmd);
		
		// 리틀 엔디안 변환
		byteArray[0] = (byte)(cmd);
		byteArray[1] = (byte)(cmd >> 8);
		byteArray[2] = (byte)(cmd >> 16);
		byteArray[3] = (byte)(cmd >> 24);
		
		for (int id : ids) {
			topic = "smartcookie_" + id + "/mission";
			mqtt.publish(topic, new String(byteArray));
			logger.info("robot stop topic > {}, cmd : {}", topic, (byteArray[0] + byteArray[1] + byteArray[2] + byteArray[3]));
		}
	}
	
	void publishScheduleReturn(ArrayList<Integer> ids) {
		String topic;
		int cmd = 3;	// RETURN Command
		byte[] byteArray = new byte[4];
		// 빅 엔디안 변환
		//byteArray[0] = (byte)(cmd >> 24);
		//byteArray[1] = (byte)(cmd >> 16);
		//byteArray[2] = (byte)(cmd >> 8);
		//byteArray[3] = (byte)(cmd);
		
		// 리틀 엔디안 변환
		byteArray[0] = (byte)(cmd);
		byteArray[1] = (byte)(cmd >> 8);
		byteArray[2] = (byte)(cmd >> 16);
		byteArray[3] = (byte)(cmd >> 24);
		
		for (int id : ids) {
			topic = "smartcookie_" + id + "/mission";
			mqtt.publish(topic, new String(byteArray));
			logger.info("robot return topic > {}, cmd : {}", topic, (byteArray[0] + byteArray[1] + byteArray[2] + byteArray[3]));
		}
	}
	
	private byte[] intToByteArray(int value) {
		return new byte[] {
				(byte)(value),
				(byte)(value >> 8),
				(byte)(value >> 16),
				(byte)(value >> 24)
		};
	}
	
	private byte[] floatToByteArray(float value) {
		int intBits = Float.floatToIntBits(value);
		return new byte[] {
			(byte)(intBits),
			(byte)(intBits >> 8),
			(byte)(intBits >> 16),
			(byte)(intBits >> 24)
		};
	}
	
	private void arrayToFloat(String type, byte[] arr, int start) {
		int i = 0;
		int len = 4;
		int cnt = 0;
		byte[] tmp = new byte[len];

		for (i = start; i < (start + len); i++) {
			tmp[cnt] = arr[i];
			cnt++;
		}

		int accum = 0;
		i = 0;
		for (int shiftBy = 0; shiftBy < 32; shiftBy += 8) {
			accum |= ((long) (tmp[i] & 0xff)) << shiftBy;
			i++;
		}
		System.out.println("byte to float conveter type : " + type + " ===> " + Float.intBitsToFloat(accum));
	}
	
	private void publishMoveCommand(JSONObject jObject) {
		int id = Integer.parseInt(jObject.get("id").toString());
		int run = Integer.parseInt(jObject.get("run").toString());
		float lin = Float.parseFloat(jObject.get("lin").toString());
		float ang = Float.parseFloat(jObject.get("ang").toString());
		float speed = Float.parseFloat(jObject.get("speed").toString());
		
		System.out.println("move command data ===> " + "id : " + id
													 + ", run : " + run
													 + ", lin : " + lin
													 + ", ang : " + ang
													 + ", speed : " + speed
		);
		
		byte[] byteArrRun = intToByteArray(run);
		byte[] byteArrLin = floatToByteArray(lin);
		byte[] byteArrAng = floatToByteArray(ang);
		byte[] byteArrSpeed = floatToByteArray(speed);
		
		arrayToFloat("lin", byteArrLin, 0);
		arrayToFloat("ang", byteArrAng, 0);
		arrayToFloat("speed", byteArrSpeed, 0);
		
		int byteLen = byteArrRun.length + byteArrLin.length + byteArrAng.length + byteArrSpeed.length;
		byte[] byteArray = new byte[byteLen];
		
		System.arraycopy(byteArrRun, 0, byteArray, 0, byteArrRun.length);
		System.arraycopy(byteArrLin, 0, byteArray, byteArrRun.length, byteArrLin.length);
		System.arraycopy(byteArrAng, 0, byteArray, byteArrRun.length + byteArrLin.length, byteArrAng.length);
		System.arraycopy(byteArrSpeed, 0, byteArray, byteArrRun.length + byteArrLin.length + byteArrAng.length, byteArrSpeed.length);
		
		String topic = "smartcookie_" + id + "/cmd";
		mqtt.publish(topic, new String(byteArray));
		System.out.println("bytearray length ===> " + byteArray.length);
	}
}
