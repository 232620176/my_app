package com.hydra.blank.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.hydra.blank.web.annotation.ServletLog;
import com.hydra.core.util.BankCardUtil;

public class BanksController implements Controller{
	@Override
	@ServletLog
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		//添加模型数据 可以是任意的POJO对象
		mv.addObject("bankList", BankCardUtil.getBanks());
		//设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
		mv.setViewName("banks");
		return mv;
	}
}
