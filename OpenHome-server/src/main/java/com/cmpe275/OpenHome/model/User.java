package com.cmpe275.OpenHome.model;

import com.cmpe275.OpenHome.enums.LoginType;
import com.cmpe275.OpenHome.enums.UserType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "Openhome")
public class User {
    private String email;
    private String password;
    private UserType usertype;
    private LoginType logintype;
    private String firstname;
    private String lastname;
    private Byte isVerified;

    @Id
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "USERTYPE")
    public UserType getUsertype() {
        return usertype;
    }

    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "LOGINTYPE")
    public LoginType getLogintype() {
        return logintype;
    }
    public void setLogintype(LoginType logintype) {
        this.logintype = logintype;
    }

    @Basic
    @Column(name = "FIRSTNAME")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "LASTNAME")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "isVerified")
    public Byte getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Byte isVerified) {
        this.isVerified = isVerified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(usertype, that.usertype) &&
                Objects.equals(logintype, that.logintype) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(isVerified, that.isVerified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, usertype, logintype, firstname, lastname, isVerified);
    }
}
