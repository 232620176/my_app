package com.hydra.util;

import java.util.Map;

import com.lakala.amber.util.Base64;
/**
 * @description 日期公共处理类
 * @author wangshuang@lakala.com
 * @date 2013年12月30日
 */
public final class MapUtil {
	private MapUtil(){}
	
	/**
	* 将fromMap键fromName对应的值以toName为键值放到toMap中，值为null不做处理
	* @param fromName
	* @param fromMap
	* @param toName
	* @param toMap
	* @returnType void
	* @author: Hydra wangshuang@lakala.com
	* @date: 2017年1月10日 上午9:42:00
	 */
	public static<K, V> void processParam(K fromName, Map<K, V> fromMap, K toName, Map<K, V> toMap){
		V tmp = fromMap.get(fromName);
		if(null != tmp){
			toMap.put(toName, tmp);
		}
	}
	
	/**
	* 将fromMap键fromName对应的值放到toMap中，值为null不做处理
	* @param name
	* @param fromMap
	* @param toMap
	* @returnType void
	* @author: Hydra wangshuang@lakala.com
	* @date: 2017年1月10日 上午9:43:34
	 */
	public static<K, V> void processParam(K name, Map<K, V> fromMap, Map<K, V> toMap){
		processParam(name, fromMap, name, toMap);
	}
	
	/**
	 * 将fromMap键fromName对应的值以toName为键，并对值做Base64处理后放到toMap中，值为null不做处理
	 * @param fromName
	 * @param fromMap
	 * @param toName
	 * @param toMap
	 * @returnType void
	 * @author: Hydra wangshuang@lakala.com
	 * @date: 2017年1月10日 上午9:42:00
	 */
	public static<K, V> void processParamWithBase64(K fromName, Map<K, V> fromMap, K toName, Map<K, Object> toMap){
		V tmp = fromMap.get(fromName);
		if(tmp instanceof String){
			String sTmp = (String)tmp;
			toMap.put(toName, new String(Base64.encode(sTmp.getBytes())));
		}
	}
	
	/**
	 * 将fromMap键fromName对应的值做Base64处理后放到toMap中，值为null不做处理
	 * @param name
	 * @param fromMap
	 * @param toMap
	 * @returnType void
	 * @author: Hydra wangshuang@lakala.com
	 * @date: 2017年1月10日 上午9:43:34
	 */
	public static<K, V> void processParamWithBase64(K name, Map<K, V> fromMap, Map<K, Object> toMap){
		processParamWithBase64(name, fromMap, name, toMap);
	}
	
	/**
	* 将fromMap键fromName对应的值以toName为键值放到toMap中，值为null时放置默认值def
	* @param fromName
	* @param fromMap
	* @param toName
	* @param toMap
	* @param def
	* @returnType void
	* @author: Hydra wangshuang@lakala.com
	* @date: 2017年1月10日 上午9:44:13
	 */
	public static<K, V> void processParamWithDefault(K fromName, Map<K, V> fromMap, K toName, Map<K, V> toMap, V def){
		V tmp = fromMap.get(fromName);
		if(null != tmp){
			toMap.put(toName, tmp);
		}else{
			toMap.put(toName, def);
		}
	}
	
	/**
	* 将fromMap键fromName对应的值以toName为键值放到toMap中，值为null时放置默认值def
	* @param name
	* @param fromMap
	* @param toMap
	* @param def
	* @returnType void
	* @author: Hydra wangshuang@lakala.com
	* @date: 2017年1月10日 上午9:44:41
	 */
	public static<K, V> void processParamWithDefault(K name, Map<K, V> fromMap, Map<K, V> toMap, V def){
		processParamWithDefault(name, fromMap, name, toMap, def);
	}
	
	/**
	* sourceMap的键keyName无对应值时，设置默认值def
	* @param keyName
	* @param sourceMap
	* @param def
	* @returnType void
	* @author: Hydra wangshuang@lakala.com
	* @date: 2017年1月10日 上午9:47:08
	 */
	public static<K, V> void setDefaultParam(K keyName, Map<K, V> sourceMap, V def){
		V tmp = sourceMap.get(keyName);
		if(null == tmp){
			sourceMap.put(keyName, def);
		}
	}
}
