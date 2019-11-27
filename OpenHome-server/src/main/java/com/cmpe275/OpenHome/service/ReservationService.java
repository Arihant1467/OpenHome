package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.model.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> list();
    long save(Reservation reservation);
}
