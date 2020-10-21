package com.wxf.dao;

import com.github.pagehelper.Page;
import com.wxf.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

    void add(CheckItem checkItem);

    Page<CheckItem> selectByCondition(String queryString);

    void deleteById(Integer id);

    Long findCountByCheckItemId(Integer id);

    CheckItem findById(Integer id);

    void editCheckItem(CheckItem checkItem);

    List<CheckItem> findAll();
}
