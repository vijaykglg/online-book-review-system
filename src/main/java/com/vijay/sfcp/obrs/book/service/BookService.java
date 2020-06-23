package com.vijay.sfcp.obrs.book.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.common.service.CRUDService;
import com.vijay.sfcp.obrs.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService extends CRUDService<Book> {
    Page<Book> findAllPageWise(Pageable pageable);

    Page<Book> findAllByPublishers(User publisher, Pageable pageable);

    Page<Book> findAllByCategory(String category, Pageable pageable);

    Page<Book> findAllByAuthor(String category, Pageable pageable);

    Page<Book> findAllByAuthor(Integer authorId, Pageable pageable);
}
