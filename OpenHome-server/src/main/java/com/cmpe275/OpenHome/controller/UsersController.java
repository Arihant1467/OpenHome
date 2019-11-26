package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.model.User;
import com.cmpe275.OpenHome.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        System.out.println("In getMapping of Users");
        List<User> users = userService.list();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/user")
    public ResponseEntity<?> save(@RequestBody User user) {
        long id = userService.save(user);
        return ResponseEntity.ok().body("New Book has been saved with ID:" + id);
    }

}