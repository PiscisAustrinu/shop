package com.Ylb.service;

import com.Ylb.pojo.User;

public interface UserService {
    /**
     * 注册用户
     * @param user
     */
    public void register(User user);

    /**
     * 登录
     * @param user
     * @return  如果返回null说明登陆失败，有返回值，登陆成功
     */
    public User login(User user);

    /**
     * 用户名是否可用
     * @param username
     * @return   返回ture表示用户名已存在，返回false表示用户名可用
     */
    public boolean existUsername(String username);
}
