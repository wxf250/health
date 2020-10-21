package com.wxf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wxf.dao.CheckGroupDao;
import com.wxf.entity.PageResult;
import com.wxf.pojo.CheckGroup;
import com.wxf.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Override
    // 添加检查组，同时添加检查项与检查组的关联关系
    public void addCheckGroup(Integer[] checkitemIds, CheckGroup checkGroup) {
          checkGroupDao.addCheckGroup(checkGroup);
          setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page page = checkGroupDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 清理原有检查组合检查项关联关系
        checkGroupDao.deleteAssociation(checkGroup.getId());
        // 添加新检查组和检查项关联关系
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
        // 更新检查组
        checkGroupDao.edit(checkGroup);
    }

    @Override
    public void deleteById(Integer id) {
        // 清理检查组与检查项的关联关系
        checkGroupDao.deleteAssociation(id);
        checkGroupDao.deleteById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds) {
        if (checkitemIds!=null && checkitemIds.length>0) {
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkgroup_id",checkGroupId);
                map.put("checkitem_id",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }
}
