package com.wxf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wxf.constant.MessageConstant;
import com.wxf.entity.PageResult;
import com.wxf.entity.QueryPageBean;
import com.wxf.entity.Result;
import com.wxf.pojo.CheckGroup;
import com.wxf.service.CheckGroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    //新增检查组
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds) {
        try {
            checkGroupService.addCheckGroup(checkitemIds,checkGroup);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    // 分页查询检查组
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
         PageResult pageResult = checkGroupService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
         return pageResult;
    }

    // 根据id查询检查组
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    // 根据检查组id查询检查项id
    @RequestMapping("/findIds")
    public Result findCheckItemIdsByCheckGroupId(Integer id) {
        try {
            List<Integer> list = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_SUCCESS);
        }

    }

    // 编辑检查组提交
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds) {
        try {
            checkGroupService.edit(checkGroup,checkitemIds);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }

    }

    // 根据id删除检查组
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id) {
        try {
            checkGroupService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    // 查询所有检查组
    @GetMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckGroup> checkGroups = checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroups);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
