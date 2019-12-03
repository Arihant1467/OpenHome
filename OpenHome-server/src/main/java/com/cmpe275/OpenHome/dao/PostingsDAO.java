package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.DataObjects.PostingForm;
import com.cmpe275.OpenHome.model.Postings;

import java.util.List;

public interface PostingsDAO {
   // List<Postings> list();
    List<Postings>  getPostings();
    Postings  getPosting(int id);
    long save(Postings postings);
    int deletePosting(int id);
    void update(Postings postings);
    List<Postings> search(PostingForm postingForm);
    List<Postings> getPostingsOfHost(String email);

}
