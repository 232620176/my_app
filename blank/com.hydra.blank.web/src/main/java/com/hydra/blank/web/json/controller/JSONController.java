package com.hydra.blank.web.json.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hydra.core.db.service.ParametersQueryService;

@Controller
@RequestMapping("/param/query")
public class JSONController {
	@RequestMapping(value="{name}", method=RequestMethod.GET)
	public @ResponseBody String getConfiguration(@PathVariable String name){
		String res = pqs.get(name);
		return res;
	}
	
	@Autowired
	@Qualifier("parametersQueryService")
	private ParametersQueryService pqs;
}
