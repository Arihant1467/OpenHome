package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.DataObjects.PostingForm;
import com.cmpe275.OpenHome.model.Postings;
import com.cmpe275.OpenHome.model.Reservation;
import com.cmpe275.OpenHome.service.ReservationServiceImpl;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Repository
public class PostingsDAOImpl implements  PostingsDAO {

    @Autowired
    private ReservationDAOImpl reservationDAO;

    @Autowired
    private SessionFactory sessionFactory;


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
        Session session = sessionFactory.getCurrentSession();
        Postings posting = session.byId(Postings.class).load(postings.getPropertyId());
        //Update cost of postings
        posting.setWeekendRent(postings.getWeekendRent());
        posting.setWeekRent(postings.getWeekRent());



        //Get Reservations for postings
        Criteria criteria = session.createCriteria(Reservation.class);
        List<Reservation> reservations = criteria.add(Restrictions.eq("posting_id", postings.getPropertyId())).list();
        for (Reservation r : reservations) {

            if (r.getCheckIn() != null ) {

                r.setIsCancelled((byte) 1);
                r.setBookingCost((int) (-1.15) * r.getBookingCost());
                reservationDAO.updateReservation(r);


            } else if (r.getStartDate() != null){
                long days = LocalDateTime.now().plusDays( 7 ).until(r.getStartDate().toLocalDateTime(), ChronoUnit.DAYS);
                System.out.println(days + "Day diff");
                if(days < 7) {
                    r.setIsCancelled((byte) 1);
                    r.setBookingCost((int) (-1.15) * r.getBookingCost());
                    reservationDAO.updateReservation(r);
                }
            } else {
                r.setIsCancelled((byte) 1);
                reservationDAO.updateReservation(r);

            }


        }
            this.save(postings);

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

        if(postingForm.getCityName()!=null){
            criteria.add(Restrictions.like("cityName", postingForm.getCityName()));
        }

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
            criteria.add(Restrictions.ge("weekRent",  postingForm.getFromPrice()));
            criteria.add(Restrictions.le("weekRent",  postingForm.getToPrice()));
        }
        if(postingForm.getDescription() != null) {
            criteria.add(Restrictions.like("description", postingForm.getDescription()));
        }
        if(postingForm.getWifi() != null) {
            criteria.add(Restrictions.eq("wifi", postingForm.getWifi()));
        }

        System.out.println("Posting end");
        System.out.println("-----------------------------------------------");
        return criteria.list();



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

}
