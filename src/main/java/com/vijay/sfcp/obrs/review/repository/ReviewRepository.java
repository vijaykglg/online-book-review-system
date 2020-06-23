package com.vijay.sfcp.obrs.review.repository;
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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
    List<Review> findReviewsById_Book(Book book);

    List<Review> findReviewsById_User(User user);

    List<Review> findReviewsById_Book_Id(Integer id);

    List<Review> findReviewsById_User_UserName(String userName);

    List<Review> findAllById(ReviewId reviewId);
}
