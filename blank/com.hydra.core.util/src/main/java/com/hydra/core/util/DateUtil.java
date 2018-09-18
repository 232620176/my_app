package com.hydra.core.util;

import java.util.Calendar;
import java.util.Date;

public final class DateUtil {
    public static Date getToday() {
        long now = System.currentTimeMillis();
        if (null == today || now - today.getTime() >= MILLISECOND_IN_ONE_DAY) {
            synchronized (today) {
                if (null == today || now - today.getTime() >= MILLISECOND_IN_ONE_DAY) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    today = cal.getTime();
                }
            }
        }
        return BeanUtil.deepClone(today);
    }

    private static Date       today                  = null;
    private static final long MILLISECOND_IN_ONE_DAY = 1000 * 60 * 60 * 24;

    // 静态工具类，防误生成
    private DateUtil() {
        throw new UnsupportedOperationException();
    }
}
