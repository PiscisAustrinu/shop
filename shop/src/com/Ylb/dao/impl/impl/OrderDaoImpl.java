package com.Ylb.dao.impl.impl;

import com.Ylb.dao.impl.OrderDao;
import com.Ylb.pojo.Order;

import java.util.List;

public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public int saveOder(Order order) {
        String sql = "insert into t_order(`order_id`,`create_time`,`price`,`status`,`user_id`) values(?,?,?,?,?) ";
        return update(sql,order.getOrder_id(),order.getCreate_time(),order.getPrice(),order.getStatus(),order.getUser_id());
    }

    @Override
    public List<Order> queryOrders() {
        String sql = "select * from t_order";
        return queryForList(Order.class,sql);
    }

    @Override
    public Integer queryForPageTotalCount() {
        String sql = "select count(*) from t_order";
        Number count = (Number) queryForSingleValue(sql);
        return count.intValue();
    }

    @Override
    public List<Order> queryForPageItems(int begin, int pageSize) {
        String sql = "select * from t_order limit ?,?";
        return queryForList(Order.class,sql,begin,pageSize);
    }

    @Override
    public void changeOrderStatus(String order_id, int status) {
        String sql = "update t_order set status = ? where status = ? and order_id = ?";
        if (status==0){
            update(sql,0,1,order_id);
        }else if(status==1){
            update(sql,1,2,order_id);
        }
    }
}
