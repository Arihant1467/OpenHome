package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.DataObjects.PostingEditForm;
import com.cmpe275.OpenHome.DataObjects.PostingForm;
import com.cmpe275.OpenHome.model.Postings;

import java.util.List;

public interface PostingsDAO {
   // List<Postings> list();
    List<Postings>  getPostings();
    Postings  getPosting(int id);
    long save(Postings postings) throws Exception;
    int deletePosting(int id) throws Exception;
    void update(PostingEditForm postings) throws Exception;
    List<Postings> search(PostingForm postingForm);
    List<Postings> getPostingsOfHost(String email);
    void updateRatingOfAPosting(int postingId, double rating);

}
