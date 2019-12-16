package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.dao.UsersRatingsDAO;
import com.cmpe275.OpenHome.model.UsersRatingsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class UsersRatingsServiceImpl implements UsersRatingsService {

    @Autowired
    UsersRatingsDAO usersRatingsDAO;

    @Override
    public void saveRating(UsersRatingsEntity userRating) {
        usersRatingsDAO.save(userRating);
        return;
    }

    @Override
    public double getAverageRatingForUser(String userId) {
        Double value = usersRatingsDAO.getAverageRatingForUser(userId);
        System.out.println("The rating for userId:"+userId+" is "+value);
        return value;

    }
}
