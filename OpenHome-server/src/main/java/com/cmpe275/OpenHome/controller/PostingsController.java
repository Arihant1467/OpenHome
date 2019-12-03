package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.DataObjects.PostingForm;
import com.cmpe275.OpenHome.model.Postings;
import com.cmpe275.OpenHome.service.PostingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;


@RestController
@RequestMapping("/api")
public class PostingsController {

    @Autowired
    private PostingsService postingsService;

    @CrossOrigin
    @GetMapping("/postings")
    public ResponseEntity<List<Postings>> getPostings() {
        List<Postings> postings = postingsService.getPostings();
        return ResponseEntity.ok().body(postings);
    }

    @CrossOrigin
    @PostMapping("/posting")
    public ResponseEntity<?> save(@RequestBody Postings postings) {
        System.out.println("In postings" + postings);
        try {
            long id = postingsService.save(postings);
            Postings posting = postingsService.getPosting((int)id);
            return ResponseEntity.ok().body("New Posting has been saved with ID:" + posting);
        } catch (Exception e){
            System.out.println(e);
        }
        return ResponseEntity.ok().body("NO Posting has been saved with ID:" );

    }

    @CrossOrigin
    @GetMapping("/posting/{id}")
    public ResponseEntity<?> get(@PathVariable("id") int id) {
        System.out.println("Posting posted" + id);
        Postings posting = postingsService.getPosting(id);
        System.out.print(posting);
        return ResponseEntity.ok().body(posting);
    }

    @CrossOrigin
    @DeleteMapping("/posting")
    public ResponseEntity<?> cancel(@RequestBody int id) {
        long deletedId = postingsService.deletePosting(id);
        return ResponseEntity.ok().body("Posting removed: " + deletedId);
    }

    @CrossOrigin
    @PutMapping("/postingAdd")
    public ResponseEntity<?> update( @RequestBody Postings postings) throws Exception {
        System.out.println("Posting has been new addition");
        postingsService.update(postings);

        return ResponseEntity.ok().body("Posting has been updated successfully.");
    }

    @CrossOrigin
    @PutMapping("/posting/search")
    public ResponseEntity<?> search(@RequestBody PostingForm postings){
        List<Postings> postingsLists = postingsService.search(postings);
        return ResponseEntity.ok().body(postingsLists);
    }

    @CrossOrigin
    @PostMapping("/getUserProperties/{email:.+}")
    public ResponseEntity<List<Postings>> getUserPosting(@PathVariable("email") String email){
        List<Postings> postingsLists = postingsService.getPostingsOfHost(email);
        return ResponseEntity.ok().body(postingsLists);
       /* System.out.printf(email + "Receievd email of user");
        try {
            List<Postings> postingsLists = postingsService.getPostingsOfHost(email);
            System.out.printf(postingsLists + "postingsLists");
            return ResponseEntity.ok().body(postingsLists);
        }
        catch(Exception e) {
            System.out.println(e);
            return null;
        }
*/
    }

}