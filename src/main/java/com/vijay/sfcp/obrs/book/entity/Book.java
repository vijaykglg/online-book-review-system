package com.vijay.sfcp.obrs.book.entity;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import com.vijay.sfcp.obrs.author.entity.Author;
import com.vijay.sfcp.obrs.category.entity.Category;
import com.vijay.sfcp.obrs.common.entity.AbstractEntityClass;
import com.vijay.sfcp.obrs.publisher.entity.Publisher;
import com.vijay.sfcp.obrs.review.entity.Review;
import com.vijay.sfcp.obrs.user.entity.User;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
@DynamicUpdate
public class Book extends AbstractEntityClass implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "isbn", unique = true, nullable = false, updatable = false)
    String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "release_date", nullable = false, updatable = false)
    private String releaseDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "published_by", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "publisher_id", referencedColumnName = "id"))
    private Set<User> publishers = new HashSet<User>();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", updatable = false)
    private Author author;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    //bi-directional many-to-one association to Review
    @OneToMany(mappedBy = "book")
    private Set<Review> reviews = new HashSet<>();

    public Book() {
    }

    /*public Book(String isbn, String title, Set<Publisher> publishers, Author author, Category category, Set<Review> reviews) {
        this.isbn = isbn;
        this.title = title;
        this.publishers = publishers;
        this.author = author;
        this.category = category;
        this.reviews = reviews;
    }*/

    public Book(String isbn, String title, String releaseDate, Set<User> publishers, Author author, Category category, Set<Review> reviews) {
        this.isbn = isbn;
        this.title = title;
        this.releaseDate = releaseDate;
        this.publishers = publishers;
        this.author = author;
        this.category = category;
        this.reviews = reviews;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Set<User> getPublishers() {
        return publishers;
    }

    public void setPublishers(Set<User> publishers) {
        this.publishers = publishers;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setBook(this);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
        review.setBook(null);
    }

    public void addPublisher(User publisher) {
        this.publishers.add(publisher);
        publisher.getBooks().add(this);
    }

    public void removePublisher(Publisher publisher) {
        this.publishers.remove(publisher);
        publisher.getBooks().add(null);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn=" + isbn +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", publishers=" + publishers +
                ", author=" + author +
                ", category=" + category +
                ", reviews=" + reviews +
                '}';
    }
}
