package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.model.Mail;
import com.cmpe275.OpenHome.service.NotificationService;
import com.cmpe275.OpenHome.service.PostingsService;
import com.cmpe275.OpenHome.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.Queue;


@Repository
public class MailServiceController {


    @Autowired
    private JavaMailSender mailSenderObj;
    Queue<Mail> emailQueue = new LinkedList<Mail>();

    @Autowired
    private ReservationService reservationService;


    @Scheduled(initialDelay = 3000, fixedDelay=30000)  // 2 minutes
    public void mailSender() {
        try {

            Mail email = getMailObject();

            if(email!= null) {

                JavaMailSenderImpl sender = new JavaMailSenderImpl();
                sender.setHost("smtp.gmail.com");
                SimpleMailMessage emailObj = new SimpleMailMessage();
                emailObj.setTo(email.getEmail());
                emailObj.setSubject(email.getSubject());
                emailObj.setText(email.getText());

                mailSenderObj.send(emailObj);
            }

        } catch (Exception e) {
            System.out.println(" mail notifications task failed: " + e.getMessage());
        }
    }


    @Scheduled(initialDelay = 30000, fixedDelay=6000000)  // 2 minutes
    public void cacheRefresh() {
        System.out.println("Running cancel reservations task");
        try {
           // reservationService.handleCancellations();
        } catch (Exception e) {
            System.out.println("cancel reservations task failed: " + e.getMessage());
        }
    }

    public void addToQueue(Mail mail) {
        emailQueue.add(mail);

    }
    public Mail getMailObject() {
        return emailQueue.poll();
    }
}
