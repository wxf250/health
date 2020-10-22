package com.wxf.service;

import com.wxf.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    // 添加预约设置
    void add(List<OrderSetting> orderSettings);

    // 展示预约信息
    List<Map> getOrderSettingByMonth(String date);

    // 预约设置
    void editNumberByDate(OrderSetting orderSetting);
}
