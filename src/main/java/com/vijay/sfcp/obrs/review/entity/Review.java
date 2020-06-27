package com.vijay.sfcp.obrs.review.entity;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Review {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ReviewId id;

    private Date dateCreated;
    private Date lastUpdated;
    private Integer rating;
    private String reviewText;

    public Review() {
    }

    public ReviewId getId() {
        return this.id;
    }

    public void setId(ReviewId id) {
        this.id = id;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return this.reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        lastUpdated = new Date();
        if (dateCreated == null) {
            dateCreated = new Date();
        }
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ", rating=" + rating +
                ", reviewText='" + reviewText + '\'' +
                '}';
    }
}
