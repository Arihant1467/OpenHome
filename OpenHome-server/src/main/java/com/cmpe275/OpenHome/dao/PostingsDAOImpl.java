package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Postings;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostingsDAOImpl implements  PostingsDAO {


    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<Postings> list() {
        List<Postings> list = sessionFactory.getCurrentSession().createQuery("from postings" +
                " ").list();
        return list;
    }


    public long save(Postings postings) {
        sessionFactory.getCurrentSession().save(postings);
        return postings.getPropertyId();
    }
}
