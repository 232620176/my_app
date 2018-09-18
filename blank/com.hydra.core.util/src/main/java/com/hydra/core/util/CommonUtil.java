package com.hydra.core.util;

import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {
    /**
     * <p>
     * Title: transform
     * </p>
     * <p>
     * Description: 将Object对象target转变为指定类型
     * </p>
     * 
     * @param <T> target的强转类型
     * @param target 需强转的目标对象
     * @return T 将目标对象强转为声明类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T transform(Object target) {
        return (T) target;
    }

    public static <V> V printRuntime(Supplier<V> ca) {
        long begin = System.currentTimeMillis();
        V res = null;
        try {
            res = ca.get();
        } finally {
            long end = System.currentTimeMillis();
            log.info("Total use: " + (end - begin));
        }
        return res;
    }

    public static final <T> void waitDoneOrTimeout(List<Future<T>> list) {
        waitDoneOrTimeout(list, MAX_WAIT_TIME);
    }

    public static final <T> void waitDoneOrTimeout(List<Future<T>> list, long timeout) {
        if (null == list || list.size() == 0) {
            return;
        }
        long begin = System.currentTimeMillis(), end = begin;
        boolean res = true;
        while (true) {
            for (Future<T> future : list) {
                if (!future.isDone()) {
                    res = false;
                    break;
                }
            }
            end = System.currentTimeMillis();
            if (end - begin >= timeout) {
                log.error("超过限时：" + timeout);
                end = System.currentTimeMillis();
                long result = end - begin;
                log.info("总用时：" + result + "ms...");
                return;
            }
            if (res) {
                end = System.currentTimeMillis();
                long result = end - begin;
                log.info("总用时：" + result + "ms...");
                return;
            } else {
                sleep(100);
                res = true;
            }
        }
    }

    public static final void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("{}", e);
        }
    }

    public static final long MAX_WAIT_TIME = 30000;

    // 静态工具类，防误生成
    private CommonUtil() {
        throw new UnsupportedOperationException();
    }
}
