package com.security_03.service;

import com.security_03.domain.User;
import com.security_03.result.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public interface RegisterService {
    ResponseResult register(User user);
}
