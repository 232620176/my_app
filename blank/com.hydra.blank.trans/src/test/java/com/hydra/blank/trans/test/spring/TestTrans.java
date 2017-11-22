package com.hydra.blank.trans.test.spring;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hydra.core.db.service.ParametersQueryService;

public class TestTrans extends BaseJunit4Test {
	@Autowired
	@Qualifier("parametersQueryService")
	private ParametersQueryService pqs;
	@Test
	public void testTrans(){
		System.out.println(pqs.get("000686"));
	}
}
