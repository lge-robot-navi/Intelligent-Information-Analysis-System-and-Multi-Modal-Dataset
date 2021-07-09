package com.lge.mams.common.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lge.mams.config.LgicConfig;

@Controller
@RequestMapping("mntr/voice")
public class VoiceController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LgicConfig config;

	@Autowired
	Environment env;

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<Resource> download(String path) throws IOException {

		logger.debug("file path : {}", config.getVoiceDir() + path);
		File file = new File(config.getVoiceDir() + path);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
	}
}
