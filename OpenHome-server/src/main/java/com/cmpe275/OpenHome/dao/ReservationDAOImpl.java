package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Reservation;

import com.cmpe275.OpenHome.service.TimeAdvancementServiceImpl;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ReservationDAOImpl implements ReservationDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Autowired
    private TimeAdvancementServiceImpl timeAdvancementService;


    @Override
    public List<Reservation> list() {
        List<Reservation> list = sessionFactory.getCurrentSession().createQuery("from Reservation" +
                " ").list();
        return list;
    }


    public Reservation makeReservation(Reservation reservation) {
        Session session = sessionFactory.getCurrentSession();
        session.save(reservation);

        // session.flush();
        return reservation;
    }

    @Override
    public Reservation updateReservation(Reservation reservation) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.update(reservation);

            System.out.println("in update reservation" + reservation.getIsCancelled());
            return reservation;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        } finally {
            session.flush();
        }

    }

    @Override
    public Reservation getReservation(int id) {

        return sessionFactory.getCurrentSession().get(Reservation.class, id);
    }

    @Override
    public List<Reservation> getReservationsForNoShow() {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Reservation.class);


        criteria.add(Restrictions.le("startDate", timeAdvancementService.getCurrentTime()));
        criteria.add(Restrictions.ne("checkIn", null));
        criteria.add(Restrictions.ne("isCancelled", false));

        return criteria.list();
    }

    @Override
    public List<Reservation> getReservationsById(String email) throws Exception {
        try {

            Query query = sessionFactory.getCurrentSession().createQuery("from Reservation as reservation where reservation.tenantEmailId = :key" +
                    " ");

            System.out.println("in get reservations DAO " + email);
            query.setString("key", email);

            System.out.println("in get reservations DAO query list " + query.list().size());
            return query.list();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());

        }
    }


    @Override
    public List<Reservation> getReservationsByPostingId(int postingId) throws Exception {
        try {

            Query query = sessionFactory.getCurrentSession().createQuery("from Reservation as reservation where reservation.postingId = :key" +
                    " ");

            System.out.println("in get reservations DAO posting id " + postingId);
            query.setInteger("key", postingId);

            System.out.println("in get reservations DAO posting id query list " + query.list().size());
            return query.list();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());

        }
    }

    @Override
    public List<Reservation> getReservationsForAutocheckout() throws Exception {

        try {

//            LocalDate localDate = timeAdvancementService.getCurrentTime().toLocalDate();
//
//            LocalDateTime dateTimeFromDateAndTime = LocalDateTime.of(localDate, time);


            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Reservation.class);
            criteria.add(Restrictions.eq("checkOut", null));
            criteria.add(Restrictions.le("endDate", timeAdvancementService.getCurrentTime()));
            return criteria.list();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());

        }

    }
}
