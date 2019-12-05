package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.model.Payments;

public interface PaymentsService {

    Payments getPaymentDetails(String user);
    void   createPayment(Payments payments);
    double getBalance(String user) throws Exception;



}
