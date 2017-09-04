package com.hydra.blank.db.test;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hydra.blank.db.service.ParametersQueryService;
import com.hydra.blank.util.CommonUtil;

public class TestDB {
	public static void main(String[] args) throws SQLException {
		ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"classpath*:spring/*.xml"});
		/*SqlMap sqlMap = CommonUtil.transfer(context.getBean("sqlMap"));
		Map<String, Object> param = MapUtil.getMap();
		List<Map<String, Object>> res = sqlMap.queryForList("param.qryParameters", param);*/
		ParametersQueryService pqs = CommonUtil.transfer(context.getBean("parametersQueryService"));
		logger.info("{}", pqs.get("bind_URL_wyhk"));
		logger.info("{}", pqs.get("KL1H00004"));
		logger.info("{}", pqs.get("bind_URL_wyhk"));
		((ClassPathXmlApplicationContext)context).close();
	}
	
	private static Logger logger = LoggerFactory.getLogger(TestDB.class);
}
