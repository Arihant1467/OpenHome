package com.cmpe275.OpenHome.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.cmpe275.OpenHome.controller.MailServiceController;
//import com.cmpe275.OpenHome.controller.ReservationController;
import com.cmpe275.OpenHome.controller.TimeAdvancementController;
import com.cmpe275.OpenHome.dao.PaymentsDAO;
import com.cmpe275.OpenHome.dao.PostingsDAO;
import com.cmpe275.OpenHome.dao.ReservationDAO;
import com.cmpe275.OpenHome.dao.TransactionsDAO;
import com.cmpe275.OpenHome.enums.TransactionType;
import com.cmpe275.OpenHome.model.*;
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
    private PostingsDAO postingsDAO;

    @Autowired
    private TransactionsDAO transactionsDAO;

    @Autowired
    private PaymentsDAO paymentsDAO;

    @Autowired
    private TimeAdvancementServiceImpl timeAdvancementService;

    @Autowired
    private MailServiceController mailServiceController;


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

        long maxStartDate = timeAdvancementService.getCurrentTime().until(startDate,ChronoUnit.DAYS);


        if(maxStartDate > 365)
            throw new Exception("Your start date should be within an year");

        List<Reservation> reservations = reservationDao.getReservationsByPostingId(reservation);

        if(reservations != null &&  reservations.size() > 0)
            throw new Exception("Sorry, this property is already booked");

        reservation.setStartDate(Timestamp.valueOf(startDate.plusHours(15)));
        reservation.setEndDate(Timestamp.valueOf(endDate.plusHours(11)));
        return reservationDao.makeReservation(reservation);
    }

    @Override
    public Reservation cancelReservation(int id) throws Exception {

        try {

            Reservation reservation = reservationDao.getReservation(id);
            reservation.setIsCancelled((byte) 1);

            double penaltyAmount = 0;

            long daysToStart = timeAdvancementService.getCurrentTime().until(reservation.getStartDate().toLocalDateTime(), ChronoUnit.DAYS);

            Calendar c1 = Calendar.getInstance();
            c1.setTime(reservation.getStartDate());
            System.out.println(c1.get(Calendar.DAY_OF_WEEK));

            Postings posting = postingsDAO.getPosting(reservation.getPostingId());

            if(daysToStart < 1) {

                if((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || ( c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) ==  Calendar.FRIDAY) )
                    penaltyAmount += 0.3 * posting.getWeekendRent();
                else
                    penaltyAmount += 0.3 * posting.getWeekRent();

            }

            else if(daysToStart < 2){

                if((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || ( c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) ==  Calendar.FRIDAY) )
                    penaltyAmount += 0.3 * posting.getWeekendRent();
                else
                    penaltyAmount += 0.3 * posting.getWeekRent();

                c1.add(Calendar.DATE, 1);

                if((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || ( c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) ==  Calendar.FRIDAY) )
                    penaltyAmount += 0.3 * posting.getWeekendRent();
                else
                    penaltyAmount += 0.3 * posting.getWeekRent();

            }


            Payments guestDetails = paymentsDAO.getPaymentDetails(reservation.getTenantEmailId());
            Transactions transaction = getTransactions(reservation, guestDetails,true,TransactionType.REFUND,penaltyAmount, guestDetails.getBalance()-penaltyAmount);
            transactionsDAO.createTransactions(transaction);
            paymentsDAO.update(guestDetails);


            Payments hostDetails = paymentsDAO.getPaymentDetails(reservation.getHostEmailId());
            transaction = getTransactions(reservation, guestDetails,false,TransactionType.PENALTY,-penaltyAmount, hostDetails.getBalance()+penaltyAmount);
//           transactionsDAO.createTransactions(transaction);
//           paymentsDAO.update(hostDetails);

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

                double penaltyAmount = 0;

                long daysBooked = reservation.getStartDate().toLocalDateTime().until(reservation.getEndDate().toLocalDateTime(), ChronoUnit.DAYS);

                Calendar c1 = Calendar.getInstance();
                c1.setTime(reservation.getStartDate());

                Postings posting = postingsDAO.getPosting(reservation.getPostingId());

                if((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || ( c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) ==  Calendar.FRIDAY) )
                    penaltyAmount += 0.3 * posting.getWeekendRent();
                else
                    penaltyAmount += 0.3 * posting.getWeekRent();


                if(daysBooked > 1){

                    c1.add(Calendar.DATE, 1);
                    if((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || ( c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) ==  Calendar.FRIDAY) )
                        penaltyAmount += 0.3 * posting.getWeekendRent();
                    else
                        penaltyAmount += 0.3 * posting.getWeekRent();
                }

                System.out.println("penalty for no show" +penaltyAmount);

                Payments guestDetails = paymentsDAO.getPaymentDetails(reservation.getTenantEmailId());
                Transactions transaction = getTransactions(reservation, guestDetails,true,TransactionType.REFUND,penaltyAmount, guestDetails.getBalance()-penaltyAmount);
                transactionsDAO.createTransactions(transaction);
                paymentsDAO.update(guestDetails);


                Payments hostDetails = paymentsDAO.getPaymentDetails(reservation.getHostEmailId());
                transaction = getTransactions(reservation, guestDetails,false,TransactionType.PENALTY,-penaltyAmount, hostDetails.getBalance()+penaltyAmount);
//              transactionsDAO.createTransactions(transaction);
//               paymentsDAO.update(hostDetails);


            }

        }

        catch (Exception e) {
            System.out.println("in exception");
            System.out.println(e.fillInStackTrace());
        }



    }


    @Override
    public void noShowcancelReservation(int bookingId) {
        try {


            Reservation reservation = reservationDao.getReservation(bookingId);
            reservation.setIsCancelled((byte) 1);

            String emailText = "No show for reservation" + reservation.getBookingId() +" Reservation is cancelled";
            String emailSubject = "Hello guest, your reservation is cancelled as we didn't see you by check in time.. Apologies !!";
            Mail email = new Mail(emailText, emailSubject, reservation.getTenantEmailId());
            mailServiceController.addToQueue(email);

            String emailText2 = "No show for property" + reservation.getPostingId() +"by" + reservation.getTenantEmailId();
            String emailSubject2 = "Hello host, your property has cancelled as guest didn't check in by start time";
            Mail email2 = new Mail(emailText2, emailSubject2, reservation.getHostEmailId());
            mailServiceController.addToQueue(email2);

            reservationDao.updateReservation(reservation);

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
            throw new Exception("You check in time starts at 3am ,You cannot check in before start time.");


        seconds = timeAdvancementService.getCurrentTime().until(startDate.plusHours( 12), ChronoUnit.SECONDS);


        System.out.println("start date plus hours" + startDate.plusHours( 12 ));
        System.out.println("seconds diff1" + seconds);

        if( seconds < 0)
            throw new Exception("You check in time ends at \"+ reservation.getStartDate()+\"  You cannot check in after end time.");


        reservation.setCheckIn(Timestamp.valueOf(timeAdvancementService.getCurrentTime()));

        Payments guestDetails = paymentsDAO.getPaymentDetails(reservation.getTenantEmailId());
        Transactions transaction = getTransactions(reservation, guestDetails,true,TransactionType.BOOKING_CHARGE,reservation.getBookingCost(), guestDetails.getBalance()-reservation.getBookingCost());
        transactionsDAO.createTransactions(transaction);
        paymentsDAO.update(guestDetails);


        Payments hostDetails = paymentsDAO.getPaymentDetails(reservation.getHostEmailId());
        transaction = getTransactions(reservation, guestDetails,false,TransactionType.BOOKING_CREDIT,-reservation.getBookingCost(), hostDetails.getBalance()+reservation.getBookingCost());
//        transactionsDAO.createTransactions(transaction);
//        paymentsDAO.update(hostDetails);

        reservationDao.updateReservation(reservation);


        return reservation;
    }

    private Transactions getTransactions(Reservation reservation, Payments paymentDetails,boolean guest, TransactionType type, double amount, double balance) {

        Transactions transaction = new Transactions();

        String email = guest ? reservation.getTenantEmailId() : reservation.getHostEmailId();
        transaction.setEmail(email);
        transaction.setAmount(amount);
        transaction.setCurrentBalance(balance);
        paymentDetails.setBalance(balance);
        transaction.setReservationId(reservation.getBookingId());
        transaction.setType(type);


        String emailText = "Payment made on your card for reservation" + reservation.getBookingId();
        String emailSubject = "Hello, payment made on your card is " + amount +". Your current balance is :" + balance;
        Mail mail = new Mail(emailText, emailSubject, email);
        mailServiceController.addToQueue(mail);
        return transaction;
    }

    @Override
    public Reservation checkOut(int id) throws Exception {

        TimeZone tzone = TimeZone.getTimeZone("PST");
        TimeZone.setDefault(tzone);


        Reservation reservation = reservationDao.getReservation(id);

        if(reservation.getCheckIn() == null)
            throw new Exception("You haven't checked In.. you cannot checkout ");


        if(reservation.getCheckOut() != null)
            throw new Exception("Your checkout is already complete ");


        if(reservation.getIsCancelled() == 1)
            throw new Exception("You cancelled your reservation.. you cannot checkout now");


        reservation.setCheckOut(Timestamp.valueOf(timeAdvancementService.getCurrentTime()));

        reservationDao.updateReservation(reservation);

        double penaltyAmount = 0;



        long days = timeAdvancementService.getCurrentTime().until(reservation.getEndDate().toLocalDateTime(), ChronoUnit.DAYS);
        long bookingDays = reservation.getStartDate().toLocalDateTime().until(reservation.getEndDate().toLocalDateTime(), ChronoUnit.DAYS);
        long hours = timeAdvancementService.getCurrentTime().until(reservation.getEndDate().toLocalDateTime(), ChronoUnit.HOURS);


        if(hours <=  24  ) {
            penaltyAmount = 0;
        }

        else if(hours > 24  && hours <= 48) {

            Calendar c1 = Calendar.getInstance();
            c1.setTime(reservation.getEndDate());
            Postings posting = postingsDAO.getPosting(reservation.getPostingId());

            if((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || ( c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) ==  Calendar.FRIDAY) )
                penaltyAmount += 0.7 * posting.getWeekendRent();
            else
                penaltyAmount += 0.7 * posting.getWeekRent();
        }

        else if(hours > 48 && hours < 72) {


            Calendar c1 = Calendar.getInstance();
            c1.setTime(reservation.getEndDate());
            Postings posting = postingsDAO.getPosting(reservation.getPostingId());

            if((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || ( c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) ==  Calendar.FRIDAY) )
                penaltyAmount += 0.7 * posting.getWeekendRent();
            else
                penaltyAmount += 0.7 * posting.getWeekRent();

            c1.add(Calendar.DATE, -1);
            if((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || ( c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) ==  Calendar.FRIDAY) )
                penaltyAmount += 0.7 * posting.getWeekendRent();
            else
                penaltyAmount += 0.7 * posting.getWeekRent();

        }

        else {


            int remainingDays = (int)timeAdvancementService.getCurrentTime().until(reservation.getEndDate().toLocalDateTime(), ChronoUnit.DAYS);

            Calendar c1 = Calendar.getInstance();
            c1.setTime(reservation.getEndDate());
            c1.add(Calendar.DATE, -remainingDays);
            Postings posting = postingsDAO.getPosting(reservation.getPostingId());

            if((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || ( c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) ==  Calendar.FRIDAY) )
                penaltyAmount += 0.7 * posting.getWeekendRent();
            else
                penaltyAmount += 0.7 * posting.getWeekRent();

            c1.add(Calendar.DATE, 1);
            if((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || ( c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) ==  Calendar.FRIDAY) )
                penaltyAmount += 0.7 * posting.getWeekendRent();
            else
                penaltyAmount += 0.7 * posting.getWeekRent();

            while(remainingDays < 2) {
                c1.add(Calendar.DATE, 1);

                if((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || ( c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) ==  Calendar.FRIDAY) )
                    penaltyAmount +=  posting.getWeekendRent();
                else
                    penaltyAmount +=  posting.getWeekRent();

                remainingDays--;

            }
        }


        System.out.println("penalty for checking out before end date" +penaltyAmount);


        Payments guestDetails = paymentsDAO.getPaymentDetails(reservation.getTenantEmailId());
        Transactions transaction = getTransactions(reservation, guestDetails,true,TransactionType.REFUND,-penaltyAmount, guestDetails.getBalance()+penaltyAmount);
        transactionsDAO.createTransactions(transaction);
        paymentsDAO.update(guestDetails);


        Payments hostDetails = paymentsDAO.getPaymentDetails(reservation.getHostEmailId());
        transaction = getTransactions(reservation, guestDetails,false,TransactionType.PENALTY,penaltyAmount, hostDetails.getBalance()-penaltyAmount);
//       transactionsDAO.createTransactions(transaction);
//       paymentsDAO.update(hostDetails);


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
                reservationDao.updateReservation(reservation);

                String emailText = "Auto Check Out Complete";
                String emailSubject = "Hello guest, your check out is complete.. Hope you had a great stay !!";
                Mail email = new Mail(emailText, emailSubject, reservation.getTenantEmailId());
                mailServiceController.addToQueue(email);

                String emailText2 = "Auto Check Out Complete for" + reservation.getPostingId() +"by" + reservation.getTenantEmailId();
                String emailSubject2 = "Hello host, your property has been successfully checked out by guest..!!";
                Mail email2 = new Mail(emailText2, emailSubject2, reservation.getHostEmailId());
                mailServiceController.addToQueue(email2);
            }
        }

        catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }

    }
}