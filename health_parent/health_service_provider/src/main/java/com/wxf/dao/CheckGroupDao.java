package com.wxf.dao;

import com.github.pagehelper.Page;
import com.wxf.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    void addCheckGroup(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    Page selectByCondition(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void deleteAssociation(Integer id);

    void edit(CheckGroup checkGroup);

    void deleteById(Integer id);

    List<CheckGroup> findAll();
}
