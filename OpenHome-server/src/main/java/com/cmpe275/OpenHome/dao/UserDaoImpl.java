package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> list() {
       // User u = new User();

        //System

        List<User> list = sessionFactory.getCurrentSession().createQuery("from Users ").list();
        return list;
    }
}
