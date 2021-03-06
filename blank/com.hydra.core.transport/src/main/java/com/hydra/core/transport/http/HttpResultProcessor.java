/**<p>Title: HttpResultProcessor.java</p>
 * @Package com.lakala.mfs.service.transport.http
 * <p>Description: TODO</p>
 * @author Hydra wangshuang@lakala.com
 * @date 2017年6月29日 下午4:33:10
 * @version V1.0
 */
package com.hydra.core.transport.http;

import java.util.Map;

import com.hydra.core.common.Dict;
import com.hydra.core.util.JsonUtil;
import com.hydra.core.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 处理HttpTransport返回数据
 * <p>
 * Created on 2017年6月29日 下午4:33:10
 * </p>
 * 
 * @author Hydra wangshuang@lakala.com
 * @since 2017年6月29日
 */
@Slf4j
public class HttpResultProcessor {
    // 静态工具类，防误生成
    private HttpResultProcessor() {
        throw new UnsupportedOperationException();
    }

    public static Map<String, Object> processResult(String result) throws Exception {
        Map<String, Object> map = str2Map(result);
        if ("true".equals(map.get("result")) || "00".equals(map.get(Dict._RETURNCODE))
                || (StringUtil.isEmpty(map.get("result")) && StringUtil.isEmpty(map.get(Dict._RETURNCODE)))) {
            map.remove(Dict._RETURNCODE);
            map.remove(Dict._RETURNMSG);
            return map;
        } else {
            if (StringUtil.isEmpty(map.get(Dict._RETURNCODE))) {
                throw new Exception((String) map.get("errordesc"));
            } else {
                throw new Exception((String) map.get(Dict._RETURNMSG));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> str2Map(String str) throws Exception {
        Map<String, Object> map;
        try {
            map = JsonUtil.objectFromJson(str, Map.class);
            return map;
        } catch (Exception e) {
            log.error("{}", e);
            throw new Exception(e.getMessage());
        }
    }
}
