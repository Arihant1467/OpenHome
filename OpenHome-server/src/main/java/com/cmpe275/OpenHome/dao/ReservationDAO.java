package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Reservation;
import java.util.List;

public interface ReservationDAO {
    List<Reservation> list();
    Reservation makeReservation(Reservation reservation);

    Reservation updateReservation(Reservation reservation);

    Reservation getReservation(int id);
}
