package com.vijay.sfcp.obrs.author.controller;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 31 May 2020
*/

import com.vijay.sfcp.obrs.author.entity.Author;
import com.vijay.sfcp.obrs.author.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String showAuthors(Model model) {
        List<Author> AuthorList = (List<Author>) this.authorService.findAll();
        model.addAttribute("authorList", AuthorList);
        return "author";
    }

    @PostMapping("/save")
    public String save(Author country) {
        this.authorService.saveOrUpdate(country);
        return "redirect:/author/";
    }

    @GetMapping("/delete")
    public String delete(Integer id) {
        this.authorService.deleteById(id);
        return "redirect:/author/";
    }

    @GetMapping("/{id}")
    public String getAuthor(@PathVariable("id") Integer id, Model model) {
        Author author = this.authorService.findById(id);
        model.addAttribute("author", author);
        return "author";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public Author getAuthor(Integer id) {
        return this.authorService.findById(id);
    }
}
