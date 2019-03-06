package com.hydra.core.common.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.hydra.core.util.CommonUtil;

@Component
public final class SpringContextUtil implements ApplicationContextAware {
    /*
     * 实现了ApplicationContextAware 接口，必须实现该方法；
     * 通过传递applicationContext参数初始化成员变量applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextUtil.applicationContext == null) {
            SpringContextUtil.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param name
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(String name) throws BeansException {
        try {
            return CommonUtil.transform(applicationContext.getBean(name));
        } catch (BeansException e) {
            throw e;
        }
    }

    /**
     * @param classType
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> classType) throws BeansException {
        try {
            return applicationContext.getBean(classType);
        } catch (BeansException e) {
            throw e;
        }
    }

    private static ApplicationContext applicationContext; // Spring应用上下文环境
}
