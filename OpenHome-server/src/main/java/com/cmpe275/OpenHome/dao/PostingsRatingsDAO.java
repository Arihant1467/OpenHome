package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.DataObjects.PostingsRatingsForm;
import com.cmpe275.OpenHome.model.PostingsratingsEntity;

import java.util.List;

public interface PostingsRatingsDAO {
    List<PostingsratingsEntity> getAllPostingsRatings();
    void save(PostingsratingsEntity form);
    double getAverageRating(int postingId);
}
