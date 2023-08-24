package com.security_03.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.io.Serializable;

/**
 * (UserOperation)实体类
 *
 * @author makejava
 * @since 2023-06-14 20:06:12
 */
@Data
@TableName("user_operation")
@Repository
public class UserOperation implements Serializable {
    private static final long serialVersionUID = 152500029468629552L;

    @TableId
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

    public Timestamp getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Timestamp  operationTime) {
        this.operationTime = operationTime;
    }

    public String getipAddress() {
        return ipAddress;
    }

    public void setipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public UserOperation(Long userId, String userName, String operationType) {
        this.userId = userId;
        this.userName = userName;
        this.operationType = operationType;
    }

    public UserOperation(Long userId, String userName, String operationType, String ipAddress) {
        this.userId = userId;
        this.userName = userName;
        this.operationType = operationType;
        this.ipAddress = ipAddress;
    }

    public UserOperation() {
    }
}

