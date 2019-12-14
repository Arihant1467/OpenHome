package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Payments;

public interface PaymentsDAO {

    Payments getPaymentDetails(String user);
    void   createPayment(Payments payments);
    double getBalance(String user) throws Exception;
    void update(Payments payment) throws  Exception;
}
