package com.hydra.blank.trans.test;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestString {

    @Test
    public void testIndexOf() {
        String val = "logging.TransInfo - --process start--- 4400003233";
        System.out.println(val.indexOf("--process start--- "));
    }

    @Test
    public void testString2Integer() {
        //		String s = "999_999_999";
        //		System.out.println(Integer.valueOf(s));NumberFormatException
        Integer i = 9_9_9_999_9_99;
        System.out.println(i);
    }

    @Test
    public void testUUID() {
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        System.out.println(uuid.replaceAll("-", ""));
    }

    @Test
    public void testSplilt() {
        String s1 = "1_2";
        String[] aS1 = s1.split("_");
        System.out.println(Arrays.toString(aS1));
    }

    @Test
    public void testSplilt2() {
        String s = "process	2017-10-20 00:00:00.018 refreshAccessToken ";
        String[] as = s.split("\\s");
        for (String res : as) {
            log.info(res);
        }
    }

    @Test
    public void testHashCode() {
        String s1 = "billNoPayNoLimitBill";
        Object o1 = new StringBuffer(s1).toString();//new String(s1);//null + s1;//new Date();
        log.info("String: {}, Object: {}", s1, o1);
        ;
        log.info("String hash code: {}", s1.hashCode());
        ;
        log.info("Object hash code: {}", o1.hashCode());
        ;
        //		log.info("Equals result: {}", StringUtil.equals(s1, o1));//经测：String类型先比较hashcode并没有提高效率
    }
}
