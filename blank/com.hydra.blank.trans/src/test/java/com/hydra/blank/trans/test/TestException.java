package com.hydra.blank.trans.test;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestException {
    @Test
    public void testOrder() throws Exception {
        Exception ee = null;
        try {
            log.info("in try");
            throw new Exception();
        } catch (Exception e) {
            ee = e;
            log.info("in catch");
            throw e;
        } finally {
            if (ee != null) {
                log.info("in finally");
            }
        }
    }
}
