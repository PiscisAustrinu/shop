package com.Ylb.service.impl;

import com.Ylb.dao.impl.UserDao;
import com.Ylb.dao.impl.impl.UserDaoImpl;
import com.Ylb.pojo.User;
import com.Ylb.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public void register(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public boolean existUsername(String username) {
        if (userDao.queryUserByUsername(username)==null)
        {
            return false;
        }
        return true;
    }
}
