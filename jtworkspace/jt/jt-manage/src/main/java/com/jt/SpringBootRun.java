package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jt.mapper")
public class SpringBootRun {

    public static void main(String[] args) {
        System.out.println("空");
        SpringApplication.run(SpringBootRun.class, args);
    }
}
