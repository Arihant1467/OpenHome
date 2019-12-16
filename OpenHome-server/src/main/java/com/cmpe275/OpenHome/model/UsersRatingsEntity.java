package com.cmpe275.OpenHome.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "usersratings", schema = "Openhome", catalog = "")
public class UsersRatingsEntity {
    private Integer ratingId;
    private String hostId;
    private String userId;
    private Integer rating;
    private String review;

    @Id
    @Column(name = "RATING_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getRatingId() {
        return ratingId;
    }

    public void setRatingId(Integer ratingId) {
        this.ratingId = ratingId;
    }

    @Basic
    @Column(name = "HOST_ID")
    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    @Basic
    @Column(name = "USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "RATING")
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "REVIEW")
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersRatingsEntity that = (UsersRatingsEntity) o;
        return Objects.equals(ratingId, that.ratingId) &&
                Objects.equals(hostId, that.hostId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(rating, that.rating) &&
                Objects.equals(review, that.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ratingId, hostId, userId, rating, review);
    }
}
