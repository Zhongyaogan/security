package com.security_03.controller;

import com.security_03.domain.User;
import com.security_03.result.ResponseResult;
import com.security_03.service.LoginService;
import com.security_03.service.RegisterService;
import com.security_03.vo.UserChangePasswordVo;
import com.security_03.vo.UserChangePermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private RegisterService registerService;


    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        return loginService.login(user);
    }


    @GetMapping("/user/loginOut")
    public ResponseResult loginOut(){
        return loginService.loginOut();
    }


    @PostMapping("/user/register")
    public ResponseResult register(@RequestBody User user){
        return registerService.register(user);
    }


    @GetMapping("/user/loginCount")
    public ResponseResult loginCount(){
        return loginService.loginCount();
    }

    //用户修改密码
    @PostMapping("/user/changePassword")
    public  ResponseResult changePassword(@RequestBody UserChangePasswordVo userChangePasswordVo){
        return loginService.loginChangePassword(userChangePasswordVo.getOldPassword(),userChangePasswordVo.getNewPassword());
    }

    @PostMapping("/user/getAllUserDescription/{pageNum}/{pageSize}")
    public  ResponseResult getAllUserDescription(@PathVariable int pageNum, @PathVariable int pageSize){
        return loginService.getAllUserDescrption(pageNum,pageSize);
    }

    @PostMapping("/user/changeUserPermission")
    public  ResponseResult changeUserPermission(@RequestBody UserChangePermissionVo userChangePermissionVo){
        return  loginService.changeUserPermission(Long.parseLong(userChangePermissionVo.getUserId()), userChangePermissionVo.getDepartmentDescription());
    }
}
