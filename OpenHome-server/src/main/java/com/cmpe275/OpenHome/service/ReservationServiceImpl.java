package com.cmpe275.OpenHome.service;

import ch.qos.logback.core.encoder.EchoEncoder;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


@Service
@EnableAsync
@EnableScheduling
@Transactional(readOnly = false)
public class ReservationServiceImpl implements ReservationService{


    InputStream input;
    Properties prop ;
    @Autowired
    private ReservationDAO reservationDao;


    @Autowired
    private JavaMailSender mailSenderObj;
//    public ReservationServiceImpl() throws Exception {
//       input = new FileInputStream("/../../../resources/messages.properties");
//        prop = new Properties();
//        prop.load(input);
//    }


    @Override
    @Transactional
    public List<Reservation> list() {
        return reservationDao.list();
    }

    @Transactional
    public Reservation save(Reservation reservation) throws Exception {

        LocalDateTime startDate = reservation.getStartDate().toLocalDateTime();
        LocalDateTime endDate = reservation.getEndDate().toLocalDateTime();

        long diff =  startDate.until(endDate, ChronoUnit.DAYS);


        if(diff > 14)
            throw new Exception("ERROR.RESERVATION_RANGE_ERROR");

        long maxStartDate = LocalDate.now().until(startDate,ChronoUnit.DAYS);


        if(maxStartDate > 365)
            throw new Exception("ERROR.RESERVATION_START_DATE_ERROR");
            //throw new Exception(prop.getProperty("ERROR.RESERVATION_START_DATE_ERROR"));

        return reservationDao.makeReservation(reservation);
    }

    @Override
    public Reservation cancelReservation(int id) throws Exception {

        try {
            TimeZone tzone = TimeZone.getTimeZone("PST");
            TimeZone.setDefault(tzone);


            Reservation reservation = reservationDao.getReservation(id);

            long diff = new Date().getTime() - reservation.getStartDate().getTime();
            long diffHours = diff / (60 * 60 * 1000);

            long cost = reservation.getBookingCost();

            if (diffHours > 48)
                reservation.setBookingCost(0);
            else if (diffHours < 24 && diffHours > 2)
                reservation.setBookingCost((int) (0.3 * cost));
            else if (diffHours <= 0) {
                long reservedDays = (reservation.getStartDate().getTime() - reservation.getEndDate().getTime()) / (60 * 60 * 1000 * 24);
                reservation.setBookingCost((int) (0.3 * ((reservedDays / cost) % 2)));

            }

            System.out.println("before cancellation");
            System.out.println((byte)1);

            reservation.setIsCancelled((byte) 1);
            System.out.println((byte)(reservation.getIsCancelled()));


            System.out.println("after cancellation");
            return reservationDao.updateReservation(reservation);
        }

        catch( Exception e ) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public void handleCancellations() {

        try {

            TimeZone tzone = TimeZone.getTimeZone("PST");
            TimeZone.setDefault(tzone);


            Map<String,Object> searchCriteria  = new HashMap<>();
            searchCriteria.put("start_date", LocalDateTime.now().plusHours(-12));


            List<Reservation> reservations = reservationDao.getReservations(searchCriteria);

            for(Reservation reservation : reservations) {
              cancelReservation(reservation.getBookingId());
            }



        }

        catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }



    }

    @Override
    public Reservation checkIn(int id) throws Exception{

        TimeZone tzone = TimeZone.getTimeZone("PST");
        TimeZone.setDefault(tzone);

        Reservation reservation = reservationDao.getReservation(id);

            LocalDateTime startDate = reservation.getStartDate().toLocalDateTime();

            long seconds = startDate.until(LocalDateTime.now(), ChronoUnit.SECONDS);

        System.out.println("start date" + startDate);
        System.out.println("present time" + LocalDateTime.now());
            System.out.println("seconds diff1" + seconds);

            if (seconds < 0)
                throw new Exception("You check in time starts at 3 pm. You cannot check in before start time.");


         seconds = LocalDateTime.now().until(startDate.plusHours( 12+7), ChronoUnit.SECONDS);


        System.out.println("start date plus hours" + startDate.plusHours( 12+17 ));
        System.out.println("seconds diff1" + seconds);

            if( seconds < 0)
                throw new Exception("You check in time ends at 3 am. You cannot check in after end time.");

            reservation.setCheckIn(Timestamp.valueOf(LocalDateTime.now()));

            reservationDao.updateReservation(reservation);

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        SimpleMailMessage emailObj = new SimpleMailMessage();
        emailObj.setTo(reservation.getTenantEmailId());
        emailObj.setSubject("Check In is Complete");
        emailObj.setText("Hello guest, your check in is complete..  Enjoy your stay at OpenHome !!");
        return reservation;
    }

    @Override
    public Reservation checkOut(int id) throws Exception {

        TimeZone tzone = TimeZone.getTimeZone("PST");
        TimeZone.setDefault(tzone);


        Reservation reservation = reservationDao.getReservation(id);

        if(reservation.getCheckIn() == null)
            throw new Exception("You haven't checked In.. you cannot checkout ");


        if(reservation.getIsCancelled() == 1)
            throw new Exception("You cancelled your reservation.. you cannot checkout now");


        reservation.setCheckOut(Timestamp.valueOf(LocalDateTime.now()));

        reservationDao.updateReservation(reservation);


        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        SimpleMailMessage emailObj = new SimpleMailMessage();
        emailObj.setTo(reservation.getTenantEmailId());
        emailObj.setSubject("Check Out is Complete");
        emailObj.setText("Hello guest, your check out is complete.. Hope you had a great stay !!");

        mailSenderObj.send(emailObj);

        long hours = LocalDateTime.now().until(reservation.getEndDate().toLocalDateTime(), ChronoUnit.HOURS);

        if(hours > 24 )
            cancelReservation(id);

        return reservation;
    }

    @Override
    public List<Reservation> getReservationsById(String email) throws Exception {
       try {
           return reservationDao.getReservationsById(email);
       }

       catch (Exception e) {
           throw new Exception(e.getMessage());
       }
    }
}