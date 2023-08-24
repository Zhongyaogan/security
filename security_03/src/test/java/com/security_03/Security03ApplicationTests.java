package com.security_03;

import com.security_03.domain.User;
import com.security_03.domain.UserOperation;
import com.security_03.mapper.UserOperationMapper;
import com.security_03.service.RegisterService;
import com.security_03.service.UserOperationService;
import com.security_03.service.impl.RegisterServiceImpl;
import com.security_03.service.impl.UserOperationServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
//class Security03ApplicationTests {
//    @Autowired
//    private UserOperationService userOperationService;
//
//    public void  TestOK(){
//        System.out.println(userOperationService.queryOperationDescription(2,3));
//        return ;
//    }
//}
