package com.hydra.core.fileservice.interfaces.impl;

import java.io.InputStream;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hydra.core.fileservice.interfaces.IFileDeliver;
import com.hydra.core.pojo.SFTPConfig;
import com.hydra.core.util.CommonSFTPKit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("fileDeliver")
public class FileDeliverImpl implements IFileDeliver {
    @Resource
    private SFTPConfig sftpConfig;

    /**
     * @param localDir
     * @param remoteFileName
     * @param remoteFileDir
     * @throws Exception
     */
    public void downloadFile(String localDir, String remoteFileName, String remoteFileDir) throws Exception {
        log.info("localDir-{},remoteFileName-{},remoteFileDir-{}", localDir, remoteFileName, remoteFileDir);
        final Result<Boolean> res = new Result<>();
        log.info("res: {}", res);
        try {
            template(remoteFileDir, remoteFileName, new Callback() {
                @Override
                public void process(CommonSFTPKit sftpUtil) throws Exception {
                    res.setResult(sftpUtil.download(remoteFileDir, localDir, remoteFileName));
                }
            });
        } catch (Exception e) {
            log.warn("downLoad " + remoteFileName + " error ,remotoFile = '" + remoteFileDir + "', localPath = '"
                    + localDir + "'", e);
            throw new RuntimeException(e);
        }
        if (!res.getResult()) {
            log.warn("downLoad bestpay file:{} fail ,remotoFile:{}, localPath:{}", remoteFileName, remoteFileDir,
                    localDir);
            throw new RuntimeException(remoteFileName + " not exist");
        }
    }

    /**
     * @param remoteFileDir
     * @param remoteFileName
     * @param in
     * @throws Exception
     */
    public void uploadFile(String remoteFileDir, String remoteFileName, InputStream in) throws Exception {
        try {
            template(remoteFileDir, remoteFileName, new Callback() {
                @Override
                public void process(CommonSFTPKit sftpUtil) throws Exception {
                    sftpUtil.upload(remoteFileDir, remoteFileName, in);
                }
            });
        } catch (Exception e) {
            log.warn("upload " + remoteFileName + " error ,remotoFile = '" + remoteFileDir + "'", e);
            throw new RuntimeException(e);
        }
    }

    private void template(String remoteFileDir, String remoteFileName, Callback callback) throws Exception {
        CommonSFTPKit sftpUtil = new CommonSFTPKit(sftpConfig.getIp(), sftpConfig.getPort(), sftpConfig.getUsername(),
                sftpConfig.getPassword());
        try {
            sftpUtil.connect();
            callback.process(sftpUtil);
        } finally {
            sftpUtil.disconnect();
        }
    }
}

interface Callback {
    void process(CommonSFTPKit sftpUtil) throws Exception;
}

@Getter
@Setter
@ToString
class Result<T> {
    private T result;
}
