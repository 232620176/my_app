package com.hydra.blank.db.test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hydra.blank.db.sqlmap.SqlMap;
import com.hydra.blank.util.CommonUtil;
import com.hydra.blank.util.MapUtil;

public class TestDB {
	public static void main(String[] args) throws SQLException {
		ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"classpath*:spring/*.xml"});
		SqlMap sqlMap = CommonUtil.transfer(context.getBean("sqlMap"));
		Map<String, Object> param = MapUtil.getMap();
		List<Map<String, Object>> res = sqlMap.queryForList("param.qryParameters", param);
		logger.info("{}", res);
		((ClassPathXmlApplicationContext)context).close();
	}
	
	private static Logger logger = LoggerFactory.getLogger(TestDB.class);
}
