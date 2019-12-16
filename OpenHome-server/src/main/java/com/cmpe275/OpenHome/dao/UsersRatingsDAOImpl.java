package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.PostingsratingsEntity;
import com.cmpe275.OpenHome.model.UsersRatingsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsersRatingsDAOImpl implements UsersRatingsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<UsersRatingsEntity> getUsersRatings() {
        List<UsersRatingsEntity> list = sessionFactory.getCurrentSession().createQuery("from UsersRatingsEntity" +
                " ").list();
        return list;
    }

    @Override
    public void save(UsersRatingsEntity userRating) {
        System.out.println("In Save of UsersRatingDAO Impl");
        System.out.println("HOST ID: "+userRating.getHostId());
        try{
            sessionFactory.getCurrentSession().save(userRating);
        }catch(Exception e){
            e.printStackTrace();
        }

        return;
    }

    @Override
    public double getAverageRatingForUser(String userId) {
        System.out.println("In getAverageRatingForUser DAO Impl");
        System.out.println("User ID:"+userId);
        try{
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("SELECT AVG(p.rating) FROM UsersRatingsEntity p WHERE p.userId = :userId");
            query.setString("userId",userId);
            Number average = (Number) query.getSingleResult();

            System.out.println("Average Rating for userId:"+userId+":"+average.doubleValue());
            return average.doubleValue();

        }catch(Exception e){
            e.printStackTrace();
            return 0d;
        }

    }
}
