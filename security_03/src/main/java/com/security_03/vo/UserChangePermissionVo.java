package com.security_03.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePermissionVo {
    private String userId;
    private String departmentDescription;
}
