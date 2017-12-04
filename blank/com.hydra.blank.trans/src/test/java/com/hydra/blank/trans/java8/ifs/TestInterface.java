package com.hydra.blank.trans.java8.ifs;

import org.junit.Test;

public class TestInterface {
	@Test
	public void testInterface(){
		MyClass mc = new MyClass(){
			@Override
			public String getName() {
				return "java 8";
			}
			
		};
		DefaultInterface.sayHello(mc.getName());
	}
}
