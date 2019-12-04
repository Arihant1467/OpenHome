package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.model.Mail;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.Queue;

@Repository
public class NotificationServiceImpl implements NotificationService {


    Queue<Mail> emailQueue = new LinkedList<Mail>();


    @Override
    public void addToQueue(Mail mail) {
        emailQueue.add(mail);

    }

    @Override
    public Mail getMailObject() {
       return emailQueue.poll();
    }
}

