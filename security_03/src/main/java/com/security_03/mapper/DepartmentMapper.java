package com.security_03.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security_03.domain.Department;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
    @Insert("        INSERT INTO department(department_description,department_name)\n" +
            "        VALUES (#{departmentDescription},#{departmentName})")
    void createDepartment(@Param("departmentDescription") String departmentDescription,@Param("departmentName") String departmentName);
}
