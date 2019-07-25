package com.hydra.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            pattern = DATE_PATTERN_0;
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
     * 用匹配的样式格式化日期字符串为指定日期
     * 
     * @param dateStr
     * @param patterns
     * @return
     */
    public static Date parseDate(String dateStr, String... patterns) {
        try {
            return DateUtils.parseDate(dateStr, patterns);
        } catch (Exception e) {
            log.warn("parse date exception, dateStr:{}, patterns:{}", dateStr, patterns);
            throw new RuntimeException(e);
        }
    }

    /**
     * 用DATE_PATTERNS中的样式格式化字符串为指定的日期
     * 
     * @param dateStr
     * @return
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, DATE_PATTERNS);
    }

    /**
     * 用DATETIME_PATTERNS中的样式格式化字符串为指定的时间
     * 
     * @param datetimeStr
     * @return
     */
    public static Date parseDatetime(String datetimeStr) {
        return parseDate(datetimeStr, DATETIME_PATTERNS);
    }

    private static String[] datetimePatterns() {
        String[] datePatterns = new String[DATE_PATTERNS.length];
        for (int i = 0; i < datePatterns.length; i++) {
            datePatterns[i] = DATE_PATTERNS[i] + DATETIME_POSTFIX;
        }
        return datePatterns;
    }

    public static final long     MILLISECOND_IN_ONE_DAY = 1000 * 60 * 60 * 24;
    public static final String   DATE_PATTERN_0         = "yyyy-MM-dd";
    public static final String   DATE_PATTERN_1         = "yyyy/MM/dd";
    public static final String   DATE_PATTERN_2         = "yyyyMMdd";
    public static final String   DATE_PATTERN_3         = "yyyy-M-d";
    public static final String   DATE_PATTERN_4         = "yyyy/M/d";
    private static final String  DATETIME_POSTFIX       = " HH:mm:ss";
    private static Date          today                  = null;

    public static final String[] DATE_PATTERNS          = { DATE_PATTERN_0, DATE_PATTERN_1, DATE_PATTERN_2,
            DATE_PATTERN_3, DATE_PATTERN_4 };
    public static final String[] DATETIME_PATTERNS      = datetimePatterns();

    // 静态工具类，防误生成
    private DateUtil() {
        throw new UnsupportedOperationException("DateUtil::new");
    }
}
