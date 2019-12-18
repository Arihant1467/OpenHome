package com.cmpe275.OpenHome.controller;


import com.cmpe275.OpenHome.service.TimeAdvancementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

@RestController
@RequestMapping("/api")
public class TimeAdvancementController {

    @Autowired
    private TimeAdvancementServiceImpl timeAdvancementService;

    @CrossOrigin
    @PutMapping("/changeTime")
    public ResponseEntity<?> changeTime(@RequestBody String dateTime) {

        try {

            if (dateTime.charAt(0) == '#') {
                System.out.println("hey i am in change time before" + timeAdvancementService.getCurrentTime());
                timeAdvancementService.resetToLocalTime();

                System.out.println("hey i am in change time after" + timeAdvancementService.getCurrentTime());
                return ResponseEntity.ok().body("Sorry, you are back");
            }
            else {

            TimeZone tzone = TimeZone.getTimeZone("PST");
            TimeZone.setDefault(tzone);

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            Date date = formatter.parse(dateTime);
            System.out.println(date);
            System.out.println("hey i am in change time before" + timeAdvancementService.getCurrentTime());
            timeAdvancementService.setCurrentTime(date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
            System.out.println("hey i am in change time after" + timeAdvancementService.getCurrentTime());
            return ResponseEntity.ok().body("Wow !! You time travelled !!");
        }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @CrossOrigin
    @PutMapping("/getTime")
    public ResponseEntity<?> getTime() {

        try {

            TimeZone tzone = TimeZone.getTimeZone("PST");
                TimeZone.setDefault(tzone);

                return ResponseEntity.ok().body(timeAdvancementService.getCurrentTime());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}

