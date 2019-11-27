package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Reservation;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ReservationDAOImpl implements ReservationDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Reservation> list() {
        List<Reservation> list = sessionFactory.getCurrentSession().createQuery("from reservations" +
                " ").list();
        return list;
    }


    public Reservation makeReservation(Reservation reservation) {
        sessionFactory.getCurrentSession().save(reservation);
        return reservation;
    }

    @Override
    public int deleteReservation(int id) {


        Reservation reservation = sessionFactory.getCurrentSession().load(Reservation.class,id);

        //This makes the pending delete to be done

         sessionFactory.getCurrentSession().delete(reservation);
         return id;
    }
}
