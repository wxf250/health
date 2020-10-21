package com.wxf.dao;

import com.github.pagehelper.Page;
import com.wxf.pojo.Setmeal;

import java.util.Map;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(Map<String, Integer> map);

    Page findByCondition(String queryString);
}
