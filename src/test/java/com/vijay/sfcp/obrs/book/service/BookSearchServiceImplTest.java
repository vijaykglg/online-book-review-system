package com.vijay.sfcp.obrs.book.service;

import com.vijay.sfcp.obrs.book.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookSearchServiceImplTest {
    @Autowired
    private BookSearchService bookSearchService;
    Pageable pageable = null;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0,10, Sort.by("isbn").ascending());
    }

    @Test
    void searchBooksByTitle() {
        Page<Book> searchBooks = bookSearchService.searchBooks("book",pageable);
        searchBooks.forEach(book -> System.out.println(book.getTitle()));
        assertEquals(searchBooks.getTotalElements(),20);
    }
}