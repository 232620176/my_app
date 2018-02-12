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

import com.hydra.blank.web.annotation.ModelViewTemplate;
import com.hydra.core.util.StringUtil;

public class BaseCommandController implements Controller{
	public static final String BASE_STR_WINDOWS = "cmd.exe /c ";
	
	@Override
	@ModelViewTemplate
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
		String data = request.getParameter("data");
		boolean anslog = true;
		if(StringUtil.isEmpty(data)){
			anslog = false;
		}
		String keyWord = "log_list";
		if("0".equals(type)){
			keyWord = "errors_list";
			if(anslog){
				keyWord = "errors_ans " + data.replace("[", "\\\\[").replace("]", "\\\\]");
			}
		}else if("1".equals(type)){
			if(anslog){
				keyWord = "log_ans " + data;
			}
		}else if("2".equals(type)){
			keyWord = "file_list";
			if(anslog){
				keyWord = "file_ans " + data;
			}
		}else{
			throw new UnsupportedOperationException("不支持的类型：" + type);
		}
		logger.info(" exec " + keyWord);
		ModelAndView mv = new ModelAndView();
		//设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
		mv.setViewName("result");
		
		String osName = System.getProperty("os.name");
		Charset ct = Charset.forName("UTF-8");
		if(osName != null && osName.toLowerCase().startsWith("windows")){
			keyWord = BASE_STR_WINDOWS + keyWord;
			ct = Charset.forName("GBK");
		}else{
			keyWord = keyWord.replace("\\\\[", "\\[").replace("\\\\]", "\\]");
		}
		logger.info(keyWord);
		BufferedReader bf = null;
		Process pro = null;
		Collection<Object> infoList = new ArrayList<>();
		Collection<Object> datas = new ArrayList<>();
		if(anslog){
			mv.addObject("datas", datas);
		}
		try {
			pro = Runtime.getRuntime().exec(keyWord);
			pro.waitFor();
			InputStream is = pro.getInputStream();
			bf = new BufferedReader(new InputStreamReader(is, ct));
			String msg = null;
			while((msg = bf.readLine()) != null){
				if(anslog){
					int i = msg.indexOf(" ");
					if(i > 0){
						if("2".equals(type)){
							datas.add(msg.substring(i + 1));
							infoList.add(msg.substring(0, i));
						}else{
							datas.add(msg.substring(0, i));
							infoList.add(msg.substring(i));
						}
					}
				}else{
					infoList.add(msg);
				}
			}
		} catch (Exception e) {
			infoList.add(e.getMessage());
			logger.error("exception occurs: {} {}", e);
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
