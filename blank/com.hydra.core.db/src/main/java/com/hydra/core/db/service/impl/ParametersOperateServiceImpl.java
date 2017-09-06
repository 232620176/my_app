package com.hydra.core.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hydra.core.db.service.ParametersOperateService;

public class ParametersOperateServiceImpl implements ParametersOperateService {
	@Autowired
	private ParametersOperateService _ParametersService;
	
	@Override
	public void set(String paramName, String paramValue) {
		_ParametersService.set(paramName, paramValue);
	}

}
