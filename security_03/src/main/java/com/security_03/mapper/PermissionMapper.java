package com.security_03.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security_03.domain.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
