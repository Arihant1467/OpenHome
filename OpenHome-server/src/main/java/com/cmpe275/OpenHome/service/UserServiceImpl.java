package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.dao.UserDAO;
import com.cmpe275.OpenHome.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
//@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{


    @Autowired
    private UserDAO userDao;

    @Override
    @Transactional
    public List<User> list() {
        return userDao.list();
    }

    @Transactional

    public long save(User user) {
        return userDao.save(user);
    }
}
