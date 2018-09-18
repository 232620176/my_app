package com.hydra.blank.web.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServletUtil {
    public static String getIpAddr(HttpServletRequest req) {
        String ipAddress = null;
        ipAddress = req.getHeader("x-forwarded-for");
        if (isEmpty(ipAddress)) {
            ipAddress = req.getHeader("Proxy-Client-IP");
        }
        if (isEmpty(ipAddress)) {
            ipAddress = req.getHeader("WL-Proxy-Client-IP");
        }
        if (isEmpty(ipAddress)) {
            ipAddress = req.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "localhost".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error("{}", e);
                }
                ipAddress = inet.getHostAddress();
            }

        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (null != ipAddress && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    private static boolean isEmpty(String ip) {
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            return true;
        } else {
            return false;
        }
    }

    // 静态工具类，防误生成
    public ServletUtil() {
        throw new UnsupportedOperationException();
    }
}
