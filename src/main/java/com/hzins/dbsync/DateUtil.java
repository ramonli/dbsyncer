package com.hzins.dbsync;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final String FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DATE = "yyyy-MM-dd";

	public static String format(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIMESTAMP);
		return sdf.format(date);
	}

//	public static String format(Date date, String pattern) {
//	    	if(date == null) return "";
//		return FastDateFormat.getInstance(pattern).format(date);
//	}
//
//	public static Date parse(String date) {
//		try {
//		    if(StringUtils.isNotBlank(date))
//			return DEFAULT_DATE_FORMAT.parse(date);
//		    else
//			return null;
//		} catch (ParseException e) {
//			throw new IllegalArgumentException(e);
//		}
//	}
//
//	public static Date parse(String date, String pattern) {
//		try {
//			return FastDateFormat.getInstance(pattern).parse(date);
//		} catch (ParseException e) {
//			throw new IllegalArgumentException(e);
//		}
//	}
}