package com.Ylb.web;

import com.Ylb.pojo.User;
import com.Ylb.service.UserService;
import com.Ylb.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1、获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");
//        2、检查 验证码是否正确   == "abcde"  验证码写死
        if ("abcde".equalsIgnoreCase(code)){
          //  正确
//        3.检查 用户名是否可用
            if (userService.existUsername(username)){
                //                不可用
//        跳回注册页面
                req.setAttribute("msg","用户名已存在");
                req.setAttribute("username",username);
                req.setAttribute("email",email);
                req.getRequestDispatcher("/pages/user/register.jsp").forward(req,resp);
            }
            else{
                //        可用
//                调用Service保存到数据库
//        跳转到Register_success.html
                userService.register(new User(null,username,password,email));
                req.getRequestDispatcher("/pages/user/register_success.jsp").forward(req,resp);
            }


        }
        else{
            //把回显信息保存到request中
            req.setAttribute("msg","验证码错误");
            req.setAttribute("username",username);
            req.setAttribute("email",email);
            req.getRequestDispatcher("/pages/user/register.jsp").forward(req,resp);
            //                不正确
//        跳回注册页面
        }
//

    }
}
