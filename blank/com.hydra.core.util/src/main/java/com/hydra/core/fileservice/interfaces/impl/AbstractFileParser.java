package com.hydra.core.fileservice.interfaces.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.hydra.core.fileservice.exception.FileException;
import com.hydra.core.fileservice.interfaces.IFileParser;
import com.hydra.core.pojo.FileResultSet;
import com.hydra.core.util.IOUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractFileParser<T> implements IFileParser<T> {
    public FileResultSet<T> parse(String fileName) throws FileException {
        FileResultSet<T> fileResultSet = new FileResultSet<>();
        BufferedReader reader = null;
        try {
            File file = new File(fileName);
            reader = new BufferedReader(new FileReader(file));
            readHeadLine(reader, fileResultSet);
            readDetailLine(reader, fileResultSet);
        } catch (IOException e) {
            log.warn("解析文件汇总信息发生异常,异常信息如下:", e);
            FileException fe = new FileException(e);
            throw fe;
        } finally {
            IOUtil.closeSilence(reader);
        }
        return fileResultSet;
    }

    public void readHeadLine(BufferedReader reader, FileResultSet<T> fileResultSet) throws FileException, IOException {
        //空实现，如需读取头信息可自行覆盖
    }

    public void readDetailLine(BufferedReader reader, FileResultSet<T> fileResultSet)
            throws FileException, IOException {
        String lineStr = null;
        while ((lineStr = reader.readLine()) != null) {
            if ("".equals(lineStr)) {
                continue;
            }
            try {
                transferObj(lineStr, fileResultSet);
            } catch (Exception e) {
                log.warn("解析文件明细信息发生异常,行:{},异常信息如下:{}", lineStr, e);
            }
        }
    }

    public abstract void transferObj(String lineStr, FileResultSet<T> fileResultSet) throws Exception;

    protected static final String SPEARTOR = "\\|";
}
