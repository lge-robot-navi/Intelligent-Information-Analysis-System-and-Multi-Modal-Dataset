package com.lge.crawling.admin.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Mail Template Util
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Component
public class MailTemplateUtil {

	public static final String DEFAULT_ENCODING = "UTF-8";
	private static final String DEFAULT_TEMPLATE_LOCATION = "velocity/mail";

	/** Velocity */
	@Autowired
	private VelocityEngine velocityEngine;

	/**
	 * Certification Tmplate
	 * @method certificationTmpl
	 * @param certificationKey
	 * @return
	 */
	public String certificationTmpl(String certificationKey, Map downloadLinkUrl) {

		String tmplNm = "certification.vm";

		Map<String, Object> model = new HashMap<>();
		model.put("certificationKey", certificationKey);
		model.putAll(downloadLinkUrl);

		return mergeTemplateIntoString(tmplNm, model);
	}

	/**
	 * Merge Template Into String
	 * @method mergeTemplateIntoString
	 * @param tmplNm
	 * @param model
	 * @return
	 */
	private String mergeTemplateIntoString(String tmplNm, Map<String, Object> model) {

		String templateLocation = String.format("%s/%s",
				DEFAULT_TEMPLATE_LOCATION, tmplNm);

		return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
				templateLocation, DEFAULT_ENCODING, model);
	}
}
