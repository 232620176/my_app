package com.hydra.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 类CommonSFTPKit.java的实现描述：SFTP工具类
 * 
 * @author wangshuang 2019年2月26日 上午9:59:45
 */
@Slf4j
@ToString
public final class CommonSFTPKit {
    /**
     * 连接SFTP
     */
    public void connect() {
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            // channel init
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            throw new RuntimeException("ftp connect exception," + this.toString(), e);
        }
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
            sftp = null;
        }
        if (sshSession != null) {
            if (sshSession.isConnected()) {
                sshSession.disconnect();
            }
            sshSession = null;
        }
    }

    public boolean download(String directory, String localDirectory, String dowanloadFile) throws Exception {
        log.info("directory={},localDirectory={},dowanloadFile={}", directory, localDirectory, dowanloadFile);
        boolean isDownload = false;
        String newLocalDir = FileUtil.formatDir(localDirectory);
        String newDirectory = FileUtil.formatDir(directory);
        log.info("SFTP 目录：" + directory + "; 下载到本地目录:" + newLocalDir + ";" + " 待下载文件:" + newDirectory);
        if (null != directory) {
            //检查本地文件夹是否存在
            FileUtil.createFolder(newLocalDir);
            try {
                sftp.cd(newDirectory);
                for (LsEntry file : listFiles(sftp.pwd())) {
                    if (file.getFilename().equals(dowanloadFile)) {
                        sftp.get(file.getFilename(), newLocalDir + dowanloadFile);
                        isDownload = true;
                        log.info("{},{}", file.getFilename(), newLocalDir + dowanloadFile);
                        break;
                    }
                }
            } catch (Exception e) {
                log.error("下载的文件目录 {}，下载失败{}", newDirectory, e);
                throw e;
            }
        }
        return isDownload;
    }

    @SuppressWarnings("unchecked")
    public Vector<LsEntry> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }

    public void mkDir(String dirName) {
        String[] dirs = dirName.split("/");
        try {
            for (int i = 0; i < dirs.length; i++) {
                boolean dirExists = openDir(dirs[i]);
                if (!dirExists) {
                    sftp.mkdir(dirs[i]);
                    sftp.cd(dirs[i]);
                }
            }
        } catch (SftpException e) {
            log.warn("mkDir{} Exception : {}", dirName, e);
        }
    }

    public boolean openDir(String directory) {
        try {
            sftp.cd(directory);
            return true;
        } catch (SftpException e) {
            log.info("openDir {} Exception :{} ", directory, e);
            return false;
        }
    }

    public void upload(String directory, String fileName, InputStream in) throws Exception {
        if (null != directory) {
            try {
                mkDir(directory);
            } catch (Exception e) {
                log.warn("创建文件目录  {} 失败:{}", directory, e);
                throw new FileNotFoundException("mkDir Exception : " + e);
            }
        }
        // 如果是空流，则创建一个空的流文件
        if (null == in) {
            in = new ByteArrayInputStream(new ByteArrayOutputStream().toByteArray());
        }
        // 正式环境的时候放开 sftp.put(in, fileName);
        sftp.put(in, fileName, ChannelSftp.APPEND);
    }

    public CommonSFTPKit(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    private final String host;
    private final int    port;
    private final String username;
    private final String password;
    private Session      sshSession = null;
    private ChannelSftp  sftp       = null;
}
