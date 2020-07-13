package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;


    /**
     * 业务:查询商品分类名称 根据id
     * url地址: http://localhost:8091/item/cat/queryItemName?itemCatId=3
     * 参数:    itemCatId=3
     * 返回值:   商品分类名称
     */
    @RequestMapping("/queryItemName")
    public String findItemCatNameById(Long itemCatId) {

        ItemCat itemCat = itemCatService.findItemCatById(itemCatId);
        return itemCat.getName();
    }


    /**
     * 业务:查询商品分类信息,返回VO对象
     * url地址: /item/cat/list
     * 参数:  id:一级分类id值
     * 返回值: EasyUITree对象
     * json格式:
     * [{"id":"2","text":"王者荣耀","state":"closed"},{"id":"3","text":"王者荣耀","state":"closed"}]`
     * sql语句:
     * 一级商品分类信息 parent_id=0 SELECT * FROM tb_item_cat WHERE parent_id=0
     */
    @RequestMapping("/list")
    public List<EasyUITree> findItemCatByParentId
    (@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        //初始化时应该设定默认值.
        //1.查询一级商品分类信息
        //Long parentId = id==null?0L:id;

        return itemCatService.findItemCatByParentId(parentId);
    }

}
