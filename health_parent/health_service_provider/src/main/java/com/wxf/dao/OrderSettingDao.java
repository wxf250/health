package com.wxf.dao;

import com.wxf.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    Long findCountByOrderDate(Date orderDate);

    void updateNumByOrderDate(OrderSetting orderSetting);

    void insert(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);
}
