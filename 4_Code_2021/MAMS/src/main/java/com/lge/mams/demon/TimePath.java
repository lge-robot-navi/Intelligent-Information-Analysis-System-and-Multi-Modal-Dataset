package com.lge.mams.demon;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.cxf.helpers.FileUtils;

/**
 * 현재 시간에서, yyyymmdd/mm 의 패스를 관리하며, 호출할 때마다. index를 증가시킨다. ==> 이미지 index로 사용할 수
 * 있도록 하기 위해서.
 * 
 * @author dulee
 *
 */
public class TimePath {

	Calendar now;
	String nowPath;

	Hashtable<Integer, Integer> hash = new Hashtable<Integer, Integer>();

	public String getnowpath(Calendar now, String area) {
		this.now = now;
		nowPath = String.format("%04d%02d%02d/%02d/%02d/%s/", now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1,
				now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), area);
		return nowPath;
	}

	public String getpath(String yyyymmddhhmm, String area) {
		String s = yyyymmddhhmm;
		String yyyymmdd = s.substring(0, 8);
		String hh = s.substring(8, 10);
		String mm = s.substring(10, 12);
		return String.format("%s/%s/%s/%s/", yyyymmdd, hh, mm, area);
	}

	public String getpath(Date d, String area) {
		return DateFormatUtils.format(d, "yyyyMMdd/HH/mm/") + area + "/";
	}

	public String initPath(String area) {
		now = Calendar.getInstance();
		return getnowpath(now, area);
	}

	public String getPath(int agentid, String area /* P or G */) {
		if (now == null) {
			hash.put(agentid, 0);
			return initPath(area);
		}
		Calendar n = Calendar.getInstance();
		if (n.get(Calendar.MINUTE) == now.get(Calendar.MINUTE))
			return nowPath;
		hash.put(agentid, 0);
		return getnowpath(n, area);
	}

	public String getFilename(int agentid, String ext) {
		if (!hash.containsKey(agentid)) {
			hash.put(agentid, 0);
		}
		int idx = hash.get(agentid) + 1;
		hash.put(agentid, idx);
		return String.format("agent_%02d_%04d.%s", agentid, idx, ext);
	}

	public void checkPath(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			FileUtils.mkDir(dir);
		}
	}

	public boolean exists(String path) {
		File dir = new File(path);
		if (dir.exists()) return true;
		return false;
	}
}
