package com.hydra.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.util.StringUtils;

public final class DateUtil {
    /**
     * 返回传入日期date加day天后的日期
     * 
     * @param date
     * @param day 可为负数
     * @return
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

    /**
     * @param pattern
     * @return
     */
    public static DateFormat createFormatter(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 格式化日期为字符串
     * 
     * @param date
     * @param pattern 可传空，默认为：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (!StringUtils.hasText(pattern)) {
            pattern = ZH_CN_PATTERN_DATETIME;
        }
        DateFormat formatter = createFormatter(pattern);
        return formatter.format(date);
    }

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

    /**
     * 格式化日期为指定格式字符串
     * 
     * @param date
     * @param format
     * @return
     */
    public static Date parseDate(String date, String format) {
        DateFormat sf = createFormatter(format);
        try {
            return sf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static Date        today                   = null;

    private static final long  MILLISECOND_IN_ONE_DAY  = 1000 * 60 * 60 * 24;

    public static final String PATTERN_YYYYMMDD        = "yyyyMMdd";

    public static final String PATTERN_YYYYMMDD_HHMMSS = "yyyyMMdd HH:mm:ss";

    public static final String ZH_CN_PATTERN_DATE      = "yyyy-MM-dd";

    public static final String ZH_CN_PATTERN_DATETIME  = "yyyy-MM-dd HH:mm:ss";

    // 静态工具类，防误生成
    private DateUtil() {
        throw new UnsupportedOperationException();
    }
}
