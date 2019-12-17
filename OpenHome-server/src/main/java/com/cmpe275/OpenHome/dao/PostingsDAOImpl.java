package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.DataObjects.PostingForm;
import com.cmpe275.OpenHome.enums.TransactionType;
import com.cmpe275.OpenHome.model.Postings;
import com.cmpe275.OpenHome.model.Reservation;
import com.cmpe275.OpenHome.model.Transactions;
import com.cmpe275.OpenHome.service.ReservationService;
import com.cmpe275.OpenHome.service.ReservationServiceImpl;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.header.writers.CacheControlHeadersWriter;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Repository
public class PostingsDAOImpl implements  PostingsDAO {

    @Autowired
    private ReservationDAOImpl reservationDAO;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private TransactionsDAO transactionsDAO ;




    @Override
    public List<Postings> getPostings() {
        List<Postings> list = sessionFactory.getCurrentSession().createQuery("from Postings" +
                " ").list();
        return list;
    }

    @Override
    public Postings getPosting(int id) {
        Postings posting = sessionFactory.getCurrentSession().get(Postings.class,id);
        return posting;
    }


    @Override
    public long save(Postings postings) throws Exception{
        System.out.println("In save of postings");
        sessionFactory.getCurrentSession().save(postings);
        return postings.getPropertyId();
    }

    @Override
    public int deletePosting(int id) {
        Postings posting = sessionFactory.getCurrentSession().get(Postings.class,id);
        sessionFactory.getCurrentSession().delete(posting);
        return id;
    }

    @Override
    public void update(Postings postings) throws Exception {

        System.out.println("posting service");

        TimeZone tzone = TimeZone.getTimeZone("PST");
        TimeZone.setDefault(tzone);
        //Cancel using

        Session session = sessionFactory.getCurrentSession();
        Postings posting = session.byId(Postings.class).load(postings.getPropertyId());
        System.out.println(posting.getCityName() + "City verify");
        //Update cost of postings
        posting.setWeekendRent(postings.getWeekendRent());
        posting.setWeekRent(postings.getWeekRent());

        posting.setStartDate(postings.getStartDate());
        posting.setEndDate(postings.getEndDate());



        //Get Reservations for postings
        Criteria criteria = session.createCriteria(Reservation.class);
        System.out.println("posting service");
        List<Reservation> reservations = new ArrayList<>();
        List<Reservation> filteredPostings = new ArrayList<Reservation>();
        try {
           reservations = criteria.add(Restrictions.eq("postingId", postings.getPropertyId())).list();



            for(Object obj: criteria.list()){
                Reservation r = (Reservation) obj;
                Postings post = (Postings)posting;
                String dayAvailibility = post.getDayAvailability();
                boolean result = true;
                for(int i=0;i<7;++i){
                    if(r.getDayAvailability().charAt(i)=='1' && dayAvailibility.charAt(i) == '0'){
                        result = false;
                        break;
                    }
                }

                if(result){
                    filteredPostings.add(r);
                }
            }




            System.out.println("posting reservations service");
        } catch(Exception e) {
            System.out.println(e);
        }
        System.out.println("posting service");
        for (Reservation r : filteredPostings) {
            System.out.println("Updating reservation service");

            long daysLeft = LocalDateTime.now().until(r.getEndDate().toLocalDateTime(), ChronoUnit.DAYS);
            long daysToStart = LocalDateTime.now().until(r.getStartDate().toLocalDateTime(), ChronoUnit.DAYS);
            System.out.println(daysToStart + "Day diff");

            double amount = r.getBookingCost();

            if (r.getCheckIn() != null ) {

                long totalDays = r.getStartDate().toLocalDateTime().until(r.getEndDate().toLocalDateTime(), ChronoUnit.DAYS);
                double eachDayAmount = amount/totalDays;

                amount  = 0;

                if(daysLeft - 7 > 0) {
                    amount += (daysLeft - 7) * eachDayAmount;
                    daysLeft -= 7;
                }
                amount += daysLeft*eachDayAmount*1.15;


            } else if (daysToStart < 7 )
                amount *=  1.15;

            reservationService.noShowcancelReservation(r.getBookingId());
            reservationDAO.updateReservation(r);


            Transactions transaction = new Transactions();
            transaction.setEmail(r.getTenantEmailId());
            System.out.println("reservation cost cancelation by host" +r.getBookingId());
            transaction.setAmount(-amount);
            transaction.setCurrentBalance(transaction.getCurrentBalance() + amount);
            transaction.setReservationId(r.getBookingId());
            transaction.setType(TransactionType.REFUND);
            transactionsDAO.createTransactions(transaction);



            transaction = new Transactions();
            transaction.setEmail(r.getHostEmailId());
            transaction.setAmount(amount);
            transaction.setCurrentBalance(transaction.getCurrentBalance() - amount);
            transaction.setReservationId(r.getBookingId());
            transaction.setType(TransactionType.PENALTY);
            transactionsDAO.createTransactions(transaction);


        }
        System.out.println("Updating postings objects" + postings );
try {
    Postings pos = (Postings) postings;
    System.out.println(pos.getPropertyId());
    System.out.println(pos.getWeekendRent());
    System.out.println(pos.getWeekRent());
    System.out.println(pos.getStartDate());
    System.out.println(pos.getCityName());
    session.update(postings);

} catch(Exception e){
    System.out.println("------------ Exception -----------------");
    System.out.println(e);
}
            session.flush();
    }

    @Override
    public List<Postings> search(PostingForm postingForm) {


        TimeZone tzone = TimeZone.getTimeZone("PST");
        TimeZone.setDefault(tzone);

        System.out.println("-----------------------------------------------");
        System.out.println("Posting start");
        System.out.println(postingForm.getStartDate() + "*" + postingForm.getEndDate() + "*");
        System.out.println(postingForm.getZipcode() + "*" + postingForm.getCityName() + "*");

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Postings.class);
        criteria.add(Restrictions.le("startDate", postingForm.getStartDate()));
        criteria.add(Restrictions.ge("endDate", postingForm.getEndDate()));




        /*if(postingForm.getCityName()!=null){
            criteria.add(Restrictions.like("cityName", postingForm.getCityName()));
        }*/

        if(postingForm.getZipcode()!=null){
            criteria.add(Restrictions.eq("zipcode", postingForm.getZipcode()));
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
        if(postingForm.getDescription() != null) {
            criteria.add(Restrictions.like("description", postingForm.getDescription()));
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
        System.out.println("PostingId:"+postingId+", Rating:"+rating);
        try{
            Session session = sessionFactory.getCurrentSession();
            Postings posting = session.load(Postings.class,postingId);
            posting.setAvgRating(rating);
            session.save(posting);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
