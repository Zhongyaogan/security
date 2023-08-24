package com.security_03.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security_03.domain.DepartmentUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentUserMapper extends BaseMapper<DepartmentUser> {
}
