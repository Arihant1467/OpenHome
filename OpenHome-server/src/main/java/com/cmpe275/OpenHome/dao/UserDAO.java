package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.User;

import java.util.List;

public interface UserDAO {
    List<User> list();
    long save(User user);
}
