package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.model.PostingsratingsEntity;
import com.cmpe275.OpenHome.model.UsersRatingsEntity;
import com.cmpe275.OpenHome.service.PostingsRatingsService;
import com.cmpe275.OpenHome.service.UsersRatingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsersRatingsController {


    @Autowired
    private UsersRatingsService usersRatingsService;

    @CrossOrigin
    @PostMapping("/userRating")
    public ResponseEntity<?> saveRating(@RequestBody UsersRatingsEntity userRate) {
        System.out.println(userRate.toString());
        usersRatingsService.saveRating(userRate);
        return  ResponseEntity.ok().body("Your rating has been saved");
    }
}
