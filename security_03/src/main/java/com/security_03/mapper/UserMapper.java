package com.security_03.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security_03.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("    SELECT *\n" +
            "    FROM sys_user\n" +
            "    ORDER BY id\n" +
            "    OFFSET #{offsetNum} LIMIT #{limitNum}")
    List<User> getUserDescription(@Param("offsetNum") Integer offsetNum, @Param("limitNum") Integer limitNum);

    @Select("    SELECT count(*)\n" +
            "    FROM sys_user")
    Integer getTotal();
}
