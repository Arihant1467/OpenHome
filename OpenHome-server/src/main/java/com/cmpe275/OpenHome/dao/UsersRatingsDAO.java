package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.UsersRatingsEntity;

import java.util.List;

public interface UsersRatingsDAO {

    void save(UsersRatingsEntity userRating);
    List<UsersRatingsEntity> getUsersRatings();
}
