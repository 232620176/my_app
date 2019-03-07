package com.hydra.core.fileservice.interfaces;

import java.io.InputStream;

/**
 * 类IFileDeliver.java的实现描述：文件操作接口类
 * 
 * @author wangshuang 2019年2月26日 上午10:50:08
 */
public interface IFileDeliver {
    /**
     * @param localDir
     * @param remoteFileName
     * @param remoteFileDir
     * @throws Exception
     */
    public void downloadFile(String localDir, String remoteFileName, String remoteFileDir) throws Exception;

    /**
     * @param remoteFileDir
     * @param remoteFileName
     * @param in
     * @throws Exception
     */
    public void uploadFile(String remoteFileDir, String remoteFileName, InputStream in) throws Exception;
}
