package com.hydra.blank.util;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
	private ConfigUtil(){throw new UnsupportedOperationException();}
	private static final Properties PRO = new Properties();
	
	public static String get(String key){
		return PRO.getProperty(key);
	}
	
	public static void load(String path) throws IOException{
		synchronized(PRO){
			PRO.clear();
			PRO.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
		}
	}
	
	public static void main(String[] args) {
		System.out.println(get("db.username"));
	}
	
	static{
		try {
			load("conf/dev.properties");
		} catch (IOException e) {
			throw new ExceptionInInitializerError(e.getMessage());
		}
	}
}
