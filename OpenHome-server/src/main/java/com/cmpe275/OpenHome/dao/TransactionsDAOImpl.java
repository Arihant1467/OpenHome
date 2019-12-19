package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Postings;
import com.cmpe275.OpenHome.model.Transactions;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
        Transactions t = transactions;
        System.out.println(t.getAmount());
        try {
            System.out.println(transactions);
            sessionFactory.getCurrentSession().save(transactions);
        }  catch(Exception e){
            System.out.println(e);
        }

    }

    @Override
    public List<Transactions> getTransactions(String user) {
        try {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Transactions.class);
            criteria.add(Restrictions.eq("email", user));
            System.out.println(criteria);
            System.out.println(criteria.list());

            return criteria.list();
        }
        catch(Exception e){
            System.out.println(e);
            return  null;
        }
    }
}
