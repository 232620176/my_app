package com.hydra.core.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hydra.core.common.Dict;


public class StringUtil {
	/**
	 * <p>Title: check</p>
	 * <p>Description: 按指定pattern检查目标字符串str格式是否合法</p>
	 * @param str 目标字符串
	 * @param pattern 指定样式
	 * @return boolean
	 */
	public static boolean check(String str, String pattern){
		Pattern pat = Pattern.compile(pattern);
		Matcher match = pat.matcher(str.trim());
		return match.matches();
	}
	
	/**
	 * <p>Title: isEmpty</p>
	 * <p>Description: 判断一个对象是否为空</p>
	 * @param str 目标对象
	 * @return boolean
	 */
	public static boolean isEmpty(Object str) {
		return str == null;
	}
	
	/**
	 * <p>Title: isEmpty</p>
	 * <p>Description: 判断一个字符串是否为空</p>
	 * @param str 目标字符串
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		return (str == null) || (str.trim().length() == 0);
	}
	
	/**
	 * <p>Title: isNotEmpty</p>
	 * <p>Description: 判断一个字符串是否不为空</p>
	 * @param str 目标字符串
	 * @return boolean
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	/**
	 * <p>Title: createRandom</p>
	 * <p>Description: 获取指定长度的随机字符串，字符串取值范围：HEXADECIMAL_CHAR_UPPER</p>
	 * @param length 指定长度
	 * @return String 随机字符串
	 */
	public static String createRandom(int length) {
		Random random = RandomUtil.getRandom();
		StringBuilder result = new StringBuilder();
		int len = Dict.HEXADECIMAL_CHAR_UPPER.length - 1;
		for (int i = 0; i < length; i++) {
			int itmp = random.nextInt(len);
			char ctmp = Dict.HEXADECIMAL_CHAR_UPPER[itmp];
			result.append(ctmp);
		}
		return result.toString();
	}
	
	public static void main(String[] args) {
		logger.info("Hello world!");
	}
	
	private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
	// 静态工具类，防误生成
	private StringUtil(){throw new UnsupportedOperationException();}
}
