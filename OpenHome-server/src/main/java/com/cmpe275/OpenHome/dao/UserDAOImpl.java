package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> list() {

        List<User> userList = sessionFactory.getCurrentSession().createQuery("from User ").list();
        return userList;
    }


    public User save(User user) {
        sessionFactory.getCurrentSession().save(user);
        return user;
    }



}
