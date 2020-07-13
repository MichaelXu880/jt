package com.jt.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.util.ImageTypeUtil;
import com.jt.vo.ImageVO;

@Service
//指定配置文件,进行属性的注入 将key-value交给spring容器管理
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {

    @Value("${image.localDir}")
    private String localDir;    // = "D:/JT-SOFT/images";	 //1.优化点一
    @Value("${image.imageUrl}")
    private String imageUrl;    //定义url虚拟网址.
    @Autowired
    private ImageTypeUtil imageTypeUtil;

    //bug:漏洞 一般没错  二般情况才会出错  传递特殊参数时报错
    //error: 错误

    /**
     * 1.如何校验上传的信息是图片??????
     * 通过后缀进行校验:  .jpg,.png,.gif........
     * 2.如何保证检索速度更快 ????
     * 分目录存储		1).hash   2).时间
     * 3.如何防止文件重名???
     * 重定义文件名称    uuid.jpg
     */
    @Override
    public ImageVO uploadFile(MultipartFile uploadFile) {
        //1.校验上传的信息 是否为图片
        //优化方式1 利用静态代码块的形式实现的.  优化方式2 利用spring方式进行优化
        Set<String> typeSet = imageTypeUtil.getTypeSet();
        //Set<String>  typeSet = imageTypeUtil.getImageType();

        //1.2动态获取用户上传的图片类型          abc.jpg|ABC.JPG
        String fileName = uploadFile.getOriginalFilename();
        fileName = fileName.toLowerCase();    //将所有的字符转化为小写.
        int index = fileName.lastIndexOf(".");
        //.jpg
        String fileType = fileName.substring(index);
        //1.2校验图片类型是否有效
        if (!typeSet.contains(fileType)) {
            //表示类型不属于图片信息  则终止程序
            return ImageVO.fail();
        }


        //2.准备文件上传的目录结构.   文件上传根目录+动态变化的目录
        String dateDir = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
        //D:/JT-SOFT/images/2020/7/10/
        String dirPath = localDir + dateDir;
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {

            dirFile.mkdirs();  //如果目录不存在则新建目录.
        }

        //3.重新指定文件名称
        String uuid = UUID.randomUUID().toString();
        String realFileName = uuid + fileType;

        //4.执行文件上传代码    目录+文件名称
        File imageFile = new File(dirPath + realFileName);
        try {
            uploadFile.transferTo(imageFile);
            //图片访问的虚拟路径    3.
            String url = imageUrl + dateDir + realFileName;
            return ImageVO.success(url);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return ImageVO.fail();
        }
    }

}
	
