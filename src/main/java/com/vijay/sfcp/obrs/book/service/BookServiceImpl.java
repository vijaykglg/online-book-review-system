package com.vijay.sfcp.obrs.book.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.book.repository.BookRepository;
import com.vijay.sfcp.obrs.error.exceptions.NotFoundException;
import com.vijay.sfcp.obrs.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<?> findAll() {
        List<Book> books = new ArrayList<>();
        this.bookRepository.findAll().forEach(books::add); //fun with Java 8
        return books;
    }

    @Override
    public Book findById(Integer id) {
        return this.bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book Not Found"));
    }

    @Override
    public Book saveOrUpdate(Book domainObject) {
        return this.bookRepository.save(domainObject);
    }

    @Override
    public void deleteById(Integer id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public Page<Book> findAllPageWise(Pageable pageable) {
        return this.bookRepository.findAll(pageable);
    }

    @Override
    public Page<Book> findAllByPublishers(User publisher, Pageable pageable) {
        return this.bookRepository.findBooksByPublishers(pageable, publisher);
    }

    @Override
    public Page<Book> findAllByCategory(String category, Pageable pageable) {
        return this.bookRepository.findAllByCategoryName(category, pageable);
    }

    @Override
    public Page<Book> findAllByAuthor(String author, Pageable pageable) {
        return this.bookRepository.findAllByAuthorName(author,pageable);
    }

    @Override
    public Page<Book> findAllByAuthor(Integer authorId, Pageable pageable) {
        return this.bookRepository.findAllByAuthorId(authorId,pageable);
    }
}
