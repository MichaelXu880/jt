package com.jt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

//表示配置类   .xml 配置文件
@Configuration
public class MybatisPlusConfig {

    //一般和bean注解联用 ,表示将返回的对象实例化之后,交给spring容器管理
    @Bean
    public PaginationInterceptor paginationInterceptor() {

        return new PaginationInterceptor();
    }
}
