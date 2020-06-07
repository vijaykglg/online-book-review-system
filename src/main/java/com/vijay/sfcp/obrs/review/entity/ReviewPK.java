package com.vijay.sfcp.obrs.review.entity;

/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the review database table.
 */
@Embeddable
public class ReviewPK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "id", insertable = false, updatable = false)
    private Integer user_pk;

    @Column(name = "isbn",insertable = false, updatable = false)
    private String book_pk;

    public ReviewPK() {
    }

    public ReviewPK(Integer user_pk, String book_pk) {
        this.user_pk = user_pk;
        this.book_pk = book_pk;
    }

    public Integer getUser_pk() {
        return user_pk;
    }

    public void setUser_pk(Integer user_pk) {
        this.user_pk = user_pk;
    }

    public String getBook_pk() {
        return book_pk;
    }

    public void setBook_pk(String book_pk) {
        this.book_pk = book_pk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewPK)) return false;

        ReviewPK reviewPK = (ReviewPK) o;

        if (getUser_pk() != null ? !getUser_pk().equals(reviewPK.getUser_pk()) : reviewPK.getUser_pk() != null)
            return false;
        return getBook_pk() != null ? getBook_pk().equals(reviewPK.getBook_pk()) : reviewPK.getBook_pk() == null;
    }

    @Override
    public int hashCode() {
        int result = getUser_pk() != null ? getUser_pk().hashCode() : 0;
        result = 31 * result + (getBook_pk() != null ? getBook_pk().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReviewPK{" +
                "user_pk='" + user_pk + '\'' +
                ", book_pk='" + book_pk + '\'' +
                '}';
    }
}