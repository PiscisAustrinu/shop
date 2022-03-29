package com.Ylb.test;

import com.Ylb.dao.impl.UserDao;
import com.Ylb.dao.impl.impl.UserDaoImpl;
import com.Ylb.pojo.User;
import org.junit.Test;


public class UserDaoTest {
    UserDao userDao = new UserDaoImpl();
    @Test
    public void queryUserByUserName() {
        if (userDao.queryUserByUsername("admin")==null){
            System.out.println("用户名可用");
        }else{
            System.out.println("用户名已存在");
        }
    }

    @Test
    public void saveUser() {
        userDao.saveUser(new User(null,"ylb","520106","ylb178@qq.com"));
        System.out.println(userDao.queryUserByUsername("ylb"));
    }

    @Test
    public void queryUserByUserNamePassword() {
        if (userDao.queryUserByUsernameAndPassword("admin","admin")==null){
        System.out.println("用户名或者密码错误");
    }else{
        System.out.println("查询成功");
    }
    }
}