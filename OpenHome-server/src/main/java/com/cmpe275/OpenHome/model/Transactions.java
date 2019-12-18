package com.cmpe275.OpenHome.model;

import com.cmpe275.OpenHome.enums.TransactionType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "transactions", schema = "Openhome")
public class Transactions {
    private String email;
    private int transactionId;
    private Double amount;
    private Integer reservationId;
    private Double currentBalance;
    private TransactionType type;
    private Timestamp date;

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Id
    @Column(name = "transactionId")
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    @Basic
    @Column(name = "amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "reservationId")
    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    @Basic
    @Column(name = "currentBalance")
    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transactions that = (Transactions) o;
        return transactionId == that.transactionId &&
                Objects.equals(email, that.email) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(reservationId, that.reservationId) &&
                Objects.equals(currentBalance, that.currentBalance) &&
                Objects.equals(type, that.type) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, transactionId, amount, reservationId, currentBalance, type, date);
    }
}
