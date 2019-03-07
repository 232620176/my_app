package com.hydra.core.pojo;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ValidationResult {
    //校验结果是否有错
    private boolean             hasErrors;
    //校验错误信息
    private Map<String, String> errorMsg;
}
