package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.DataObjects.PostingsRatingsForm;
import com.cmpe275.OpenHome.model.Payments;
import com.cmpe275.OpenHome.model.PostingsratingsEntity;

public interface PostingsRatingsService {

    void saveRating(PostingsratingsEntity form);
}
