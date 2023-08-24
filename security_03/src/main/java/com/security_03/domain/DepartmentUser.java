package com.security_03.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * (DepartmentUser)实体类
 * 部门组与用户关系实体，用于表示部门与用户间多对多的关系
 * @author makejava
 * @since 2023-08-01 14:04:07
 */
@Data
@Repository
@AllArgsConstructor
@NoArgsConstructor
@TableName("department_user")
public class DepartmentUser implements Serializable {
    private static final long serialVersionUID = 343877748814710578L;
    
    private Integer id;
    
    private Integer departmentId;
    
    private Integer userId;
}

