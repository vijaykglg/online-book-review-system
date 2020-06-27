package com.vijay.sfcp.obrs.book.entity;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vijay.sfcp.obrs.author.entity.Author;
import com.vijay.sfcp.obrs.category.entity.Category;
import com.vijay.sfcp.obrs.common.entity.AbstractEntityClass;
import com.vijay.sfcp.obrs.publisher.entity.Publisher;
import com.vijay.sfcp.obrs.user.entity.User;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
@DynamicUpdate
public class Book extends AbstractEntityClass implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false, updatable = false)
    String isbn;

    private String title;

    private String description;

    @Column(nullable = false, updatable = false)
    private String releaseDate;

    @Transient //@Column(name = "book_image")
    private MultipartFile image;

    private String bookImage;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "published_by", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "publisher_id", referencedColumnName = "id"))
    private Set<User> publishers = new HashSet<User>();

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id"/*, updatable = false*/)
    private Author author;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    public Book() {
    }

    public Book(String isbn, String title, String description, String releaseDate, String bookImage, Set<User> publishers, Author author, Category category) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.bookImage = bookImage;
        this.publishers = publishers;
        this.author = author;
        this.category = category;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
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

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", bookImage='" + bookImage + '\'' +
                ", publishers=" + publishers +
                ", author=" + author +
                ", category=" + category +
                '}';
    }
}
