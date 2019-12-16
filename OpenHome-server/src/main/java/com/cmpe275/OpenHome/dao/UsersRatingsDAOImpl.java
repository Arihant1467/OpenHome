package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.PostingsratingsEntity;
import com.cmpe275.OpenHome.model.UsersRatingsEntity;
import org.hibernate.SessionFactory;
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
}
