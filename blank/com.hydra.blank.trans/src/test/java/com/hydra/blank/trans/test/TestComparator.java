package com.hydra.blank.trans.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TestComparator {
	@Test
	public void testSort() {
		List<Pojo> pojos = new ArrayList<>();
		pojos.add(new Pojo("499", "http://hadoop.apache.org"));
		pojos.add(new Pojo("200", "http://www.baidu.com"));
		pojos.add(new Pojo("500", "http://hive.apache.org"));
		pojos.add(new Pojo("499", "http://zookeeper.apache.org"));
		pojos.add(new Pojo("200", "http://www.google.com"));
		pojos.add(new Pojo("500", "http://hive.apache.org"));
		System.out.println(pojos);
//		pojos.stream().map((x) -> x.getCode()).forEach(System.out::println);
		Collections.sort(pojos);
		pojos.forEach(System.out::println);
	}
	
	public static class Pojo implements Comparable<Pojo>{
		private String code;
		private String url;
		
		public Pojo() {
		}
		
		public Pojo(String code, String url){
			this.code = code;
			this.url = url;
		}
		
		@Override
		public int compareTo(Pojo pojo) {
			int res = this.code.compareTo(pojo.code);
			if(res == 0){
				return this.url.compareTo(pojo.url);
			}
			return res;
		}
		
		@Override
		public String toString() {
			return this.code + ":" + this.url;
		}
		
		public String getCode() {
			return code;
		}
		
		public void setCode(String code) {
			this.code = code;
		}
		
		public String getUrl() {
			return url;
		}
		
		public void setUrl(String url) {
			this.url = url;
		}
	}
}
