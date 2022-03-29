package com.Ylb.dao.impl;

import com.Ylb.pojo.Order;

import java.util.List;

public interface OrderDao {
    public int saveOder(Order order);
    public List<Order> queryOrders();
    Integer queryForPageTotalCount();
    public void changeOrderStatus(String order_id, int status);

    List<Order> queryForPageItems(int begin, int pageSize);
}
