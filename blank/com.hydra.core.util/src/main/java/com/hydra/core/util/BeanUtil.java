package com.hydra.core.util;

import java.util.Arrays;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;

public final class BeanUtil {
    public static void copyProperties(final Object orig, final Object dest) {
        copyProperties(orig, dest, IGNORE_PROPERTIES);
    }

    public static void copyProperties(final Object orig, final Object dest, @Nullable String... ignoreProperties) {
        BeanUtils.copyProperties(orig, dest, ignoreProperties);
    }

    public static <T> T deepClone(T obj) {
        if (null == obj) {
            return null;
        }
        return CommonUtil.transform(IOUtil.deserialize(IOUtil.serialize(obj)));
    }

    public static String toString(Object obj) {
        return toString(obj, "");
    }

    public static String toString(Object object, String defaultStr) {
        if (null == object) {
            return defaultStr;
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

    private static final String[] IGNORE_PROPERTIES = {};

    private BeanUtil() {
        throw new UnsupportedOperationException();
    }
}
