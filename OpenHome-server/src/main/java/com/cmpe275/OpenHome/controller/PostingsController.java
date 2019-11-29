package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.model.Postings;
import com.cmpe275.OpenHome.service.PostingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostingsController {

    @Autowired
    private PostingsService postingsService;

    @GetMapping("/postings")
    public ResponseEntity<List<Postings>> getPostings() {
        System.out.println("In getMapping of Postings");
        List<Postings> postings = postingsService.getPostings();
        return ResponseEntity.ok().body(postings);
    }

    @PostMapping("/posting")
    public ResponseEntity<?> save(@RequestBody Postings postings) {
        long id = postingsService.save(postings);
        return ResponseEntity.ok().body("New Posting has been saved with ID:" + id);
    }

    /*---Get a Posting by id---*/
    @GetMapping("/posting/{id}")
    public ResponseEntity<?> get(@PathVariable("id") int id) {
        Postings posting = postingsService.getPosting(id);
        return ResponseEntity.ok().body(posting);
    }


    @DeleteMapping("/posting")
    public ResponseEntity<?> cancel(@RequestBody int id) {
        long deletedId = postingsService.deletePosting(id);
        return ResponseEntity.ok().body("Posting removed: " + deletedId);
    }

}