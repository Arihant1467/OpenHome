package com.cmpe275.OpenHome.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class UserPK implements Serializable {
    private Integer id;
    private String email;

    @Column(name = "ID")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "EMAIL")
    @Id
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPK userPK = (UserPK) o;
        return Objects.equals(id, userPK.id) &&
                Objects.equals(email, userPK.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
