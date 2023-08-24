package com.security_03.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionVo {
    private Integer permissionId;
    private String permissType;

    //用于判断此权限是否是该部门组的
    private Boolean isHavePermission;
}
