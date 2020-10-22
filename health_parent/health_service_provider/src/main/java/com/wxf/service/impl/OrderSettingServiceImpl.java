package com.wxf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wxf.dao.OrderSettingDao;
import com.wxf.pojo.OrderSetting;
import com.wxf.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    public void add(List<OrderSetting> orderSettings) {
        for (OrderSetting orderSetting : orderSettings) {
            // 检查此数据是否存在
            Long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            if (count>0) {
                // 更新此数据
                orderSettingDao.updateNumByOrderDate(orderSetting);
            } else {
                // 不存在此数据,插入
                orderSettingDao.insert(orderSetting);
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {  //2020-2
        String beginDate = date+"-1";
        String lastDate = date + "-31";
        Map<String,String> map = new HashMap<>();
        map.put("beginDate",beginDate);
        map.put("lastDate",lastDate);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> maps = new ArrayList<>();
        if (list!=null&&list.size()>0) {
            for (OrderSetting orderSetting : list) {
                Map map1 = new HashMap();
                map1.put("date",orderSetting.getOrderDate().getDate());
                map1.put("number",orderSetting.getNumber());
                map1.put("reservations",orderSetting.getReservations());
                maps.add(map1);
            }
        }
        return maps;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        // 检查此数据是否存在
        Long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (count>0) {
            // 更新此数据
            orderSettingDao.updateNumByOrderDate(orderSetting);
        } else {
            // 不存在此数据,插入
            orderSettingDao.insert(orderSetting);
        }
    }
}
