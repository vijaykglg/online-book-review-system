package com.vijay.sfcp.obrs.book.repository;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 16 June 2020
*/

import com.vijay.sfcp.obrs.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookSearchRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
}
