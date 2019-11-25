package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.model.User;
import com.cmpe275.OpenHome.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> get() {
        List<User> books = userService.list();
        return ResponseEntity.ok().body(books);
    }

}
