package com.security_03.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.security_03.domain.User;
import com.security_03.mapper.UserMapper;
import com.security_03.result.ResponseResult;
import com.security_03.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseResult register(User user) {
        //获取前端注册的用户示例，根据用户名
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User userCheck = userMapper.selectOne(queryWrapper.eq("user_name",user.getUserName()));

//        判断前端注册请求用户示例是否存在，如果不存在则存入，如果存在则返回存在
        if(Objects.isNull(userCheck)){
            //用BCryptPasswordEncoder对用户密码加密
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encryptedPassWord = encoder.encode(user.getPassword());
            user.setPassword(encryptedPassWord);

//            将加密后的用户数据存入数据库
            userMapper.insert(user);
            return new ResponseResult(200,"注册成功",user);
        }else {
            return new ResponseResult(200,"注册失败",null);
        }
    }
}
