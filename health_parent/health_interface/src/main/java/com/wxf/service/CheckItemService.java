package com.wxf.service;

import com.wxf.entity.PageResult;
import com.wxf.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    // 新增检查项
    void add(CheckItem checkItem);

    // 分页查询检查项
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    // 删除检查项
    void deleteById(Integer id);

    // 根据id查询检查项
    CheckItem findById(Integer id);

    // 编辑检查项并提交
    void editCheckItem(CheckItem checkItem);

    // 普通查询所有检查项
    List<CheckItem> findAll();



}
