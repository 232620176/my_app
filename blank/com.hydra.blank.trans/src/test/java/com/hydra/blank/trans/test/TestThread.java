package com.hydra.blank.trans.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.hydra.core.util.MultiThreadPoolTask;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestThread {
    @Test
    public void testAtomicInteger() {
        AtomicInteger ai = new AtomicInteger(0);
        Runnable rn = () -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    log.error("{}", e);
                }
                int iVal = ai.incrementAndGet();
                System.out.println(this + ":" + iVal);
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(rn).start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.error("{}", e);
        }
        System.out.println(ai.get());
    }

    @Test
    public void testTread() throws Exception {
        List<Integer> list = new ArrayList<>();
        for (Integer i = 0; i < 30; i++) {
            list.add(i);
        }

        MultiThreadPoolTask<Integer> mtt = new MultiThreadPoolTask<Integer>() {
            @Override
            public void runTask(Integer item) {
                log.info("Thread + " + item + " started...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("{}", e);
                }
                log.info("Thread + " + item + " sotped...");
            }
        };
        mtt.setTaskList(list);
        mtt.start();
    }
}
