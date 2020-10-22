package com.wxf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wxf.constant.MessageConstant;
import com.wxf.constant.RedisConstant;
import com.wxf.entity.PageResult;
import com.wxf.entity.QueryPageBean;
import com.wxf.entity.Result;
import com.wxf.pojo.Setmeal;
import com.wxf.service.SetmealService;
import com.wxf.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;

    // 使用JdisPool操作Redis服务
    @Autowired
    private JedisPool jedisPool;

    // 图片文件上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile) {
        String originalFilename = imgFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(index);
        String fileName = UUID.randomUUID().toString()+substring;
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    // 检查套餐添加
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds) {
        try {
            setmealService.add(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }

    }

    // 查询检查套餐分页
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = setmealService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;
    }
}
