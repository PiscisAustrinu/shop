package com.Ylb.web;

import com.Ylb.pojo.User;
import com.Ylb.service.UserService;
import com.Ylb.service.impl.UserServiceImpl;
import com.Ylb.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();
    /**
     * 处理login业务
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User loginUser = userService.login(new User(null,username,password,null));
        if (loginUser==null){
            //把错误信息，回显表单项信息保存到request域中
            req.setAttribute("msg","用户名或密码错误");
            req.setAttribute("username",username);
            req.setAttribute("password",password);
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
        }else{
//            保存用户登录的信息到Session域中
            req.getSession().setAttribute("user",loginUser);
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
        }
    }
    /**
     * 处理register业务
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //        获取Session中的验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
//        删除Session的验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        //        1、获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        User user = WebUtils.copyParamToBean(req.getParameterMap(),new User());
//        2、检查 验证码是否正确   == "abcde"  验证码写死
        if (token!=null&&token.equalsIgnoreCase(code)){
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
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            //通过action业务鉴别字符串，获取相应业务 方法反射对象
            Method declaredMethod = this.getClass().getDeclaredMethod(action,HttpServletRequest.class,HttpServletResponse.class);
//            System.out.println(declaredMethod);
            //调用业务方法
            declaredMethod.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注销
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1、销毁Session中用户登录的信息（或者销毁Session)
        req.getSession().invalidate();
//        2、重定向到首页或登录页面
        resp.sendRedirect(req.getContextPath());
    }

    /**
     * ajax验证用户名是否可用
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxExistUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1、获取请求参数
        String username = req.getParameter("username");
//        2、调用userService.existUsername()
        boolean existUsername = userService.existUsername(username);
//        3、把返回的结果封装成为Map对象
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("existUsername",existUsername);

        Gson gson = new Gson();
        String json = gson.toJson(resultMap);

        resp.getWriter().write(json);
    }
}
