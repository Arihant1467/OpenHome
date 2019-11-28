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


   /* @Override
    public List<Postings> list() {
        List<Postings> list = sessionFactory.getCurrentSession().createQuery("from Postings" +
                " ").list();
        return list;
    }*/

    @Override
    public List<Postings> getPostings() {
        List<Postings> list = sessionFactory.getCurrentSession().createQuery("from Postings" +
                " ").list();
        return list;
    }

    @Override
    public Postings getPosting(int id) {
        Postings posting = sessionFactory.getCurrentSession().load(Postings.class,id);
        return posting;
    }


    public long save(Postings postings) {
        sessionFactory.getCurrentSession().save(postings);
        return postings.getPropertyId();
    }

    @Override
    public int deletePosting(int id) {
        Postings posting = sessionFactory.getCurrentSession().load(Postings.class,id);
        sessionFactory.getCurrentSession().delete(posting);
        return id;
    }
}
