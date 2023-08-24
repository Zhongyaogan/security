package com.security_03.vo;

import lombok.Data;

@Data
public class UserOperationCountVo {
    //登陆操作总数
    private Integer operationCount;

    //登陆操作类型
    private String operationType;



    public Integer getOperationCount() {
        return operationCount;
    }

    public void setOperationCount(Integer operationCount) {
        this.operationCount = operationCount;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public UserOperationCountVo(Integer operationCount, String operationType) {
        this.operationCount = operationCount;
        this.operationType = operationType;
    }
}
