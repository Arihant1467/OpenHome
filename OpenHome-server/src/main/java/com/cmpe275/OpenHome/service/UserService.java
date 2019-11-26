package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.model.User;

import java.util.List;

public interface UserService {
    List<User> list();
    long save(User user);
}
