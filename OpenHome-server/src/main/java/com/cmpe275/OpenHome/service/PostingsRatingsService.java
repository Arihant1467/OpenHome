package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.DataObjects.PostingsRatingsForm;
import com.cmpe275.OpenHome.model.Payments;
import com.cmpe275.OpenHome.model.PostingsratingsEntity;

import java.util.List;

public interface PostingsRatingsService {

    void saveRating(PostingsratingsEntity form);
    double getAverageRating(int postingId);
}
