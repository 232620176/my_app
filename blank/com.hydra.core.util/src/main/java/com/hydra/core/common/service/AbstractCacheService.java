package com.hydra.core.common.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

public abstract class AbstractCacheService<T> implements InitializingBean {
    @Override
    public final void afterPropertiesSet() throws Exception {
        CacheLoader<String, T> loader = new CacheLoader<String, T>() {
            public T load(String key) {
                return build(key);
            }

            public ListenableFuture<T> reload(final String key, final T prevGraph) {
                ListenableFutureTask<T> task = ListenableFutureTask.create(new Callable<T>() {
                    public T call() {
                        return build(key);
                    }
                });
                EXECUTOR.execute(task);
                return task;
            }
        };
        cache = CacheBuilder.newBuilder().maximumSize(10).refreshAfterWrite(getInterval(), TimeUnit.MINUTES)
                .build(loader);
    }

    public final T get(String key) {
        return cache.getUnchecked(key);
    }

    protected abstract T build(String key);

    public final void setInterval(Integer interval) {
        this.interval = interval;
    }

    public final Integer getInterval() {
        return interval;
    }

    private LoadingCache<String, T>      cache           = null;
    private Integer                      interval        = DEFAULT_INTEVAL;
    private static final Integer         DEFAULT_INTEVAL = 5;
    private static final ExecutorService EXECUTOR        = Executors.newFixedThreadPool(4);
}
