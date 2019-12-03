package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Transactions;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TransactionsDAOImpl implements TransactionsDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void createTransactions(Transactions transactions) {
        System.out.println("In create of transactions");
        sessionFactory.getCurrentSession().save(transactions);
        //return postings.getPropertyId();
    }

    @Override
    public List<Transactions> getTransactions(String user) {

        return null;
    }
}
