package com.security_03.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

//封装修改部门组权限的请求体
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeDepartmentPermissionVo {
    public String departmentDescription;
    public ArrayList<Integer> permissionList;
}
