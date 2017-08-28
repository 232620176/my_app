package com.hydra.blank.util;

public class CommonUtil {
	private CommonUtil(){throw new UnsupportedOperationException();}
	
	@SuppressWarnings("unchecked")
	public static <T> T transfer(Object target){
		return (T)target;
	}
}
