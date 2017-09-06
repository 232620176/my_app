/**<p>Title: ReadOlnyFactory.java</p>
 * @Package com.hydra.blank.pojo
 * <p>Description: TODO</p>
 * @date 2017年7月25日 上午11:12:56
 * @version V1.0
 */
package com.hydra.core.pojo;

import java.util.Map;
import java.util.Set;

public class ReadOlnyFactory {
	/**
	 * <p>Title: getReadOnlyMap</p>
	 * <p>Description: 返回只读Map</p>
	 * @param map
	 * @return Map<K,V>
	 */
	public static<K, V> Map<K, V> getReadOnlyMap(Map<K, V> map){
		return new ReadOnlyMap<>(map);
	}
	
	/**
	 * <p>Title: getReadOnlySet</p>
	 * <p>Description: 返回只读Set</p>
	 * @param set
	 * @return Set<E>
	 */
	public static <E> Set<E> getReadOnlySet(Set<E> set){
		return new ReadOnlySet<>(set);
	}
	
	// 静态工具类，防误生成
	private ReadOlnyFactory(){throw new UnsupportedOperationException();}
}
