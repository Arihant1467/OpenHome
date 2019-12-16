package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.DataObjects.PostingsRatingsForm;
import com.cmpe275.OpenHome.model.Postings;
import com.cmpe275.OpenHome.model.PostingsratingsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PostingsRatingsDAOImpl implements PostingsRatingsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<PostingsratingsEntity> getPostingsRatings() {
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
}
