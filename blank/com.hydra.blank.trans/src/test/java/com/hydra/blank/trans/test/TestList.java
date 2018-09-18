package com.hydra.blank.trans.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.hydra.core.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestList {
    @Test
    public void testPerform() {
        final List<Integer> ll = new LinkedList<>();
        CommonUtil.printRuntime(() -> {
            for (Integer i = 0; i < Integer.valueOf(5000000); i++) {
                ll.add(1);
            }
            return null;
        });

        CommonUtil.printRuntime(() -> {
            new ArrayList<>(ll);
            return null;
        });

        CommonUtil.printRuntime(() -> {
            new LinkedList<>(ll);
            return null;
        });
    }

    private static <T> void popAll(List<T> taskList) {
        long begin = System.currentTimeMillis();
        while (0 < taskList.size()) {
            pop(taskList);
        }
        long end = System.currentTimeMillis();
        log.info("Total use: " + (end - begin));
    }

    private static <T> T pop(List<T> taskList) {
        T res = null;
        int len = taskList.size();
        if (len > 0) {
            res = taskList.remove(--len);
        }
        return res;
    }
}
