package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Postings;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;

import java.util.List;

@Repository
public class PostingsDAOImpl implements  PostingsDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<Postings> getPostings() {
        List<Postings> list = sessionFactory.getCurrentSession().createQuery("from Postings" +
                " ").list();
        return list;
    }

    @Override
    public Postings getPosting(int id) {
        Postings posting = sessionFactory.getCurrentSession().get(Postings.class,id);
        return posting;
    }


    @Override
    public long save(Postings postings) {
        sessionFactory.getCurrentSession().save(postings);
        return postings.getPropertyId();
    }

    @Override
    public int deletePosting(int id) {
        Postings posting = sessionFactory.getCurrentSession().get(Postings.class,id);
        sessionFactory.getCurrentSession().delete(posting);
        return id;
    }

    @Override
    public void update(long id, Postings postings) {
        Session session = sessionFactory.getCurrentSession();
        Postings posting = session.byId(Postings.class).load(id);

        session.flush();
    }
}
