package com.wxf.jobs;

import com.wxf.constant.RedisConstant;
import com.wxf.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * 自定义Job，定时清理图片
 */
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;
    public void clearImg() {
        // 根据Redis集合中保存的两个Set集合进行差值计算，找出垃圾图片
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (sdiff!=null) {
            for (String s : sdiff) {
                // 七牛云删除
                QiniuUtils.deleteFileFromQiniu(s);
                // 从Redis集合中删除垃圾图片
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);
            }
        }
    }
}
