package com.cmpe275.OpenHome.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.cmpe275.OpenHome.controller.MailServiceController;
//import com.cmpe275.OpenHome.controller.ReservationController;
import com.cmpe275.OpenHome.controller.TimeAdvancementController;
import com.cmpe275.OpenHome.dao.ReservationDAO;
import com.cmpe275.OpenHome.dao.TransactionsDAO;
import com.cmpe275.OpenHome.enums.TransactionType;
import com.cmpe275.OpenHome.model.Mail;
import com.cmpe275.OpenHome.model.Reservation;
import com.cmpe275.OpenHome.model.Transactions;
import com.mysql.cj.conf.ConnectionUrlParser;
import org.hibernate.Transaction;
import org.hibernate.mapping.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;
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

    @Autowired
    private ReservationDAO reservationDao;

    @Autowired
    private TransactionsDAO transactionsDAO;

    @Autowired
    private TimeAdvancementServiceImpl timeAdvancementService;


    @Override
    @Transactional
    public List<Reservation> list() {
        return reservationDao.list();
    }

    @Transactional
    public Reservation save(Reservation reservation) throws Exception {


        TimeZone tzone = TimeZone.getTimeZone("PST");
        TimeZone.setDefault(tzone);

        LocalDateTime startDate = reservation.getStartDate().toLocalDateTime();
        LocalDateTime endDate = reservation.getEndDate().toLocalDateTime();

        long diff =  startDate.until(endDate, ChronoUnit.DAYS);


        if(diff > 14)
            throw new Exception("Exceeded maximum reservation days range, you can only book for 14 days");

        long maxStartDate = LocalDate.now().until(startDate,ChronoUnit.DAYS);


        if(maxStartDate > 365)
            throw new Exception("Your start date should be within an year");

        List<Reservation> reservations = reservationDao.getReservationsByPostingId(reservation.getPostingId());

        if(reservations != null &&  reservations.size() > 0)
            throw new Exception("Sorry, this property is already booked");

        reservation.setStartDate(Timestamp.valueOf(startDate.plusHours(15)));
        reservation.setEndDate(Timestamp.valueOf(endDate.plusHours(11)));


        Transactions transaction = new Transactions();
        transaction.setEmail(reservation.getTenantEmailId());
        System.out.println("reservation cost" +reservation.getBookingId());
        transaction.setAmount(transaction.getAmount());
        transaction.setCurrentBalance(transaction.getCurrentBalance() - transaction.getAmount());
        transaction.setReservationId(reservation.getBookingId());
        transaction.setType(TransactionType.BOOKING_CHARGE);
        transactionsDAO.createTransactions(transaction);



        transaction = new Transactions();
        transaction.setEmail(reservation.getHostEmailId());
        transaction.setAmount(-transaction.getAmount());
        transaction.setCurrentBalance(transaction.getCurrentBalance() + transaction.getAmount());
        transaction.setReservationId(reservation.getBookingId());
        transaction.setType(TransactionType.BOOKING_CREDIT);
        transactionsDAO.createTransactions(transaction);

        return reservationDao.makeReservation(reservation);
    }

    @Override
    public Reservation cancelReservation(int id) throws Exception {

        try {


            Reservation reservation = reservationDao.getReservation(id);
            System.out.println("before cancellation");
            System.out.println((byte)1);

            reservation.setIsCancelled((byte) 1);
            System.out.println((byte)(reservation.getIsCancelled()));


            System.out.println("after cancellation");


                Transactions transaction = new Transactions();
                transaction.setEmail(reservation.getTenantEmailId());
                System.out.println("cancellation cost by guest" +reservation.getBookingId());
                transaction.setAmount(-transaction.getAmount());
                transaction.setCurrentBalance(transaction.getCurrentBalance() + transaction.getAmount());
                transaction.setReservationId(reservation.getBookingId());
                transaction.setType(TransactionType.REFUND);
                transactionsDAO.createTransactions(transaction);



                transaction = new Transactions();
                transaction.setEmail(reservation.getHostEmailId());
                transaction.setAmount(+transaction.getAmount());
                transaction.setCurrentBalance(transaction.getCurrentBalance() - transaction.getAmount());
                transaction.setReservationId(reservation.getBookingId());
                transaction.setType(TransactionType.PENALTY);
                transactionsDAO.createTransactions(transaction);


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
            List<Reservation> reservations = reservationDao.getReservationsForNoShow();

            for(Reservation reservation : reservations) {
              noShowcancelReservation(reservation.getBookingId());

                Transactions transaction = new Transactions();
                transaction.setEmail(reservation.getTenantEmailId());

                double amount = reservation.getBookingCost();

                long diff = new Date().getTime() - reservation.getStartDate().getTime();
            long diffHours = diff / (60 * 60 * 1000);

            double cost = reservation.getBookingCost();

            if (diffHours > 48)
                amount = amount;
            else if (diffHours < 24 && diffHours > 2)
                amount -= amount * 0.3;
            else if (diffHours <= 0) {
                long reservedDays = (reservation.getStartDate().getTime() - reservation.getEndDate().getTime()) / (60 * 60 * 1000 * 24);
           //     reservation.setBookingCost((int) (0.3 * ((reservedDays / cost) % 2)));
                amount -= amount * (int) (0.3 * ((reservedDays / cost) % 2));

            }

            System.out.println("penalty" +amount);

                transaction.setAmount(-amount);

                transaction.setCurrentBalance(transaction.getCurrentBalance() + amount);
                transaction.setReservationId(reservation.getBookingId());
                transaction.setType(TransactionType.REFUND);

                transactionsDAO.createTransactions(transaction);



                transaction = new Transactions();

                transaction.setEmail(reservation.getHostEmailId());

                transaction.setAmount(amount);

                transaction.setCurrentBalance(transaction.getCurrentBalance() - amount);
                transaction.setReservationId(reservation.getBookingId());
                transaction.setType(TransactionType.PENALTY);

                transactionsDAO.createTransactions(transaction);

            }

        }

        catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }



    }


    @Override
    public void noShowcancelReservation(int bookingId) {
        try {


            Reservation reservation = reservationDao.getReservation(bookingId);


            System.out.println("before cancellation");
            System.out.println((byte)1);

            reservation.setIsCancelled((byte) 1);
            System.out.println((byte)(reservation.getIsCancelled()));


            System.out.println("after cancellation");
            return;
        }

        catch( Exception e ) {

            System.out.println(e.getMessage());

        }

    }

    @Override
    public Reservation checkIn(int id) throws Exception{

        TimeZone tzone = TimeZone.getTimeZone("PST");
        TimeZone.setDefault(tzone);

        Reservation reservation = reservationDao.getReservation(id);

        LocalDateTime startDate = reservation.getStartDate().toLocalDateTime();

        long seconds = startDate.until(timeAdvancementService.getCurrentTime(), ChronoUnit.SECONDS);

        System.out.println("start date" + startDate);
        System.out.println("present time" + timeAdvancementService.getCurrentTime());
        System.out.println("seconds diff1" + seconds);

        if (seconds < 0)
            throw new Exception("You check in time starts at 3 pm. You cannot check in before start time.");


        seconds = timeAdvancementService.getCurrentTime().until(startDate.plusHours( 12), ChronoUnit.SECONDS);


        System.out.println("start date plus hours" + startDate.plusHours( 12 ));
        System.out.println("seconds diff1" + seconds);

            if( seconds < 0)
                throw new Exception("You check in time ends at 3 am. You cannot check in after end time.");


            reservation.setCheckIn(Timestamp.valueOf(timeAdvancementService.getCurrentTime()));

            reservationDao.updateReservation(reservation);


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


        reservation.setCheckOut(Timestamp.valueOf(timeAdvancementService.getCurrentTime()));

        reservationDao.updateReservation(reservation);



        long hours = timeAdvancementService.getCurrentTime().until(reservation.getEndDate().toLocalDateTime(), ChronoUnit.HOURS);


      //  cancelReservation(id);

        if(hours > 24 ) {

            long days = timeAdvancementService.getCurrentTime().until(reservation.getEndDate().toLocalDateTime(), ChronoUnit.DAYS);
            long bookingDays = reservation.getStartDate().toLocalDateTime().until(reservation.getEndDate().toLocalDateTime(), ChronoUnit.DAYS);


            if(days > 2) {
                double refund = (reservation.getBookingCost()/bookingDays) * days;

                Transactions transaction = new Transactions();
                transaction.setEmail(reservation.getTenantEmailId());
                transaction.setReservationId(reservation.getBookingId());
                transaction.setType(TransactionType.REFUND);
                System.out.println("penalty" +refund);
                transaction.setAmount(-refund);
                transaction.setCurrentBalance(transaction.getCurrentBalance() + refund);
                transactionsDAO.createTransactions(transaction);



                transaction = new Transactions();
                transaction.setEmail(reservation.getHostEmailId());
                transaction.setAmount(refund);
                transaction.setCurrentBalance(transaction.getCurrentBalance() - refund);
                transaction.setReservationId(reservation.getBookingId());
                transaction.setType(TransactionType.PENALTY);
                transactionsDAO.createTransactions(transaction);
            }

        }


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

    @Override
    public void autoCheckouts() throws Exception {
        try {

            TimeZone tzone = TimeZone.getTimeZone("PST");
            TimeZone.setDefault(tzone);
            List<Reservation> reservations = reservationDao.getReservationsForAutocheckout();

            for(Reservation reservation : reservations) {
                reservation.setCheckOut(Timestamp.valueOf(timeAdvancementService.getCurrentTime()));
            }
        }

        catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }

    }
}