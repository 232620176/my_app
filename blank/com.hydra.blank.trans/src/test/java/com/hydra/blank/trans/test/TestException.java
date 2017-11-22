package com.hydra.blank.trans.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestException {
	private static Logger logger = LoggerFactory.getLogger(TestException.class);
	@Test
	public void testOrder() throws Exception {
		Exception ee = null;
		try{
			logger.info("in try");
			throw new Exception();
		}catch(Exception e){
			ee = e;
			logger.info("in catch");
			throw e;
		}finally{
			if(ee != null){
				logger.info("in finally");
			}
		}
	}
}
