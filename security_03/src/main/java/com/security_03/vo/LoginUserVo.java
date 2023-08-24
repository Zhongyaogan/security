package com.security_03.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//登录用户返回封装类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserVo {
    //用户token
    private String token;
    //用户权限信息
    private List<PermissionDescriptVo> permissionDescriptVoList;
}
