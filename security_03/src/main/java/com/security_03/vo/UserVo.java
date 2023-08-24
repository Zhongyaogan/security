package com.security_03.vo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.security_03.domain.User;
import com.security_03.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//用户信息返回类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 用户所属部门名称
     */
    private String departmentName;

}
