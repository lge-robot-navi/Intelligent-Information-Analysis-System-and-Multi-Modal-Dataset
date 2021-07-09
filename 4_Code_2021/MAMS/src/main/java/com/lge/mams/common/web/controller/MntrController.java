package com.lge.mams.common.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lge.mams.api.web.entity.MileageEntity;
import com.lge.mams.api.web.entity.MileageRouterEntity;
import com.lge.mams.api.web.service.MileageService;
import com.lge.mams.config.LgicConfig;
import com.lge.mams.demon.TimePath;
import com.lge.mams.jpa.impl.MileageInfoRepository;
import com.lge.mams.jpa.impl.TbEventInfoRepository;
import com.lge.mams.jpa.model.TbEventInfo;
import com.lge.mams.util.DateUtil;

@Controller
@RequestMapping("mntr")
public class MntrController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LgicConfig config;

	@Autowired
	Environment env;

	@Autowired
	TbEventInfoRepository repoEvent;

	// 마일리지 테스트
	@Autowired
	MileageInfoRepository repoMileage;

	// 마일리지 
	@Autowired
	MileageService service;
	

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(method = RequestMethod.GET, value = "map")
	public String map() {
		return "mntr/mntr-map";
	}

	@RequestMapping(method = RequestMethod.GET, value = "scheduling")
	public String scheduling() {
		return "mntr/scheduling";
	}

	@RequestMapping(method = RequestMethod.GET, value = "evtinfo/{eventSn}")
	@ResponseBody
	public List<String> evtinfo(@PathVariable Long eventSn) {
		/**
		 * 이미지 저장 규칙. path = String.format("%sG/%d/%d/", config.getEventImageDir(),
		 * agentid, statSn); path = String.format("%sP/%d/%d/",
		 * config.getEventImageDir(), agentid, statSn);
		 * 
		 * String filename = path + String.format("evt-img%04d.jpg", idx);
		 */
		// 1. event 를 조회해서, event 정보를 가져올 것.
		// 2. 이벤트 정보를 이용해서, 폴더를 찾을 것.
		// 3. 폴더에서 파일명을 스캔하여 파일 목록을 리턴할 것.
		logger.debug("event info : eventSn={}", eventSn);

		TbEventInfo evt = repoEvent.findOne(eventSn);
		String path = String.format("%s%s/%d/%d/", config.getEventImageDir(), evt.getAreaCode(), evt.getRobotId(),
				eventSn);
		logger.debug("path : {}", path);

		List<String> list = new ArrayList<String>();

		File dir = new File(path);
		File[] flist = dir.listFiles();
		if (flist != null) {
			for (File f : flist) {
				String fname = String.format("%s/%d/%d/%s", evt.getAreaCode(), evt.getRobotId(), eventSn, f.getName());
				list.add(fname);
				logger.debug("FILE:{}  => {}", f.getPath(), fname);
			}
		} else {
			logger.debug("flist is null ..");
		}

		Collections.sort(list);
		return list;
	}

	@RequestMapping(value = "evtimage", method = RequestMethod.GET)
	public void getEvtImageAsByteArray(@RequestParam(value = "filePath", required = false) String filePath,
			HttpServletResponse response) throws IOException {
		File initialFile;
		String ext = filePath.substring(filePath.lastIndexOf(".") + 1);

		initialFile = new File(config.getEventImageDir() + filePath);

		InputStream in = new FileInputStream(initialFile);
		if ("png".equalsIgnoreCase(ext)) {
			response.setContentType(MediaType.IMAGE_PNG_VALUE);
		} else if ("jpg".equalsIgnoreCase(ext) || "jpeg".equalsIgnoreCase(ext)) {
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		} else {
			logger.error("unknown ext : {}", ext);
		}
		IOUtils.copy(in, response.getOutputStream());
		in.close();
	}

	TimePath pathMgr = new TimePath();

	/**
	 * 이미지 정보.
	 * 
	 * @param map : playmin = YYYYMMDDhhmm 의 시간정보. area : 지역정보. G,P, robotid : 로봇ID
	 * @return
	 */
	@RequestMapping(value = "mntrimageinfo", method = RequestMethod.POST)
	@ResponseBody
	List<String> getMntrImageInfo(@RequestBody Map<String, String> map) {
		logger.debug("mntriamgeinfo : {}", map); //
		String playmin = map.get("playmin"); // yyyymmddhhmm
		String area = map.get("area");
		Integer robotid = Integer.parseInt(map.get("robotid"));

		String path = config.getMntrImageDir() + pathMgr.getpath(playmin, area);
		String newfile = path + "index_" + robotid + ".txt";

		List<String> list = new ArrayList<String>();

		readLines(newfile, list);

		return list;
	}
	
    /**
     * 두 지점간의 거리 계산
     *
     * @param lat1 지점 1 위도
     * @param lon1 지점 1 경도
     * @param lat2 지점 2 위도
     * @param lon2 지점 2 경도
     * @param unit 거리 표출단위
     * @return
     */
    private static double distance(MileageEntity list, double lat1, double lon1, double lat2, double lon2) {         
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
         
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;			// kilometer 거리 단위 변환
        // dist = dist * 1609.344;		// Meter 거리 단위 변환
        
        int distChk = Integer.parseInt(String.valueOf(Math.round(dist)));
        
        // 1분 동안 로봇이 이동한 거리를 체크하며, 1 Km 이상으로 거리 계산된 값은 0 으로 초기화 후, return
        if (distChk > 1) {
        	dist = 0.0;
        	System.out.println("로봇" + list.getRobotId() + " 마일리지 데이터값 정합성 체크 예외 처리 ===> " + list.getStatDt() + ", " + dist + ", " + distChk);
        }
 
        return dist;
    }
 
    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
     
    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
    
    private static List<Double> mileageChartCheck(List<MileageEntity> lists) {
		double dist = 0.0;
		
		double r1LatOld = 0.0, r2LatOld = 0.0, r3LatOld = 0.0, r4LatOld = 0.0;
		double r5LatOld = 0.0, r6LatOld = 0.0, r7LatOld = 0.0, r8LatOld = 0.0, r9LatOld = 0.0;
		
		double r1LonOld = 0.0, r2LonOld = 0.0, r3LonOld = 0.0, r4LonOld = 0.0;
		double r5LonOld = 0.0, r6LonOld = 0.0, r7LonOld = 0.0, r8LonOld = 0.0, r9LonOld = 0.0;
		
		double r1Sum = 0.0, r2Sum = 0.0, r3Sum = 0.0, r4Sum = 0.0;
		double r5Sum = 0.0, r6Sum = 0.0, r7Sum = 0.0, r8Sum = 0.0, r9Sum = 0.0;
		
		for (MileageEntity list : lists) {
			if (list.getRobotId() == 1) {
				if (r1LatOld != list.getLat() && r1LonOld != list.getLon()) {
					dist = distance(list, r1LatOld, r1LonOld, list.getLat(), list.getLon());
						if (dist != 0.0) r1Sum += dist;
				}
				r1LatOld = list.getLat();
				r1LonOld = list.getLon();
			}
			if (list.getRobotId() == 2) {
				if (r2LatOld != list.getLat() && r2LonOld != list.getLon()) {
					dist = distance(list, r2LatOld, r2LonOld, list.getLat(), list.getLon());
					if (dist != 0.0) r2Sum += dist;
				}
				r2LatOld = list.getLat();
				r2LonOld = list.getLon();
			}
			if (list.getRobotId() == 3) {
				if (r3LatOld != list.getLat() && r3LonOld != list.getLon()) {
					dist = distance(list, r3LatOld, r3LonOld, list.getLat(), list.getLon());
					if (dist != 0.0) r3Sum += dist;
				}
				r3LatOld = list.getLat();
				r3LonOld = list.getLon();
			}
			if (list.getRobotId() == 4) {
				if (r4LatOld != list.getLat() && r4LonOld != list.getLon()) {
					dist = distance(list, r4LatOld, r4LonOld, list.getLat(), list.getLon());
					if (dist != 0.0) r4Sum += dist;
				}
				r4LatOld = list.getLat();
				r4LonOld = list.getLon();
			}
			if (list.getRobotId() == 5) {
				if (r5LatOld != list.getLat() && r5LonOld != list.getLon()) {
					dist = distance(list, r5LatOld, r5LonOld, list.getLat(), list.getLon());
					if (dist != 0.0) r5Sum += dist;
				}
				r5LatOld = list.getLat();
				r5LonOld = list.getLon();
			}
			if (list.getRobotId() == 6) {
				if (r6LatOld != list.getLat() && r6LonOld != list.getLon()) {
					dist = distance(list, r6LatOld, r6LonOld, list.getLat(), list.getLon());
					if (dist != 0.0) r6Sum += dist;
				}
				r6LatOld = list.getLat();
				r6LonOld = list.getLon();
			}
			if (list.getRobotId() == 7) {
				if (r7LatOld != list.getLat() && r7LonOld != list.getLon()) {
					dist = distance(list, r7LatOld, r7LonOld, list.getLat(), list.getLon());
					if (dist != 0.0) r7Sum += dist;
				}
				r7LatOld = list.getLat();
				r7LonOld = list.getLon();
			}
			if (list.getRobotId() == 8) {
				if (r8LatOld != list.getLat() && r8LonOld != list.getLon()) {
					dist = distance(list, r8LatOld, r8LonOld, list.getLat(), list.getLon());
					if (dist != 0.0) r8Sum += dist;
				}
				r8LatOld = list.getLat();
				r8LonOld = list.getLon();
			}
			if (list.getRobotId() == 9) {
				if (r9LatOld != list.getLat() && r9LonOld != list.getLon()) {
					dist = distance(list, r9LatOld, r9LonOld, list.getLat(), list.getLon());
					if (dist != 0.0) r9Sum += dist;
				}
				r9LatOld = list.getLat();
				r9LonOld = list.getLon();
			}
		}
    
		List<Double> result = new ArrayList<Double>();
		
		System.out.println("로봇1 : " + (double)Math.round(r1Sum*1000)/1000 + 
						 ", 로봇2 : " + (double)Math.round(r2Sum*1000)/1000 + 
						 ", 로봇3 : " + (double)Math.round(r3Sum*1000)/1000 + 
						 ", 로봇4 : " + (double)Math.round(r4Sum*1000)/1000 + 
						 ", 로봇5 : " + (double)Math.round(r5Sum*1000)/1000 + 
						 ", 로봇6 : " + (double)Math.round(r6Sum*1000)/1000 + 
						 ", 로봇7 : " + (double)Math.round(r7Sum*1000)/1000 + 
						 ", 로봇8 : " + (double)Math.round(r8Sum*1000)/1000 + 
						 ", 로봇9 : " + (double)Math.round(r9Sum*1000)/1000);
		
		result.add((double)Math.round(r1Sum*1000)/1000);
		result.add((double)Math.round(r2Sum*1000)/1000);
		result.add((double)Math.round(r3Sum*1000)/1000);
		result.add((double)Math.round(r4Sum*1000)/1000);
		result.add((double)Math.round(r5Sum*1000)/1000);
		result.add((double)Math.round(r6Sum*1000)/1000);
		result.add((double)Math.round(r7Sum*1000)/1000);
		result.add((double)Math.round(r8Sum*1000)/1000);
		result.add((double)Math.round(r9Sum*1000)/1000);
			
    	return result;
    }
    
    private static List<MileageEntity> mileageRouterCheck(List<MileageEntity> lists) {
		double dist = 0.0;
		
		double r1LatOld = 0.0, r2LatOld = 0.0, r3LatOld = 0.0, r4LatOld = 0.0;
		double r5LatOld = 0.0, r6LatOld = 0.0, r7LatOld = 0.0, r8LatOld = 0.0, r9LatOld = 0.0;
		
		double r1LonOld = 0.0, r2LonOld = 0.0, r3LonOld = 0.0, r4LonOld = 0.0;
		double r5LonOld = 0.0, r6LonOld = 0.0, r7LonOld = 0.0, r8LonOld = 0.0, r9LonOld = 0.0;
		
		double r1Sum = 0.0, r2Sum = 0.0, r3Sum = 0.0, r4Sum = 0.0;
		double r5Sum = 0.0, r6Sum = 0.0, r7Sum = 0.0, r8Sum = 0.0, r9Sum = 0.0;
		
		List<MileageEntity> result = new ArrayList<MileageEntity>();
		
		for (MileageEntity list : lists) {
			if (list.getRobotId() == 1) {
				if (r1LatOld != list.getLat() && r1LonOld != list.getLon()) {
					dist = distance(list, r1LatOld, r1LonOld, list.getLat(), list.getLon());
				}
				r1LatOld = list.getLat();
				r1LonOld = list.getLon();
			}
			if (list.getRobotId() == 2) {
				if (r2LatOld != list.getLat() && r2LonOld != list.getLon()) {
					dist = distance(list, r2LatOld, r2LonOld, list.getLat(), list.getLon());
				}
				r2LatOld = list.getLat();
				r2LonOld = list.getLon();
			}
			if (list.getRobotId() == 3) {
				if (r3LatOld != list.getLat() && r3LonOld != list.getLon()) {
					dist = distance(list, r3LatOld, r3LonOld, list.getLat(), list.getLon());
				}
				r3LatOld = list.getLat();
				r3LonOld = list.getLon();
			}
			if (list.getRobotId() == 4) {
				if (r4LatOld != list.getLat() && r4LonOld != list.getLon()) {
					dist = distance(list, r4LatOld, r4LonOld, list.getLat(), list.getLon());
				}
				r4LatOld = list.getLat();
				r4LonOld = list.getLon();
			}
			if (list.getRobotId() == 5) {
				if (r5LatOld != list.getLat() && r5LonOld != list.getLon()) {
					dist = distance(list, r5LatOld, r5LonOld, list.getLat(), list.getLon());
				}
				r5LatOld = list.getLat();
				r5LonOld = list.getLon();
			}
			if (list.getRobotId() == 6) {
				if (r6LatOld != list.getLat() && r6LonOld != list.getLon()) {
					dist = distance(list, r6LatOld, r6LonOld, list.getLat(), list.getLon());
				}
				r6LatOld = list.getLat();
				r6LonOld = list.getLon();
			}
			if (list.getRobotId() == 7) {
				if (r7LatOld != list.getLat() && r7LonOld != list.getLon()) {
					dist = distance(list, r7LatOld, r7LonOld, list.getLat(), list.getLon());
				}
				r7LatOld = list.getLat();
				r7LonOld = list.getLon();
			}
			if (list.getRobotId() == 8) {
				if (r8LatOld != list.getLat() && r8LonOld != list.getLon()) {
					dist = distance(list, r8LatOld, r8LonOld, list.getLat(), list.getLon());
				}
				r8LatOld = list.getLat();
				r8LonOld = list.getLon();
			}
			if (list.getRobotId() == 9) {
				if (r9LatOld != list.getLat() && r9LonOld != list.getLon()) {
					dist = distance(list, r9LatOld, r9LonOld, list.getLat(), list.getLon());
				}
				r9LatOld = list.getLat();
				r9LonOld = list.getLon();
			}
			if (dist != 0.0) result.add(list);
		}
    	
    	return result;
    }
	
    @RequestMapping("mileagerouter")
    public @ResponseBody List<MileageRouterEntity> getMileageRouter(
    		@RequestParam(value = "id") int id,
    		@RequestParam(value = "start") String start,
    		@RequestParam(value = "end") String end
    		) throws ParseException {
    	System.out.println("getMileageRouter id : " + id + ", start : " + start + ", end : " + end);
    	List<MileageRouterEntity> results = new ArrayList<MileageRouterEntity>();  
    	MileageEntity entity = new MileageEntity();
    	entity.setRobotId(id);
    	entity.setStartDate(start);
    	entity.setEndDate(end);
    	List<MileageEntity> lists = service.getRouterInfo(entity);
    	//lists = mileageRouterCheck(lists);
        for (MileageEntity list : lists) {
          results.add(new MileageRouterEntity(list.getLat(), list.getLon()));
        }

    	return results;
    }
    
	@RequestMapping("mileageinfo")
	public @ResponseBody ArrayList<Double> getMileageInfo(
			@RequestParam(value = "start") String start, @RequestParam(value = "end") String end)
			throws ParseException {
		ArrayList<Double> list = new ArrayList<Double>();
		logger.info("getMileageInfo date : {} {}", start, end);
		
		MileageEntity entity = new MileageEntity();
		entity.setStartDate(start);
		entity.setEndDate(end);
		List<MileageEntity> mlists = service.getChartInfo(entity);
		
		/* TODO: 마일리지 계산 성능 최적화 작업중...
		List<Integer> agentTemp = new ArrayList<Integer>();
		List<Integer> agents = new ArrayList<Integer>();
		
		for (MileageEntity item : mlists) agentTemp.add(item.getRobotId());
		for (Integer agent : agentTemp) {
			if (!agents.contains(agent)) agents.add(agent);
		}
		
		Collections.sort(agents);
		*/
		
		/* 거리 단위 환산 계산식 참고
        // 마일(Mile) 단위
        double distanceMile =
            distance(37.504198, 127.047967, 37.501025, 127.037701, "");
        System.out.println("Mile: " + distanceMile);
         
        // 미터(Meter) 단위
        double distanceMeter =
            distance(37.504198, 127.047967, 37.501025, 127.037701, "meter");
        System.out.println("Meter: " + distanceMeter);
        */
        // 킬로미터(Kilo Meter) 단위
		/*
        double distanceKiloMeter =
            distance(37.504198, 127.047967, 37.501025, 127.037701, "kilometer");
        System.out.println("KiloMeter: " + distanceKiloMeter);
        */
		
		List<Double> arr = mileageChartCheck(mlists);
		
		for (Double item : arr) {
			list.add(item);
		}
		return list;
	}

	void readLines(String file, List<String> list) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				list.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			logger.error("E", e);
		}
	}

	@RequestMapping(value = "mntrimage", method = RequestMethod.GET)
	public void getMntrImageAsByteArray(@RequestParam(value = "timestr") String timestr,
			@RequestParam(value = "filename") String filename, @RequestParam(value = "area") String area,
			HttpServletResponse response) throws IOException {
		File initialFile;
		String path = pathMgr.getpath(timestr, area);
		String ext = filename.substring(filename.lastIndexOf(".") + 1);

		initialFile = new File(config.getMntrImageDir() + path + filename);

		InputStream in = new FileInputStream(initialFile);
		if ("png".equalsIgnoreCase(ext)) {
			response.setContentType(MediaType.IMAGE_PNG_VALUE);
		} else if ("jpg".equalsIgnoreCase(ext) || "jpeg".equalsIgnoreCase(ext)) {
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		} else {
			logger.error("unknown ext : {}", ext);
		}
		IOUtils.copy(in, response.getOutputStream());
		in.close();
	}

	@RequestMapping(value = "noimage", method = RequestMethod.GET)
	public void getMntrNoImageAsByteArray(HttpServletResponse response) throws IOException {

		// InputStream is =
		// servletContext.getResourceAsStream("/resources/images/no_image.jpg");
		InputStream is = servletContext.getResourceAsStream("/resources/images/no_signal.jpg");
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		IOUtils.copy(is, response.getOutputStream());
		is.close();

	}

	String getstartdt(String path) throws IOException, ParseException {
		File dir = new File(path);
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".txt");
			}
		});
		Date min = null;
		if (files == null)
			return null;
		for (File file : files) {
			// DateUtils.parseDate(min, "yyyyMMddHHmmss.SSS");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			if (line != null && line.length() >= 18) {
				line = line.substring(0, 18);
			}
			Date d = DateUtils.parseDate(line, "yyyyMMddHHmmss.SSS");
			if (min == null)
				min = d;
			if (min.compareTo(d) > 0) {
				min = d;
			}
			br.close();
		}
		if (min == null)
			return null;

		return DateFormatUtils.format(min, "yyyy-MM-dd HH:mm:ss SSS");
	}

	@RequestMapping(value = "getstartend", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getStartEnd(@RequestParam(value = "start") String start,
			@RequestParam(value = "end") String end, @RequestParam(value = "area") String area)
			throws ParseException, IOException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		logger.debug("start:{}, end:{}", start, end);
		Date ds = DateUtils.parseDate(start, "yyyy-MM-dd HH:mm");
		Date de = DateUtils.parseDate(end, "yyyy-MM-dd HH:mm");

		Date dnow = ds;
		logger.debug("START dstart:{}, dend:{}, compare:{}", ds, de, dnow.compareTo(de));
		boolean first = true;
		Date dexist = null;
		while (dnow.compareTo(de) <= 0) {
			//
			String fullpath = config.getMntrImageDir() + pathMgr.getpath(dnow, area);
			// logger.debug("path:{}", fullpath);
			if (pathMgr.exists(fullpath)) {
				dexist = dnow;
				if (first) {
					String s = getstartdt(fullpath);
					if (s != null) {
						map.put("start", s);
						first = false;
					}
				}
				// list.add(DateFormatUtils.format(dnow, "yyyy-MM-dd HH:mm"));
				logger.debug("LIST:{}", fullpath);
			}
			dnow = DateUtils.addMinutes(dnow, 1);
		}
		if (dexist != null) {
			// map.put("end", );
			dexist = DateUtils.addMinutes(dexist, 1);
			map.put("end", DateFormatUtils.format(dexist, "yyyy-MM-dd HH:mm:ss SSS"));
		}
		logger.debug("END");
		return map;
	}

	@RequestMapping(value = "listtimes", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getListTimes(@RequestParam(value = "start") String start,
			@RequestParam(value = "end") String end, @RequestParam(value = "area") String area) throws ParseException {
		List<String> list = new ArrayList<String>();
		logger.debug("start:{}, end:{}", start, end);
		Date ds = DateUtils.parseDate(start, "yyyy-MM-dd HH:mm");
		Date de = DateUtils.parseDate(end, "yyyy-MM-dd HH:mm");

		Date dnow = ds;
		logger.debug("START dstart:{}, dend:{}, compare:{}", ds, de, dnow.compareTo(de));
		while (dnow.compareTo(de) <= 0) {
			//
			String fullpath = config.getMntrImageDir() + pathMgr.getpath(dnow, area);
			// logger.debug("path:{}", fullpath);
			if (pathMgr.exists(fullpath)) {
				list.add(DateFormatUtils.format(dnow, "yyyy-MM-dd HH:mm"));
				logger.debug("LIST:{}", fullpath);
			}
			dnow = DateUtils.addMinutes(dnow, 1);
		}
		logger.debug("END");
		return list;
	}
}
