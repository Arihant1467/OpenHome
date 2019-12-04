package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.DataObjects.PostingForm;
import com.cmpe275.OpenHome.model.Payments;
import com.cmpe275.OpenHome.model.Postings;

import java.util.List;

public interface PaymentsDAO {

    Payments getPaymentDetails(String user);
    void   createPayment(Payments payments);
    double getBalance(String user) throws Exception;
}
