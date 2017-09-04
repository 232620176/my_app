package com.hydra.blank.transport.test;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hydra.blank.common.Dict;
import com.hydra.blank.transport.http.HttpResultProcessor;
import com.hydra.blank.transport.http.HttpTransport;
import com.hydra.blank.util.CommonUtil;
import com.hydra.blank.util.MapUtil;

public class TestHttpTransport {
	public static void main(String[] args) throws Exception{
		ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"classpath*:spring/transport/transport.xml"});
		HttpTransport httpClient = CommonUtil.transfer(context.getBean("httpClient"));
		Map<String, Object> param = MapUtil.getMap();
		param.put(Dict._SUBCHANNELID, "100017");
		param.put("wxInx", 3);
		String url = "http://10.5.31.13:9060/adaptor/convert.do?_t=json&_TransCode=getWxAccessToken&wxInx=2";
		String res = httpClient.doSubmit(url, param, new HttpTransport.SimpleGetMehtodProcessor());
		param = HttpResultProcessor.processResult(res);
		System.out.println(param);
		((ClassPathXmlApplicationContext)context).close();
	}
}
