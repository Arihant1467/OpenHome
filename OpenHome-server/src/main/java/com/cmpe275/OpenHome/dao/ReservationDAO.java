package com.cmpe275.OpenHome.dao;

import com.cmpe275.OpenHome.model.Reservation;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Map;

public interface ReservationDAO {
    List<Reservation> list();
    Reservation makeReservation(Reservation reservation);

    Reservation updateReservation(Reservation reservation) throws Exception;

    Reservation getReservation(int id);

    List<Reservation> getReservations(Map<String,Object> inputConditions   );

    List<Reservation> getReservationsById(String email) throws Exception;
}
