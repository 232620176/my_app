package com.hydra.core.util;

import java.math.BigDecimal;
import java.util.Map;

public final class NumberUtil {
    public static BigDecimal getZero() {
        return getZero(0);
    }

    public static BigDecimal getZero(int size) {
        if (size < 1) {
            return BIG_DECIMALS_ZERO_MAP.get(0);
        } else {
            BigDecimal res = BIG_DECIMALS_ZERO_MAP.get(size);
            if (null == res) {
                StringBuilder sb = new StringBuilder("0").append(".");
                for (int i = 0; i < size; i++) {
                    sb.append("0");
                }
                res = new BigDecimal(sb.toString());
                BIG_DECIMALS_ZERO_MAP.put(size, res);
            }
            return res;
        }
    }

    public static int long2Int(long num) {
        if (num > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else if (num < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        } else {
            return (int) num;
        }
    }

    public static BigDecimal number2BigDecimal(Number num) {
        if (null == num) {
            return BigDecimal.ZERO;
        } else if (num instanceof BigDecimal) {
            return (BigDecimal) num;
        } else {
            return new BigDecimal(num.toString());
        }
    }

    private NumberUtil() {
        throw new UnsupportedOperationException();
    }

    private static final Map<Integer, BigDecimal> BIG_DECIMALS_ZERO_MAP = MapUtil.getHashMap();

    static {
        BIG_DECIMALS_ZERO_MAP.put(0, BigDecimal.ZERO);
    }
}
