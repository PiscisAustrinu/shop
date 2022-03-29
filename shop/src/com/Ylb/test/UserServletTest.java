package com.Ylb.test;

import java.lang.reflect.Method;

public class UserServletTest {
    public void login(){
        System.out.println("login方法被调用");
    }
    public void register(){
        System.out.println("register方法被调用");
    }
    public void updateUser(){
        System.out.println("updateUser方法被调用");
    }
    public void updateUserPassword(){
        System.out.println("updateUserPassword方法被调用");
    }

    public static void main(String[] args) {
        String action = "login";
        try {
            //通过action业务鉴别字符串，获取相应业务 方法反射对象
            Method declaredMethod = UserServletTest.class.getDeclaredMethod(action);
//            System.out.println(declaredMethod);
            //调用业务方法
            declaredMethod.invoke(new UserServletTest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
