package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.DataObjects.PostingEditForm;
import com.cmpe275.OpenHome.DataObjects.PostingForm;
import com.cmpe275.OpenHome.model.Postings;

import java.util.List;

public interface PostingsService {

    List<Postings>  getPostings();
    Postings  getPosting(int id);
    long save(Postings postings) throws Exception;
    int deletePosting(int id);
    List<Postings> getPostingsOfHost(String email);
    void update(PostingEditForm postings) throws Exception;
    List<Postings> search(PostingForm posting);

}
