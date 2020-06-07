package com.vijay.sfcp.obrs.publisher.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import com.vijay.sfcp.obrs.author.entity.Author;
import com.vijay.sfcp.obrs.error.exceptions.NotFoundException;
import com.vijay.sfcp.obrs.publisher.entity.Publisher;
import com.vijay.sfcp.obrs.publisher.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<?> findAll() {
        List<Publisher> publishers = new ArrayList<>();
        this.publisherRepository.findAll().forEach(publishers::add); //fun with Java 8
        return publishers;
    }

    @Override
    public Publisher findById(Integer id) throws NotFoundException {
        return this.publisherRepository.findById(id).orElseThrow(()-> new NotFoundException("Publisher Not Found"));
    }

    @Override
    public Publisher saveOrUpdate(Publisher domainObject) {
        return this.publisherRepository.save(domainObject);
    }

    @Override
    public void deleteById(Integer id) {
        this.publisherRepository.deleteById(id);
    }
}
