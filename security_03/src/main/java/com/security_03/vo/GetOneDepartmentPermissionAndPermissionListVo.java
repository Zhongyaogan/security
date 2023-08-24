package com.security_03.vo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.security_03.domain.Department;
import com.security_03.domain.Permission;
import com.security_03.mapper.PermissionDepartmentMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetOneDepartmentPermissionAndPermissionListVo {
    //部门描述名称
    private String departmentDescription;

    //权限信息列表（id+type）
    private List<PermissionVo> permissionvoList;

}
