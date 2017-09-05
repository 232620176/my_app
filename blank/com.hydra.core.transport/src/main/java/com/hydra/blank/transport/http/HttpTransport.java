package com.hydra.blank.transport.http;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hydra.blank.common.Dict;
import com.hydra.blank.util.JsonUtil;
import com.hydra.blank.util.MapUtil;
import com.hydra.blank.util.StringUtil;

/**
* HTTP请求工具类
* <p>
* Created on 2015年10月9日 下午12:08:04
* </p>
* @since: 2015年10月9日
 */
public class HttpTransport extends AbstractHttpTransport {
	@Override
	public String doSubmit(String url, Map<String, Object> params,
			HttpMethodProcessor processor) throws Exception {
		//处理渠道号
		String subChannelId = MapUtil.get(params, Dict._SUBCHANNELID);
		if(StringUtil.isEmpty(subChannelId)){
			throw new Exception("未定义渠道");
		}
		String systemCode = channel.getProperty(subChannelId);
		if(systemCode == null){
			params.put("chncode", DEFAULT_CHANNEL_ID);
		}else{
			params.put("chncode", systemCode);
		}
		logger.debug(MapUtil.get(params, Dict._PROCESSID) + " params: " + params);
		
		//处理参数
		HttpMethod method = processor.process(url, params);
		
		//发送
		try {
			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				throw new Exception(method.getStatusLine().toString());
			} else {
				String str = method.getResponseBodyAsString();
				str = str.replaceAll("\r\n", "").trim();
				logger.debug(str);
				return str;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			method.releaseConnection();
		}
	}
	
	/**
	 * PostMethod简单处理器，参数形式：key-value
	 * <p>
	 * Created on 2017年6月30日 上午11:19:29
	 * </p>
	 * @author Hydra wangshuang@lakala.com
	 * @since 2017年6月30日
	 */
	public static class SimplePostMethodProcessor implements HttpMethodProcessor{
		/* (non-Javadoc)
		 * @Title: process
		 * @Description: key-value对形式传参
		 * @param url 链接地址
		 * @param params 参数列表
		 * @return HttpMethod
		 * @see com.lakala.mfs.service.transport.http.AbstractHttpTransport.HttpMethodProcessor#process(java.lang.String, java.util.Map)
		 */
		@Override
		public HttpMethod process(String url, Map<String, Object> params) {
			PostMethod method = new PostMethod(url);
			for(Iterator<String> it = params.keySet().iterator();it.hasNext();){
				String key = it.next();
				String value = null;
				if(params.get(key) != null){
					value = params.get(key).toString();
				}
				method.addParameter(key, value);
			}
			return method;
		}
		
	}
	/**
	 * PostMethod处理器，参数形式：json
	 * <p>
	 * Created on 2017年6月30日 上午11:20:52
	 * </p>
	 * @author Hydra wangshuang@lakala.com
	 * @since 2017年6月30日
	 */
	public static class JsonParamPostMethodProcessor implements HttpMethodProcessor{
		/* (non-Javadoc)
		 * @Title: process
		 * @Description: key-value对形式传参
		 * @param url 链接地址
		 * @param params 参数列表
		 * @return HttpMethod
		 * @see com.lakala.mfs.service.transport.http.AbstractHttpTransport.HttpMethodProcessor#process(java.lang.String, java.util.Map)
		 */
		@Override
		public HttpMethod process(String url, Map<String, Object> params) {
			PostMethod method = null;
			try {
				method = new PostMethod(url);
				String transJson = new String(JsonUtil.jsonFromObject(params, "UTF-8"));
				RequestEntity se = new StringRequestEntity(transJson, "application/json", "UTF-8");
				method.setRequestEntity(se);
			} catch (UnsupportedEncodingException e) {
				logger.error("{}", e);
			}
			return method;
		}
		
	}
	
	/**
	 * GetMehtod简单处理器，参数形式：url拼接
	 * <p>
	 * Created on 2017年6月30日 上午11:21:06
	 * </p>
	 * @author Hydra wangshuang@lakala.com
	 * @since 2017年6月30日
	 */
	public static class SimpleGetMehtodProcessor implements HttpMethodProcessor {
		/* (non-Javadoc)
		 * @Title: process
		 * @Description: get方式提交时的参数处理
		 * @param url 链接地址
		 * @param params 参数列表
		 * @return
		 * @see com.lakala.mfs.service.transport.http.AbstractHttpTransport.HttpMethodProcessor#process(java.lang.String, java.util.Map)
		 */
		@Override
		public HttpMethod process(String url, Map<String, Object> params) {
			StringBuilder sb = new StringBuilder(url);
			boolean markFlag = false;
			String value;
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				Object val = entry.getValue();
				if(val != null){
					value = val.toString();
				}else{
					value = "";
				}
				if(markFlag || url.indexOf("?") > 0){
					sb.append("&");
				}else{
					sb.append("?");
				}
				sb.append(entry.getKey()).append("=").append(value);
				markFlag = true;
			}
			HttpMethod method = new GetMethod(sb.toString());
			return method;
		}
	}
	
	/**
	 * GetMehtod简单处理器，参数形式：key-value
	 * <p>
	 * Created on 2017年6月30日 上午11:21:44
	 * </p>
	 * @author Hydra wangshuang@lakala.com
	 * @since 2017年6月30日
	 */
	public static class KeyValuePairGetMehtodProcessor implements HttpMethodProcessor {
		/* (non-Javadoc)
		 * @Title: process
		 * @Description: get方式提交时的参数处理
		 * @param url 链接地址
		 * @param params 参数列表
		 * @return
		 * @see com.lakala.mfs.service.transport.http.AbstractHttpTransport.HttpMethodProcessor#process(java.lang.String, java.util.Map)
		 */
		@Override
		public HttpMethod process(String url, Map<String, Object> params) {
			HttpMethod method = new GetMethod(url);
			HttpMethodParams nameValuePair = new HttpMethodParams();
			String value;
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				Object val = entry.getValue();
				if(val != null){
					value = val.toString();
				}else{
					value = "";
				}
				nameValuePair.setParameter(entry.getKey(), value);
			}
			method.setParams(nameValuePair);
			return method;
		}
	}
	
	public static final String DEFAULT_CHANNEL_ID = "LAKALA";
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Properties channel;
	public void setChannel(Properties channel) {
		this.channel = channel;
	}
}
