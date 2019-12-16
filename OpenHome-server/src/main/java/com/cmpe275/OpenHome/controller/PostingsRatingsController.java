package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.DataObjects.PostingsRatingsForm;
import com.cmpe275.OpenHome.model.Postings;
import com.cmpe275.OpenHome.model.PostingsratingsEntity;
import com.cmpe275.OpenHome.service.PostingsRatingsService;
import com.cmpe275.OpenHome.service.PostingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

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

    @CrossOrigin
    @GetMapping("/postingRating/{postingId}")
    public ResponseEntity<?> getParticularPostingRating(@PathVariable(name = "postingId") String postingId) {
        System.out.println("In getParticularPostingRating");
        System.out.println("PostingID:"+postingId);
        double avgRating = postingsRatingsService.getAverageRating(Integer.parseInt(postingId));
        Map<String,Double> map = new HashMap<>();
        map.put("AvgRating",avgRating);
        return  ResponseEntity.ok().body(map);
    }

    
}
