package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Postings;
import com.cmpe275.OpenHome.model.Reservation;
import com.cmpe275.OpenHome.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
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
        Session session = sessionFactory.getCurrentSession();
       session.save(reservation);

       // session.flush();
        return reservation;
    }

    @Override
    public Reservation updateReservation(Reservation reservation) throws  Exception{
        Session session = sessionFactory.getCurrentSession();
        try {


            session.update(reservation);

            System.out.println("in update reservation" + reservation.getIsCancelled());
            return reservation;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }

        finally {
            session.flush();
        }

    }

    @Override
    public Reservation getReservation(int id) {

        return sessionFactory.getCurrentSession().get(Reservation.class,id);
    }

    @Override
    public List<Reservation> getReservations(Map<String,Object> inputConditions  ) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Reservation.class);

        //for(Map.Entry<String,Object> entry : inputConditions.entrySet())

        criteria.add(Restrictions.ge("startDate", LocalDateTime.now().plusHours(-12)));
        criteria.add(Restrictions.ne("checkIn", null));
        criteria.add(Restrictions.ne("isCancelled", false));

        return criteria.list();
    }

    @Override
    public List<Reservation> getReservationsById(String email) throws Exception{
        try {

            Query query =  sessionFactory.getCurrentSession().createQuery("from Reservation as reservation where reservation.tenantEmailId = :key or reservation.hostEmailId = :key " +
                    " ");

            System.out.println("in get reservations DAO " + email);
            query.setString("key", email);

            System.out.println("in get reservations DAO query list " + query.list().size());
            return query.list();

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());

        }
    }
}
