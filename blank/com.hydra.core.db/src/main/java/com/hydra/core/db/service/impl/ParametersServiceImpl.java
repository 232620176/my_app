package com.hydra.core.db.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.hydra.core.db.service.ParametersOperateService;
import com.hydra.core.db.service.ParametersQueryService;
import com.hydra.core.db.sqlmap.SqlMap;
import com.hydra.core.util.MapUtil;
import com.hydra.core.util.StringUtil;
/**
* <p>
* Created on 2014年11月11日 下午3:31:07
* </p>
* @author: Hydra wangshuang@lakala.com
* @since: 2014年11月11日
 */
public class ParametersServiceImpl implements ParametersQueryService, 
		ParametersOperateService, InitializingBean {
	private static final ExecutorService executor = Executors.newFixedThreadPool(4);
	private static final Integer DEFAULT_INTEVAL = 5;
	private static final String SEPARATOR = ";";
	private static final String VALUE = "Value";
	
	private Integer interval;
	private LoadingCache<String, Map<String, Object>> cache = null;
	private LoadingCache<String, List<Map<String, Object>>> cacheList = null;
	private Logger logger = LoggerFactory.getLogger(ParametersServiceImpl.class);
	@Autowired
	private SqlMap sqlMap;
	
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	public Integer getInterval() {
		if(this.interval != null)
			return interval;
		else
			return DEFAULT_INTEVAL;
	}
	
	@Override
	public String get(String paramName) {
		Map<String, Object> res = cache.getUnchecked(paramName);
		if(null == res || res.size() == 0){
			return null;
		}
		logger.debug("\n--------------{}", res);
		return (String)res.get(VALUE);
	}
	
	@Override
	public void set(String paramName, String paramValue) {
		Map<String, Object> param = MapUtil.getMap(5);
		param.put("key", paramName);
		param.put("value", paramValue);
		logger.debug("\n--------------参数{}的值变更为{}", paramName, paramValue);
		int total = sqlMap.update("param.updateParameterByKey", param);
		if(total < 1){
			sqlMap.insert("param.addNewParameter", param);
		}
		
		Map<String, Object> data = cache.getUnchecked(paramName);
		data.put(VALUE, paramValue);
	}
	
	@Override
	public List<Map<String, Object>> getList(String listName, String prefix, String suffix) {
		if(StringUtil.isEmpty(prefix) || StringUtil.isEmpty(suffix)){
			throw new RuntimeException("前缀或后缀不能为空");
		}
		StringBuffer sb = new StringBuffer(listName).append(SEPARATOR)
				.append(prefix).append(SEPARATOR).append(suffix);
		return cacheList.getUnchecked(sb.toString());
	}
	
	private String processNullToString(String str){
		String res = str;
		if(StringUtil.isEmpty(res)){
			res = "";
		}
		return res;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		CacheLoader<String, Map<String, Object>> loader = new CacheLoader<String, Map<String, Object>>() {
			public Map<String, Object> load(String key) {
				return build(key);
			}
			public ListenableFuture<Map<String, Object>> reload(final String key, final Map<String, Object> prevGraph) {
				ListenableFutureTask<Map<String, Object>> task = ListenableFutureTask.create(new Callable<Map<String, Object>>() {
					public Map<String, Object> call() {
						return build(key);
					}
				});
				executor.execute(task);
				return task;
			}
		};
		
		CacheLoader<String, List<Map<String, Object>>> loaderList = new CacheLoader<String, List<Map<String, Object>>>() {
			public List<Map<String, Object>> load(String key) throws Exception {
				return buildList(key);
			}
			
			public ListenableFuture<List<Map<String, Object>>> reload(final String key, final List<Map<String, Object>> prevGraph) {
				ListenableFutureTask<List<Map<String, Object>>> task = ListenableFutureTask.create(new Callable<List<Map<String, Object>>>() {
					public List<Map<String, Object>> call() throws Exception {
						return buildList(key);
					}
					
				});
				executor.execute(task);
				return task;
			}
		};
		
		cache = CacheBuilder.newBuilder().maximumSize(10).refreshAfterWrite(getInterval(), TimeUnit.MINUTES).build(loader);
		cacheList = CacheBuilder.newBuilder().maximumSize(10).refreshAfterWrite(getInterval(), TimeUnit.MINUTES).build(loaderList);
	}
	
	private Map<String, Object> build(String key){
		Map<String, Object> param = MapUtil.getMap(5);
		param.put("key", key);
		Map<String, Object> res = sqlMap.queryForObject("param.qryParameterByKey", param);
		if(null == res){
			res = MapUtil.getMap();
		}
		return res;
	}
	
	private List<Map<String, Object>> buildList(String key){
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		
		String[] names = key.split(SEPARATOR);
		String listName = names[0];
		String prefix = names[1];
		String suffix = names[2];
		
		Map<String, Object> listNameMap = cache.getUnchecked(listName);
		String listNameValue = (String)listNameMap.get(VALUE);
		String[] listNames = listNameValue.split(SEPARATOR);
		for(int i = 0; i < listNames.length; i++){
			String _prefix = processNullToString(prefix);
			String _suffix = processNullToString(suffix);
			StringBuffer fullName = new StringBuffer(_prefix);
			fullName.append(listNames[i]).append(_suffix);
			logger.debug("\n--------------fullName: {}", fullName);
			Map<String, Object> data = cache.getUnchecked(fullName.toString());
			res.add(data);
		}
		logger.debug("\n--------------return list: {}", res);
		return res;
	}
}
