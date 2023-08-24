package com.security_03.service;

import com.security_03.domain.User;
import com.security_03.result.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    ResponseResult login(User user);

    ResponseResult loginOut();

    ResponseResult loginCount();

    ResponseResult loginChangePassword(String oldPassword,String newPassword);

    //根据页数与页数行数获取所有用户信息
    ResponseResult getAllUserDescrption(Integer pageNum,Integer pageSize);


    //修改用户权限信息
    ResponseResult changeUserPermission(Long UserId, String DepartmentDescription);

//    String getUserPermisson();
}
