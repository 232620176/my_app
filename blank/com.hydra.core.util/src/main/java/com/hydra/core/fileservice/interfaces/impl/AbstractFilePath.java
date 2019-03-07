package com.hydra.core.fileservice.interfaces.impl;

import java.util.Date;

import com.hydra.core.fileservice.interfaces.IFilePath;
import com.hydra.core.pojo.SFTPConfig;

public abstract class AbstractFilePath implements IFilePath {
    /**
     * 获取配置对象
     *
     * @return
     */
    protected abstract SFTPConfig getSFTPConfig();

    @Override
    public String getLocalFileDir(Date date) {
        return processFileDir(getSFTPConfig().getLocalRootDir());
    }

    @Override
    public String getLocalFileName(Date date) {
        return getRemoteFileName(date);
    }

    @Override
    public String getLocalAbsFilePath(Date date) {
        Date dt = processDate(date);
        return getLocalFileDir(dt) + getLocalFileName(dt);
    }

    @Override
    public abstract String getRemoteFileDir(Date date);

    @Override
    public abstract String getRemoteFileName(Date date);

    @Override
    public String getRemoteAbsFilePath(Date date) {
        Date dt = processDate(date);
        return getRemoteFileDir(dt) + getRemoteFileName(dt);
    }

    protected Date processDate(Date date) {
        Date dt = date;
        if (null == date) {
            dt = new Date();
        }
        return dt;
    }

    protected String processFileDir(String dir) {
        if (dir.endsWith(SEPARATOR)) {
            return dir;
        } else {
            return dir + SEPARATOR;
        }
    }

    protected static final String SEPARATOR = "/";
}
