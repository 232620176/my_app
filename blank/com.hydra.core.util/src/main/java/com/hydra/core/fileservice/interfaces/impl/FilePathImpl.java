package com.hydra.core.fileservice.interfaces.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hydra.core.pojo.SFTPConfig;
import com.hydra.core.util.DateUtil;

/**
 * @author ZhangJF
 * @Description:
 * @date 2018/4/10 14:40
 */
@Service("filePath")
public class FilePathImpl extends AbstractFilePath {
    @Resource
    private SFTPConfig sftpConfig;

    @Override
    public String getRemoteFileDir(Date date) {
        Date dt = processDate(date);
        return sftpConfig.getDownloadRootDir() + SEPARATOR + DateUtil.formatDate(dt, DateUtil.PATTERN_YYYYMMDD)
                + SEPARATOR;
    }

    @Override
    public String getRemoteFileName(Date date) {
        Date dt = processDate(date);
        return "SxxOfflineRepay" + DateUtil.formatDate(dt, DateUtil.PATTERN_YYYYMMDD) + ".txt";
    }

    @Override
    public String getRemoteAbsFilePath(Date date) {
        return getRemoteFileDir(date) + getRemoteFileName(date);
    }

    /**
     * 获取配置对象 。
     *
     * @return
     */
    @Override
    protected SFTPConfig getSFTPConfig() {
        return sftpConfig;
    }
}
