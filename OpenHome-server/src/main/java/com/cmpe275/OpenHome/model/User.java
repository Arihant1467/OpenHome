package com.cmpe275.OpenHome.model;


import javax.persistence.*;
import javax.xml.bind.SchemaOutputResolver;

@Entity(name = "users")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String userType;
    @Column
    private String loginType;

    public User(){
        System.out.println("In User constructor");
    }

    public User(String email, String password, String userType, String loginType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.loginType = loginType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}

