package com.vijay.sfcp.obrs.category.controller;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 01 June 2020
*/

import com.vijay.sfcp.obrs.category.entity.Category;
import com.vijay.sfcp.obrs.category.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String showCategory(Model model) {
        List<Category> categoryList = (List<Category>) this.categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        return "category";
    }

    @PostMapping("/save")
    public String save(Category country) {
        this.categoryService.saveOrUpdate(country);
        return "redirect:/category/";
    }

    @GetMapping("/delete")
    public String delete(Integer id) {
        this.categoryService.deleteById(id);
        return "redirect:/category/";
    }

    @GetMapping("/{id}")
    public String getCategory(@PathVariable("id") Integer id, Model model) {
        Category category = this.categoryService.findById(id);
        model.addAttribute("category", category);
        return "category";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public Category getCategory(Integer id) {
        return this.categoryService.findById(id);
    }
}
