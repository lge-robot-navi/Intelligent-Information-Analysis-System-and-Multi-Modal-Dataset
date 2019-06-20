package com.lge.mams.agentif.web;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lge.mams.agentif.model.AgentResult;
import com.lge.mams.agentif.model.DataAgentEvent;
import com.lge.mams.agentif.model.DataAgentStat;
import com.lge.mams.agentif.model.Header;
import com.lge.mams.common.util.CiaConfig;
import com.lge.mams.jpa.impl.TbAgentStatRepository;
import com.lge.mams.jpa.impl.TbCodeRepository;
import com.lge.mams.jpa.impl.TbEventInfoRepository;
import com.lge.mams.jpa.model.MdlLog;
import com.lge.mams.jpa.model.TbAgentStat;
import com.lge.mams.jpa.model.TbCode;
import com.lge.mams.jpa.model.TbEventInfo;
import com.lge.mams.websocket.WsAgentIfHandler;

@Controller
@RequestMapping("agentif")
public class AgentIfController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WsAgentIfHandler wsAgent;
	
	@Autowired
	private TbCodeRepository repoCode;
	
	@Autowired
	private TbAgentStatRepository repoStat;

	@Autowired
	private TbEventInfoRepository repoEvent;
	
	@Autowired 
	private CiaConfig ciaConfig;

	@RequestMapping(method = RequestMethod.POST, value = "stat")
	@ResponseBody
	public AgentResult createStat(@RequestBody() String json) {
		if(ciaConfig.isStatLog()) logger.debug("request : " + json);

		try {
			ObjectMapper mapper = new ObjectMapper();

			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); // 콤마 없어도.

			JsonNode root = mapper.readTree(json);
			JsonNode header = root.path("header");
			Header h = mapper.treeToValue(header, Header.class);
			if(ciaConfig.isStatLog()) logger.debug("Header : " + ToStringBuilder.reflectionToString(h, ToStringStyle.MULTI_LINE_STYLE));
			JsonNode data = root.path("data");
			DataAgentStat d = mapper.treeToValue(data, DataAgentStat.class);

			if(ciaConfig.isStatLog()) logger.debug("Data : " + ToStringBuilder.reflectionToString(d, ToStringStyle.MULTI_LINE_STYLE));

			TbAgentStat stat = new TbAgentStat();

			stat.load(h);
			stat.load(d);

			repoStat.save(stat);
			wsAgent.pushData(stat);
			return AgentResult.success("statSn : " + stat.getStatSn());

		} catch (JsonProcessingException e) {
			logger.error("Json", e);
			return AgentResult.fail(-1, "Fail : " + e.getMessage());
		} catch (IOException e) {
			logger.error("Json", e);
			return AgentResult.fail(-1, "Fail : " + e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "stat")
	@ResponseBody
	public List<TbAgentStat> listStat(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		logger.debug("request : pageNo => " + pageNo);
		logger.debug("request : pageSize => " + pageSize);
		if (pageSize == null)
			pageSize = 10;
		if (pageNo == null)
			pageNo = 0;

		Pageable page = new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "statSn"));

		Page<TbAgentStat> pageStat = repoStat.findAll(page);

		return pageStat.getContent();

	}

	@RequestMapping(method = RequestMethod.GET, value = "event")
	@ResponseBody
	public List<TbEventInfo> listEvent(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		logger.debug("request : pageNo => " + pageNo);
		logger.debug("request : pageSize => " + pageSize);
		if (pageSize == null)
			pageSize = 10;
		if (pageNo == null)
			pageNo = 0;

		Pageable page = new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "eventSn"));

		Page<TbEventInfo> pageStat = repoEvent.findAll(page);

		return pageStat.getContent();

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "alarm") // 해지 되지 않은 이벤트의 목록. 
	@ResponseBody
	public List<TbEventInfo> listAlarm(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		logger.debug("request : pageNo => " + pageNo);
		logger.debug("request : pageSize => " + pageSize);
		if (pageSize == null)
			pageSize = 10;
		if (pageNo == null)
			pageNo = 0;

		Pageable page = new PageRequest(pageNo, pageSize);

		Page<TbEventInfo> pageStat = repoEvent.findByConfirmYn(page, "N");

		return pageStat.getContent();

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "clearAlarm") // 해지 되지 않은 이벤트의 목록. 
	@ResponseBody
	public AgentResult clearAlarm(@RequestBody() String json) {

		logger.debug("request : " + json);

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); // 콤마 없어도.

			JsonNode root = mapper.readTree(json);
			
			TbEventInfo evt = mapper.treeToValue(root, TbEventInfo.class);
			
			repoEvent.delete(evt.getEventSn());
			wsAgent.pushClearEvent(evt.getEventSn());

			return AgentResult.success("LOG PUSH OK");
		} catch (Exception e) {
			logger.error("ERROR", e);
			return AgentResult.fail(-1, "Fail : " + e.getMessage());
		}		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "log")
	@ResponseBody
	public AgentResult createLog(@RequestBody() String json) {
		logger.debug("request : " + json);

		try {
			ObjectMapper mapper = new ObjectMapper();

			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); // 콤마 없어도.

			JsonNode root = mapper.readTree(json);
			MdlLog log = mapper.treeToValue(root, MdlLog.class);

			logger.debug("LOG : " + ToStringBuilder.reflectionToString(log, ToStringStyle.MULTI_LINE_STYLE));
			
			wsAgent.pushData(log);

			return AgentResult.success("LOG PUSH OK");

		} catch (JsonProcessingException e) {
			logger.error("Json", e);
			return AgentResult.fail(-1, "Fail : " + e.getMessage());
		} catch (IOException e) {
			logger.error("Json", e);
			return AgentResult.fail(-1, "Fail : " + e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "event")
	@ResponseBody
	public AgentResult createEvent(@RequestBody() String json) {
		if(ciaConfig.isEventLog()) logger.debug("request : " + json);

		try {
			ObjectMapper mapper = new ObjectMapper();

			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); // 콤마 없어도.

			JsonNode root = mapper.readTree(json);
			JsonNode header = root.path("header");
			Header h = mapper.treeToValue(header, Header.class);
			if(ciaConfig.isEventLog()) logger.debug("Header : " + ToStringBuilder.reflectionToString(h, ToStringStyle.MULTI_LINE_STYLE));
			JsonNode data = root.path("data");
			DataAgentEvent d = mapper.treeToValue(data, DataAgentEvent.class);

			if(ciaConfig.isEventLog()) logger.debug("Data : " + ToStringBuilder.reflectionToString(d, ToStringStyle.MULTI_LINE_STYLE));

			TbEventInfo event = new TbEventInfo();

			event.load(h);
			event.load(d);

			repoEvent.save(event);
			
			wsAgent.pushData(event);

			return AgentResult.success("eventSn : " + event.getEventSn());

		} catch (JsonProcessingException e) {
			logger.error("Json", e);
			return AgentResult.fail(-1, "Fail : " + e.getMessage());
		} catch (IOException e) {
			logger.error("Json", e);
			return AgentResult.fail(-1, "Fail : " + e.getMessage());
		}
	}

	
	
	@RequestMapping(method = RequestMethod.GET, value = "code")
	@ResponseBody
	public List<TbCode> listCode(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		logger.debug("request : pageNo => " + pageNo);
		logger.debug("request : pageSize => " + pageSize);
		if (pageSize == null)
			pageSize = 100;
		if (pageNo == null)
			pageNo = 0;

		Pageable page = new PageRequest(pageNo, pageSize);

		Page<TbCode> pageStat = repoCode.findAll(page);

		return pageStat.getContent();

	}
}
