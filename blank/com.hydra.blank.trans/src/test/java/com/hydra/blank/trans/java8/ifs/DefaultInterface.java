package com.hydra.blank.trans.java8.ifs;

public interface DefaultInterface {
	default String getName(){
		return "java 8";
	}
	
	public static void sayHello(String name){
		System.out.println("Hello, " + name);
	}
}
