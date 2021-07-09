package util;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

public class StrTest {

	@Test
	public void testStr() throws ParseException {
		
		String s = "01234567890123456";
		String d = "yyyymmddhhmmss";
		assertEquals(s.substring(0,1), "0");
		assertEquals(d.substring(0,8), "yyyymmdd");
		assertEquals(d.substring(8,10), "hh");
		assertEquals(d.substring(10,12), "mm");
		
		
		Date ds = DateUtils.parseDate("2019-01-01 00:00", "yyyy-MM-dd HH:mm");
		Date de = DateUtils.parseDate("2019-01-01 00:02", "yyyy-MM-dd HH:mm");
		assertEquals(ds.compareTo(de) < 0 , true);
	}
}
