package com.hzins.dbsync;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleLogger {

	public static void info(String msg) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("[" + sdf.format(new Date()) + "][" + Thread.currentThread().getName() + "] - " + msg);
	}

	public static void debug(String msg) {
//		info(msg);
	}

	public static void error(String msg) {
		info(msg);
	}
	
	public static void warn(String msg){
		info(msg);
	}
}
