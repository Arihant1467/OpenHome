package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Reservation;
import java.util.List;

public interface ReservationDao {
    List<Reservation> list();
    long save(Reservation reservation);
}
