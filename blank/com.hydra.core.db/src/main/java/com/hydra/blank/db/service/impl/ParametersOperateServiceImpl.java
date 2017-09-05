package com.hydra.blank.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hydra.blank.db.service.ParametersOperateService;

public class ParametersOperateServiceImpl implements ParametersOperateService {
	@Autowired
	private ParametersOperateService _ParametersService;
	
	@Override
	public void set(String paramName, String paramValue) {
		_ParametersService.set(paramName, paramValue);
	}

}
