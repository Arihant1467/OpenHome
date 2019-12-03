package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Payments;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PaymentsDAOImpl implements PaymentsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Payments getPaymentDetails(String user) {
        return null;
    }

    @Override
    public Payments createPayment(Payments payments) {
        return null;
    }

    @Override
    public int getBalance(String user) {
        return 0;
    }
}
