package com.security_03.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.security_03.domain.*;
import com.security_03.mapper.*;
import com.security_03.vo.LoginUserVo;
import com.security_03.vo.PermissionDescriptVo;
import com.security_03.vo.UserOperationCountVo;
import com.security_03.constants.SystemConstants;
import com.security_03.result.ResponseResult;
import com.security_03.service.LoginService;
import com.security_03.uitls.JwtUtil;
import com.security_03.uitls.RedisCache;
import com.security_03.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private UserOperationMapper userOperationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionDepartmentMapper permissionDepartmentMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private HttpServletRequest request;



    //登录接口
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //authenticate存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);


        //获取用户ip地址信息
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

        //TODO 获取用户权限信息
        //获取用户所属部门id
        LambdaQueryWrapper<User> userLambdaQueryWrapperqueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapperqueryWrapper.eq(User::getId,loginUser.getUser().getId());
        Integer userDepartmentId = userMapper.selectOne(userLambdaQueryWrapperqueryWrapper).getDepartmentId();

        //获取部门权限表中部门所对应的权限id列表
        LambdaQueryWrapper<PermissionDepartment> queryWrapperDepartmentToPermission = new LambdaQueryWrapper<>();
        queryWrapperDepartmentToPermission.eq(PermissionDepartment::getDepartmentId,userDepartmentId);
        //返回用户所属的部门的权限列表
        List<PermissionDepartment> listPToD = permissionDepartmentMapper.selectList(queryWrapperDepartmentToPermission);
        System.out.println(listPToD.toString());
        List<PermissionDescriptVo> permissionDescriptVos = new ArrayList<>();
        listPToD.forEach(i -> {
            LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Permission::getPermissionId,i.getPermissionId());
            permissionDescriptVos.add((new PermissionDescriptVo(permissionMapper.selectOne(queryWrapper))));
        });

        //将用户操作插入用户操作
        UserOperation userOperation = new UserOperation(loginUser.getUser().getId(),loginUser.getUser().getUserName(), SystemConstants.OPERATION_STATUS_LOGIN,ipAddress);
        userOperationMapper.insert(userOperation);

        return new ResponseResult(200,"登陆成功",new LoginUserVo(jwt,permissionDescriptVos));
    }
//    @Override
//    public ResponseResult logout() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//        Long userid = loginUser.getUser().getId();
//        redisCache.deleteObject("login:"+userid);
//        return new ResponseResult(200,"退出成功");
//    }
    //登出接口
    @Override
    public ResponseResult loginOut() {
        //从SecurityContextHolder获取认证信息
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        //通过token获取当前登录用户实体数据
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();

        //获取用户ip地址信息
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

        //通过获取到的userid获取到redis缓存删除
        redisCache.deleteObject("login:"+userId);

        //插入用户操作日志
        UserOperation userOperation = new UserOperation(loginUser.getUser().getId(),loginUser.getUser().getUserName(), SystemConstants.OPERATION_STATUS_LOGINOUT,ipAddress);
        userOperationMapper.insert(userOperation);

        return new ResponseResult(200,"登出成功");
    }

    //登陆操作总数查询接口
    @Override
    public ResponseResult loginCount() {
        //获取用户操作类型为登录的操作总数，并返回
        LambdaQueryWrapper<UserOperation> queryWrapper = new LambdaQueryWrapper<UserOperation>()
                .eq(UserOperation::getOperationType,SystemConstants.OPERATION_STATUS_LOGIN);
        Integer loginCount =  userOperationMapper.selectCount(queryWrapper);
        UserOperationCountVo userOperationCountVo = new UserOperationCountVo(loginCount,SystemConstants.OPERATION_STATUS_LOGIN);
        return new ResponseResult(200, userOperationCountVo);
    }

    //更改密码接口
    @Override
    public ResponseResult loginChangePassword(String oldPassword, String newPassword) {
        //获取用户认证信息及用户id并且准备修改用户密码
        UsernamePasswordAuthenticationToken autnentication = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) autnentication.getPrincipal();
        Long userId = loginUser.getUser().getId();

        //判断用户输入的密码是否正确
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(oldPassword,loginUser.getPassword())){
            return new ResponseResult(200,"用户输入的密码错误，密码修改失败");
        }

        //更新用户的密码
          //获取用户实体类
        User user = loginUser.getUser();
          //对用户密码加密
        String encoderedNewPassword = encoder.encode(newPassword);
        user.setPassword(encoderedNewPassword);
          //更新密码
        userMapper.updateById(user);

        // 退出当前用户的登录状态
        redisCache.deleteObject("login:"+userId);

        //获取用户ip地址信息
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

        //记录用户操作
        UserOperation userOperationLoginOut = new UserOperation(loginUser.getUser().getId(),loginUser.getUser().getUserName(), SystemConstants.OPERATION_STATUS_LOGINOUT,ipAddress);
        UserOperation userOperationChangePassword = new UserOperation(loginUser.getUser().getId(),loginUser.getUser().getUserName(), SystemConstants.OPERATION_STATUS_CHANGEPASSWORD,ipAddress);
        userOperationMapper.insert(userOperationLoginOut);
        userOperationMapper.insert(userOperationChangePassword);

        return new ResponseResult(200,"用户密码更改成功");
    }

//以下所有接口都需要admin用户

    //获取所有用户信息
    @Override
    public ResponseResult getAllUserDescrption(Integer pageNum, Integer pageSize){
        //验证用户是否有权限获取操作记录
        //从SecurityContextHolder获取认证信息
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //通过token获取当前登录用户实体数据
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if (!userMapper.selectById(loginUser.getUser().getId()).getDepartmentId().equals(SystemConstants.ADMIN)){
            return new ResponseResult(200,"您无权限查看其他用户信息");
        }

        //
        Integer total = userMapper.getTotal();
        if ((pageNum-2)*pageSize+pageSize >= total){
            return new ResponseResult(200,"查询超限，查询所有结果已返回");
        }
        if (pageNum<0 || pageSize<0)
            return  new ResponseResult(200,"查询页数或页面行数有误");

        //获取全部用户的信息
        List<User> allUserList = userMapper.getUserDescription((pageNum-1)*pageSize,pageSize);
        List<UserVo> userVoList = new ArrayList<>();

        allUserList.forEach(user -> {
            userVoList.add(new UserVo(user.getId().toString(),user.getUserName(),user.getPhoneNumber(),departmentMapper.selectById(user.getDepartmentId()).getDepartmentDescription()));
        });

        HashMap<String,Object> returnMap = new HashMap<>();
        returnMap.put("total",total);
        returnMap.put("data",userVoList);
        return new ResponseResult(200,returnMap);
    }


    //修改用户权限信息
    @Override
    public ResponseResult changeUserPermission(Long UserId, String DepartmentDescription){
        //验证用户是否有权限获取操作记录
        //从SecurityContextHolder获取认证信息
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //通过token获取当前登录用户实体数据
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if (!userMapper.selectById(loginUser.getUser().getId()).getDepartmentId().equals(SystemConstants.ADMIN)){
            return new ResponseResult(200,"您无权限修改其他用户权限信息");
        }

        //获取用户信息
        User user = userMapper.selectById(UserId);
        //通过部门描述获取部门实体，并将该部门id赋值给用户实体
        LambdaQueryWrapper<Department> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Department::getDepartmentDescription,DepartmentDescription);
        user.setDepartmentId(departmentMapper.selectOne(queryWrapper).getDepartmentId());

        //将用户更新实体传回数据库实现更新
        userMapper.updateById(user);
        return new ResponseResult(200,"用户部门信息修改成功");
    }
}