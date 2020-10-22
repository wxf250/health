package com.wxf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wxf.constant.MessageConstant;
import com.wxf.entity.Result;
import com.wxf.pojo.OrderSetting;
import com.wxf.service.OrderSettingService;
import com.wxf.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预约管理设置
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {
        try {
            List<String[]> strings = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettings = new ArrayList<>();
            if (strings!=null&&strings.size()>0) {
                for (String[] string : strings) {
                    OrderSetting orderSetting = new OrderSetting(new Date(string[0]),Integer.parseInt(string[1]));
                    orderSettings.add(orderSetting);
                }
            }
            orderSettingService.add(orderSettings);
            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }
    // 预约设置展示
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date) {  //2020-03
        try {
            List<Map> maps = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,maps);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }
    // 预约人数设置
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }

    }
}
