package com.security_03.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.security_03.domain.UserOperation;
import com.security_03.result.ResponseResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserOperationService {
    ResponseResult queryOperationDescription(int pageNum, int pageSize);

    ResponseResult queryOperationDescriptionByName(int pageNum, int pageSize, String userName);

    //插入用户操作
    ResponseResult insertOperationDescription(String operationType);
}
