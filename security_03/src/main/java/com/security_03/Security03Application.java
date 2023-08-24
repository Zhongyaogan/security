package com.security_03;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
@MapperScan("com.**.mapper")
public class Security03Application {

    public static void main(String[] args) {
        SpringApplication.run(Security03Application.class, args);//登陆界面前后端交互启动
    }

}
