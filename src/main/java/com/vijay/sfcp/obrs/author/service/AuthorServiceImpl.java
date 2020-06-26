package com.vijay.sfcp.obrs.author.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import com.vijay.sfcp.obrs.author.entity.Author;
import com.vijay.sfcp.obrs.author.repository.AuthorRepository;
import com.vijay.sfcp.obrs.error.exceptions.NotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("springdatajpa")
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<?> findAll() {
        List<Author> authors = new ArrayList<>();
        this.authorRepository.findAll().forEach(authors::add); //fun with Java 8
        return authors;
    }

    @Override
    public Author findById(Integer id) {
        return this.authorRepository.findById(id).orElseThrow(()-> new NotFoundException("Author Not Found"));
    }

    @Override
    public Author saveOrUpdate(Author domainObject) {
        return this.authorRepository.save(domainObject);
    }

    @Override
    public void deleteById(Integer id) {
        this.authorRepository.deleteById(id);
    }
}
