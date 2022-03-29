package com.Ylb.web;

import com.Ylb.pojo.Book;
import com.Ylb.pojo.Cart;
import com.Ylb.pojo.CartItem;
import com.Ylb.service.BookService;
import com.Ylb.service.impl.BookServiceImpl;
import com.Ylb.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends BaseServlet{
    private BookService bookService = new BookServiceImpl();
    /**
     * 加入购物车
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        获取请求的参数  商品编号
        int id = WebUtils.parseInt(req.getParameter("id"),0);
//        调用bookService.queryBookById(id):Book得到图书的信息
        Book book = bookService.queryBookById(id);
//        把图书信息转换为CartItem商品项
        CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart==null){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
//        调用Cart.addItem(CartItem);添加商品项
        cart.addItem(cartItem);
        System.out.println(cart);
        req.getSession().setAttribute("lastname",cartItem.getName());
//        重定向回商品列表页面
        resp.sendRedirect(req.getHeader("Referer"));
    }

    /**
     * 删除商品项
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        获取商品id
        int id = WebUtils.parseInt(req.getParameter("id"),0);
//       获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if (cart!=null){
//            删除购物车商品项
            cart.deleteItem(id);
//            重定向会原来购物车页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    /**
     * 清空购物车
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        获取购物车对象
        Cart  cart = (Cart) req.getSession().getAttribute("cart");
        if (cart!=null){
            cart.clearItem();
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    /**
     * 更新商品数量
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        获取请求的参数：商品编号，商品数量
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        int count = WebUtils.parseInt(req.getParameter("count"),1);
//        获取Cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if (cart!=null){
//            修改商品数量
            cart.updateCount(id,count);
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    /**
     * 使用ajax将商品加入购物车
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxAddItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        获取请求的参数  商品编号
        int id = WebUtils.parseInt(req.getParameter("id"),0);
//        调用bookService.queryBookById(id):Book得到图书的信息
        Book book = bookService.queryBookById(id);
//        把图书信息转换为CartItem商品项
        CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart==null){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
//        调用Cart.addItem(CartItem);添加商品项
        cart.addItem(cartItem);
        System.out.println(cart);
        req.getSession().setAttribute("lastname",cartItem.getName());
//        6、返回购物车的总商品数量和最后一个添加商品的名称
        Map<String,Object> resultMap = new HashMap<String,Object>();

        resultMap.put("totalCount",cart.getTotalCount());
        resultMap.put("lastName",cartItem.getName());

        Gson gson = new Gson();
        String resultMapJsonString = gson.toJson(resultMap);

        resp.getWriter().write(resultMapJsonString);

    }
}
