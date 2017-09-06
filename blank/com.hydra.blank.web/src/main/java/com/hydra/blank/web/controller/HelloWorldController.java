package com.hydra.blank.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.hydra.core.db.service.ParametersQueryService;
import com.hydra.core.util.StringUtil;

public class HelloWorldController implements Controller{
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//1、收集参数、验证参数
		//2、绑定参数到命令对象
		//3、将命令对象传入业务对象进行业务处理
		//4、选择下一个页面
		String keyName = "KL1H00004";
		String param = request.getParameter("keyName");
		if(StringUtil.isNotEmpty(param)){
			keyName = param;
		}
		ModelAndView mv = new ModelAndView();
		//添加模型数据 可以是任意的POJO对象
		mv.addObject("message", pqs.get(keyName));
		//设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
		mv.setViewName("hello");
		return mv;
	}
	
	@Autowired
	@Qualifier("parametersQueryService")
	private ParametersQueryService pqs;
}
