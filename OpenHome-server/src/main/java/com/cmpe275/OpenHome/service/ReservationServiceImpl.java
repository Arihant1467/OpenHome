package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.dao.ReservationDAO;
import com.cmpe275.OpenHome.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService{


    @Autowired
    private ReservationDAO reservationDao;

    @Override
    @Transactional
    public List<Reservation> list() {
        return reservationDao.list();
    }

    @Transactional

    public long save(Reservation reservation) {
        return reservationDao.save(reservation);
    }
}