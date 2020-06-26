package com.vijay.sfcp.obrs.book.specification;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 15 June 2020
*/

import com.vijay.sfcp.obrs.author.entity.Author;
import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.category.entity.Category;
import com.vijay.sfcp.obrs.common.utils.LogUtil;
import com.vijay.sfcp.obrs.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.SetJoin;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class BookSpecification extends BaseSpecification {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final String CLASS_NAME = this.getClass().getName();

    @Override
    public Specification<Book> getFilter(String search) {
        LogUtil.logDebug(LOG,CLASS_NAME,"getFilter","search = " + search);

        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the addressAttribute specifications
            return where(titleContains(search))
                    .or(isbnContains(search))
                    .or(descriptionContains(search))
                    .or(authorNameContains(search))
                    .or(authorDescriptionContains(search))
                    .or(categoryNameContains(search))
                    .or(categoryDescriptionContains(search))
                    .or(publisherFirstNameContains(search))
                    .or(publisherLastNameContains(search))
                    .or(publisherDescriptionContains(search))
                    .toPredicate(root, query, cb);
        };

    }

    private Specification<Book> titleContains(String title) {
        return bookAttributeContains("title", title);
    }

    private Specification<Book> isbnContains(String isbn) {
        return bookAttributeContains("isbn", isbn);
    }

    private Specification<Book> descriptionContains(String description) {
        return bookAttributeContains("description", description);
    }

    private Specification<Book> bookAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.like(
                    cb.lower(root.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }

    private Specification<Book> authorNameContains(String name) {
        return authorAttributeContains("name", name);
    }

    private Specification<Book> authorDescriptionContains(String description) {
        return authorAttributeContains("description", description);
    }

    private Specification<Book> authorAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            Join<Book, Author> author = root.join("author", JoinType.LEFT);

            return cb.like(
                    cb.lower(author.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }

    private Specification<Book> categoryNameContains(String name) {
        return categoryAttributeContains("name", name);
    }

    private Specification<Book> categoryDescriptionContains(String description) {
        return categoryAttributeContains("description", description);
    }

    private Specification<Book> categoryAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            Join<Book, Category> category = root.join("category", JoinType.LEFT);

            return cb.like(
                    cb.lower(category.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }

    private Specification<Book> publisherFirstNameContains(String firstName) {
        return publisherAttributeContains("firstName", firstName);
    }

    private Specification<Book> publisherLastNameContains(String lastName) {
        return publisherAttributeContains("lastName", lastName);
    }

    private Specification<Book> publisherDescriptionContains(String description) {
        return publisherAttributeContains("description", description);
    }

    private Specification<Book> publisherAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            SetJoin<Book, User> publishers = root.joinSet("publishers", JoinType.LEFT);

            return cb.like(
                    cb.lower(publishers.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }
}
