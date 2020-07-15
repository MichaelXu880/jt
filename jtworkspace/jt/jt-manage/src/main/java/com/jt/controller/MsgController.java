package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgController {
    @Value("${server.port}")
    private int port;
    //主要获取当前访问服务器的端口号信息!!!
    @RequestMapping("/getPort")
    public String getMsg(){
        return "您当前访问的服务器端口号:"+port;
    }
}
