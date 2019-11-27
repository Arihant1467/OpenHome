package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Postings;

import java.util.List;

public interface PostingsDAO {
    List<Postings> list();
    long save(Postings postings);
}
