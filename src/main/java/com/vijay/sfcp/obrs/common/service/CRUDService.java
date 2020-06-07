package com.vijay.sfcp.obrs.common.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/
import com.vijay.sfcp.obrs.error.exceptions.NotFoundException;

import java.util.List;

public interface CRUDService<T> {
    List<?> findAll();
    T findById(Integer id) throws NotFoundException;
    T saveOrUpdate(T domainObject);
    void deleteById(Integer id);
}
