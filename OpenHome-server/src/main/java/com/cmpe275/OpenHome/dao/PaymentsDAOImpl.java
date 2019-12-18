package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Payments;
import com.cmpe275.OpenHome.model.Reservation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentsDAOImpl implements PaymentsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Payments getPaymentDetails(String user) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Payments.class);
        criteria.add(Restrictions.eq("email", user));
        Payments payment = (Payments)criteria.list().get(0);
        System.out.println("payments returned" + payment);
        return payment;
    }

    @Override
    public void createPayment(Payments payments)
    {
        payments.setBalance(500.00);
        System.out.println("In save of postings");
        sessionFactory.getCurrentSession().save(payments);
    }

//    @Override
//    public void make(Payments payments)
//    {
//        payments.setBalance(500.00);
//        System.out.println("In save of postings");
//        sessionFactory.getCurrentSession().save(payments);
//    }

    @Override
    public double getBalance(String user) throws  Exception{
        System.out.println("In get Balance of postings");
        try {

            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Payments.class);
            criteria.add(Restrictions.eq("email", user));
            Payments payment = (Payments)criteria.list().get(0);
            System.out.println("payments returned" + payment);
            return payment.getBalance();
        } catch(Exception e){
                throw  new Exception("No user found to fetch balance");
        }
    }


    @Override
    public void update(Payments payment) throws  Exception{

        Session session = sessionFactory.getCurrentSession();
        try {

            System.out.println(payment);
            session.update(payment);

            System.out.println("in update payment" );
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        } finally {
            session.flush();
        }

    }
}
