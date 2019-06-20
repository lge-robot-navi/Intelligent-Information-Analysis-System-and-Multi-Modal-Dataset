package com.lge.mams.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lge.mams.agentif.model.DataAgentEvent;
import com.lge.mams.agentif.model.DataAgentStat;
import com.lge.mams.agentif.model.Header;
import com.lge.mams.agentif.udperver.SeqImageProvider;
import com.lge.mams.agentif.udperver.SeqImg;
import com.lge.mams.common.util.CiaConfig;
import com.lge.mams.jpa.model.TbAgentStat;

import tv.twelvetone.json.JsonValue;
import tv.twelvetone.rjson.RJsonParserFactory;

@Controller
@RequestMapping("test")
public class TestController {

	private String getJsonStr(String res) throws FileNotFoundException, IOException {
		File file = ResourceUtils.getFile("classpath:" + res);

		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

		String str = new String(data, "UTF-8");

		return str;
	}

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired 
	private CiaConfig ciaConfig;
	
	@Autowired
	private Environment env;
	
	@Autowired private ServletContext servletContext;
	
	


	@Autowired
	private SeqImageProvider imageProvider;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "config")
	@ResponseBody public Object config(@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "val", required = false) Boolean val) {
		if( "udp".equals(type)) {
			if( val == true) {
				ciaConfig.setUdpLog(true);
			}else {
				ciaConfig.setUdpLog(false);
			}
		}else if( "event".equals(type)) {
			if( val == true) {
				ciaConfig.setEventLog(true);
			}else {
				ciaConfig.setEventLog(false);
			}
		}else if( "stat".equals(type)) {
			if( val == true) {
				ciaConfig.setStatLog(true);
			}else {
				ciaConfig.setStatLog(false);
			}
		}
		return ciaConfig;
	}
	

	@RequestMapping(method = RequestMethod.GET, value = "ws")
	public String ws() {
		return "test-ws";
	}

	@RequestMapping(method = RequestMethod.GET, value = "player")
	public String player() {
		return "test-player";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "player2")
	public String player2() {
		return "test-player2";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "player3")
	public String player3() {
		return "test-player3";
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "map")
	public String map() {
		return "test-map";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "json")
	public String json() {
		// http://www.relaxedjson.org/
		// {lat:36.091418, lon:129.331982, fixed:Y, markerImage:cctv.png ,agentId:101}
		String s = "{lat:36.091418, lon:129.331982, fixed:Y, markerImage:cctv.png ,agentId:101}";
		JsonValue parsed = new RJsonParserFactory().createParser().stringToValue(s);
		
		logger.debug("ORG  :{}", s);
		logger.debug("RJSON:{}", parsed.toString());
		
		
		return "test-json";
	}

	@RequestMapping(value = "/image", method = RequestMethod.GET)
	public void getImageAsByteArray(@RequestParam(value = "agentId", required = false) String agentId,
			@RequestParam(value = "imageNo", required = false) String imageNo,
			HttpServletResponse response) throws IOException {
		File initialFile ;
		if(env.getProperty("DEV_TYPE")!= null && env.getProperty("DEV_TYPE").equals("PC")) {
			initialFile = new File("C:/test/agent/" + agentId + "_" + imageNo + ".jpg");
		}else {
			initialFile = new File("/var/lib/tomcat8/images/agent/" + agentId + "_" + imageNo + ".jpg");
		}
		InputStream in = new FileInputStream(initialFile);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		IOUtils.copy(in, response.getOutputStream());
	}
	
	@RequestMapping(value = "/realtimeimage", method = RequestMethod.GET)
	public void getRealtimeImageAsByteArray(
			@RequestParam(value = "areaCode", required = false) String areaCode,
			@RequestParam(value = "agentId", required = false) Integer agentId,
			@RequestParam(value = "imageNo", required = false) String imageNo,
			HttpServletResponse response) throws IOException {
		
		
		//logger.debug("areaCode:{}, agentId:{}, imageNo:{}", areaCode,agentId, imageNo );
		
		SeqImg seqImg = imageProvider.getSeqImg(areaCode, agentId==null?-1:agentId);
		if( seqImg == null ) {
			InputStream is = servletContext.getResourceAsStream("/resources/images/no_image.jpg");
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			IOUtils.copy(is, response.getOutputStream());
			return;
		}

		InputStream in = new ByteArrayInputStream(seqImg.getData());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		IOUtils.copy(in, response.getOutputStream());
	}
	
	

	@RequestMapping(method = RequestMethod.GET, value = "a")
	public String login(@ModelAttribute("stat") @Valid TbAgentStat m, BindingResult rlt, Model model)
			throws FileNotFoundException, IOException {

		if (rlt.hasErrors()) {
			logger.error("ERROR...." + rlt.toString());
		}

		logger.debug("==================================================");

		logger.debug("statdt---:");
		logger.debug("statdt:" + m.getStatSn());
		logger.debug("statdt:" + m.getStatDt());

		String s = getJsonStr("data/agent-event-json.txt");
		logger.debug("" + s);
		parseAgentEvent(s);

		s = getJsonStr("data/agent-stat-json.txt");
		logger.debug("" + s);
		parseAgentStat(s);

		return "test";
	}

	private void parseAgentEvent(String s) {
		// ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); // 콤마 없어도.
			mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
			JsonNode root = mapper.readTree(s);
			JsonNode header = root.path("header");
			Header h = mapper.treeToValue(header, Header.class);
			logger.debug("Header : " + ToStringBuilder.reflectionToString(h, ToStringStyle.MULTI_LINE_STYLE));
			JsonNode data = root.path("data");
			DataAgentEvent d = mapper.treeToValue(data, DataAgentEvent.class);

			logger.debug("Data : " + ToStringBuilder.reflectionToString(d, ToStringStyle.MULTI_LINE_STYLE));
		} catch (JsonProcessingException e) {
			logger.error("Json", e);
		} catch (IOException e) {
			logger.error("Json", e);
		}
	}

	private void parseAgentStat(String s) {
		// ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); // 콤마 없어도.
			JsonNode root = mapper.readTree(s);
			JsonNode header = root.path("header");
			Header h = mapper.treeToValue(header, Header.class);
			logger.debug("Header : " + ToStringBuilder.reflectionToString(h, ToStringStyle.MULTI_LINE_STYLE));
			JsonNode data = root.path("data");
			DataAgentStat d = mapper.treeToValue(data, DataAgentStat.class);

			logger.debug("Data : " + ToStringBuilder.reflectionToString(d, ToStringStyle.MULTI_LINE_STYLE));
		} catch (JsonProcessingException e) {
			logger.error("Json", e);
		} catch (IOException e) {
			logger.error("Json", e);
		}
	}

}
