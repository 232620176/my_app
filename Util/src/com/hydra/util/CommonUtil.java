package com.hydra.util;

public class CommonUtil {
	private CommonUtil(){throw new UnsupportedOperationException();}
	
	@SuppressWarnings("unchecked")
	public static <T> T transfer(Object target){
		return (T)target;
	}
}
