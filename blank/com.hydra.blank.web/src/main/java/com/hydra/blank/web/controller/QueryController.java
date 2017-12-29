package com.hydra.blank.web.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.hydra.blank.web.annotation.ServletLog;
import com.hydra.core.db.sqlmap.SqlMap;
import com.hydra.core.util.MapUtil;
import com.hydra.core.util.StringUtil;

public class QueryController implements Controller{
	@Override
	@ServletLog
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//1、收集参数、验证参数
		//2、绑定参数到命令对象
		//3、将命令对象传入业务对象进行业务处理
		//4、选择下一个页面
		Map<String, Object> param = MapUtil.getMap();
		param.put("table_name", request.getParameter("tableName"));
		String col = request.getParameter("col");
		List<String> column = null;
		if(StringUtil.isEmpty(col)){
			column = sqlMap.queryForList("tables.qryColumn", param);
			StringBuilder sb = new StringBuilder();
			boolean flag = false;
			for(String str : column){
				if(flag){
					sb.append(", ");
				}
				sb.append(str);
				flag = true;
			}
			param.put("table_header", sb.toString());
		}else{
			column = Arrays.asList(col.toUpperCase().split(","));
			param.put("table_header", col);
		}
		processParam("k0", param, request);
		processParam("v0", param, request);
		processParam("k1", param, request);
		processParam("v1", param, request);
		processParam("k2", param, request);
		processParam("v2", param, request);
		processParam("lk0", param, request);
		processParam("lv0", param, request);
		processParam("lk1", param, request);
		processParam("lv1", param, request);
		processParam("lk2", param, request);
		processParam("lv2", param, request);
		logger.info("{}", param);
		List<String> values = sqlMap.queryForList("tables.qryTables", param);
		
		ModelAndView mv = new ModelAndView();
		//添加模型数据 可以是任意的POJO对象
		mv.addObject("theader", column);
		mv.addObject("tbody", values);
		//设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
		mv.setViewName("tables");
		return mv;
	}
	
	private void processParam(String key, Map<String, Object> param, HttpServletRequest request){
		String tmp = request.getParameter(key);
		if(StringUtil.isNotEmpty(tmp)){
			param.put(key, tmp);
		}
	}
	
	@Autowired
	private SqlMap sqlMap;
	private Logger logger = LoggerFactory.getLogger(QueryController.class);
}
