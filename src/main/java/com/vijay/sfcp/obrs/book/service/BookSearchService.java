package com.vijay.sfcp.obrs.book.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 16 June 2020
*/

import com.vijay.sfcp.obrs.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookSearchService {
    Page<Book> searchBooks(String searchKey, Pageable pageable);
}
