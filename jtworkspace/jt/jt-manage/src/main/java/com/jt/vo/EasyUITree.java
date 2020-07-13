package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITree {
    // {"id":"2","text":"王者荣耀","state":"closed"}
    private Long id;        //id值   与ItemCat中的Id一致的
    private String text;    //文本信息   itemCat中的name属性
    private String state;    //状态   打开:open  关闭: closed   如果是父级应该关闭

}








