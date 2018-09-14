package com.hydra.core.util;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
/**
 * 用于随机服务的工具
 * <p>
 * Created on 2017年6月22日 上午11:18:53
 * </p>
 * @since 2017年6月22日
 */
public class RandomUtil {
	public static Random get() {
		return RANDOM;
	}

	public static int randomAge() {
		return randomAge(AGE_MIN, AGE_STEP);
	}

	public static int randomAge(int min, int maxStep) {
		int step = RANDOM.nextInt(maxStep);
		return min + step;
	}

	public static String randomBirthday() {
		int year = NOW_YEAR - randomAge();
		StringBuilder sb = new StringBuilder();
		sb.append(year);
		sb.append(RandomUtil.randomNumberBelowStr(12));
		sb.append(RandomUtil.randomNumberBelowStr(28));
		return sb.toString();
	}

	public static int randomNumber(int size) {
		int max = getMax(size);
		return randomNumberBelow(max);
	}

	public static int randomNumberBelow(int max) {
		return RANDOM.nextInt(max) + 1;
	}

	public static String randomNumberBelowStr(int max) {
		int maxLen = getLength(max);
		int val = randomNumberBelow(max);
		int len = getLength(val);
		StringBuilder sb = new StringBuilder();
		appendZero(sb, len, maxLen);
		sb.append(val);
		return sb.toString();
	}

	public static String randomNumberStr(int size) {
		int max = getMax(size);
		int val = randomNumberBelow(max);
		String str = String.valueOf(val);
		int len = str.length();
		StringBuilder sb = new StringBuilder(str);
		appendZero(sb, len, size);
		return sb.toString();
	}

	public static void appendZero(StringBuilder sb, int length, int maxLength) {
		for (int i = maxLength; i > length; i--) {
			sb.append(ZERO);
		}
	}

	public static int getLength(int num) {
		return String.valueOf(num).length();
	}

	private static int getMax(int size) {
		if (size < 0 || size > 10) {
			size = 10;
		}
		return (int) (Math.pow(10, size) - 1);
	}

	private static final Integer AGE_MIN  = 16;
	private static final Integer AGE_STEP = 40;
	private static final Integer NOW_YEAR = Calendar.getInstance().get(Calendar.YEAR);
	private static final Random  RANDOM   = ThreadLocalRandom.current();
	public static final String   ZERO	 = "0";
	
	// 静态工具类，防误生成
	private RandomUtil(){throw new UnsupportedOperationException();}
}
