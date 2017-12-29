package com.hydra.blank.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.hydra.blank.web.annotation.ServletLog;
import com.hydra.core.util.StringUtil;

public class StringProcessController implements Controller{
	@Override
	@ServletLog
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String param = request.getParameter("type");
		String source = request.getParameter("source");
		String result = null;
		if("upper".equals(param)){
			result = source.toUpperCase();
		}else if("lower".equals(param)){
			result = source.toLowerCase();
		}else if("process".equals(param)){
			String pattern = request.getParameter("pattern");
			String target = request.getParameter("target");
			if(StringUtil.isEmpty(target)){
				target = "";
			}
			try {
				result = source.replaceAll(pattern, target);
			} catch (Exception e) {
				result = e.getMessage();
			}
		}
		
		ModelAndView mv = new ModelAndView();
		//添加模型数据 可以是任意的POJO对象
		mv.addObject("res", result);
		//设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
		mv.setViewName("show");
		return mv;
	}
}
