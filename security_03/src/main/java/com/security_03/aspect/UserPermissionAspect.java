package com.security_03.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//主要处理关于用户权限相关的
@Component
@Aspect
public class UserPermissionAspect {
//    @Pointcut("execution(* com.security_03.*.*(..))")
//    public void p1(){}
}
