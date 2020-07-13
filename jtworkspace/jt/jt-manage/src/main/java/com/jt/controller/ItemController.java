package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;

import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;

@RestController    //返回数据时不需要经过视图解析器.
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 业务: 展现商品列表数据,以EasyUI表格数据展现
     * url地址: http://localhost:8091/item/query?page=1&rows=20
     * 参数:	page=1 页数  &  rows=20 没有记录数
     * 返回值: EasyUITable VO对象
     */
    @RequestMapping("/query")    //所有的请求类型都能接收
    public EasyUITable findItemByPage(Integer page, Integer rows) {

        return itemService.findItemByPage(page, rows);
    }


    /**
     * 商品新增操作
     * url: /item/save
     * 参数: form表单数据
     * 返回值结果: SysResult对象
     */
    @RequestMapping("/save")
    public SysResult saveItem(Item item, ItemDesc itemDesc) {

        //2张表同时入库
        itemService.saveItem(item, itemDesc);
        return SysResult.success();
    }


    /**
     * 商品修改操作
     * url: /item/update
     * 参数: form表单数据
     * 返回值结果: SysResult对象
     */
    @RequestMapping("/update")
    public SysResult updateItem(Item item, ItemDesc itemDesc) {

        itemService.updateItem(item, itemDesc);
        return SysResult.success();
    }


    /**
     * 补充1:  SpringMVC框架 不熟!
     * 页面中传递什么样的数据,后端才能接收什么样的数据.
     * 要求1: name属性名称必须与参数名称一致.
     * 要求2: name属性名称必须与属性名称一致
     * 例子1:
     * 页面信息  <input type="text" name="id"  value="100"  />
     * 页面信息  <input type="text" name="age"  value="19"  />
     * 页面信息  <input type="text" name="sex"  value="男"  />
     * <p>
     * 补充2: SpringMVC底层实现Servlet,数据传输协议https/http 一般传递的数据都是String结构.
     * 案例:  页面信息  <input type="text" name="sex"  value="男"  />
     * 页面信息  <input type="text" name="sex"  value="男"  />
     * 页面信息  <input type="text" name="sex"  value="男"  />
     * 如果遇到重名属性提交,则浏览器会自己解析将value进行拼接     sex="男,男,男"
     * <p>
     * springMVC优化:
     * 1.可以自动的根据参数类型进行转化  string转化为具体类型
     * 2.如果遇到了重名属性提交,则会自动的转化为数组类型接收.
     * <p>
     * url:/item/delete
     * 参数: ids=id,id,id,id
     * 返回值结果: SysResult对象
     */
    @RequestMapping("/delete")
    public SysResult deleteItems(Long[] ids) {

        itemService.deleteItems(ids);
        return SysResult.success();
    }


    /**
     * url地址:   /item/updateStatus/2   /item/updateStatus/1
     * 参数:  1.url中  2.ids
     * 返回值:  SysResult对象
     */
    @RequestMapping("/updateStatus/{status}")
    public SysResult updateItemStatus(Long[] ids, @PathVariable Integer status) {

        //实现商品状态修改
        itemService.updateItemStatus(ids, status);
        return SysResult.success();
    }

    /**
     * 业务需求:  根据itemId查询商品详情信息
     * url地址:  http://localhost:8091/item/query/item/desc/1474391972
     * 参数:		使用restFul方式使用传输传递
     * 返回值:    SysResult对象,并且需要携带itemDesc数据信息.
     */
    @RequestMapping("/query/item/desc/{itemId}")
    public SysResult findItemDescById(@PathVariable Long itemId) {

        ItemDesc itemDesc = itemService.findItemDescById(itemId);
        return SysResult.success(itemDesc);
    }


}
