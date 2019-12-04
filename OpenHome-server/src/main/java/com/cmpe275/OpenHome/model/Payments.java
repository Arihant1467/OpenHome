package com.cmpe275.OpenHome.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Payments {
    private String email;
    private Long cardnumber;
    private Integer cvv;
    private double balance;

    @Id
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "cardnumber")
    public Long getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(Long cardnumber) {
        this.cardnumber = cardnumber;
    }

    @Basic
    @Column(name = "cvv")
    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    @Basic
    @Column(name = "balance")
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payments payments = (Payments) o;
        return Objects.equals(email, payments.email) &&
                Objects.equals(cardnumber, payments.cardnumber) &&
                Objects.equals(cvv, payments.cvv) &&
                Objects.equals(balance, payments.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, cardnumber, cvv, balance);
    }
}
