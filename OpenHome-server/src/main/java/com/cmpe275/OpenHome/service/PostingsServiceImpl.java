package com.cmpe275.OpenHome.service;
import com.cmpe275.OpenHome.DataObjects.PostingEditForm;
import com.cmpe275.OpenHome.DataObjects.PostingForm;
import com.cmpe275.OpenHome.dao.PostingsDAO;
import com.cmpe275.OpenHome.model.Postings;
import com.cmpe275.OpenHome.model.Reservation;
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
    public long save(Postings postings) throws Exception {

        return postingsDAO.save(postings);
    }

    @Override
    @Transactional
    public int deletePosting(int id) throws Exception {
      return postingsDAO.deletePosting(id);
    }

    @Transactional
    @Override
    public void update(PostingEditForm postings) throws Exception {
        postingsDAO.update(postings);
    }

    @Transactional
    @Override
    public Reservation cancelPosting(int id ) throws Exception {
        return postingsDAO.cancelPostingByHost(id);
    }

    @Override
    @Transactional
    public List<Postings> search(PostingForm postings) {
        return postingsDAO.search(postings);
    }

    @Override
    @Transactional
    public List<Postings> getPostingsOfHost(String email) {
        return postingsDAO.getPostingsOfHost(email);
    }








}
