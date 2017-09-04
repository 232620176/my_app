package com.hydra.blank.db.service;

import java.util.List;
import java.util.Map;

import oracle.core.lmx.CoreException;
/**
* 数据缓存查询
* <p>
* Created on 2014年11月18日 上午10:37:39
* see @table parameters
* </p>
* @author: Hydra wangshuang@lakala.com
* @since: 2014年11月18日
 */
public interface ParametersQueryService {
	/**
	* 获取系统参数
	* @param paramName：参数名
	* @return
	* @returnType String
	* @author: Hydra wangshuang@lakala.com
	* @date: 2014年11月17日 下午5:36:27
	 */
	public String get(String paramName);
	
	/**
	* 根据列表名取参数列表
	* @param listName
	* @param prefix
	* @param suffix
	* @return
	* @throws CoreException
	* @returnType List<String>
	* @author: Hydra wangshuang@lakala.com
	* @date: 2014年11月18日 上午10:42:41
	 */
	public List<Map<String, Object>> getList(String listName, String prefix, String suffix);
	
}
