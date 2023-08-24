package com.security_03.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * (Department)实体类
 * 用户描述部门组
 * @author makejava
 * @since 2023-07-26 10:35:16
 */
@Data
@TableName("department")
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class Department implements Serializable {
    private static final long serialVersionUID = 957798030207481591L;

    @TableId
    private Integer departmentId;
    
    private String departmentDescription;
    
    private String departmentName;


    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Department(String departmentDescription, String departmentName) {
        this.departmentDescription = departmentDescription;
        this.departmentName = departmentName;
    }
}

