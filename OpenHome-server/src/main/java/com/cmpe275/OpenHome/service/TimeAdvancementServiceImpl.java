package com.cmpe275.OpenHome.service;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public class TimeAdvancementServiceImpl {


    private  LocalDateTime currentTime = LocalDateTime.now();


    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    public void resetToLocalTime() {
        currentTime = LocalDateTime.now();
    }
}
