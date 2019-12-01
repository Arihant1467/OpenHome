package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    InputStream input;
    Properties prop ;


//    public UserDAOImpl() throws Exception {
//        input = new FileInputStream("/../../../resources/messages.properties");
//        prop = new Properties();
//        prop.load(input);
//    }

    @Override
    public List<User> list() {

        List<User> userList = sessionFactory.getCurrentSession().createQuery("from User ").list();
        return userList;
    }


    public User save(User user) throws Exception {
        System.out.println("In user dao impl");

        Query query = sessionFactory.getCurrentSession().createQuery("select 1 from User t where t.email = :key");
        query.setString("key", user.getEmail() );


        List existingUser =query.list();

        System.out.println("Existing User"+existingUser);
        if(existingUser.size() == 0) {
            sessionFactory.getCurrentSession().save(user);
            return user;
        }
        else
            throw new Exception("ERROR.USER_EXISTS");
            //throw new Exception(prop.getProperty("ERROR.USER_EXISTS"));

    }




}
