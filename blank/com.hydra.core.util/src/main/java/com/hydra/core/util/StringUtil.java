package com.hydra.core.util;

import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {
    /**
     * <p>
     * Title: check
     * </p>
     * <p>
     * Description: 按指定pattern检查目标字符串str格式是否合法
     * </p>
     * 
     * @param str 目标字符串
     * @param pattern 指定样式
     * @return boolean
     */
    public static boolean check(String str, String pattern) {
        Pattern pat = Pattern.compile(pattern);
        Matcher match = pat.matcher(str.trim());
        return match.matches();
    }

    public static String formatNumber(int num, int size) {
        String src = String.valueOf(num);
        int len = src.length();
        if (size > len) {
            StringBuilder sb = new StringBuilder();
            for (int i = size; i > len; i--) {
                sb.append("0");
            }
            sb.append(num);
            return sb.toString();
        } else if (size == len) {
            return src;
        } else {
            return src.substring(len - size);
        }
    }

    /**
     * <p>
     * Title: getGuid
     * </p>
     * <p>
     * Description: TODO
     * </p>
     * 
     * @return String
     */
    public static String getGuid() {
        String guid = UUID.randomUUID().toString();
        return guid.replace("-", "");
    }

    /**
     * <p>
     * Title: isEmpty
     * </p>
     * <p>
     * Description: 判断一个对象是否为空
     * </p>
     * 
     * @param str 目标对象
     * @return boolean
     */
    public static boolean isEmpty(Object str) {
        return str == null;
    }

    /**
     * <p>
     * Title: isEmpty
     * </p>
     * <p>
     * Description: 判断一个字符串是否为空
     * </p>
     * 
     * @param str 目标字符串
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return (str == null) || (str.trim().length() == 0);
    }

    /**
     * <p>
     * Title: isNotEmpty
     * </p>
     * <p>
     * Description: 判断一个字符串是否不为空
     * </p>
     * 
     * @param str 目标字符串
     * @return boolean
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 格式化打印
     * 
     * @param object
     * @return
     */
    public static String toString(Object object) {
        if (null == object) {
            return null;
        } else if (object.getClass().isArray()) {
            if (object instanceof boolean[]) {
                boolean[] tmp = (boolean[]) object;
                return Arrays.toString(tmp);
            } else if (object instanceof byte[]) {
                byte[] tmp = (byte[]) object;
                return Arrays.toString(tmp);
            } else if (object instanceof char[]) {
                char[] tmp = (char[]) object;
                return Arrays.toString(tmp);
            } else if (object instanceof double[]) {
                double[] tmp = (double[]) object;
                return Arrays.toString(tmp);
            } else if (object instanceof float[]) {
                float[] tmp = (float[]) object;
                return Arrays.toString(tmp);
            } else if (object instanceof long[]) {
                long[] tmp = (long[]) object;
                return Arrays.toString(tmp);
            } else if (object instanceof int[]) {
                int[] tmp = (int[]) object;
                return Arrays.toString(tmp);
            } else if (object instanceof short[]) {
                short[] tmp = (short[]) object;
                return Arrays.toString(tmp);
            } else {
                Object[] tmp = (Object[]) object;
                return Arrays.toString(tmp);
            }
        } else {
            return object.toString();
        }
    }

    // 静态工具类，防误生成
    private StringUtil() {
        throw new UnsupportedOperationException();
    }
}
