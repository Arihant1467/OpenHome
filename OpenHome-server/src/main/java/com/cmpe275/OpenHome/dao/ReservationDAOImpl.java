package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Postings;
import com.cmpe275.OpenHome.model.Reservation;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Repository
public class ReservationDAOImpl implements ReservationDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Reservation> list() {
        List<Reservation> list = sessionFactory.getCurrentSession().createQuery("from Reservation" +
                " ").list();
        return list;
    }


    public Reservation makeReservation(Reservation reservation) {
        sessionFactory.getCurrentSession().save(reservation);
        return reservation;
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {

         sessionFactory.getCurrentSession().save(reservation);
         return reservation;
    }

    @Override
    public Reservation getReservation(int id) {

        return sessionFactory.getCurrentSession().get(Reservation.class,id);
    }

    @Override
    public List<Reservation> getReservations(Map<String,Object> inputConditions  ) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Reservation.class);

        //for(Map.Entry<String,Object> entry : inputConditions.entrySet())

        criteria.add(Restrictions.ge("start_date", LocalDateTime.now().plusHours(-12)));
        criteria.add(Restrictions.ne("checkIn", null));
        criteria.add(Restrictions.ne("isCancelled", false));

        return criteria.list();
    }
}
