package com.security_03.service;

import com.security_03.domain.User;
import com.security_03.result.ResponseResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface DepartmentService {
    //创建组接口
    ResponseResult createDepartment(String departmentDescription,String departmentName);

    //修改部门组权限，应该与查询部门权限返回做对比，方便前端了解部门
    ResponseResult changeDepartmentPermission(String departmentDescription, ArrayList<Integer> permissionList);


    //删除部门组
    ResponseResult deleteDepartment(String departmentDescription);

//    查询类接口
    //查询部门组人员情况接口

    //查询部门所有情况接口
    ResponseResult getAllDepartment();

    // 返回单部门权限，及权限列表
    ResponseResult getOneDepartmentPermissionAndPermissionList(String departmentDescription);

}
