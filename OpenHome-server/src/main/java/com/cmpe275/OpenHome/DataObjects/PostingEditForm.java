package com.cmpe275.OpenHome.DataObjects;

import java.sql.Timestamp;

public class PostingEditForm {

    private int propertyId;
    private Timestamp startDate;
    private Timestamp endDate;
    private String dayAvailability;
    private int weekRent;
    private int weekendRent;
    private boolean bearPenalty;


    public boolean isBearPenalty() {
        return bearPenalty;
    }

    public void setBearPenalty(boolean bearPenalty) {
        this.bearPenalty = bearPenalty;
    }


    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getDayAvailability() {
        return dayAvailability;
    }

    public void setDayAvailability(String dayAvailability) {
        this.dayAvailability = dayAvailability;
    }

    public int getWeekRent() {
        return weekRent;
    }

    public void setWeekRent(int weekRent) {
        this.weekRent = weekRent;
    }

    public int getWeekendRent() {
        return weekendRent;
    }

    public void setWeekendRent(int weekendRent) {
        this.weekendRent = weekendRent;
    }
}
