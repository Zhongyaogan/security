package com.security_03.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * (PermissionDepartment)实体类
 *
 * @author makejava
 * @since 2023-07-26 10:36:11
 */
@Data
@TableName("permission_department")
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class PermissionDepartment implements Serializable {
    private static final long serialVersionUID = 798246031232359662L;

    private Integer permissionId;
    
    private Integer departmentId;

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

}

