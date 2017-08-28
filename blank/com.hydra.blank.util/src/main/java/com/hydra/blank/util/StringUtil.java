package com.hydra.blank.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	private StringUtil(){throw new UnsupportedOperationException();}
	private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	public static boolean isEmpty(Object target){
		return null == target || "".equals(target);
	}
	
	public static void main(String[] args) {
		logger.info("Hello World!");
	}
}
