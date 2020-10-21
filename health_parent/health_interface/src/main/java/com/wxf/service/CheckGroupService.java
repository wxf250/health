package com.wxf.service;

import com.wxf.entity.PageResult;
import com.wxf.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    // 新增检查组
    void addCheckGroup(Integer[] checkitemIds, CheckGroup checkGroup);

    // 分页检查组
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    // 根据id查询检查组
    CheckGroup findById(Integer id);

    // 根据检查组id查询检查项id
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    // 编辑检查组
    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    void deleteById(Integer id);

    // 查询所有检查组
    List<CheckGroup> findAll();
}
