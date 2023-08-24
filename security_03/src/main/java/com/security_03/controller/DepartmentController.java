package com.security_03.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.security_03.domain.Department;
import com.security_03.mapper.DepartmentMapper;
import com.security_03.result.ResponseResult;
import com.security_03.service.DepartmentService;
import com.security_03.vo.ChangeDepartmentPermissionVo;
import com.security_03.vo.RequestbodyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @PostMapping("/createDepartment")
    public ResponseResult createDepartment(@RequestBody Department department){
        return departmentService.createDepartment(department.getDepartmentDescription(),department.getDepartmentName());
    }

    @PostMapping("/changeDepartmentPermission")
    public  ResponseResult changeDepartmentPermission(@RequestBody ChangeDepartmentPermissionVo changeDepartmentPermissionVo){
        return departmentService.changeDepartmentPermission(changeDepartmentPermissionVo.getDepartmentDescription(),changeDepartmentPermissionVo.getPermissionList());
    }

    @PostMapping("/deleteDepartment")
    public  ResponseResult deleteDepartment(@RequestBody Department department){
        return departmentService.deleteDepartment(department.getDepartmentDescription());
    }

    @GetMapping("/getAllDepartment")
    public  ResponseResult getAllDepartment(){
        return departmentService.getAllDepartment();
    }


    @PostMapping("/getOneDepartmentPermissionAndPermissionList")
    public  ResponseResult getOneDepartmentPermissionAndPermissionList(@RequestBody RequestbodyVo requestbodyVo){
        return departmentService.getOneDepartmentPermissionAndPermissionList(requestbodyVo.getDepartmentDescription());
    }


}
