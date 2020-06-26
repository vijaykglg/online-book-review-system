package com.vijay.sfcp.obrs.review.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.book.service.BookService;
import com.vijay.sfcp.obrs.review.entity.Review;
import com.vijay.sfcp.obrs.review.entity.ReviewId;
import com.vijay.sfcp.obrs.review.repository.ReviewRepository;
import com.vijay.sfcp.obrs.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> findAll() {
        List<Review> reviews = new ArrayList<>();
        this.reviewRepository.findAll().forEach(reviews::add);
        return reviews;
    }

    @Override
    public Review findById(ReviewId reviewId) {
        return this.reviewRepository.getOne(reviewId);
    }

    @Override
    public Review saveOrUpdate(Review domainObject) {
        return this.reviewRepository.save(domainObject);
    }

    @Override
    public void delete(ReviewId reviewId) {
        this.reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<Review> findReviewsByBook(Book book){
        List<Review> reviews = new ArrayList<>();
        this.reviewRepository.findReviewsById_Book(book).forEach(reviews::add);
        return reviews;
    }

    @Override
    public List<Review> findAllById(Book book, User user){
        List<Review> reviews = new ArrayList<>();
        this.reviewRepository.findAllById(new ReviewId(book,user)).forEach(reviews::add);
        return reviews;
    }
}
