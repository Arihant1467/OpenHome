package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.model.Postings;

import java.util.List;

public interface PostingsService {
    
    List<Postings>  getPostings();
    Postings  getPosting(int id);
    long save(Postings postings);
    int deletePosting(int id);
}
