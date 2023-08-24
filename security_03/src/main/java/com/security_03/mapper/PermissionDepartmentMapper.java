package com.security_03.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security_03.domain.PermissionDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PermissionDepartmentMapper extends BaseMapper<PermissionDepartment> {

    @Select("    SELECT permission_id\n" +
            "    FROM permission_department\n" +
            "    WHERE department_id = #{departmentId}")
    List<Integer> getPermissionList(@Param("departmentId") Integer departmentId);
}
