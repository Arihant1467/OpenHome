package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.DataObjects.PostingsRatingsForm;
import com.cmpe275.OpenHome.model.Postings;
import com.cmpe275.OpenHome.model.PostingsratingsEntity;
import com.cmpe275.OpenHome.service.PostingsRatingsService;
import com.cmpe275.OpenHome.service.PostingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostingsRatingsController {

    @Autowired
    private PostingsRatingsService postingsRatingsService;

    @CrossOrigin
    @PostMapping("/postingRating")
    public ResponseEntity<?> saveRating(@RequestBody PostingsratingsEntity postRate) {
        System.out.println(postRate.toString());
        postingsRatingsService.saveRating(postRate);
        return  ResponseEntity.ok().body("Your rating has been saved");
    }

}
