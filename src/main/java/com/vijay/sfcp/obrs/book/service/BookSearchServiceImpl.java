package com.vijay.sfcp.obrs.book.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 16 June 2020
*/

import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.book.repository.BookSearchRepository;
import com.vijay.sfcp.obrs.book.specification.BookSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookSearchServiceImpl implements BookSearchService {
    private final BookSearchRepository bookSearchRepository;
    private final BookSpecification bookSpecification;

    public BookSearchServiceImpl(BookSearchRepository bookSearchRepository, BookSpecification bookSpecification) {
        this.bookSearchRepository = bookSearchRepository;
        this.bookSpecification = bookSpecification;
    }

    @Override
    public Page<Book> searchBooks(String searchKey, Pageable pageable) {
        return this.bookSearchRepository.findAll(bookSpecification.getFilter(searchKey),pageable);
    }
}
