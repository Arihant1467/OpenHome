package com.cmpe275.OpenHome.service;


import com.cmpe275.OpenHome.dao.PostingsDAO;
import com.cmpe275.OpenHome.dao.TransactionsDAO;
import com.cmpe275.OpenHome.model.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
    @Transactional(readOnly = true)
public class TransactionsServiceImpl implements TransactionsService {

        @Autowired
        private TransactionsDAO transactionsDAO;



        @Override
    public void createTransactions(Transactions transactions) {
            transactionsDAO.createTransactions(transactions);
    }

    @Override
    public List<Transactions> getTransactions(String user) {
        List<Transactions> allTransactions = transactionsDAO.getTransactions(user);
        return allTransactions;
    }
}
