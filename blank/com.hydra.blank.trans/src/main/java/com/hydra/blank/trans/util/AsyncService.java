package com.hydra.blank.trans.util;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import com.hydra.core.common.interfaces.Callable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsyncService {
    @Async
    public Future<Long> asyncMethod(final Callable call) {
        return asyncMethodTempalte(new Callable() {
            @Override
            public void call() {
                call.call();
            }
        });
    }

    @Async
    public Future<Long> asyncMethodWithNoException(final Callable call) {
        return asyncMethodTempalte(new Callable() {
            @Override
            public void call() {
                try {
                    call.call();
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }
        });
    }

    private Future<Long> asyncMethodTempalte(Callable call) {
        long begin = System.currentTimeMillis();
        call.call();
        long end = System.currentTimeMillis();
        long res = end - begin;
        log.info("用时：" + res + "ms...");
        return new AsyncResult<Long>(res);
    }
}
