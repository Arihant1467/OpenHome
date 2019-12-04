package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Payments;
import com.cmpe275.OpenHome.model.Transactions;

import java.util.List;

public interface TransactionsDAO {
    void createTransactions(Transactions transactions);
    List<Transactions>  getTransactions(String user);

}
