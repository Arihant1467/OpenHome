package com.cmpe275.OpenHome.service;
import com.cmpe275.OpenHome.DataObjects.PostingForm;
import com.cmpe275.OpenHome.dao.PostingsDAO;
import com.cmpe275.OpenHome.model.Postings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostingsServiceImpl implements PostingsService {

    @Autowired
    private PostingsDAO postingsDAO;

    @Override
    @Transactional
    public List<Postings> getPostings() {
        return postingsDAO.getPostings();
    }

    @Override
    @Transactional
    public Postings getPosting(int id) {
        return postingsDAO.getPosting(id);
    }

    @Override
    @Transactional
    public long save(Postings postings) {
        return postingsDAO.save(postings);
    }

    @Override
    @Transactional
    public int deletePosting(int id) {
      return postingsDAO.deletePosting(id);
    }

    @Transactional
    @Override
    public void update(Postings postings) {
        postingsDAO.update(postings);
    }

    @Override
    @Transactional
    public List<Postings> search(PostingForm postings) {
      return postingsDAO.search(postings);
    }
}
