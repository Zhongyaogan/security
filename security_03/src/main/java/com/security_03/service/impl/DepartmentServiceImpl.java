package com.security_03.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.security_03.constants.SystemConstants;
import com.security_03.domain.Department;
import com.security_03.domain.LoginUser;
import com.security_03.domain.PermissionDepartment;
import com.security_03.mapper.*;
import com.security_03.result.ResponseResult;
import com.security_03.service.DepartmentService;
import com.security_03.vo.GetOneDepartmentPermissionAndPermissionListVo;
import com.security_03.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    //部门用户关系实体
    @Autowired
    DepartmentUserMapper departmentUserMapper;

    //部门权限关系实体
    @Autowired
    PermissionDepartmentMapper permissionDepartmentMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PermissionMapper permissionMapper;

    //新建部门组
    @Override
    public ResponseResult createDepartment(String departmentDescription, String departmentName) {
        //判断所建组是否存在
        LambdaQueryWrapper<Department> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Department::getDepartmentDescription,departmentDescription);
        if (departmentMapper.selectCount(queryWrapper) != 0){
            return new ResponseResult(200,"所建组已存在");
        }
        departmentMapper.createDepartment(departmentDescription,departmentName);
        return new ResponseResult(200,departmentMapper.selectOne(queryWrapper));
    }

    //修改部门组权限列表
    @Override
    public ResponseResult changeDepartmentPermission(String departmentDescription, ArrayList<Integer> permissionList) {
        //查询部门描述为departmentDescription的部门组权限列表
        LambdaQueryWrapper<PermissionDepartment> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Department> queryWrapperDepartment = new LambdaQueryWrapper<>();
        queryWrapperDepartment.eq(Department::getDepartmentDescription,departmentDescription);
        queryWrapper.eq(PermissionDepartment::getDepartmentId,departmentMapper.selectOne(queryWrapperDepartment).getDepartmentId());
        //删除旧权限，添加新权限列表
        permissionDepartmentMapper.delete(queryWrapper);
        permissionList.forEach(pms -> {
            permissionDepartmentMapper.insert(new PermissionDepartment(pms,departmentMapper.selectOne(queryWrapperDepartment).getDepartmentId()));
        });
        return new ResponseResult(200,"部门组"+departmentMapper.selectOne(queryWrapperDepartment).getDepartmentId()+"权限修改成功");
    }

    //删除部门
    @Override
    public ResponseResult deleteDepartment(String departmentDescription) {
        LambdaQueryWrapper<Department> queryWrapperDepartment = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<PermissionDepartment> queryWrapperPermissionDepartment = new LambdaQueryWrapper<>();

        //获取用户组id
        queryWrapperDepartment.eq(Department::getDepartmentDescription,departmentDescription);
        Integer departmentId = departmentMapper.selectOne(queryWrapperDepartment).getDepartmentId();

        //删除权限关系表
        queryWrapperPermissionDepartment.eq(PermissionDepartment::getDepartmentId,departmentId);
        permissionDepartmentMapper.delete(queryWrapperPermissionDepartment);

        //删除部门组组
        departmentMapper.delete(queryWrapperDepartment);
        return new ResponseResult(200,"删除部门组完成");
    }

    //删除部门组

    //更改部门组人员
//    @Override
//    public ResponseResult changeDepartmentUsers(String departmentDescription, List<User> userList) {
//        return null;
//    }

    //获取部门列表
    @Override
    public ResponseResult getAllDepartment(){
        //验证用户是否有权限获取操作记录
        //从SecurityContextHolder获取认证信息
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //通过token获取当前登录用户实体数据
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        if (!userMapper.selectById(userId).getDepartmentId().equals(SystemConstants.ADMIN)){
            return new ResponseResult(200,"您无权限查看");
        }

        List<Department> departmentList = departmentMapper.selectList(null);
        return new ResponseResult(200,departmentList);
    }

    //返回单部门权限，及权限列表
    @Override
    public ResponseResult getOneDepartmentPermissionAndPermissionList(String departmentDescription){
        //验证用户是否有权限获取操作记录
        //从SecurityContextHolder获取认证信息
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //通过token获取当前登录用户实体数据
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        if (!userMapper.selectById(userId).getDepartmentId().equals(SystemConstants.ADMIN)){
            return new ResponseResult(200,"您无权限查看");
        }

        //通过部门名称查询部门权限列表
        LambdaQueryWrapper<Department> departmentPermissionQuery = new LambdaQueryWrapper<>();
        departmentPermissionQuery.eq(Department::getDepartmentDescription,departmentDescription);

        LambdaQueryWrapper<PermissionDepartment> permissionDepartmentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        permissionDepartmentLambdaQueryWrapper.eq(PermissionDepartment::getDepartmentId,departmentMapper.selectOne(departmentPermissionQuery).getDepartmentId());
        List<PermissionDepartment> permissionDepartmentList = permissionDepartmentMapper.selectList(permissionDepartmentLambdaQueryWrapper);
        List<Integer> departmentPermissionList = new ArrayList<>();
        permissionDepartmentList.forEach(permissionDepartment -> {
            departmentPermissionList.add(new Integer(permissionDepartment.getPermissionId()));
        });

        // 获取权限表的所有权限信息
        GetOneDepartmentPermissionAndPermissionListVo getOneDepartmentPermissionAndPermissionListVo = new GetOneDepartmentPermissionAndPermissionListVo();
        getOneDepartmentPermissionAndPermissionListVo.setDepartmentDescription(departmentDescription);

        List<PermissionVo> permissionVoList = new ArrayList<>();
        permissionMapper.selectList(null).forEach(permission -> {
            if (departmentPermissionList.contains(permission.getPermissionId())){
                //判断权限信息是否包含
                permissionVoList.add(new PermissionVo(permission.getPermissionId(),permission.getPermissionType(),true));
            }
            else {
                permissionVoList.add(new PermissionVo(permission.getPermissionId(),permission.getPermissionType(),false));
            }
        });
        getOneDepartmentPermissionAndPermissionListVo.setPermissionvoList(permissionVoList);
        return new ResponseResult(200, getOneDepartmentPermissionAndPermissionListVo);
    }
}
