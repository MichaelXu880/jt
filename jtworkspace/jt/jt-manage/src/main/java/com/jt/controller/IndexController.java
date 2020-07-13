package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    /**
     * 1.url地址:/page/item-add
     * /page/item-list
     * /page/item-param-list
     * 需求:使用一个方法实现页面跳转
     * <p>
     * 实现: RestFul风格
     * 语法:
     * 1.使用/方式分割参数.
     * 2.使用{}包裹参数
     * 3.在参数方法中 动态接收参数时使用特定注解@PathVariable
     * 注解: @PathVariable
     * name/value 表示接收参数的名称
     * required   改参数是否必须  默认true
     * <p>
     * restFul风格2:
     * 思路:利用通用的url地址,实现不同的业务调用
     * 例子1:  http://www.jt.com/user?id=1    查询      			GET
     * 例子2:  http://www.jt.com/user?id=1    删除				DELETE
     * 例子3:  http://www.jt.com/user?id=1&name=xxx  更新          PUT
     * 例子4:  http://www.jt.com/user	       新增操作			POST
     *
     * @RequestMapping(value = "/user",method = RequestMethod.POST)
     * @PostMapping("/user") 总结:
     * 一般在工作中有2种用法.
     * 作用1: 动态获取url路径中的参数
     * 作用2: 以统一的url地址,不同的请求类型,实现不同业务的调用.
     * 一般都会添加请求类型进行标识.为了安全.
     */

    @RequestMapping("/page/{moduleName}")
    public String moduleName(@PathVariable String moduleName) {

        return moduleName;
    }


    /*
     * //@RequestMapping(value = "/user",method = RequestMethod.POST)
     *
     * @PostMapping("/user") public String insertUser(User user) { //////入库 }
     *
     * @RequestMapping(value = "/user",method = RequestMethod.GET) public User
     * selectUser(int id) { //////入库 }
     */


}
