package com.hydra.core.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
	private MapUtil(){throw new UnsupportedOperationException();}
	
	public static <T, K, V> T get(Map<K, V> map, K key){
		return CommonUtil.transform(map.get(key));
	}
	
	public static <K, V> Map<K, V> getMap(){
		return new HashMap<>(16);
	}
	
	public static <K, V> Map<K, V> getMap(int init){
		return new HashMap<>(init);
	}
}
