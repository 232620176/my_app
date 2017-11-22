package com.hydra.blank.trans.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class TestMap {
	@Test
	public void testOrder() {
		//LinkedHashMap保存插入顺序
		Map<String, Integer> linkMap = new LinkedHashMap<>();
		linkMap.put("key1", 15);
		linkMap.put("key4", 39);
		linkMap.put("key3", 17);
		linkMap.put("key2", 23);
		printMapByForEach(linkMap);
		
		//HashMap按Key排序
		Map<String, Integer> hashMap = new HashMap<>();
		hashMap.putAll(linkMap);
		printMapByForEach(hashMap);
		
		//TreeMap按Key排序
		Map<String, Integer> treeMap = new TreeMap<>();
		treeMap.putAll(linkMap);
		printMapByIterator(treeMap);
	}
	
	private void printMapByForEach(Map<?, ?> map){
		map.forEach((x, y) -> System.out.print("{" + x + ", " + y + "} "));
		System.out.println();
	}
	
	private<K, V> void printMapByIterator(Map<K, V> map){
		Iterator<K> it = map.keySet().iterator();
		while(it.hasNext()){
			K key = it.next();
			System.out.print("{" + key + ", " + map.get(key) + "} ");
		}
		System.out.println();
	}
}
