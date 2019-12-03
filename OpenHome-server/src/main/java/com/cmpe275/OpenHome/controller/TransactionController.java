package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.dao.TransactionsDAO;
import com.cmpe275.OpenHome.dao.TransactionsDAOImpl;
import com.cmpe275.OpenHome.model.Payments;
import com.cmpe275.OpenHome.model.Transactions;
import com.cmpe275.OpenHome.service.PostingsService;
import com.cmpe275.OpenHome.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionsService transactionsService;


    @CrossOrigin
    @PostMapping("/transaction")
    public ResponseEntity<?> createTransaction(@RequestBody Transactions transactions){
        transactionsService.createTransactions(transactions);
        return ResponseEntity.ok().body("transaction created");
    }

    @CrossOrigin
    @GetMapping("/transactions")
    public ResponseEntity<?> getTransactions(@RequestBody  String user){
        transactionsService.getTransactions(user);
        return ResponseEntity.ok().body("transaction created");
    }
}
