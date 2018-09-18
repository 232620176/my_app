package com.hydra.blank.web.json.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hydra.blank.web.annotation.ServletLog;
import com.hydra.core.db.service.ParametersQueryService;
import com.hydra.core.util.MD5;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/sign")
@Slf4j
public class MD5SignController {
    @ServletLog
    @RequestMapping(value = "/{text}/{key}", method = RequestMethod.GET)
    public @ResponseBody String sign(@PathVariable String text, @PathVariable String key) {
        String res = MD5.sign(text, key);
        log.info(key);
        return res;
    }

    @ServletLog
    @RequestMapping(value = "/{text}", method = RequestMethod.GET)
    public @ResponseBody String sign(@PathVariable String text) {
        String key = pqs.get("WX_QRCode_Payback_Key");
        String res = sign(text, key);
        return res;
    }

    @Autowired
    @Qualifier("parametersQueryService")
    private ParametersQueryService pqs;
}
