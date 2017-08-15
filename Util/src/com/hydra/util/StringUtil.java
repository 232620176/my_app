package com.hydra.util;

public class StringUtil {
	private StringUtil(){throw new UnsupportedOperationException();}
	
	public static boolean isEmpty(Object target){
		return null == target || "".equals(target);
	}
}
