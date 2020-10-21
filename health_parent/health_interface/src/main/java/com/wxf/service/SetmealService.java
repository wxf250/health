package com.wxf.service;

import com.wxf.entity.PageResult;
import com.wxf.pojo.Setmeal;

public interface SetmealService {
    // 新增套餐
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    // 查询套餐分页
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);
}
