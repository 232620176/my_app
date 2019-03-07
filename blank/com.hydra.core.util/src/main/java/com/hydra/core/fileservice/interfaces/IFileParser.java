package com.hydra.core.fileservice.interfaces;

import com.hydra.core.fileservice.exception.FileException;
import com.hydra.core.pojo.FileResultSet;

/**
 * 
 * @author wangshuang
 *
 * @param <T>
 */
public interface IFileParser<T> {
    /**
     * 解析文件
     *
     * @param fileName
     * @return
     * @throws FileException
     */
    FileResultSet<T> parse(String fileName) throws FileException;
}
