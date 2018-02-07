package com.hydra.blank.web.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.hydra.blank.web.util.ServletUtil;
import com.hydra.core.util.JsonUtil;
import com.hydra.core.util.MapUtil;
import com.hydra.core.util.NetUtil;
import com.hydra.core.util.StringUtil;


@Aspect
@Component
@Order(8)
public class ModelViewTemplateAspect {
	@Pointcut("@annotation(com.hydra.blank.web.annotation.ModelViewTemplate)")
	public void annotationPointCut(){}
	
	@Around("annotationPointCut()")
	public Object process(ProceedingJoinPoint joinPoint) throws Exception{
		long begin = System.currentTimeMillis();
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();
		String methodName = method.getName();
		StringBuffer sb = new StringBuffer(methodName);
		sb.append(" IP: ");
		
		Object[] args = joinPoint.getArgs();
		Object o = args[0];
		String guid = StringUtil.getGuid();
		if(o instanceof HttpServletRequest){
			HttpServletRequest req = (HttpServletRequest)o;
			req.setAttribute("_Guid", guid);
			sb.append(ServletUtil.getIpAddr(req));
			sb.append(" && ");
			logger.info(sb.toString() + "request: {} {}", guid, req.getParameterMap());
		}else{
			sb.append(NetUtil.getRealIp());
			sb.append(" && ");
			logger.info(sb.toString() + "request: {} {}", guid, Arrays.toString(args));
		}
		
		Object ret = null;
		Map<String, Object> map = MapUtil.getMap();
		Map<String, Object> retStatus = MapUtil.getMap();
		retStatus.put("retCode", "0000");
		retStatus.put("errMsg", "SUCCESS");
		map.put("retStatus", retStatus);
		Map<String, Object> data = MapUtil.getMap();
		data.put("_Guid", guid);
		map.put("retData", data);
		try {
			ret = joinPoint.proceed(args);
			if(ret != null){
				logger.info(sb.toString() + "response: {} {}", guid, ret.toString());
				if(ret instanceof ModelAndView) {
					ModelAndView mv = (ModelAndView)ret;
					data.putAll(mv.getModel());
					mv.addObject("res", JsonUtil.object2JsonString(map));
				}
			}
		} catch (Throwable e) {
			logger.error("exception occurs: {} {}", guid, e);
			retStatus.put("retCode", "NS01");
			retStatus.put("errMsg", e.getMessage());
			ModelAndView mv = new ModelAndView();
			mv.addObject("res", JsonUtil.object2JsonString(map));
			mv.setViewName("result");
			ret = mv;
		}finally{
			long end = System.currentTimeMillis();
			logger.info("--process end--- execute time:{} {}", end - begin, methodName);
		}
		return ret;
	}
	
	private Logger logger = LoggerFactory.getLogger(getClass());
}