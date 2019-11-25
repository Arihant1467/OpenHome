package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.dao.UserDao;
import com.cmpe275.OpenHome.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{


    @Autowired
    private UserDao userDao;

    @Override
    public List<User> list() {
        return userDao.list();
    }
}
