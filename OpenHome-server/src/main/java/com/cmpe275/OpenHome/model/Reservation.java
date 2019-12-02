package com.cmpe275.OpenHome.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "reservations", schema = "Openhome", catalog = "")
public class Reservation {
    private int bookingId;
    private String hostEmailId;
    private String tenantEmailId;
    private Timestamp startDate;
    private Integer postingId;
    private Timestamp endDate;
    private Integer bookingCost;
    private Byte isCancelled;
    private Timestamp checkIn;
    private Timestamp checkOut;

    @Id
    @Column(name = "booking_id")
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    @Basic
    @Column(name = "host_email_id")
    public String getHostEmailId() {
        return hostEmailId;
    }

    public void setHostEmailId(String hostEmailId) {
        this.hostEmailId = hostEmailId;
    }

    @Basic
    @Column(name = "tenant_email_id")
    public String getTenantEmailId() {
        return tenantEmailId;
    }

    public void setTenantEmailId(String tenantEmailId) {
        this.tenantEmailId = tenantEmailId;
    }

    @Basic
    @Column(name = "start_date")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "posting_id")
    public Integer getPostingId() {
        return postingId;
    }

    public void setPostingId(Integer postingId) {
        this.postingId = postingId;
    }

    @Basic
    @Column(name = "end_date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "booking_cost")
    public Integer getBookingCost() {
        return bookingCost;
    }

    public void setBookingCost(Integer bookingCost) {
        this.bookingCost = bookingCost;
    }

    @Basic
    @Column(name = "isCancelled")
    public Byte getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Byte isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Basic
    @Column(name = "checkIn")
    public Timestamp getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Timestamp checkIn) {
        this.checkIn = checkIn;
    }

    @Basic
    @Column(name = "checkOut")
    public Timestamp getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Timestamp checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return bookingId == that.bookingId &&
                Objects.equals(hostEmailId, that.hostEmailId) &&
                Objects.equals(tenantEmailId, that.tenantEmailId) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(postingId, that.postingId) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(bookingCost, that.bookingCost) &&
                Objects.equals(isCancelled, that.isCancelled) &&
                Objects.equals(checkIn, that.checkIn) &&
                Objects.equals(checkOut, that.checkOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, hostEmailId, tenantEmailId, startDate, postingId, endDate, bookingCost, isCancelled, checkIn, checkOut);
    }
}
