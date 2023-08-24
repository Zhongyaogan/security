package com.security_03.vo;

import com.security_03.domain.UserOperation;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class UserOperationVo {
    private Long userId;

    private String userName;

    //用户操作类型
    private String operationType;

    //用户操作时间
    private Timestamp operationTime;

    //用户操作ip地址信息
    private String ipAddress;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public ZonedDateTime getOperationTime() {
        Instant instant = operationTime.toInstant();
        ZoneId beijingZone = ZoneId.of("Asia/Shanghai");
        ZonedDateTime operationTimeBerJing = instant.atZone(beijingZone);
        return operationTimeBerJing;
    }

    public void setOperationTime(Timestamp operationTime) {
        this.operationTime = operationTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public UserOperationVo(Long userId, String userName, String operationType, Timestamp operationTime, String ipAddress) {
        this.userId = userId;
        this.userName = userName;
        this.operationType = operationType;
        this.operationTime = operationTime;
        this.ipAddress = ipAddress;
    }

    public UserOperationVo(UserOperation userOperation){
        this.userId = userOperation.getUserId();
        this.userName = userOperation.getUserName();
        this.operationType = userOperation.getOperationType();
        this.operationTime = userOperation.getOperationTime();
        this.ipAddress = userOperation.getipAddress();
    }
}
