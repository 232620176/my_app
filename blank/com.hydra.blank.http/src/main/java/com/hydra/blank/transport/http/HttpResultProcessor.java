/**<p>Title: HttpResultProcessor.java</p>
 * @Package com.lakala.mfs.service.transport.http
 * <p>Description: TODO</p>
 * @author Hydra wangshuang@lakala.com
 * @date 2017年6月29日 下午4:33:10
 * @version V1.0
 */
package com.hydra.blank.transport.http;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hydra.blank.common.Dict;
import com.hydra.blank.util.JsonUtil;
import com.hydra.blank.util.StringUtil;


/**
 * 处理HttpTransport返回数据
 * <p>
 * Created on 2017年6月29日 下午4:33:10
 * </p>
 * @author Hydra wangshuang@lakala.com
 * @since 2017年6月29日
 */
public class HttpResultProcessor {
	// 静态工具类，防误生成
	private HttpResultProcessor(){throw new UnsupportedOperationException();}
	private static Logger logger = LoggerFactory.getLogger(HttpResultProcessor.class);
	
	public static Map<String, Object> processResult(String result) throws Exception{
		Map<String, Object> map = str2Map(result);
		if("true".equals(map.get("result")) || "00".equals(map.get(Dict._RETURNCODE)) || (StringUtil.isEmpty(map.get("result")) && StringUtil.isEmpty(map.get(Dict._RETURNCODE)))){
			map.remove(Dict._RETURNCODE);
			map.remove(Dict._RETURNMSG);
			return map;
		}else{
			if(StringUtil.isEmpty(map.get(Dict._RETURNCODE))){
				throw new Exception((String)map.get("errordesc"));
			}else{
				throw new Exception((String)map.get(Dict._RETURNMSG));
			}
		}
	}
	
	/**
	 * <p>Title: processLUCResult</p>
	 * <p>Description: 处理集团用户中心返回结果</p>
	 * @param result
	 * @return
	 * @throws CoreException Map<String,Object>
	 */
	public static Map<String, Object> processLUCResult(String result) throws Exception{
		Map<String, Object> map = str2Map(result);
		if("00".equals(map.get("returnCode"))){
			map.remove(Dict._RETURNCODE);
			map.remove(Dict._RETURNMSG);
			return map;
		}else{
			if(StringUtil.isEmpty(map.get(Dict._RETURNCODE))){
				throw new Exception((String)map.get("returnMsg"));
			}else{
				throw new Exception((String)map.get(Dict._RETURNMSG));
			}
		}
	}
	/**
	 * <p>Title: processLFEResult</p>
	 * <p>Description: 处理金交所返回结果</p>
	 * @param result
	 * @return
	 * @throws CoreException Map<String,Object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> processLFEResult(String result) throws Exception{
		Map<String, Object> map = str2Map(result);
		Map<String, Object> dataMap = null;
		if("00".equals(map.get(Dict._RETURNCODE))){
			map.remove(Dict._RETURNCODE);
			map.remove(Dict._RETURNMSG);
			dataMap = (Map<String, Object>) map.get("data");
			return dataMap;
		}else{
			throw new Exception((String)map.get(Dict._RETURNMSG));
		}
	}
	/**
	 * <p>Title: processLFPResult</p>
	 * <p>Description: 处理理财平台返回结果</p>
	 * @param result
	 * @return
	 * @throws CoreException Map<String,Object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> processLFPResult(String result) throws Exception{
		Map<String, Object> map = str2Map(result);
		Map<String, Object> dataMap = null;
		if("00".equals(map.get(Dict._RETURNCODE))){
			map.remove(Dict._RETURNCODE);
			map.remove(Dict._RETURNMSG);
			dataMap = (Map<String, Object>) map.get("data");
			return dataMap;
		}else{
			throw new Exception((String)map.get(Dict._RETURNMSG));
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> str2Map(String str) throws Exception{
		Map<String, Object> map;
		try {
			map = JsonUtil.objectFromJson(str, Map.class);
			return map;
		} catch (Exception e) {
			logger.error("{}", e);
			throw new Exception(e.getMessage());
		}
	}
}
