package com.lge.mams.api.web;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lge.mams.jpa.impl.TaCodeInfoRepository;
import com.lge.mams.jpa.model.TaCodeInfo;
import com.lge.mams.util.StrUtil;

@Controller
@RequestMapping("api/drone")
public class ApiDroneUrlController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	TaCodeInfoRepository repoCodeInfo;

	@RequestMapping(value = "urls", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> geturls() {
		logger.debug("settings... ");

		Map<String, String> map = new LinkedHashMap<String, String>();

		List<TaCodeInfo> list = repoCodeInfo.findByCdgrpCdAndUseYn("TA012", "Y");

		for (TaCodeInfo cd : list) {
			if (StrUtil.isEqual("001", cd.getCodeCd())) {
				// 포항
				map.put("dronUrlPohang", cd.getCodeDs());
			}
			if (StrUtil.isEqual("002", cd.getCodeCd())) {
				// 광주.
				map.put("dronUrlGwangju", cd.getCodeDs());
			}
		}

		return map;
	}

	@RequestMapping(value = "urls", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> posturls(@RequestBody LinkedHashMap<String, String> settings) {
		logger.debug("settings...{} ", settings);

		List<TaCodeInfo> list = repoCodeInfo.findByCdgrpCdAndUseYn("TA012", "Y");

		for (TaCodeInfo cd : list) {
			if (StrUtil.isEqual("001", cd.getCodeCd())) {
				// 포항
				String url = settings.get("dronUrlPohang");

				if (!StrUtil.isEmpty(url)) {
					cd.setCodeDs(url);
					repoCodeInfo.save(cd);
				}
			}
			if (StrUtil.isEqual("002", cd.getCodeCd())) {
				// 광주.
				String url = settings.get("dronUrlGwangju");

				if (!StrUtil.isEmpty(url)) {
					cd.setCodeDs(url);
					repoCodeInfo.save(cd);
				}
			}
		}

		HashMap<String, String> rlt = new HashMap<String, String>();
		rlt.put("resultCode", "OK");
		rlt.put("resultMsg", "성공하였습니다");

		return rlt;
	}
}
