package com.Ylb.test;

import com.Ylb.dao.impl.OrderItemDao;
import com.Ylb.dao.impl.impl.OrderItemDaoImpl;
import com.Ylb.pojo.OrderItem;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OrderItemDaoImplTest {

    @Test
    public void saveOrderItem() {
        OrderItemDao orderItemDao = new OrderItemDaoImpl();
        orderItemDao.saveOrderItem(new OrderItem(null,"java从入门到精通",3,new BigDecimal(100),new BigDecimal(22),"1212121"));
    }

}