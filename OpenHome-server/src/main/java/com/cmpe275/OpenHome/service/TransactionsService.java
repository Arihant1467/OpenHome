package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.model.Transactions;
import java.util.List;

public interface TransactionsService {
    void createTransactions(Transactions transactions);

    List<Transactions> getTransactions(String user);
}
