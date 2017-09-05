package com.hydra.blank.util;

public class CommonUtil {
	/**
	 * <p>Title: transform</p>
	 * <p>Description: 将Object对象target转变为指定类型</p>
	 * @param <T> target的强转类型
	 * @param target 需强转的目标对象
	 * @return T 将目标对象强转为声明类型
	 */
	@SuppressWarnings("unchecked")
	public static <T> T transfer(Object target){
		return (T)target;
	}
	
	// 静态工具类，防误生成
	private CommonUtil(){throw new UnsupportedOperationException();}
}
