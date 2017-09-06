package com.hydra.core.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hydra.core.db.service.ParametersQueryService;

public class ParametersQueryServiceImpl implements ParametersQueryService {
	@Autowired
	private ParametersQueryService _ParametersService;
	
	@Override
	public String get(String paramName) {
		return _ParametersService.get(paramName);
	}
	
	@Override
	public List<Map<String, Object>> getList(String listName, String prefix, String suffix) {
		return _ParametersService.getList(listName, prefix, suffix);
	}
}
