package com.vijay.sfcp.obrs.book.service;

import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.user.entity.User;
import com.vijay.sfcp.obrs.user.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceImplTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    User publisher = null;
    Pageable pageable = null;

    @BeforeEach
    void setup(){
        pageable = PageRequest.of(0,10, Sort.by("isbn").ascending());
        publisher = userService.findByUsername("publisher");
        System.out.println("BookController.showBooksByPublisher - byUsername.toString() = "+publisher.toString());
    }

    @Test
    void findAll() {
        List<Book> books = (List<Book>) bookService.findAll();
        assertEquals(books.size(),20);
    }

    @Test
    void findAllByPublishers() {
        Page<Book> allByPublishers = bookService.findAllByPublishers(publisher,pageable);
        assertEquals(allByPublishers.getTotalElements(),10);
        allByPublishers.forEach(book -> System.out.println(book.getTitle().startsWith("BOOK_XYZ")?"TRUE":"FALSE"));

    }
}