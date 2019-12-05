package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.model.Transactions;
import com.cmpe275.OpenHome.model.User;
import com.cmpe275.OpenHome.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionsService transactionsService;


    @CrossOrigin
    @PostMapping("/transaction")
    public ResponseEntity<?> createTransaction(@RequestBody Transactions transactions){
        System.out.println("In controller"+transactions.getType());
        System.out.println("In controller"+transactions.getEmail());

        transactionsService.createTransactions(transactions);
        return ResponseEntity.ok().body("transaction created");
    }

    @CrossOrigin
    @PostMapping("/transactions")
    public ResponseEntity<?> getTransactions(@RequestBody User user){
        List<Transactions> trans= transactionsService.getTransactions(user.getEmail());
        return ResponseEntity.ok().body(trans);
    }
}
