package com.hydra.blank.trans.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hydra.core.util.CommonUtil;

public class TestList {
	@Test
	public void testPerform() throws Exception{
		final List<Integer> ll = new LinkedList<>();
		CommonUtil.printRuntime(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				for(Integer i = 0; i < Integer.valueOf(5000000); i++){
					ll.add(1);
				}
				return null;
			}
			
		});
		
		CommonUtil.printRuntime(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				new ArrayList<>(ll);;
				return null;
			}
			
		});
		
		CommonUtil.printRuntime(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				new LinkedList<>(ll);
				return null;
			}
			
		});
	}
	
	private static<T> void popAll(List<T> taskList){
		long begin = System.currentTimeMillis();
		while(0 < taskList.size()){
			pop(taskList);
		}
		long end = System.currentTimeMillis();
		logger.info("Total use: " + (end - begin));
	}
	
	private static<T> T pop(List<T> taskList){
		T res = null;
		int len = taskList.size();
		if(len > 0){
			res = taskList.remove(--len);
		}
		return res;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(TestList.class);
}
