package com.hydra.core.util;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class FileUtil {
    /**
     * 检查并创建文件夹
     * 
     * @param directoryName
     */
    public static void createFolder(String directoryName) {
        File theDir = new File(directoryName);
        if (!theDir.exists()) {
            log.info("directory {} not exist,creating.", directoryName);
            boolean result = false;
            try {
                theDir.mkdirs();
                result = true;
            } catch (SecurityException e) {
                log.warn("directory {} create fail:{}.", directoryName, e);
            }
            if (result) {
                log.info("dir created:" + theDir.exists());
            }
        }
    }

    /**
     * @param filePathAndName 文本文件完整绝对路径及文件名
     * @return Boolean 成功删除返回true遭遇异常返回false
     */
    public static boolean delFile(String filePathAndName) {
        log.info("删除文件：{}", filePathAndName);
        boolean bea = false;
        try {
            String filePath = filePathAndName;
            File myDelFile = new File(FilenameUtils.getFullPath(filePath) + FilenameUtils.getName(filePath));
            if (myDelFile.exists()) {
                myDelFile.delete();
                bea = true;
            } else {
                bea = false;
            }
        } catch (Exception e) {
            log.info("删除文件失败！filePath={}", filePathAndName, e);
        }
        return bea;
    }

    /**
     * 根据当前日期和配置的日期格式生成文件路径
     * 
     * @param dir
     * @return
     */
    public static String formatDir(String dir) {
        if (StringUtils.hasText(dir) && dir.contains("/")) {
            String[] paths = dir.split("/");
            int length = paths.length;
            String format = paths[length - 1];
            if (format.startsWith("{") && format.endsWith("}")) {
                dir = dir.replace(format, DateUtil.formatDate(DateUtil.addDate(new Date(), -1),
                        format.replace("{", "").replace("}", "")));
            }
        }
        return dir;
    }

    private FileUtil() {
        throw new UnsupportedOperationException("constructor: FileUtil()");
    }
}
