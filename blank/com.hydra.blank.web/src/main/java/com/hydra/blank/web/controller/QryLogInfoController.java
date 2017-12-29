package com.hydra.blank.web.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.hydra.blank.web.annotation.ServletLog;

public class QryLogInfoController implements Controller{
	public static final String BASE_STR_WINDOWS = "cmd.exe /c ";
	public static final String BASE_STR_LINUX = "getLog ";
	public static final String BASE_STR_LINUX2 = "egetLog ";
	
	@Override
	@ServletLog
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String keyWord = request.getParameter("keyWord");
		logger.info(" exec " + keyWord);
		ModelAndView mv = new ModelAndView();
		//设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
		mv.setViewName("show2");
		if(null == keyWord || keyWord.trim().equals("")){
			//添加模型数据 可以是任意的POJO对象
			mv.addObject("res", "No parameter keyWord");
			return mv;
		}
		String osName = System.getProperty("os.name");
		if(osName != null && osName.toLowerCase().startsWith("windows")){
			keyWord = BASE_STR_WINDOWS + keyWord;
		}else{
			if(!"clearGetLog".equals(keyWord)){
				if(keyWord.indexOf("exact@") > -1){
					keyWord = BASE_STR_LINUX2 + keyWord.split("@")[1];
				}else{
					keyWord = BASE_STR_LINUX + keyWord;
				}
			}
		}
		BufferedReader bf = null;
		Process pro = null;
		Collection<String> infoList = new ArrayList<String>();
		try {
			pro = Runtime.getRuntime().exec(keyWord);
			pro.waitFor();
			InputStream is = pro.getInputStream();
			bf = new BufferedReader(new InputStreamReader(is, Charset.forName("GBK")));
			String msg = null;
			while((msg = bf.readLine()) != null){
				infoList.add(msg);
			}
		} catch (Exception e) {
			infoList.add(e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != bf){try{bf.close();}catch(Exception e){}}
			if(null != pro){try{pro.destroy();}catch(Exception e){}}
		}
		
		//添加模型数据 可以是任意的POJO对象
		mv.addObject("list", infoList);
		return mv;
	}
	
	private Logger logger = LoggerFactory.getLogger(getClass());
}
