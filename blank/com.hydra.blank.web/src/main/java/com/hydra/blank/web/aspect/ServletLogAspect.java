package com.hydra.blank.web.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.hydra.blank.web.util.ServletUtil;
import com.hydra.core.util.NetUtil;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Order(9)
@Slf4j
public class ServletLogAspect {
    @Pointcut("@annotation(com.hydra.blank.web.annotation.ServletLog)")
    public void annotationPointCut() {
    }

    @Around("annotationPointCut()")
    public Object process(ProceedingJoinPoint joinPoint) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        StringBuffer sb = new StringBuffer(methodName);
        sb.append(" IP: ");

        Object[] args = joinPoint.getArgs();
        Object o = args[0];
        if (o instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) o;
            sb.append(ServletUtil.getIpAddr(req));
            sb.append(" && ");
            log.info(sb.toString() + "request: {}", req.getParameterMap());
        } else {
            sb.append(NetUtil.getRealIp());
            sb.append(" && ");
            log.info(sb.toString() + "request: {}", Arrays.toString(args));
        }

        Object ret = null;
        try {
            ret = joinPoint.proceed(args);
            if (ret != null) {
                log.info(sb.toString() + "response: {}", ret.toString());
            }
        } catch (Throwable e) {
            if (e instanceof Exception) {
                throw (Exception) e;
            } else if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new Exception(e.getMessage(), e);
            }
        }
        return ret;
    }
}
