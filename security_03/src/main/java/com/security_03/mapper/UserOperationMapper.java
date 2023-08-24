package com.security_03.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security_03.domain.UserOperation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserOperationMapper extends BaseMapper<UserOperation> {
    @Select("    SELECT *\n" +
            "    FROM user_operation\n" +
            "    ORDER BY operation_time DESC\n" +
            "    OFFSET #{offsetNum} LIMIT #{limitNum}")
    List<UserOperation> getUserOperationX10(@Param("offsetNum") int offsetNum,@Param("limitNum") int limitNum);


    @Select(
            "    SELECT *\n" +
            "    FROM user_operation\n" +
            "    WHERE user_name = #{userName}\n" +
                    "    ORDER BY operation_time DESC\n" +
            "    OFFSET #{offsetNum} LIMIT #{limitNum}"
            )
    List<UserOperation> getUserOperationByUserNameX10(@Param("offsetNum") int offsetNum,@Param("limitNum") int limitNum,@Param("userName") String userName);

    @Select("SELECT count(*) FROM user_operation")
    int getTotal();
}
