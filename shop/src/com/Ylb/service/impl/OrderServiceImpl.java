package com.Ylb.service.impl;

import com.Ylb.dao.impl.OrderDao;
import com.Ylb.dao.impl.OrderItemDao;
import com.Ylb.dao.impl.bookDao;
import com.Ylb.dao.impl.impl.BookDaoImpl;
import com.Ylb.dao.impl.impl.OrderDaoImpl;
import com.Ylb.dao.impl.impl.OrderItemDaoImpl;
import com.Ylb.pojo.*;
import com.Ylb.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private bookDao bookDao = new BookDaoImpl();
    @Override
    public String createOrder(Cart cart, Integer userId) {
//        生成订单号
        String orderId = System.currentTimeMillis()+""+userId;
//        创建一个订单对象
        Order order = new Order(orderId,new Date(),cart.getTotalPrice(),0,userId);
//        保存订单
        orderDao.saveOder(order);

//        遍历购物车中每一个商品项转换成为订单项保存到数据库
        for (Map.Entry<Integer, CartItem>entry:cart.getItems().entrySet()){
//            获取每一个购物车中的商品项
            CartItem cartItem  =entry.getValue();
//            转换为订单项
            OrderItem orderItem = new OrderItem(null,cartItem.getName(),cartItem.getCount(),cartItem.getPrice(),cartItem.getTotalPrice(),orderId);
//            保存订单项到数据库
            orderItemDao.saveOrderItem(orderItem);

//            更新库存和销量
            Book book = (Book) bookDao.queryBookById(cartItem.getId());

            book.setSales(book.getSales()+cartItem.getCount());
            book.setStock(book.getStock()-cartItem.getCount());

            bookDao.update_book(book);
        }
//        清空购物车
        cart.clearItem();
        return orderId;
    }

    @Override
    public List<Order> queryOrders() {
        return orderDao.queryOrders();
    }

    @Override
    public Page<Order> page(int pageNo, int pageSize) {
        Page<Order> page = new Page<Order>();
//        设置每页显示最大数据条数
        page.setPageSize(pageSize);
//        设置总数据数
        Integer pageTotalCount = orderDao.queryForPageTotalCount();
        page.setPageTotalCount(pageTotalCount);
//        设置总页数
        Integer pageTotal = pageTotalCount/pageSize;
        if (pageTotalCount%pageSize>0){
            pageTotal++;
        }
        page.setPageTotal(pageTotal);
//        设置当前页码
        page.setPageNo(pageNo);
//        设置索引
        int begin = (page.getPageNo()-1)*pageSize;
        List<Order> items = orderDao.queryForPageItems(begin,pageSize);
        page.setItems(items);
        return page;
    }
}
