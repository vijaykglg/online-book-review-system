package com.vijay.sfcp.obrs.review.service;

import com.vijay.sfcp.obrs.review.entity.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceImplTest {
    @Autowired
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testFindReviewsByBook_Title() {
        /*List<Review> book_xyz7 = reviewService.findReviewsByBook("BOOK_XYZ7");
        book_xyz7.forEach(review -> System.out.println(review.getReviewText()));*/
    }
}