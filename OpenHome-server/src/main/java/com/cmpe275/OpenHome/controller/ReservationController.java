package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.model.Reservation;
import com.cmpe275.OpenHome.service.ReservationService;
import com.cmpe275.OpenHome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


@RestController
@RequestMapping("/api")

public class ReservationController {


    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {

        List<Reservation> reservations = reservationService.list();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservation")
    public ResponseEntity<?> makeReservation(@RequestBody Reservation reservation) {


        try {
            Reservation reser = reservationService.save(reservation);
            return ResponseEntity.ok().body("New reservation has been saved with ID:" + reser);
        }

        catch (Exception e) {

            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }


    }


    @DeleteMapping("/reservation")
    public ResponseEntity<?> cancel(@RequestBody int id) {

         Reservation reservation = reservationService.cancelReservation(id);


        return ResponseEntity.ok().body("Reservation cancelled:" + reservation);
    }

    @PostMapping("/checkIn")
    public ResponseEntity<?> checkIn(@RequestBody int id) {

        try {

            Reservation reservation = reservationService.checkIn(id);

            return ResponseEntity.ok().body("Checkin Complete:" + reservation);
        }

        catch(Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }

    @PostMapping("/checkOut")
    public ResponseEntity<?> checkOut(@RequestBody int id) {

        try {

            Reservation reservation = reservationService.checkOut(id);

            return ResponseEntity.ok().body("Checkout Complete:" + reservation);
        }

        catch(Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }




    @Scheduled(initialDelay = 30000, fixedDelay=120000000)  // 2 minutes
    public void cacheRefresh() {
        System.out.println("Running cancel reservations task");
        try {

            reservationService.handleCancellations();
        } catch (Exception e) {
            System.out.println("cancel reservations task failed: " + e.getMessage());
        }
    }

}