package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Payments;
import com.cmpe275.OpenHome.model.Postings;
import com.cmpe275.OpenHome.model.Transactions;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PaymentsDAOImpl implements PaymentsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Payments getPaymentDetails(String user) {
        Payments payment = sessionFactory.getCurrentSession().get(Payments.class,user);
        return payment;
    }

    @Override
    public void createPayment(Payments payments)
    {
        System.out.println("In save of postings");
        sessionFactory.getCurrentSession().save(payments);
    }

    @Override
    public double getBalance(String user) {

        Payments payment = sessionFactory.getCurrentSession().get(Payments.class,user);
        return payment.getBalance();
    }
}
