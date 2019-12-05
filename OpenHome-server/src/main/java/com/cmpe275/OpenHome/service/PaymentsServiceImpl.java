package com.cmpe275.OpenHome.service;


import com.cmpe275.OpenHome.dao.PaymentsDAO;
import com.cmpe275.OpenHome.dao.PostingsDAO;
import com.cmpe275.OpenHome.model.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class PaymentsServiceImpl implements PaymentsService {

    @Autowired
    private PaymentsDAO paymentsDAO;
    @Override
    public Payments getPaymentDetails(String user) {
        return paymentsDAO.getPaymentDetails(user);
    }

    @Override
    public void createPayment(Payments payments) {
    paymentsDAO.createPayment(payments);
    }

    @Override
    public double getBalance(String user) throws Exception {
      return paymentsDAO.getBalance(user);
    }
}
