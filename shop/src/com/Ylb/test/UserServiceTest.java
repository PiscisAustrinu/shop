package com.Ylb.test;

import com.Ylb.pojo.User;
import com.Ylb.service.UserService;
import com.Ylb.service.impl.UserServiceImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {

    UserService userService = new UserServiceImpl();
    @Test
    public void register() {
        userService.register(new User(null,"weiyan","520227","162@wy.com"));
        userService.register(new User(null,"xiaobeike","520527","xiaobeike@Y.com"));
    }

    @Test
    public void login() {
        System.out.println(userService.login(new User(null,"ylb","520106",null)));
    }

    @Test
    public void existUsername() {
        if (userService.existUsername("xiaobeike"))
        {
            System.out.println("用户名已存在");
        }
        else{
            System.out.println("用户名可用");
        }
    }
}