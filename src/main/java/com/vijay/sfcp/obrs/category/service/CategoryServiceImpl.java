package com.vijay.sfcp.obrs.category.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import com.vijay.sfcp.obrs.category.entity.Category;
import com.vijay.sfcp.obrs.category.repository.CategoryRepository;
import com.vijay.sfcp.obrs.error.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<?> findAll() {
        List<Category> categories = new ArrayList<>();
        this.categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public Category findById(Integer id) {
        return this.categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Category Not Found"));
    }

    @Override
    public Category saveOrUpdate(Category domainObject) {
        return this.categoryRepository.save(domainObject);
    }

    @Override
    public void deleteById(Integer id) {
        this.categoryRepository.deleteById(id);
    }
}
