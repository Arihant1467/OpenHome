package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.dao.PostingsDAO;
import com.cmpe275.OpenHome.model.Postings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostingsServiceImpl implements PostingsService {

    @Autowired
    private PostingsDAO postingsDAO;

    @Override
    @Transactional
    public List<Postings> list() {
        return postingsDAO.list();
    }

    @Transactional
    public long save(Postings postings) {
        return postingsDAO.save(postings);
    }
}
