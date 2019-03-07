package com.hydra.core.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileResultSet<T> {
    /**
     * 总记录数
     */
    private int        totalCount;
    /**
     * 解析成功记录数
     */
    private int        successCount;
    /**
     * 解析失败记录数
     */
    private int        failCount;
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    /**
     * 文件生成日期
     */
    private Date       fileDate;
    /**
     * 解析结果集,只包含解析成功的记录
     */
    private List<T>    resultList = new ArrayList<T>();
    /**
     * 解析失败记录集
     */
    private List<T>    failList   = new ArrayList<T>();
}
