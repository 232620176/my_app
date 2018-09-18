package com.hydra.core.transport.http;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

public abstract class AbstractHttpTransport {
    /**
     * 连接超时时间
     */
    private Integer                     connTimeout    = 30000;
    /**
     * 读取内容超时时间
     */
    private Integer                     soTimeout      = 30000;
    /**
     * 网页内容编码
     */
    protected String                    contentCharset = "UTF-8";

    protected HttpClient                httpClient     = null;
    private HttpConnectionManagerParams managerParams  = null;

    public AbstractHttpTransport() {
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setDefaultMaxConnectionsPerHost(20);
        params.setMaxTotalConnections(200);
        MultiThreadedHttpConnectionManager connectManager = new MultiThreadedHttpConnectionManager();
        connectManager.setParams(params);
        httpClient = new HttpClient(connectManager);
        managerParams = httpClient.getHttpConnectionManager().getParams();
        managerParams.setConnectionTimeout(connTimeout);
        managerParams.setSoTimeout(soTimeout);
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, contentCharset);
    }

    /**
     * <p>
     * Title: doSubmit
     * </p>
     * <p>
     * Description: 提交Http请求
     * </p>
     * 
     * @param url 地址
     * @param params 参数
     * @param processor Http方法处理器
     * @return String Http请求返回信息
     * @throws CoreException
     */
    public abstract String doSubmit(String url, Map<String, Object> params, HttpMethodProcessor processor)
            throws Exception;

    public Integer getConnTimeout() {
        return connTimeout;
    }

    public AbstractHttpTransport setConnTimeout(Integer connTimeout) {
        this.connTimeout = connTimeout;
        managerParams.setConnectionTimeout(connTimeout);

        return this;
    }

    public Integer getSoTimeout() {
        return soTimeout;
    }

    public AbstractHttpTransport setSoTimeout(Integer soTimeout) {
        this.soTimeout = soTimeout;
        managerParams.setSoTimeout(soTimeout);

        return this;
    }

    public String getContentCharset() {
        return contentCharset;
    }

    public AbstractHttpTransport setContentCharset(String contentCharset) {
        this.contentCharset = contentCharset;
        managerParams.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, contentCharset);
        return this;
    }

    public static interface HttpMethodProcessor {
        HttpMethod process(String url, Map<String, Object> params);
    }
}
