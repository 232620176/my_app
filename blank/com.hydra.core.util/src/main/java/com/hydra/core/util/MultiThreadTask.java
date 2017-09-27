package com.hydra.core.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Deprecated
public abstract class MultiThreadTask<T> {
	public void start(){
		Thread monitor = new Thread() {
			@Override
			public void run() {
				while(runAble){
					boolean flag = tList.removeAll(rList);
					if(flag){
						logger.info("Time out thread had been cleared...");
					}
					rList.clear();
					rList.addAll(tList);
					try {
						Thread.sleep(timeout);
					} catch (InterruptedException e) {
						logger.error("{}", e);
					}
				}
			}
		};
		monitor.start();
		
		while(runAble){
			while(tList.size() >= max_thread){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					logger.error("{}", e);
				}
			}
			
			final T item = popTask();
			Thread t = new Thread() {
				@Override
				public void run() {
					runTask(item);
					tList.remove(this);
					logger.info("Thread finished & The tList size is " + tList.size());
				}
			};
			tList.add(t);
			t.start();
		}
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
	private List<Thread> tList = new ArrayList<>();
	private List<Thread> rList = new ArrayList<>();
	private long timeout = 30000;
	private static final Logger logger = LoggerFactory.getLogger(MultiThreadTask.class);
	public void setTaskList(List<T> taskList) {
		runAble = true;
		this.taskList = taskList;
	}
	
	public void setMax_thread(int max_thread) {
		this.max_thread = max_thread;
	}
}
