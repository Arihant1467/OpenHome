package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.model.Mail;
import com.cmpe275.OpenHome.service.ReservationService;
import com.cmpe275.OpenHome.service.TimeAdvancementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.Queue;


@Repository
public class MailServiceController {


    Queue<Mail> emailQueue = new LinkedList<Mail>();
    @Autowired
    private JavaMailSender mailSenderObj;
    @Autowired
    private ReservationService reservationService;


    @Autowired
    private TimeAdvancementServiceImpl timeAdvancementService;



    @Scheduled(initialDelay = 3000, fixedDelay = 3000)  // 2 minutes
    public void mailSender() {
        try {

            Mail email = getMailObject();

            if (email != null) {

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


    @Scheduled(initialDelay = 30000, fixedDelay = 600000)  // 2 minutes
    public void noShowCancellationTask() {
      //  System.out.println("Running cancel reservations task");
        try {
                reservationService.handleCancellations();

        } catch (Exception e) {
            System.out.println("cancel reservations task failed: " + e.getMessage());
        }
    }


    @Scheduled(initialDelay = 30000, fixedDelay = 600000)  // 2 minutes
    public void autoCheckoutTask() {
     //   System.out.println("Auto check out task");
        try {

//            if(timeAdvancementService.getCurrentTime().getHour() >= 11  && timeAdvancementService.getCurrentTime().getHour() <= 12 )
               reservationService.autoCheckouts();

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
