package com.hydra.core.util;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {
	/**
	 * <p>Title: transform</p>
	 * <p>Description: 将Object对象target转变为指定类型</p>
	 * @param <T> target的强转类型
	 * @param target 需强转的目标对象
	 * @return T 将目标对象强转为声明类型
	 */
	@SuppressWarnings("unchecked")
	public static <T> T transform(Object target){
		return (T)target;
	}
	
	public static<V> V printRuntime(Callable<V> ca) throws Exception{
		long begin = System.currentTimeMillis();
		V res = null;
		try {
			res = ca.call();
		}finally{
			long end = System.currentTimeMillis();
			logger.info("Total use: " + (end - begin));
		}
		return res;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	// 静态工具类，防误生成
	private CommonUtil(){throw new UnsupportedOperationException();}
}
