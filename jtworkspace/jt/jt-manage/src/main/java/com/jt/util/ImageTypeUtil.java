package com.jt.util;
//定义文件上传类型的集合信息

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component    //一般用来标识该类交给spring容器进行管理. 不是任何业务层
@PropertySource("classpath:/properties/image.properties")
public class ImageTypeUtil {
    //可以利用spring容器动态为属性赋值.
    @Value("${image.imageTypes}")
    private String imageTypes;    //type1,type2,type3,
    private Set<String> typeSet = new HashSet<String>();

    //初始化集合信息
    //@PreDestroy	//当spring容器中关闭前,执行该方法.
    @PostConstruct    //当对象交给容器管理之后,执行该方法
    public void init() {

        String[] typeArray = imageTypes.split(",");
        for (String type : typeArray) {
            typeSet.add(type);
        }
        //循环遍历完成之后,.typeSet集合类型中有值的.
        System.out.println("set集合初始化完成!!!!" + typeSet);
    }


    public Set<String> getTypeSet() {
        return typeSet;
    }


    private static Set<String> typeSet1 = new HashSet<String>();

    /**
     * 一般图片的类型就是常见的几种,变化范围不大 暂时可以写死.
     * 如果自己不想写死,则通过IO流,读取指定的配置文件进行封装.
     */
    static {
        typeSet1.add(".jpg");
        typeSet1.add(".png");
        typeSet1.add(".gif");
        typeSet1.add(".jpeg");
        typeSet1.add(".bmp");
    }

    public static Set<String> getImageType() {
        return typeSet1;
    }


}