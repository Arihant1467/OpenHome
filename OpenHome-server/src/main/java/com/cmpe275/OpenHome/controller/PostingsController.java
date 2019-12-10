package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.DataObjects.PostingForm;
import com.cmpe275.OpenHome.model.Mail;
import com.cmpe275.OpenHome.model.Postings;
import com.cmpe275.OpenHome.model.User;
import com.cmpe275.OpenHome.service.PostingsService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;


@RestController
@RequestMapping("/api")
public class PostingsController {


    @Autowired
    private PostingsService postingsService;

    @Autowired
    private MailServiceController mailServiceController;


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

            String emailText = "Successfully added a property :" + posting.getPropertyId();
            String emailSubject = "Hello host, your  property  is added to OpenHome Successfully.";
            Mail email = new Mail(emailText, emailSubject, posting.getUserId());
            mailServiceController.addToQueue(email);

            return ResponseEntity.ok().body("New Posting has been saved with ID:" + posting);
        } catch (Exception e) {
            System.out.println("In exception" + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

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

        Postings posting = postingsService.getPosting(id);
        String emailText = "Posting deleted Successfully :" + id;
        String emailSubject = "Hello host, your  property  is deleted from OpenHome Successfully.";
        Mail email = new Mail(emailText, emailSubject, posting.getUserId());
        mailServiceController.addToQueue(email);
        long deletedId = postingsService.deletePosting(id);

        return ResponseEntity.ok().body("Posting removed: " + deletedId);
    }

    @CrossOrigin
    @PutMapping("/postingAdd")
    public ResponseEntity<?> update( @RequestBody Postings postings) throws Exception {
        System.out.println("Posting has been updating ");

        Postings posting = postingsService.getPosting(postings.getPropertyId());

        String emailText = "Posting updated Successfully :" + postings.getPropertyId();
        String emailSubject = "Hello host, your  posting is updated in OpenHome Successfully.";
        Mail email = new Mail(emailText, emailSubject, posting.getUserId());
        mailServiceController.addToQueue(email);

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
    @PostMapping("/getUserProperties")
    public ResponseEntity<List<Postings>> getUserPosting(@RequestBody User user){
        List<Postings> postingsLists = postingsService.getPostingsOfHost(user.getEmail());

        return ResponseEntity.ok().body(postingsLists);
    }

}