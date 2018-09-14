package com.hydra.core.common.aspect;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.hydra.core.common.CONSMSG;
import com.hydra.core.common.Dict;
import com.hydra.core.common.annotation.CheckMap;
import com.hydra.core.pojo.ReadOlnyFactory;
import com.hydra.core.util.CommonUtil;
import com.hydra.core.util.MapUtil;
import com.hydra.core.util.StringUtil;

@Aspect
@Component
@Order(9)
public class CheckMapAspect {
	@Pointcut("@annotation(com.hydra.core.common.annotation.CheckMap)")
	public void annotationPointCut(){}
	
	@Around("annotationPointCut()")
	public Object process(ProceedingJoinPoint joinPoint) throws Exception{
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();
		CheckMap cm = method.getAnnotation(CheckMap.class);
		
		String mapId = cm.value();
		if(StringUtil.isEmpty(mapId)){
			mapId = method.getName();
		}
		String namespace = cm.namespace();
		if(!StringUtil.isEmpty(namespace)){
			mapId = namespace + Dict.POINT + mapId;
		}
		boolean filterFlag = cm.filterFlag();
		
		Object[] args = joinPoint.getArgs();
		Object o = args[0];
		if(!(o instanceof Map)){
			throw new UnsupportedOperationException("First param must be Map.");
		}
		Map<String, Object> map = CommonUtil.transform(o);
		Map<String, Object> mapInfo = PROCESSED_MAP_INFO.get(mapId);
		if(null == mapInfo){
			throw new Exception("");
		}
		Set<String> keySet = MapUtil.get(mapInfo, MAIN_KEY_SET);
		Iterator<String> it = keySet.iterator();
		while(it.hasNext()){
			if(StringUtil.isEmpty(map.get(it.next()))){
				throw new Exception("");
			}
		}
		
		if(filterFlag){
			Map<String, Object> tmp = new HashMap<>(map);
			keySet = MapUtil.get(mapInfo, KEY_SET);
			it = keySet.iterator();
			map.clear();
			while(it.hasNext()){
				String key = it.next();
				Object val = tmp.get(key);
				if(!StringUtil.isEmpty(val)){
					map.put(key, val);
				}
			}
		}
		
		Object ret = null;
		try {
			ret = joinPoint.proceed(args);
		} catch (Throwable e) {
			if(e instanceof Exception){
				throw (Exception)e;
			}else if(e instanceof RuntimeException){
				throw (RuntimeException)e;
			}else{
				throw new Exception(e.getMessage(), e);
			}
		}
		return ret;
	}
	
	private static Map<String, Object> processValue(String val) {
		Set<String> keySet = new HashSet<>();
		Set<String> mainKeySet = new HashSet<>();
		Set<String> optionalKeySet = new HashSet<>();
		
		Map<String, Object> map = MapUtil.getHashMap();
		Map<String, Object> tmp = MapUtil.getHashMap();
		String[] params = val.split(Dict.BROKEN_BAR);
		for(String param : params){
			String[] info = param.split(Dict.SEMICOLON);
			int len = info.length;
			String paramName = info[0];
			if(len < 2){
				throw new RuntimeException(paramName + " is a bad config.");
			}else{
				tmp.clear();
				String optionalFlag = info[1];
				tmp.put(PARAM_NAME, paramName);
				tmp.put(OPTIONAL_FLAG, optionalFlag);
				if(CONSMSG.VALUE_1.equals(optionalFlag)){
					optionalKeySet.add(paramName);
				}else{
					mainKeySet.add(paramName);
				}
				keySet.add(paramName);
				if(len > 2){
					tmp.put(TRANSFORM_TYPE, info[2]);
				}
				map.put(paramName, ReadOlnyFactory.getReadOnlyMap(tmp));
			}
		}
		map.put(KEY_SET, ReadOlnyFactory.getReadOnlySet(keySet));
		map.put(MAIN_KEY_SET, ReadOlnyFactory.getReadOnlySet(mainKeySet));
		map.put(OPTIONAL_KEY_SET, ReadOlnyFactory.getReadOnlySet(optionalKeySet));
		return ReadOlnyFactory.getReadOnlyMap(map);
	}
	
	private final static Properties MAP_INFO;
	private final static Logger logger = LoggerFactory.getLogger(CheckMap.class);
	static{
		try {
			MAP_INFO = new Properties();
			MAP_INFO.load(ClassLoader.getSystemResourceAsStream("msg/mapInfo.properties"));
		} catch (IOException e) {
			throw new ExceptionInInitializerError("File mapInfo.properties can't be found in the classpath.");
		}
		Map<String, Map<String, Object>> map = MapUtil.getHashMap();
		Set<String> keySet = MAP_INFO.stringPropertyNames();
		Iterator<String> it = keySet.iterator();
		while(it.hasNext()){
			String key = it.next();
			String val = MAP_INFO.getProperty(key);
			try {
				map.put(key, processValue(val));
			} catch (Throwable t) {
				logger.error("{}", t);
				throw new ExceptionInInitializerError(key + "." + t.getMessage());
			}
		}
		PROCESSED_MAP_INFO = ReadOlnyFactory.getReadOnlyMap(map);
	}
	private final static Map<String, Map<String, Object>> PROCESSED_MAP_INFO;
	
	public final static String KEY_SET = "keySet";
	public final static String MAIN_KEY_SET = "mainKeySet";
	public final static String OPTIONAL_FLAG = "optionalFlag";
	public final static String OPTIONAL_KEY_SET = "optionalKeySet";
	public final static String PARAM_NAME = "paramName";
	public final static String TRANSFORM_TYPE = "transformType";
}
