package com.Ylb.test;

import com.Ylb.dao.impl.OrderDao;
import com.Ylb.dao.impl.impl.OrderDaoImpl;
import com.Ylb.pojo.Order;
import com.Ylb.pojo.Page;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDaoTest {
    private OrderDao orderDao = new OrderDaoImpl();

    @Test
    public void saveOder() {
        OrderDao orderDao = new OrderDaoImpl();
        orderDao.saveOder(new Order("1212121",new Date(),new BigDecimal(100),0,1));
    }
    @Test
    public void queryOrders(){
        System.out.println(orderDao.queryOrders());
    }
    @Test
    public void queryCount(){
        System.out.println(orderDao.queryForPageTotalCount());
    }
    @Test
    public void queryForPageItems() {
        for (Order order: orderDao.queryForPageItems(1, Page.page_size)){
            System.out.println(order);
        }
    }
    @Test
    public void changeOrderStatus(){

    }
}