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

    @CrossOrigin
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        System.out.println("In getMapping of Users");
        List<User> users = userService.list();
        return ResponseEntity.ok().body(users);
    }


    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        System.out.println("In login controller");
        try {
            User loggedinUser = userService.login(user);
            return ResponseEntity.ok().body(loggedinUser);
        }
        catch(Exception e)
        {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

   @CrossOrigin
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

    @CrossOrigin
    @PostMapping("/signupWithGoogle")
    public ResponseEntity<?> savewithGoogle(@RequestBody User user) {
        try {
            System.out.println("In Users controller for signup");
            User savedUser = userService.saveWithGoogle(user);
            return ResponseEntity.ok().body(savedUser);
        }
        catch (Exception e)
        {
            System.out.println("Exception Message"+e.getMessage());
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @CrossOrigin
    @PutMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody String user)
    {
        try{
            User verifiedUser = userService.verify(user);
            return ResponseEntity.ok().body(verifiedUser);
        }
        catch(Exception e)
        {
            System.out.println("Exception"+e);
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



}