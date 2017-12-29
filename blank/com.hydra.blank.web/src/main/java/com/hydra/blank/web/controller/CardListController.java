package com.hydra.blank.web.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.hydra.blank.web.annotation.ServletLog;
import com.hydra.core.util.CardUtil;
import com.hydra.core.util.StringUtil;

public class CardListController implements Controller{
	public static final int DEFAULT_SIZE = 5;
	@Override
	@ServletLog
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String bankName = request.getParameter("bankName");
		String cardType = request.getParameter("cardType");
		String sTimes = request.getParameter("times");
		int times = DEFAULT_SIZE;
		if(!StringUtil.isEmpty(sTimes)){
			times = Integer.valueOf(sTimes);
		}
		Collection<String> cardList = CardUtil.getCards(bankName, cardType, times);
		ModelAndView mv = new ModelAndView();
		//添加模型数据 可以是任意的POJO对象
		mv.addObject("list", cardList);
		//设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
		mv.setViewName("show");
		return mv;
	}
}
