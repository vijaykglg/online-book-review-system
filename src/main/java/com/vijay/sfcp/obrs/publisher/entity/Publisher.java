package com.vijay.sfcp.obrs.publisher.entity;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.common.entity.AbstractEntityClass;
import com.vijay.sfcp.obrs.review.entity.Review;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "publisher")
public class Publisher extends AbstractEntityClass {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "publishers")//mappedBy = "publishers" refers to the 'publishers' property in Book Class
    private Set<Book> books;

    public Publisher() {
    }

    public Publisher(String name, String description, Set<Book> books) {
        this.name = name;
        this.description = description;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    /*public Book addBook(Book book) {
        getBooks().add(book);
        book.setPublishers(this);
        return book;
    }

    public Book removeReview(Book book) {
        getBooks().remove(book);
        book.setPublishers(this);
        return book;
    }*/

    @Override
    public String toString() {
        return "Publisher{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", books=" + books +
                '}';
    }
}
