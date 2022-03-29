package com.Ylb.service;

import com.Ylb.pojo.Cart;
import com.Ylb.pojo.Order;
import com.Ylb.pojo.Page;

import java.util.List;

public interface OrderService {
    public String createOrder(Cart cart,Integer userId);
    public List<Order> queryOrders();
    Page<Order> page(int pageNo,int pageSize);
}
