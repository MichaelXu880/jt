package com.jt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;


    /**
     * 开发方式:
     * 	1.自下而上的方式     自己写页面的开发方式  自己写页面的JS
     * 	2.自上而下的方式     通过接口的方式进行的开发.
     * 分页sql:  select xxxxx,xxxx from tb_item limit 起始位置,查询记录数
     * 查询第一页: 规则:含头不尾特点
     * 			select xxxxx,xxxx from tb_item limit 0,20    0-19
     * 			select xxxxx,xxxx from tb_item limit 20,20   20-39
     * 			select xxxxx,xxxx from tb_item limit 40,20   40-59
     * 等差数列第N项:   (page-1)*rows
     */

    /*
     * @Override public EasyUITable findItemByPage(Integer page, Integer rows) {
     * //1.获取记录总数 int total = itemMapper.selectCount(null);
     *
     * //2.查询分页后的结果 int start = (page-1) * rows; List<Item> itemList =
     * itemMapper.selectItemByPage(start,rows);
     *
     * return new EasyUITable(total, itemList); }
     */


    /**
     * 利用MP的方式查询数据记录
     * current:查询第几页
     * size: 每页记录数
     */
    @Override
    public EasyUITable findItemByPage(Integer page, Integer rows) {
        //1.定义分页对象
        Page<Item> mpPage = new Page<>(page, rows);
        //2.定义条件构造器
        QueryWrapper<Item> queryWrapper = new QueryWrapper<Item>();
        //3.要求:按照更新时间,进行排序  降序排列.
        queryWrapper.orderByDesc("updated");
        //执行分页操作,之后封装Page对象数据
        mpPage = itemMapper.selectPage(mpPage, queryWrapper);
        int total = (int) mpPage.getTotal();    //获取总记录数
        List<Item> items = mpPage.getRecords(); //获取当前分页记录
        return new EasyUITable(total, items);
    }

    //控制数据库事务
    @Transactional
    @Override
    public void saveItem(Item item, ItemDesc itemDesc) {
        //1.商品入库
        item.setStatus(1)   //
                .setCreated(new Date())
                .setUpdated(item.getCreated());
        itemMapper.insert(item);
        //由于主键自增的原因,所以程序入库之后才会有主键信息.
        //MP提供业务支持实现数据自动的回显功能!!!!  入库操作之后,会将所有的数据库记录进行映射给对象
        //底层实现是Mybatis的主键自动回显功能

        //2.商品详情入库   item/itemDesc中的ID的值一致.
        itemDesc.setItemId(item.getId())
                .setCreated(item.getCreated())
                .setUpdated(item.getCreated());
        itemDescMapper.insert(itemDesc);
    }

    @Override
    @Transactional
    public void updateItem(Item item, ItemDesc itemDesc) {
        //1.更新商品信息
        item.setUpdated(new Date());
        itemMapper.updateById(item);

        //2.更新商品详情信息
        itemDesc.setItemId(item.getId())
                .setUpdated(item.getUpdated());
        itemDescMapper.updateById(itemDesc);
    }


    //参数是数组类型 有多个
    @Override
    @Transactional
    public void deleteItems(Long[] ids) {
        //1.删除商品信息
        //将数组转化为集合类型
        List<Long> idList = Arrays.asList(ids);
        itemMapper.deleteBatchIds(idList);

        //2.删除商品详情信息
        itemDescMapper.deleteBatchIds(idList);
    }

    /**
     * MP的更新操作
     * 1.entity  要修改的记录
     * 2.updateWrapper   修改条件构造器
     * Sql: update tb_item set status=#{status},updated = #{updated}
     * where id in (id1,id2,id3.....);
     * 单表操作 性能损耗可以忽略.
     */
    @Override
    @Transactional
    public void updateItemStatus(Long[] ids, Integer status) {

        //批注: 操作数据库时需要考虑数据库性能问题.

        //1.定义修改数据
        Item item = new Item();
        item.setStatus(status).setUpdated(new Date());
        //2.定义修改的条件
        List<Long> idList = Arrays.asList(ids);
        UpdateWrapper<Item> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", idList);
        itemMapper.update(item, updateWrapper);
    }

    @Override
    public ItemDesc findItemDescById(Long itemId) {

        return itemDescMapper.selectById(itemId);
    }


}
