package com.hydra.core.db.service;

/**
* 数据缓存设置
* <p>
* Created on 2014年11月18日 上午10:37:39
* see @table parameters
* </p>
* @author: Hydra wangshuang@lakala.com
* @since: 2014年11月18日
 */
public interface ParametersOperateService {
	/**
	* 设置系统参数，存在时更新，不存在时插入
	* @param paramName：参数名
	* @param paramValue：参数值
	* @returnType void
	* @author: Hydra wangshuang@lakala.com
	* @date: 2014年11月17日 下午5:36:41
	 */
	public void set(String paramName, String paramValue);
}
