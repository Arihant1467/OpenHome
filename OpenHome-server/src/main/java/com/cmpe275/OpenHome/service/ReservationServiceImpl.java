package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.dao.ReservationDAO;
import com.cmpe275.OpenHome.model.Reservation;
import com.mysql.cj.conf.ConnectionUrlParser;
import org.hibernate.mapping.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


@Service
@EnableAsync
@EnableScheduling
//@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService{


    @Autowired
    private ReservationDAO reservationDao;


    @Autowired
    private JavaMailSender mailSenderObj;


    @Override
    @Transactional
    public List<Reservation> list() {
        return reservationDao.list();
    }

    @Transactional
    public Reservation save(Reservation reservation) throws Exception {


        Period reservationDays = Period.between(reservation.getStartDate().toLocalDate(), reservation.getEndDate().toLocalDate());
        int diff = reservationDays.getDays();

        if(diff > 14)
            throw new Exception("ERROR.RESERVATION_RANGE_ERROR");

        Period reservationStartDate = Period.between(LocalDate.now(), reservation.getEndDate().toLocalDate());
        int maxStartDate = reservationStartDate.getDays();

        if(maxStartDate > 365)
            throw new Exception(new Properties().getProperty("ERROR.RESERVATION_START_DATE_ERROR"));

        return reservationDao.makeReservation(reservation);
    }

    @Override
    public Reservation cancelReservation(int id) {



        Reservation reservation = reservationDao.getReservation(id);

        long diff = new Date().getTime() - reservation.getStartDate().getTime();
        long diffHours = diff / (60 * 60 * 1000);

        long cost = reservation.getBookingCost();

        if(diffHours > 48)
            reservation.setBookingCost(0);
        else if(diffHours < 24 &&  diffHours > 2)
            reservation.setBookingCost((int)(0.3 * cost));
        else if(diffHours <=0 ) {
            long reservedDays = (reservation.getStartDate().getTime() - reservation.getEndDate().getTime()) /(60*60*1000*24);
            reservation.setBookingCost((int)(0.3 * ((reservedDays/cost)%2)));

        }

        reservation.setIsCancelled((byte)1);
        return reservationDao.updateReservation(reservation);
    }


    @Override
    public void handleCancellations() {

        try {

//            JavaMailSenderImpl sender = new JavaMailSenderImpl();
//            sender.setHost("smtp.gmail.com");
//            SimpleMailMessage emailObj = new SimpleMailMessage();
//            emailObj.setTo("deepika.yannamani@sjsu.edu");
//            emailObj.setSubject("hello");
//            emailObj.setText("hello");
//
//            mailSenderObj.send(emailObj);
        }

        catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }



    }
}