package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.model.Mail;

public interface NotificationService {
     void addToQueue(Mail mail);
     Mail getMailObject();


}
