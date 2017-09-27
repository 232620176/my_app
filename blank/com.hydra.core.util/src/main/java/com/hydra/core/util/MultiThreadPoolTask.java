package com.hydra.core.util;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MultiThreadPoolTask<T> {
	public void start(){
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(max_thread);
		while(runAble){
			final T item = popTask();
			fixedThreadPool.execute(new Thread() {
				@Override
				public void run() {
					try {
						runTask(item);
					} catch (Exception e) {
						logger.error("{}", e);
					}
				}
			});
		}
		fixedThreadPool.shutdown();
	}
	
	public boolean isStoped(){
		return !runAble;
	}
	
	protected T popTask(){
		T res = null;
		int len = taskList.size();
		if(len > 0){
			res = taskList.remove(--len);
			if(len == 0){
				runAble = false;
			}
			if(0 == len % logSize){
				logger.info(logSize + " items finished & " + len + " items left...");
			}
		}
		return res;
	}
	
	public abstract void runTask(T item);
	
	private boolean runAble = false;
	private int max_thread = 8;
	private int logSize = 100;
	private List<T> taskList;
	private static final Logger logger = LoggerFactory.getLogger(MultiThreadPoolTask.class);
	public void setTaskList(List<T> taskList) {
		runAble = true;
		this.taskList = taskList;
	}
	
	public void setMax_thread(int max_thread) {
		this.max_thread = max_thread;
	}
}