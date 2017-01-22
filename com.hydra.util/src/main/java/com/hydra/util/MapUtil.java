package com.hydra.util;

import java.util.Map;

import com.lakala.amber.util.Base64;
/**
 * @description ���ڹ���������
 * @author wangshuang@lakala.com
 * @date 2013��12��30��
 */
public final class MapUtil {
	private MapUtil(){}
	
	/**
	* ��fromMap��fromName��Ӧ��ֵ��toNameΪ��ֵ�ŵ�toMap�У�ֵΪnull��������
	* @param fromName
	* @param fromMap
	* @param toName
	* @param toMap
	* @returnType void
	* @author: Hydra wangshuang@lakala.com
	* @date: 2017��1��10�� ����9:42:00
	 */
	public static<K, V> void processParam(K fromName, Map<K, V> fromMap, K toName, Map<K, V> toMap){
		V tmp = fromMap.get(fromName);
		if(null != tmp){
			toMap.put(toName, tmp);
		}
	}
	
	/**
	* ��fromMap��fromName��Ӧ��ֵ�ŵ�toMap�У�ֵΪnull��������
	* @param name
	* @param fromMap
	* @param toMap
	* @returnType void
	* @author: Hydra wangshuang@lakala.com
	* @date: 2017��1��10�� ����9:43:34
	 */
	public static<K, V> void processParam(K name, Map<K, V> fromMap, Map<K, V> toMap){
		processParam(name, fromMap, name, toMap);
	}
	
	/**
	 * ��fromMap��fromName��Ӧ��ֵ��toNameΪ��������ֵ��Base64�����ŵ�toMap�У�ֵΪnull��������
	 * @param fromName
	 * @param fromMap
	 * @param toName
	 * @param toMap
	 * @returnType void
	 * @author: Hydra wangshuang@lakala.com
	 * @date: 2017��1��10�� ����9:42:00
	 */
	public static<K, V> void processParamWithBase64(K fromName, Map<K, V> fromMap, K toName, Map<K, Object> toMap){
		V tmp = fromMap.get(fromName);
		if(tmp instanceof String){
			String sTmp = (String)tmp;
			toMap.put(toName, new String(Base64.encode(sTmp.getBytes())));
		}
	}
	
	/**
	 * ��fromMap��fromName��Ӧ��ֵ��Base64�����ŵ�toMap�У�ֵΪnull��������
	 * @param name
	 * @param fromMap
	 * @param toMap
	 * @returnType void
	 * @author: Hydra wangshuang@lakala.com
	 * @date: 2017��1��10�� ����9:43:34
	 */
	public static<K, V> void processParamWithBase64(K name, Map<K, V> fromMap, Map<K, Object> toMap){
		processParamWithBase64(name, fromMap, name, toMap);
	}
	
	/**
	* ��fromMap��fromName��Ӧ��ֵ��toNameΪ��ֵ�ŵ�toMap�У�ֵΪnullʱ����Ĭ��ֵdef
	* @param fromName
	* @param fromMap
	* @param toName
	* @param toMap
	* @param def
	* @returnType void
	* @author: Hydra wangshuang@lakala.com
	* @date: 2017��1��10�� ����9:44:13
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
	* ��fromMap��fromName��Ӧ��ֵ��toNameΪ��ֵ�ŵ�toMap�У�ֵΪnullʱ����Ĭ��ֵdef
	* @param name
	* @param fromMap
	* @param toMap
	* @param def
	* @returnType void
	* @author: Hydra wangshuang@lakala.com
	* @date: 2017��1��10�� ����9:44:41
	 */
	public static<K, V> void processParamWithDefault(K name, Map<K, V> fromMap, Map<K, V> toMap, V def){
		processParamWithDefault(name, fromMap, name, toMap, def);
	}
	
	/**
	* sourceMap�ļ�keyName�޶�Ӧֵʱ������Ĭ��ֵdef
	* @param keyName
	* @param sourceMap
	* @param def
	* @returnType void
	* @author: Hydra wangshuang@lakala.com
	* @date: 2017��1��10�� ����9:47:08
	 */
	public static<K, V> void setDefaultParam(K keyName, Map<K, V> sourceMap, V def){
		V tmp = sourceMap.get(keyName);
		if(null == tmp){
			sourceMap.put(keyName, def);
		}
	}
}
