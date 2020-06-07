package com.vijay.sfcp.obrs.review.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import com.vijay.sfcp.obrs.common.service.CRUDService;
import com.vijay.sfcp.obrs.review.entity.Review;
import com.vijay.sfcp.obrs.review.entity.ReviewPK;

import java.util.List;

public interface ReviewService{
    List<?> listAll();
    Review getById(ReviewPK reviewPK);
    Review saveOrUpdate(Review domainObject);
    void delete(ReviewPK reviewPK);
}
