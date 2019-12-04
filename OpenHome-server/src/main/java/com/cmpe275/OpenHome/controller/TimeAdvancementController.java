package com.cmpe275.OpenHome.controller;


import com.cmpe275.OpenHome.model.Reservation;
import com.cmpe275.OpenHome.service.ReservationService;
import com.cmpe275.OpenHome.service.TimeAdvancementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public  class TimeAdvancementController {

    @Autowired
    private TimeAdvancementServiceImpl timeAdvancementService;

    @CrossOrigin
    @PutMapping("/changeTime")
    public ResponseEntity<?> changeTime(@RequestBody String dateTime) {

        try {

//            DateTimeFormatter df = new DateTimeFormatterBuilder()
//                    // case insensitive to parse JAN and FEB
//                    .parseCaseInsensitive()
//                    // add pattern
//                    .appendPattern("YYYY-MM-DD")
//                    // create formatter (use English Locale to parse month names)
//                    .toFormatter(Locale.ENGLISH);

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            Date date = (Date)formatter.parse(dateTime);
            System.out.println(date);

           // DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
          //  final ZonedDateTime parsed = ZonedDateTime.parse(dateTime, formatter.withZone(ZoneId.of("UTC")));
           // System.out.println(parsed.toLocalDateTime());
          //  System.out.println("Given time"+ dateTime.toString());
                  //  System.out.println("hey i am in change time before"+  timeAdvancementService.getCurrentTime());
           // timeAdvancementService.setCurrentTime( LocalDateTime.parse(dateTime, df));
            System.out.println("hey i am in change time after"+ timeAdvancementService.getCurrentTime());
            return ResponseEntity.ok().body("Wow !! You time travelled !!");
        }
        catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }




}

