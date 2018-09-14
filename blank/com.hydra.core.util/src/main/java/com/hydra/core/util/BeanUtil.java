package com.hydra.core.util;

public final class BeanUtil {
	public static<T> T deepClone(T obj) {
		if(null == obj) {
			return null;
		}
		return CommonUtil.transform(IOUtil.deserialize(IOUtil.serialize(obj)));
	}
	
	public static String toString(Object obj) {
		return toString(obj, "");
	}

	public static String toString(Object obj, String defaultStr) {
		return null == obj ? defaultStr : obj.toString();
	}
	
	private BeanUtil() {throw new UnsupportedOperationException();}
}
