package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.DataObjects.PostingEditForm;
import com.cmpe275.OpenHome.DataObjects.PostingForm;
import com.cmpe275.OpenHome.controller.MailServiceController;
import com.cmpe275.OpenHome.enums.TransactionType;
import com.cmpe275.OpenHome.model.*;
import com.cmpe275.OpenHome.service.ReservationService;
import com.cmpe275.OpenHome.service.ReservationServiceImpl;
import com.cmpe275.OpenHome.service.TimeAdvancementServiceImpl;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.header.writers.CacheControlHeadersWriter;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.sound.midi.Soundbank;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Repository
public class PostingsDAOImpl implements  PostingsDAO {

    @Autowired
    private ReservationDAO reservationDAO;



    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private TransactionsDAO transactionsDAO ;

    @Autowired
    private TimeAdvancementServiceImpl timeAdvancementService;

    @Autowired
    private PaymentsDAO paymentsDAO;

    @Autowired
    private MailServiceController mailServiceController;



    @Override
    public List<Postings> getPostings() {
        List<Postings> list = sessionFactory.getCurrentSession().createQuery("from Postings" +
                " ").list();
        return list;
    }

    @Override
    public Postings getPosting(int id) {
        Postings posting = sessionFactory.getCurrentSession().get(Postings.class,id);
        sessionFactory.getCurrentSession().flush();
        return posting;
    }


    @Override
    public long save(Postings postings) throws Exception{
        System.out.println("In save of postings");
        sessionFactory.getCurrentSession().save(postings);
        sessionFactory.getCurrentSession().flush();
        return postings.getPropertyId();

    }

    @Override
    public int deletePosting(int id) throws Exception {

        System.out.println("************** Deleting Postings *************************");
        TimeZone tzone = TimeZone.getTimeZone("PST");
        TimeZone.setDefault(tzone);
        Session session = sessionFactory.getCurrentSession();
        Postings posting = session.byId(Postings.class).load(id);

        Criteria criteria = session.createCriteria(Reservation.class);
        List<Reservation> reservations = criteria.add(Restrictions.eq("postingId", id)).list();
        boolean canUpdate = true;


        for(Reservation r : reservations){

            double amount = r.getBookingCost();
            if(r.getCheckOut() == null) {
                // If the change conflicts with a guest who has already checkedIn
                if (r.getCheckIn() != null && canUpdate) {

                    double penaltyAmount = 0;
                    LocalDateTime reservationStartDate = r.getStartDate().toLocalDateTime().plusHours(8);

                    LocalDateTime endDate = r.getEndDate().toLocalDateTime().plusHours(8);
                    LocalDateTime currentTime = timeAdvancementService.getCurrentTime();
                    LocalDateTime endTimeConsideration = currentTime.plusDays(7);

                    reservationStartDate = currentTime;

                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(Timestamp.valueOf(currentTime));
                    System.out.println("Current Time stamp");
                    System.out.println(c1.get(Calendar.DAY_OF_WEEK));

                    while((reservationStartDate.isBefore(endTimeConsideration)  || reservationStartDate.isEqual(endTimeConsideration)) && (reservationStartDate.isBefore(endDate) || reservationStartDate.isEqual(endDate))) {

                        if ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) ) {
                            penaltyAmount += 1.15 * (posting.getWeekendRent() + posting.getDailyParkingFee());
                            System.out.println("penalty till now weekend" + penaltyAmount);
                        }
                        else {
                            penaltyAmount += 1.15 * (posting.getWeekRent() + posting.getDailyParkingFee());
                            System.out.println("penalty till now weekday" + penaltyAmount);
                        }
                        reservationStartDate = reservationStartDate.plusDays(1);

                        System.out.println(reservationStartDate + "reservation Date changed");
                        c1.add(Calendar.DATE, 1);


                    }

                    while((reservationStartDate.isBefore(endDate) || reservationStartDate.isEqual(endDate))) {

                        if ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) ) {
                            penaltyAmount += 1 * (posting.getWeekendRent() + posting.getDailyParkingFee());
                            System.out.println(" extra days penalty till now weekend" + penaltyAmount);
                        }
                        else {
                            penaltyAmount += 1 * (posting.getWeekRent() + posting.getDailyParkingFee());
                            System.out.println(" extra days penalty till now weekday" + penaltyAmount);
                        }
                        reservationStartDate = reservationStartDate.plusDays(1);

                        System.out.println(reservationStartDate + "reservation Date changed");
                        c1.add(Calendar.DATE, 1);


                    }

                    amount = penaltyAmount;
                    //For guest
                    Payments guestDetails = paymentsDAO.getPaymentDetails(r.getTenantEmailId());
                    Transactions transaction = getTransactions(r, guestDetails, true, TransactionType.REFUND, -amount, guestDetails.getBalance() + amount);
                    transactionsDAO.createTransactions(transaction);
                    paymentsDAO.update(guestDetails);

                    //For host
                    Payments hostDetails = paymentsDAO.getPaymentDetails(r.getHostEmailId());
                    Transactions hosttransaction = getTransactions(r, hostDetails, false, TransactionType.PENALTY, amount, hostDetails.getBalance() - amount);
                    transactionsDAO.createTransactions(hosttransaction);
                    paymentsDAO.update(hostDetails);

                    r.setIsCancelled((byte) 1);
                    reservationDAO.updateReservation(r);



                } //if not checkedIn
                else {

                    long daysToStart = timeAdvancementService.getCurrentTime().until(r.getStartDate().toLocalDateTime(), ChronoUnit.DAYS);

                    if(daysToStart < 7 && canUpdate) {

                        //Timestamp  startDay = r.getStartDate();
                        LocalDateTime reservationStartDate = r.getStartDate().toLocalDateTime().plusHours(8);

                        LocalDateTime endDate = r.getEndDate().toLocalDateTime().plusHours(8);
                        LocalDateTime currentTime = timeAdvancementService.getCurrentTime();
                        LocalDateTime endTimeConsideration = currentTime.plusDays(7);


                        double penaltyAmount = 0;

                        Calendar c1 = Calendar.getInstance();
                        c1.setTime(r.getStartDate());
                        System.out.println(c1.get(Calendar.DAY_OF_WEEK));

                        while((reservationStartDate.isBefore(endTimeConsideration)  || reservationStartDate.isEqual(endTimeConsideration)) && (reservationStartDate.isBefore(endDate) || reservationStartDate.isEqual(endDate))) {

                            if ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) ) {
                                penaltyAmount += 0.15 * (posting.getWeekendRent() + posting.getDailyParkingFee());
                                System.out.println("penalty till now weekend" + penaltyAmount);
                            }
                            else {
                                penaltyAmount += 0.15 * (posting.getWeekRent() + posting.getDailyParkingFee());
                                System.out.println("penalty till now weekday" + penaltyAmount);
                            }
                            reservationStartDate = reservationStartDate.plusDays(1);

                            System.out.println(reservationStartDate + "reservation Date changed");
                            c1.add(Calendar.DATE, 1);


                        }

                        System.out.println(penaltyAmount + "penaltyAmount");

                        //For amount to be changed
                        amount = penaltyAmount;
                        //For guest
                        Payments guestDetails = paymentsDAO.getPaymentDetails(r.getTenantEmailId());
                        Transactions transaction = getTransactions(r, guestDetails, true, TransactionType.REFUND, -amount, guestDetails.getBalance() + amount);
                        transactionsDAO.createTransactions(transaction);
                        paymentsDAO.update(guestDetails);

                        //For host
                        Payments hostDetails = paymentsDAO.getPaymentDetails(r.getHostEmailId());
                        Transactions hosttransaction = getTransactions(r, hostDetails, false, TransactionType.PENALTY, amount, hostDetails.getBalance() - amount);
                        transactionsDAO.createTransactions(hosttransaction);
                        paymentsDAO.update(hostDetails);
                        r.setIsCancelled((byte) 1);
                        reservationDAO.updateReservation(r);
                    } else {
                        canUpdate = true;

                    }
                }


            }


        }


        //DELET POSTING
        System.out.println("************** Deleting Postings *************************");
        Postings deletePost = sessionFactory.getCurrentSession().get(Postings.class,id);
        sessionFactory.getCurrentSession().delete(deletePost);
        sessionFactory.getCurrentSession().flush();
        return id;
    }





    public Reservation cancelPostingByHost(int id) throws Exception {
        System.out.println("************** Deleting Postings *************************");
        TimeZone tzone = TimeZone.getTimeZone("PST");
        TimeZone.setDefault(tzone);
        Session session = sessionFactory.getCurrentSession();

        Reservation r = session.byId(Reservation.class).load(id);
        Postings posting = session.byId(Postings.class).load(r.getPostingId());

        boolean canUpdate = true;


            double amount = r.getBookingCost();
            if (r.getCheckOut() == null) {
                // If the change conflicts with a guest who has already checkedIn
                if (r.getCheckIn() != null && canUpdate) {
                    double penaltyAmount = 0;
                    LocalDateTime reservationStartDate = r.getStartDate().toLocalDateTime().plusHours(8);

                    LocalDateTime endDate = r.getEndDate().toLocalDateTime().plusHours(8);
                    LocalDateTime currentTime = timeAdvancementService.getCurrentTime();
                    LocalDateTime endTimeConsideration = currentTime.plusDays(7);
                    reservationStartDate = currentTime;
                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(Timestamp.valueOf(currentTime));
                    System.out.println("Current Time stamp");
                    System.out.println(c1.get(Calendar.DAY_OF_WEEK));

                    while ((reservationStartDate.isBefore(endTimeConsideration) || reservationStartDate.isEqual(endTimeConsideration)) && (reservationStartDate.isBefore(endDate) || reservationStartDate.isEqual(endDate))) {

                        if ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) {
                            penaltyAmount += 1.15 * (posting.getWeekendRent() + posting.getDailyParkingFee());
                            System.out.println("penalty till now weekend" + penaltyAmount);
                        } else {
                            penaltyAmount += 1.15 * (posting.getWeekRent() + posting.getDailyParkingFee());
                            System.out.println("penalty till now weekday" + penaltyAmount);
                        }
                        reservationStartDate = reservationStartDate.plusDays(1);

                        System.out.println(reservationStartDate + "reservation Date changed");
                        c1.add(Calendar.DATE, 1);

                    }

                    while ((reservationStartDate.isBefore(endDate) || reservationStartDate.isEqual(endDate))) {

                        if ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) {
                            penaltyAmount += 1 * (posting.getWeekendRent() + posting.getDailyParkingFee());
                            System.out.println(" extra days penalty till now weekend" + penaltyAmount);
                        } else {
                            penaltyAmount += 1 * (posting.getWeekRent() + posting.getDailyParkingFee());
                            System.out.println(" extra days penalty till now weekday" + penaltyAmount);
                        }
                        reservationStartDate = reservationStartDate.plusDays(1);

                        System.out.println(reservationStartDate + "reservation Date changed");
                        c1.add(Calendar.DATE, 1);


                    }

                    amount = penaltyAmount;
                    //For guest
                    Payments guestDetails = paymentsDAO.getPaymentDetails(r.getTenantEmailId());
                    Transactions transaction = getTransactions(r, guestDetails, true, TransactionType.REFUND, -amount, guestDetails.getBalance() + amount);
                    transactionsDAO.createTransactions(transaction);
                    paymentsDAO.update(guestDetails);

                    //For host
                    Payments hostDetails = paymentsDAO.getPaymentDetails(r.getHostEmailId());
                    Transactions hosttransaction = getTransactions(r, hostDetails, false, TransactionType.PENALTY, amount, hostDetails.getBalance() - amount);
                    transactionsDAO.createTransactions(hosttransaction);
                    paymentsDAO.update(hostDetails);

                    r.setIsCancelled((byte) 1);
                    reservationDAO.updateReservation(r);


                } //if not checkedIn
                else {

                    long daysToStart = timeAdvancementService.getCurrentTime().until(r.getStartDate().toLocalDateTime(), ChronoUnit.DAYS);
                    if (daysToStart < 7 && canUpdate) {
                        //Timestamp  startDay = r.getStartDate();
                        LocalDateTime reservationStartDate = r.getStartDate().toLocalDateTime().plusHours(8);
                        LocalDateTime endDate = r.getEndDate().toLocalDateTime().plusHours(8);
                        LocalDateTime currentTime = timeAdvancementService.getCurrentTime();
                        LocalDateTime endTimeConsideration = currentTime.plusDays(7);
                        double penaltyAmount = 0;
                        Calendar c1 = Calendar.getInstance();
                        c1.setTime(r.getStartDate());
                        System.out.println(c1.get(Calendar.DAY_OF_WEEK));

                        while ((reservationStartDate.isBefore(endTimeConsideration) || reservationStartDate.isEqual(endTimeConsideration)) && (reservationStartDate.isBefore(endDate) || reservationStartDate.isEqual(endDate))) {

                            if ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) {
                                penaltyAmount += 0.15 * (posting.getWeekendRent() + posting.getDailyParkingFee());
                                System.out.println("penalty till now weekend" + penaltyAmount);
                            } else {
                                penaltyAmount += 0.15 * (posting.getWeekRent() + posting.getDailyParkingFee());
                                System.out.println("penalty till now weekday" + penaltyAmount);
                            }
                            reservationStartDate = reservationStartDate.plusDays(1);

                            System.out.println(reservationStartDate + "reservation Date changed");
                            c1.add(Calendar.DATE, 1);


                        }

                        System.out.println(penaltyAmount + "penaltyAmount");

                        //For amount to be changed
                        amount = penaltyAmount;
                        //For guest
                        Payments guestDetails = paymentsDAO.getPaymentDetails(r.getTenantEmailId());
                        Transactions transaction = getTransactions(r, guestDetails, true, TransactionType.REFUND, -amount, guestDetails.getBalance() + amount);
                        transactionsDAO.createTransactions(transaction);
                        paymentsDAO.update(guestDetails);

                        //For host
                        Payments hostDetails = paymentsDAO.getPaymentDetails(r.getHostEmailId());
                        Transactions hosttransaction = getTransactions(r, hostDetails, false, TransactionType.PENALTY, amount, hostDetails.getBalance() - amount);
                        transactionsDAO.createTransactions(hosttransaction);
                        paymentsDAO.update(hostDetails);
                        r.setIsCancelled((byte) 1);
                        reservationDAO.updateReservation(r);
                    }
                }


            }
            return r;

    }


    @Override
    public void update(PostingEditForm postings) throws Exception {
        System.out.println("************** Updating Postings *************************");
        TimeZone tzone = TimeZone.getTimeZone("PST");
        TimeZone.setDefault(tzone);
        Session session = sessionFactory.getCurrentSession();
        Postings posting = session.byId(Postings.class).load(postings.getPropertyId());
        System.out.println(posting.getCityName());
        Criteria criteria = session.createCriteria(Reservation.class);
        List<Reservation> reservations = criteria.add(Restrictions.eq("postingId", postings.getPropertyId())).list();
        List<Reservation> conflictreservations = new ArrayList<>();
        String dayAvailibility = postings.getDayAvailability();
        boolean canUpdate = postings.isBearPenalty();
        for(Object o : reservations) {
            Reservation r = (Reservation) o;
            boolean result = true;
            for (int i = 0; i < 7; ++i) {
                if (r.getDayAvailability() != null && r.getDayAvailability().charAt(i) == '1' && dayAvailibility.charAt(i) == '0') {
                    result = false;
                    break;
                }
            }
            if (!result) {
                conflictreservations.add(r);
            }
        }

        for(Reservation r : conflictreservations){

            double amount = r.getBookingCost();
            if(r.getCheckOut() == null) {
                // If the change conflicts with a guest who has already checkedIn
                if (r.getCheckIn() != null && canUpdate) {

                    double penaltyAmount = 0;
                    LocalDateTime reservationStartDate = r.getStartDate().toLocalDateTime().plusHours(8);

                    LocalDateTime endDate = r.getEndDate().toLocalDateTime().plusHours(8);
                    LocalDateTime currentTime = timeAdvancementService.getCurrentTime();
                    LocalDateTime endTimeConsideration = currentTime.plusDays(7);

                    reservationStartDate = currentTime;

                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(Timestamp.valueOf(currentTime));
                    System.out.println("Current Time stamp");
                    System.out.println(c1.get(Calendar.DAY_OF_WEEK));

                    while((reservationStartDate.isBefore(endTimeConsideration)  || reservationStartDate.isEqual(endTimeConsideration)) && (reservationStartDate.isBefore(endDate) || reservationStartDate.isEqual(endDate))) {

                        if ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) ) {
                            penaltyAmount += 1.15 * (posting.getWeekendRent() + posting.getDailyParkingFee());
                            System.out.println("penalty till now weekend" + penaltyAmount);
                        }
                        else {
                            penaltyAmount += 1.15 * (posting.getWeekRent() + posting.getDailyParkingFee());
                            System.out.println("penalty till now weekday" + penaltyAmount);
                        }
                        reservationStartDate = reservationStartDate.plusDays(1);

                        System.out.println(reservationStartDate + "reservation Date changed");
                        c1.add(Calendar.DATE, 1);


                    }

                    while((reservationStartDate.isBefore(endDate) || reservationStartDate.isEqual(endDate))) {

                        if ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) ) {
                            penaltyAmount += 1 * (posting.getWeekendRent() + posting.getDailyParkingFee());
                            System.out.println(" extra days penalty till now weekend" + penaltyAmount);
                        }
                        else {
                            penaltyAmount += 1 * (posting.getWeekRent() + posting.getDailyParkingFee());
                            System.out.println(" extra days penalty till now weekday" + penaltyAmount);
                        }
                        reservationStartDate = reservationStartDate.plusDays(1);

                        System.out.println(reservationStartDate + "reservation Date changed");
                        c1.add(Calendar.DATE, 1);


                    }

                    amount = penaltyAmount;
                    //For guest
                    Payments guestDetails = paymentsDAO.getPaymentDetails(r.getTenantEmailId());
                    Transactions transaction = getTransactions(r, guestDetails, true, TransactionType.REFUND, -amount, guestDetails.getBalance() + amount);
                    transactionsDAO.createTransactions(transaction);
                    paymentsDAO.update(guestDetails);

                    //For host
                    Payments hostDetails = paymentsDAO.getPaymentDetails(r.getHostEmailId());
                    Transactions hosttransaction = getTransactions(r, hostDetails, false, TransactionType.REFUND, amount, hostDetails.getBalance() - amount);
                    transactionsDAO.createTransactions(hosttransaction);
                    paymentsDAO.update(hostDetails);

                    r.setIsCancelled((byte) 1);
                    reservationDAO.updateReservation(r);



                } //if not checkedIn
                else {

                    long daysToStart = timeAdvancementService.getCurrentTime().until(r.getStartDate().toLocalDateTime(), ChronoUnit.DAYS);

                    if(daysToStart < 7 && canUpdate) {

                        //Timestamp  startDay = r.getStartDate();
                        LocalDateTime reservationStartDate = r.getStartDate().toLocalDateTime().plusHours(8);

                        LocalDateTime endDate = r.getEndDate().toLocalDateTime().plusHours(8);
                        LocalDateTime currentTime = timeAdvancementService.getCurrentTime();
                        LocalDateTime endTimeConsideration = currentTime.plusDays(7);


                        double penaltyAmount = 0;

                        Calendar c1 = Calendar.getInstance();
                        c1.setTime(r.getStartDate());
                        System.out.println(c1.get(Calendar.DAY_OF_WEEK));

                        while((reservationStartDate.isBefore(endTimeConsideration)  || reservationStartDate.isEqual(endTimeConsideration)) && (reservationStartDate.isBefore(endDate) || reservationStartDate.isEqual(endDate))) {

                            if ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) ) {
                                penaltyAmount += 0.15 * (posting.getWeekendRent() + posting.getDailyParkingFee());
                                System.out.println("penalty till now weekend" + penaltyAmount);
                            }
                            else {
                                penaltyAmount += 0.15 * (posting.getWeekRent() + posting.getDailyParkingFee());
                                System.out.println("penalty till now weekday" + penaltyAmount);
                            }
                                reservationStartDate = reservationStartDate.plusDays(1);

                            System.out.println(reservationStartDate + "reservation Date changed");
                            c1.add(Calendar.DATE, 1);


                        }

                        System.out.println(penaltyAmount + "penaltyAmount");

                        //For amount to be changed
                        amount = penaltyAmount;
                        //For guest
                        Payments guestDetails = paymentsDAO.getPaymentDetails(r.getTenantEmailId());
                        Transactions transaction = getTransactions(r, guestDetails, true, TransactionType.REFUND, -amount, guestDetails.getBalance() + amount);
                        transactionsDAO.createTransactions(transaction);
                        paymentsDAO.update(guestDetails);

                        //For host
                        Payments hostDetails = paymentsDAO.getPaymentDetails(r.getHostEmailId());
                        Transactions hosttransaction = getTransactions(r, hostDetails, false, TransactionType.REFUND, amount, hostDetails.getBalance() - amount);
                        transactionsDAO.createTransactions(hosttransaction);
                        paymentsDAO.update(hostDetails);
                        r.setIsCancelled((byte) 1);
                        reservationDAO.updateReservation(r);
                    } else {
                       canUpdate = true;

                    }
                }


            }




        }


        //UPDATE POSTING

        if(canUpdate) {
            posting.setWeekendRent(postings.getWeekendRent());
            posting.setWeekRent(postings.getWeekRent());
           // posting.setCityName(postings.getCityName());
           // posting.setStartDate(postings.getStartDate());
           // posting.setEndDate(postings.getEndDate());
            posting.setDayAvailability(postings.getDayAvailability());
            try {
                Postings pos = (Postings) posting;
                System.out.println(pos.getPropertyId());
                System.out.println(pos.getWeekendRent());
                System.out.println(pos.getWeekRent());
                System.out.println(pos.getStartDate());
                System.out.println(pos.getCityName());

                session.update(posting);

            } catch (Exception e) {
                System.out.println("------------ Exception -----------------");
                System.out.println(e);
            }

            session.flush();
        }

        System.out.println("************** Updating Postings *************************");


    }

    @Override
    public List<Postings> search(PostingForm postingForm) {


       // TimeZone tzone = TimeZone.getTimeZone("PST");
        //TimeZone.setDefault(tzone);

        System.out.println("-----------------------------------------------");
        System.out.println("Posting start");
        System.out.println(postingForm.getStartDate() + "*" + postingForm.getEndDate() + "*");
        System.out.println(postingForm.getZipcode() + "*" + postingForm.getCityName() + "*");

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Postings.class);

        System.out.println("Start Time" + postingForm.getStartDate().toLocalDateTime());
        System.out.println("End Time" + postingForm.getEndDate());

       criteria.add(Restrictions.le("startDate", postingForm.getStartDate()));
        criteria.add(Restrictions.ge("endDate", postingForm.getEndDate()));




        /*if(postingForm.getCityName()!=null){
            criteria.add(Restrictions.like("cityName", postingForm.getCityName()));
        }*/

        if(postingForm.getZipcode()!=null){
            criteria.add(Restrictions.eq("zipcode", postingForm.getZipcode()));
        }
        if(postingForm.getDescription() !=null && postingForm.getDescription() != ""){
            criteria.add(Restrictions.like("description",  "%" + postingForm.getDescription() + "%"));
        }


        if(postingForm.getSharingType() != null) {
           criteria.add(Restrictions.eq("sharingType", postingForm.getSharingType()));
        }
        if(postingForm.getPropertyType() != null) {
            criteria.add(Restrictions.eq("propertyType", postingForm.getPropertyType()));
        }
        if(postingForm.getFromPrice() != null && postingForm.getToPrice() != null) {
            System.out.println("Week day prices");
            System.out.println(postingForm.getFromPrice()+ "    prices      " + postingForm.getToPrice());
            criteria.add(Restrictions.ge("weekRent",  (double)postingForm.getFromPrice()));
            criteria.add(Restrictions.le("weekRent",  (double)postingForm.getToPrice()));
        }

        if(postingForm.getWifi() != null) {
            criteria.add(Restrictions.eq("wifi", postingForm.getWifi()));
        }

        System.out.println("criteria end" + criteria);
        System.out.println("-----------------------------------------------");
        List<Postings> filteredPostings = new ArrayList<Postings>();
        for(Object obj: criteria.list()){
            Postings post = (Postings)obj;
            String dayAvailibility = post.getDayAvailability();
            boolean result = true;
            for(int i=0;i<7;++i){
             if(postingForm.getDayAvailibility().charAt(i)=='1' && dayAvailibility.charAt(i) == '0'){
                 result = false;
                 break;
             }
            }

            if(result){
                filteredPostings.add(post);
            }
        }

        List<Postings> allPostings = new ArrayList<>();

        for(Postings p : filteredPostings){
            String city = p.getCityName().replaceAll(" ","");
            String input = postingForm.getCityName().replaceAll(" ","");
            System.out.println(city + "DB city");
            System.out.println(input + "input city");
            System.out.println(p.getStartDate() + "compare with" + postingForm.getStartDate().toLocalDateTime().plusHours(8));
            System.out.println(p.getEndDate()+ "compare with" + postingForm.getEndDate().toLocalDateTime().plusHours(8) );
        if(city.toLowerCase().indexOf(input.toLowerCase()) != -1){

                allPostings.add(p);
        }

        }

        return  allPostings;



    }

@Override
   public List<Postings> getPostingsOfHost(String userId){

        try {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Postings.class);
            criteria.add(Restrictions.eq("userId", userId));
            System.out.println(criteria);
            return criteria.list();

        }
        catch(Exception e){
            System.out.println(e);
            return  null;
        }

}
    @Override
    public void updateRatingOfAPosting(int postingId, double rating){
        System.out.println("In updateRatingOfAPosting of PostingsDAOImpl");
        Session session = sessionFactory.getCurrentSession();
        System.out.println("PostingId:"+postingId+", Rating:"+rating);
        try{

            Postings posting = session.load(Postings.class,postingId);
            posting.setAvgRating(rating);
            session.save(posting);

        }catch(Exception e){
            e.printStackTrace();
        }
        session.flush();
    }


    private Transactions getTransactions(Reservation reservation, Payments paymentDetails, boolean guest, TransactionType type, double amount, double balance) {

        Transactions transaction = new Transactions();

        String email = guest ? reservation.getTenantEmailId() : reservation.getHostEmailId();
        transaction.setEmail(email);
        transaction.setAmount(amount);
        System.out.println("balance" + balance);
        transaction.setCurrentBalance(balance);
        paymentDetails.setBalance(balance);
        transaction.setReservationId(reservation.getBookingId());
        transaction.setType(type);
        transaction.setDate(Timestamp.valueOf(timeAdvancementService.getCurrentTime()));



        String emailText = "Payment made on your card for reservation" + reservation.getBookingId();
        String emailSubject = "Hello, payment made on your card is " + amount + ". Your current balance is :" + balance;
        Mail mail = new Mail(emailText, emailSubject, email);
        mailServiceController.addToQueue(mail);
        return transaction;
    }



   /* public void cancelReservation(int bookingId) {
        try {


            Reservation reservation = reservationDao.getReservation(bookingId);
            reservation.setIsCancelled((byte) 1);

            String emailText = "No show for reservation" + reservation.getBookingId() + " Reservation is cancelled";
            String emailSubject = "Hello guest, your reservation is cancelled as we didn't see you by check in time.. Apologies !!";
            Mail email = new Mail(emailText, emailSubject, reservation.getTenantEmailId());
            mailServiceController.addToQueue(email);

            String emailText2 = "No show for property" + reservation.getPostingId() + "by" + reservation.getTenantEmailId();
            String emailSubject2 = "Hello host, your property has cancelled as guest didn't check in by start time";
            Mail email2 = new Mail(emailText2, emailSubject2, reservation.getHostEmailId());
            mailServiceController.addToQueue(email2);

            reservationDao.updateReservation(reservation);

            return;
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }*/

}
