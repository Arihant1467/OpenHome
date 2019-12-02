package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.model.User;

import java.util.List;

public interface UserService {
    List<User> list();
    User save(User user) throws Exception;
    User login(User user) throws Exception;
}
