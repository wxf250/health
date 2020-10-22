package com.wxf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wxf.constant.RedisConstant;
import com.wxf.dao.SetmealDao;
import com.wxf.entity.PageResult;
import com.wxf.pojo.Setmeal;
import com.wxf.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    // 添加检查套餐，同时添加检查套餐和检查组的关联关系
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        String fileName = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    private void setSetmealAndCheckGroup(Integer setmealId, Integer[] checkgroupIds) {
        if (checkgroupIds.length>0&&checkgroupIds!=null) {
            for (Integer checkgroupId : checkgroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("setmeal_id",setmealId);
                map.put("checkgroup_id",checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }
}
