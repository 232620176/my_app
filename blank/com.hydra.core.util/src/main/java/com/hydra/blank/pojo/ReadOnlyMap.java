/**<p>Title: ReadOnlyMap.java</p>
 * @Package com.hydra.blank.pojo
 * <p>Description: TODO</p>
 * @date 2017年7月25日 上午11:12:56
 * @version V1.0
 */
package com.hydra.blank.pojo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**TODO
 * <p>
 * Created on 2017年7月25日 上午11:12:56
 * </p>
 * @since 2017年7月25日
 */
public class ReadOnlyMap<K, V> implements Map<K, V> {

	/* (non-Javadoc)
	 * @Title: size
	 * @Description: TODO
	 * @return
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return proxyMap.size();
	}

	/* (non-Javadoc)
	 * @Title: isEmpty
	 * @Description: TODO
	 * @return
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return proxyMap.isEmpty();
	}

	/* (non-Javadoc)
	 * @Title: containsKey
	 * @Description: TODO
	 * @param key
	 * @return
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return proxyMap.containsKey(key);
	}

	/* (non-Javadoc)
	 * @Title: containsValue
	 * @Description: TODO
	 * @param value
	 * @return
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return proxyMap.containsValue(value);
	}

	/* (non-Javadoc)
	 * @Title: get
	 * @Description: TODO
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public V get(Object key) {
		// TODO Auto-generated method stub
		return proxyMap.get(key);
	}

	/* (non-Javadoc)
	 * @Title: put
	 * @Description: TODO
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @Title: remove
	 * @Description: TODO
	 * @param key
	 * @return
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @Title: putAll
	 * @Description: TODO
	 * @param m
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @Title: clear
	 * @Description: TODO
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		proxyMap.clear();
	}

	/* (non-Javadoc)
	 * @Title: keySet
	 * @Description: TODO
	 * @return
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return keySet;
	}

	/* (non-Javadoc)
	 * @Title: values
	 * @Description: TODO
	 * @return
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return proxyMap.values();
	}

	/* (non-Javadoc)
	 * @Title: entrySet
	 * @Description: TODO
	 * @return
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return proxyMap.entrySet();
	}
	
	ReadOnlyMap(Map<K, V> map){
		proxyMap = new HashMap<>(map);
		keySet = new ReadOnlySet<>(proxyMap.keySet());
	}
	
	private Set<K> keySet;
	private Map<K, V> proxyMap;
}
