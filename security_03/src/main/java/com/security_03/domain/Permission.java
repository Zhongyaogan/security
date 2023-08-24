package com.security_03.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * (Permission)实体类
 * 用于描述部门权限
 * @author makejava
 * @since 2023-07-26 10:35:48
 */
@Data
@TableName("permission")
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class Permission implements Serializable {
    private static final long serialVersionUID = 429944574009579663L;

    @TableId
    private Integer permissionId;
    
    private String permissionType;
    
    private String permissionDescription;
    
    private String title;
    
    private String url;


    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

