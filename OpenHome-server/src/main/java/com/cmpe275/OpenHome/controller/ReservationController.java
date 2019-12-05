package com.cmpe275.OpenHome.controller;

import com.cmpe275.OpenHome.model.Mail;
import com.cmpe275.OpenHome.model.Reservation;
import com.cmpe275.OpenHome.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")

public class ReservationController {


    @Autowired
    private ReservationService reservationService;

    @Autowired
    private MailServiceController mailServiceController;


    @CrossOrigin
    @GetMapping("/reservations")
    public ResponseEntity<?> getReservations() {

        try {
            System.out.println("hey i am in get reservations");
            List<Reservation> reservations = reservationService.list();
            return ResponseEntity.ok().body(reservations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @CrossOrigin
    @GetMapping("/reservations/{email:.+}")
    public ResponseEntity<?> getReservations(@PathVariable("email") String email) {

        try {
            System.out.println("hey i am in get reservations by email");
            List<Reservation> reservations = reservationService.getReservationsById(email);
            return ResponseEntity.ok().body(reservations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @CrossOrigin
    @PostMapping("/reservation")
    public ResponseEntity<?> makeReservation(@RequestBody Reservation reservation) {


        try {

            System.out.println(reservation);
            Reservation reservation1 = reservationService.save(reservation);
            String emailText = "Reservation is confirmed :" + reservation1.getBookingId();
            String emailSubject = "Hello guest, your  reservation is confirmed. Your check in starts at 3pm.. See you soon!!";

            Mail email = new Mail(emailText, emailSubject, reservation1.getTenantEmailId());
            mailServiceController.addToQueue(email);

            return ResponseEntity.ok().body("New reservation has been saved with ID:" + reservation1.getBookingId());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }


    }

    @CrossOrigin
    @PutMapping("/cancelReservation/{id}")
    public ResponseEntity<?> cancel(@PathVariable("id") int id) {
        System.out.println("In delete");

        try {

            Reservation reservation = reservationService.cancelReservation(id);


            String emailText = "Reservation cancelled id :" + reservation.getBookingId();
            String emailSubject = "Hello guest, your  reservation cancellation is successful. We will miss you !!";

            Mail email = new Mail(emailText, emailSubject, reservation.getTenantEmailId());
            mailServiceController.addToQueue(email);


            return ResponseEntity.ok().body("Reservation cancelled:" + reservation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @CrossOrigin
    @PutMapping("/checkIn")
    public ResponseEntity<?> checkIn(@RequestBody int id) {

        try {
            System.out.println("in check in" + id);
            Reservation reservation = reservationService.checkIn(id);

            String emailText = "Check In Complete";
            String emailSubject = "Hello guest, your check in is complete..  Enjoy your stay at OpenHome !!";

            Mail email = new Mail(emailText, emailSubject, reservation.getTenantEmailId());
            mailServiceController.addToQueue(email);

            return ResponseEntity.ok().body("Checkin Complete:" + reservation);
        } catch (Exception e) {

            String emailText = "Check In failed";
            String emailSubject = e.getMessage();

            Mail email = new Mail(emailText, emailSubject, "");
            mailServiceController.addToQueue(email);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }

    @CrossOrigin
    @PutMapping("/checkOut")
    public ResponseEntity<?> checkOut(@RequestBody int id) {

        try {

            Reservation reservation = reservationService.checkOut(id);


            String emailText = "Check Out Complete";
            String emailSubject = "Hello guest, your check out is complete.. Hope you had a great stay !!";

            Mail email = new Mail(emailText, emailSubject, reservation.getTenantEmailId());
            mailServiceController.addToQueue(email);

            return ResponseEntity.ok().body("Checkout Complete:" + reservation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }


}