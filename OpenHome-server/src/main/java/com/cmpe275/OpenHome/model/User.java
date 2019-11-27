package com.cmpe275.OpenHome.model;

import com.cmpe275.OpenHome.enums.LoginType;
import com.cmpe275.OpenHome.enums.UserType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "Openhome")
@IdClass(UserPK.class)
public class User {
    private Integer id;
    private String email;
    private String password;
    private UserType usertype;
    private LoginType logintype;
    private String firstname;
    private String lastname;

    @Id
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    @Basic
    @Column(name = "USERTYPE",columnDefinition = "ENUM('HOST', 'GUEST')")
    public UserType getUsertype() {
        return usertype;
    }

    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }

    @Basic
    @Column(name = "LOGINTYPE",columnDefinition = "ENUM('REGULAR', 'FACEBOOK','GOOGLE','SJSU')")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(usertype, user.usertype) &&
                Objects.equals(logintype, user.logintype) &&
                Objects.equals(firstname, user.firstname) &&
                Objects.equals(lastname, user.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, usertype, logintype, firstname, lastname);
    }
}
