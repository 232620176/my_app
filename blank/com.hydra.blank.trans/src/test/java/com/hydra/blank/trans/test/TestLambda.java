package com.hydra.blank.trans.test;

import org.junit.Test;

public class TestLambda {
	@Test
	public void test1(){
		Runnable run = () -> System.out.println("Hello lambda!");
		run.run();
	}
}
