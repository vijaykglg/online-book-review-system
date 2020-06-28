package com.vijay.sfcp.obrs.review.entity;

/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.user.entity.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 * The primary key class for the review database table.
 */
@Embeddable
public class ReviewId implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Book book;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public ReviewId() {
    }

    public ReviewId(Book book, User user) {
        this.book = book;
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBook(), getUser());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewId)) return false;

        ReviewId that = (ReviewId) o;

        return Objects.equals(getBook(), that.getBook()) && Objects.equals(getUser(), that.getUser());
    }
}