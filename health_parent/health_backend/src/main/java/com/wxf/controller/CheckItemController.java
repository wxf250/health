package com.wxf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wxf.constant.MessageConstant;
import com.wxf.entity.PageResult;
import com.wxf.entity.QueryPageBean;
import com.wxf.entity.Result;
import com.wxf.pojo.CheckItem;
import com.wxf.service.CheckItemService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    // 新增检查项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    // 分页查询检查项
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkItemService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }

    // 查询所有检查项
    @RequestMapping("/findAll")
    public Result findAll() {
       List<CheckItem> checkItemList = checkItemService.findAll();
        if (checkItemList!=null && checkItemList.size()>0) {
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
        }
        return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
    }

    // 检查项删除
    @RequestMapping("/delete")
    public Result delete(@Param("id") Integer id) {
        try {
            checkItemService.deleteById(id);
        }catch (RuntimeException e) {
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    // 编辑检查项
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.editCheckItem(checkItem);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }


}
