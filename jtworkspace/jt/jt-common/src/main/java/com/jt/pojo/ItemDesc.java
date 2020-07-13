package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_item_desc")
@Data
@Accessors(chain = true)
public class ItemDesc extends BasePojo {

    @TableId                    //主键
    private Long itemId;        //商品id号   itemId既是item表主键,也是itemDesc表的主键 值相同
    private String itemDesc;    //商品详情信息

}










