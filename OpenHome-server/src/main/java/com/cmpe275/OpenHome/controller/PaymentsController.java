package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.dao.PaymentsDAO;
import com.cmpe275.OpenHome.dao.PostingsDAO;
import com.cmpe275.OpenHome.model.Payments;
import com.cmpe275.OpenHome.model.Transactions;
import com.cmpe275.OpenHome.model.User;
import com.cmpe275.OpenHome.service.PaymentsService;
import com.cmpe275.OpenHome.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentsController {

    @Autowired
    private PaymentsService paymentsService;

    @CrossOrigin
    @PostMapping("/getPayments")
    public ResponseEntity<?> getPayment(@RequestBody User user){
        Payments payment = paymentsService.getPaymentDetails(user.getEmail());
        return ResponseEntity.ok().body(payment);
    }

    @CrossOrigin
    @PostMapping("/createPayments")
    public ResponseEntity<?> createPayment(@RequestBody Payments payment){
        paymentsService.createPayment(payment);
        return ResponseEntity.ok().body("Create Payments");
    }

    @CrossOrigin
    @PostMapping("/getBalance")
    public ResponseEntity<?> getBalance(@RequestBody User user) throws Exception{
        double price = paymentsService.getBalance(user.getEmail());
        return ResponseEntity.ok().body(price);
    }

}




