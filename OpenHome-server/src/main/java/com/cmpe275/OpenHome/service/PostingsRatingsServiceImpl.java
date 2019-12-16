package com.cmpe275.OpenHome.service;

import java.util.List;
import com.cmpe275.OpenHome.DataObjects.PostingsRatingsForm;
import com.cmpe275.OpenHome.dao.PaymentsDAO;
import com.cmpe275.OpenHome.dao.PostingsRatingsDAO;
import com.cmpe275.OpenHome.model.PostingsratingsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class PostingsRatingsServiceImpl implements  PostingsRatingsService{

    @Autowired
    private PostingsRatingsDAO postingsRatingsDAO;

    @Override
    public void saveRating(PostingsratingsEntity form) {
            postingsRatingsDAO.save(form);
            return;
    }

    @Override
    public double getAverageRating(int postingId) {
        return postingsRatingsDAO.getAverageRating(postingId);

    }
}
