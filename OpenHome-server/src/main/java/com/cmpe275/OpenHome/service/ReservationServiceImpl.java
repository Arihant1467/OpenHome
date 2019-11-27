package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.dao.ReservationDao;
import com.cmpe275.OpenHome.model.Reservation;
import com.mysql.cj.conf.ConnectionUrlParser;
import org.hibernate.mapping.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


@Service
//@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService{


    @Autowired
    private ReservationDao reservationDao;

    @Override
    @Transactional
    public List<Reservation> list() {
        return reservationDao.list();
    }

    @Transactional
    public Reservation save(Reservation reservation) throws Exception {


        Period reservationDays = Period.between(reservation.getStartDate().toLocalDate(), reservation.getEndDate().toLocalDate());
        int diff = reservationDays.getDays();

        if(diff > 14)
            throw new Exception("ERROR.RESERVATION_RANGE_ERROR");

        Period reservationStartDate = Period.between(LocalDate.now(), reservation.getEndDate().toLocalDate());
        int maxStartDate = reservationStartDate.getDays();

        if(maxStartDate > 365)
            throw new Exception(new Properties().getProperty("ERROR.RESERVATION_START_DATE_ERROR"));

        return reservationDao.makeReservation(reservation);
    }

    @Override
    public int cancelReservation(int id) {
        return reservationDao.deleteReservation(id);
    }
}