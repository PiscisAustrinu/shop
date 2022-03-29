package com.Ylb.test;

import com.Ylb.pojo.Cart;
import com.Ylb.pojo.CartItem;
import com.Ylb.pojo.Page;
import com.Ylb.service.OrderService;
import com.Ylb.service.impl.OrderServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OrderServiceTest {
    private OrderService orderService = new OrderServiceImpl();
    @Test
    public void createOrder() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1,"java从入门到精通",1,new BigDecimal(1000),new BigDecimal(1000)));
        cart.addItem(new CartItem(1,"java从入门到精通",1,new BigDecimal(1000),new BigDecimal(1000)));
        cart.addItem(new CartItem(2,"数据结构与算法",1,new BigDecimal(100),new BigDecimal(100)));
        OrderService orderService = new OrderServiceImpl();
        System.out.println("订单号为："+orderService.createOrder(cart,1));
    }
    @Test
    public void showAllOrders(){
        System.out.println(orderService.queryOrders());
    }
    @Test
    public void page(){
        System.out.println(orderService.page(1, Page.page_size));
    }
}