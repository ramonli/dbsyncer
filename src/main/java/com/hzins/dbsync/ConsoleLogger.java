package com.hzins.dbsync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleLogger {
	private static Logger logger = LoggerFactory.getLogger(ConsoleLogger.class);

	public static void info(String msg) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println("[" + sdf.format(new Date()) + "][" + Thread.currentThread().getName() + "] - " + msg);
		logger.info(msg);
	}

	public static void debug(String msg) {
		logger.debug(msg);
	}

	public static void error(String msg) {
		logger.error(msg);
	}
	
	public static void error(String msg, Throwable t) {
		logger.error(msg, t);
	}
	
	public static void warn(String msg){
		logger.warn(msg);
	}
}
