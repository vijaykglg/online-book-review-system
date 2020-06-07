package com.vijay.sfcp.obrs.review.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import com.vijay.sfcp.obrs.review.entity.Review;
import com.vijay.sfcp.obrs.review.entity.ReviewPK;
import com.vijay.sfcp.obrs.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<?> listAll() {
        List<Review> reviews = new ArrayList<>();
        this.reviewRepository.findAll().forEach(reviews::add);
        return reviews;
    }

    @Override
    public Review getById(ReviewPK reviewPK) {
        return this.reviewRepository.getOne(reviewPK);
    }

    @Override
    public Review saveOrUpdate(Review domainObject) {
        return this.reviewRepository.save(domainObject);
    }

    @Override
    public void delete(ReviewPK reviewPK) {
        this.reviewRepository.deleteById(reviewPK);
    }
}
