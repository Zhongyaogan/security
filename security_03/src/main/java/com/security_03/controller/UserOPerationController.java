package com.security_03.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.security_03.domain.UserOperation;
import com.security_03.result.ResponseResult;
import com.security_03.service.UserOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/operation")
public class UserOPerationController {
    @Resource
    UserOperationService userOperationService;

    @PostMapping("/operationQuery/{pageNum}/{pageSize}")
    public ResponseResult getUserOperationList(@PathVariable int pageNum, @PathVariable int pageSize) {
        return userOperationService.queryOperationDescription(pageNum, pageSize);
    }

    @PostMapping("/operationQueryByUserName/{pageNum}/{pageSize}")
    public ResponseResult getUserOperationListByName(@PathVariable int pageNum, @PathVariable int pageSize, @RequestParam("userName") String userName) {
        return userOperationService.queryOperationDescriptionByName(pageNum, pageSize,userName);
    }

    @PostMapping("/insertOperationDescription")
    public ResponseResult insertOperationDescription(@RequestBody UserOperation userOperation){
        return userOperationService.insertOperationDescription(userOperation.getOperationType());
    }
}
