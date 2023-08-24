package com.security_03.vo;

import com.security_03.domain.UserOperation;
import lombok.Data;

import java.util.List;

@Data
public class UserOperationRecordVo {
    private int total;
    private List<UserOperationVo> operationRecordList;

    public UserOperationRecordVo(int total, List<UserOperationVo> operationRecordList) {
        this.total = total;
        this.operationRecordList = operationRecordList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<UserOperationVo> getOperationRecordList() {
        return operationRecordList;
    }

    public void setOperationRecordList(List<UserOperationVo> operationRecordList) {
        this.operationRecordList = operationRecordList;
    }

    @Override
    public String toString() {
        return "UserOperationRecordVo{" +
                "total=" + total +
                ", operationRecordList=" + operationRecordList +
                '}';
    }
}
