package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.User;

import java.util.List;

public interface UserDAO {
    List<User> list();

    User save(User user) throws Exception;
    User login(User user) throws Exception;
    User verify(String user) ;
}
