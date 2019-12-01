package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.model.User;
import com.cmpe275.OpenHome.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class UsersController {

    @Autowired
    private UserService userService;

   // @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        System.out.println("In getMapping of Users");
        List<User> users = userService.list();
        return ResponseEntity.ok().body(users);
    }

   // @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signup")
    public ResponseEntity<?> save(@RequestBody User user) {
        try {
            System.out.println("In Users controller for signup");
            User savedUser = userService.save(user);
            return ResponseEntity.ok().body(savedUser);
        }
        catch (Exception e)
        {
            System.out.println("Exception Message"+e.getMessage());
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}