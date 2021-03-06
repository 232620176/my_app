package com.hydra.core.util;

import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ConfigUtil {
    public static String get(String key) {
        return PRO.getProperty(key);
    }

    public static void load(String path) {
        synchronized (PRO) {
            PRO.clear();
            InputStream is = null;
            try {
                is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
                PRO.load(is);
            } catch (Exception e) {
                log.error("{}", e);
            } finally {
                IOUtil.closeSilence(is);
            }
        }
    }

    public static void init(String param) {
        if (PARAM_PROD.equals(param)) {
            load(PROD_FILE_NAME);
        } else {
            load(DEV_FILE_NAME);
        }
    }

    public static void main(String[] args) {
        init("");
        log.info(get("db.username"));
        init(PARAM_PROD);
        log.info(get("db.username"));
    }

    private ConfigUtil() {
        throw new UnsupportedOperationException();
    }

    private static final Properties PRO            = new Properties();
    public final static String      DEV_FILE_NAME  = "conf/dev.properties";
    public final static String      PARAM_PROD     = "prod";
    public final static String      PROD_FILE_NAME = "conf/prod.properties";
}
