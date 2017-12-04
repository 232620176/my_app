package com.hydra.blank.trans.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hydra.core.util.MultiThreadPoolTask;

public class TestThread {
	@Test
	public void testAtomicInteger(){
		AtomicInteger ai = new AtomicInteger(0);
		Runnable rn = () -> {
			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					logger.error("{}", e);
				}
				int iVal = ai.incrementAndGet();
				System.out.println(this + ":" + iVal);
			}
		};
		
		for (int i = 0; i < 10; i++) {
			new Thread(rn).start();
		}
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			logger.error("{}", e);
		}
		System.out.println(ai.get());
	}
	
	@Test
	public void testTread() throws Exception{
		List<Integer> list = new ArrayList<>();
		for(Integer i = 0; i < 30; i++){
			list.add(i);
		}
		
		MultiThreadPoolTask<Integer> mtt = new MultiThreadPoolTask<Integer>() {
			@Override
			public void runTask(Integer item) {
				logger.info("Thread + " + item + " started...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					logger.error("{}", e);
				}
				logger.info("Thread + " + item + " sotped...");
			}
		};
		mtt.setTaskList(list);
		mtt.start();
	}
	private static final Logger logger = LoggerFactory.getLogger(TestThread.class);
}
