package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.model.Postings;

import java.util.List;

public interface PostingsService {
    List<Postings> list();
    long save(Postings postings);
}
