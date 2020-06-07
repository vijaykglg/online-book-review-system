package com.vijay.sfcp.obrs.review.entity;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.user.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Review {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ReviewPK id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private BigDecimal rating;

    @Column(name = "review_text")
    private String reviewText;

    //bi-directional many-to-one association to Book
    @ManyToOne
    @JoinColumn(name = "isbn", insertable = false, updatable = false)
    private Book book;

    //bi-directional many-to-one association to User
    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private User user;

    public Review() {
    }

    public ReviewPK getId() {
        return this.id;
    }

    public void setId(ReviewPK id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getRating() {
        return this.rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return this.reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
