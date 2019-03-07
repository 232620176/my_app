package com.hydra.core.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

/**
 * 类SFTPConfig.java的实现描述：SFTP配置类
 * 
 * @author wangshuang 2019年2月26日 上午10:49:48
 */
@Builder
@Getter
@Setter
@ToString
public class SFTPConfig {
    //SFTP服务相关配置
    private final String ip;
    private final int    port;
    private final String username;
    private final String password;
    //文件路径相关配置
    private String       downloadRootDir;
    private String       localRootDir;
    private String       uploadRootDir;
}
