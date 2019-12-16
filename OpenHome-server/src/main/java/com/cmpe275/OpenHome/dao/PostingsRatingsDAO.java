package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.DataObjects.PostingsRatingsForm;
import com.cmpe275.OpenHome.model.PostingsratingsEntity;

import java.util.List;

public interface PostingsRatingsDAO {
    void save(PostingsratingsEntity form);
    List<PostingsratingsEntity> getPostingsRatings();
}
