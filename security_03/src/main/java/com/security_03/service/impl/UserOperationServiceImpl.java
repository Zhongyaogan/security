package com.security_03.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.security_03.constants.SystemConstants;
import com.security_03.domain.LoginUser;
import com.security_03.domain.User;
import com.security_03.domain.UserOperation;
import com.security_03.mapper.UserMapper;
import com.security_03.mapper.UserOperationMapper;
import com.security_03.result.ResponseResult;
import com.security_03.service.UserOperationService;
import com.security_03.vo.UserOperationRecordVo;
import com.security_03.vo.UserOperationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserOperationServiceImpl implements UserOperationService {
    @Resource
    private UserOperationMapper userOperationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HttpServletRequest request;

    @Override
    public ResponseResult queryOperationDescription(int pageNum, int pageSize) {
        //验证用户是否有权限获取操作记录
        //从SecurityContextHolder获取认证信息
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //通过token获取当前登录用户实体数据
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        if (!userMapper.selectById(userId).getDepartmentId().equals(SystemConstants.ADMIN)){
            return new ResponseResult(200,"您无权限查看用户操作");
        }

        int total = userOperationMapper.getTotal();
        //判断分页查询数据是否超限
        if ((pageNum-2)*pageSize+pageSize >= total){
            return new ResponseResult(200,"查询超限，查询所有结果已返回");
        }

        if (pageNum<0 || pageSize<0)
            return  new ResponseResult(200,"查询页数或页面行数有误");

        List<UserOperation> userOperationList = userOperationMapper.getUserOperationX10((pageNum-1)*pageSize,pageSize);
        List<UserOperationVo> userOperationVoList = new ArrayList<>();
        userOperationList.forEach(userOperation -> {
            userOperationVoList.add(new UserOperationVo(userOperation));
        });

        UserOperationRecordVo userOperationRecordVo = new UserOperationRecordVo(total,userOperationVoList);
        return new ResponseResult(200,userOperationRecordVo);
    }

    @Override
    public ResponseResult queryOperationDescriptionByName(int pageNum, int pageSize, String userName) {
        //验证用户是否有权限获取操作记录
        //从SecurityContextHolder获取认证信息
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //通过token获取当前登录用户实体数据
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        if (!userMapper.selectById(userId).getDepartmentId().equals(SystemConstants.ADMIN)){
            return new ResponseResult(200,"您无权限查看用户操作");
        }

        //获取查询条件返回总数
        LambdaQueryWrapper<UserOperation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserOperation::getUserName,userName);
        int total = userOperationMapper.selectCount(queryWrapper);

        //判断分页查询数据是否超限,传入参数无误
        if ((pageNum-2)*pageSize+pageSize >= total){
            return new ResponseResult(200,"查询超限，查询所有结果已返回");
        }
        if (pageNum<0 || pageSize<0)
            return  new ResponseResult(200,"查询页数或页面行数有误");

        //获取返回列表
        List<UserOperation> userOperationsList = userOperationMapper.getUserOperationByUserNameX10((pageNum-1)*pageSize,pageSize,userName);
        List<UserOperationVo> userOperationVoList = new ArrayList<>();
        userOperationsList.forEach(userOperation -> {
            userOperationVoList.add(new UserOperationVo(userOperation));
        });
        UserOperationRecordVo userOperationRecordVo = new UserOperationRecordVo(total,userOperationVoList);
        //为查询到时
        if (userOperationsList.isEmpty()){
            return new ResponseResult(200,"查询用户名结果为空");
        }
        //返回分页查询结果
        return new ResponseResult(200,userOperationRecordVo);
    }

    @Override
    public ResponseResult insertOperationDescription(String operationType) {
        //验证用户是否有权限获取操作记录
        //从SecurityContextHolder获取认证信息
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //通过token获取当前登录用户实体数据
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //h获取用户ip地址
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        userOperationMapper.insert(new UserOperation(loginUser.getUser().getId(),loginUser.getUser().getUserName(),operationType,ipAddress));
        return new ResponseResult(200,"用户操作插入成功");
    }
}
