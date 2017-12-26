package com.hydra.blank.trans.test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class TestNumber {
	@Test
	public void testRandom(){
		Random r = new Random(47);
		for(int i = 0; i < 100; i++)
		System.out.println(r.nextInt(2));
	}
	
	@Test
	public void testAuto(){
		for(int j = 0; j < 100; j ++){
			int i = AI.getAndIncrement();
			if(i % 2 == 0){
				System.out.println("wxapi" + i);
			}else{
				System.out.println("ft" + i);
			}
			if(i > 90){
				AI.set(0);
			}
		}
	}
	private static final AtomicInteger AI = new AtomicInteger();
}
