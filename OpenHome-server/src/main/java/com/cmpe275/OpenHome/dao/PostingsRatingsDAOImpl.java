package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.DataObjects.PostingsRatingsForm;
import com.cmpe275.OpenHome.model.Postings;
import com.cmpe275.OpenHome.model.PostingsratingsEntity;
import com.cmpe275.OpenHome.model.Reservation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostingsRatingsDAOImpl implements PostingsRatingsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<PostingsratingsEntity> getAllPostingsRatings() {
        List<PostingsratingsEntity> list = sessionFactory.getCurrentSession().createQuery("from PostingsratingsEntity" +
                " ").list();
        return list;
    }

    @Override
    @Transactional
    public void save(PostingsratingsEntity postingrating) {
        System.out.println("In save of PostingsRatingsDAOImpl");
        System.out.println("Posting ID in PostingRatingEntityDaoIMPL"+postingrating.getPostingId());
        System.out.println("Posting ID in PostingRatingEntityDaoIMPL"+postingrating.getReview());
        try{
            sessionFactory.getCurrentSession().save(postingrating);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return;
    }

    @Override
    public double getAverageRating(int postingId) {
        System.out.println("In getAverageRating DAO Impl");
        System.out.println("Posting ID"+postingId);
       try{
           Session session = sessionFactory.getCurrentSession();
           Query query = session.createQuery("SELECT AVG(p.rating) FROM PostingsratingsEntity p WHERE p.postingId = :postingId");
           query.setInteger("postingId",postingId);
           Number average = (Number) query.getSingleResult();

           System.out.println("Average Rating for PostingId:"+postingId+":"+average.doubleValue());
           return average.doubleValue();

       }catch(Exception e){
           e.printStackTrace();
           return 0d;
       }

    }
}
