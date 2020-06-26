package com.vijay.sfcp.obrs.review.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.review.entity.Review;
import com.vijay.sfcp.obrs.review.entity.ReviewId;
import com.vijay.sfcp.obrs.user.entity.User;

import java.util.List;

public interface ReviewService{
    List<Review> findAll();
    Review findById(ReviewId reviewId);
    Review saveOrUpdate(Review domainObject);
    void delete(ReviewId reviewId);

    List<Review> findReviewsByBook(Book book);

    List<Review> findAllById(Book book, User user);
}
