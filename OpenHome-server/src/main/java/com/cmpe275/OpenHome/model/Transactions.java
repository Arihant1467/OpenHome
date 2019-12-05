package com.cmpe275.OpenHome.model;

import com.cmpe275.OpenHome.enums.TransactionType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "transactions", schema = "Openhome")
public class Transactions {
    private String email;
    private Integer transactionId;
    private double amount;
    private Integer reservationId;
    private Integer currentBalance;
    private TransactionType type;


    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }




    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "transactionId")
    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    @Basic
    @Column(name = "amount")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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
    public Integer getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Integer currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transactions that = (Transactions) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(transactionId, that.transactionId) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(reservationId, that.reservationId) &&
                Objects.equals(currentBalance, that.currentBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, transactionId, amount, reservationId, currentBalance);
    }
}
