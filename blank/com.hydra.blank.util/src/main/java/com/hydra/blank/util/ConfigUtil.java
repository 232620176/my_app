package com.hydra.blank.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigUtil {
	public static String get(String key){
		return PRO.getProperty(key);
	}
	
	public static void load(String path) {
		synchronized(PRO){
			PRO.clear();
			InputStream is = null;
			try {
				is = ClassLoader.getSystemResourceAsStream(path);
				PRO.load(is);
			} catch (Exception e) {
				logger.error("{}", e);
			}finally{
				try{if(null != is){is.close();}}catch(IOException e){logger.error("{}", e);}
			}
		}
	}
	
	public static void init(String param){
		if(PARAM_PROD.equals(param)){
			load(PROD_FILE_NAME);
		}else{
			load(DEV_FILE_NAME);
		}
	}
	
	public static void main(String[] args) {
		init("");
		logger.info(get("db.username"));
		init(PARAM_PROD);
		logger.info(get("db.username"));
	}
	
	private ConfigUtil(){throw new UnsupportedOperationException();}
	private static final Properties PRO = new Properties();
	private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
	public final static String DEV_FILE_NAME = "conf/dev.properties";
	public final static String PARAM_PROD = "prod";
	public final static String PROD_FILE_NAME = "conf/prod.properties";
}
