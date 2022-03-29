package com.Ylb.web;

import com.Ylb.pojo.Cart;
import com.Ylb.pojo.Order;
import com.Ylb.pojo.Page;
import com.Ylb.pojo.User;
import com.Ylb.service.OrderService;
import com.Ylb.service.impl.OrderServiceImpl;
import com.Ylb.utils.JdbcUtils;
import com.Ylb.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.Ylb.pojo.Page.page_size;

public class OrderServlet extends BaseServlet{
    private OrderService orderService = new OrderServiceImpl();
    /**
     * 生成订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//                获取Cart对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
//        获取userId
        User loginUser = (User) req.getSession().getAttribute("user");
        if (loginUser==null){
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
            return;
        }
        Integer userId = loginUser.getId();
        //        调用OrderService.createOrder(Cart,Userid);
        String orderId = null;
        try {
            orderId = orderService.createOrder(cart,userId);
            JdbcUtils.commitAndClose();  //提交事务
        } catch (Exception e) {
            JdbcUtils.rollbackAndClose();//回滚事务
            e.printStackTrace();
        }
//        req.setAttribute("orderId",orderId);
////        请求转发到/pages/cart/checkout.jsp
//        req.getRequestDispatcher("/pages/cart/checkout.jsp").forward(req,resp);
        req.getSession().setAttribute("orderId",orderId);
        resp.sendRedirect(req.getContextPath()+"/pages/cart/checkout.jsp");
    }

    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1、通过orderService查询图书
        List<Order> orders = orderService.queryOrders();
//        2、把订单保存到request域中
        req.setAttribute("orders",orders);
//        3、请求转发到/pages/manager/order_manager.jsp页面
        req.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(req,resp);
    }


    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1、获取请求参数pageNo和pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"),page_size);
//        2、调用OrderService.page(pageNo,pageSize):Page对象
        Page<Order> page1 = orderService.page(pageNo,pageSize);
//        设置URL
        page1.setUrl("manager/orderServlet?action=page");
//        3、保存到request域中
        req.setAttribute("page1",page1);
//        4、请求转发到/pages/manager/order_manager.jsp页面
        req.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(req,resp);

    }
}
