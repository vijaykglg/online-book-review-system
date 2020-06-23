package com.vijay.sfcp.obrs.book.controller;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 16 June 2020
*/

import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.book.service.BookSearchService;
import com.vijay.sfcp.obrs.book.service.BookService;
import com.vijay.sfcp.obrs.category.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookSearchController {
    private BookSearchService bookSearchService;
    private BookService bookService;

    @Autowired
    public void setBookSearchService(BookSearchService bookSearchService) {
        this.bookSearchService = bookSearchService;
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/searchPage")
    public String searchBooks(Model model)
    {
        model.addAttribute("searchKey", "");
        model.addAttribute("bookResult", new ArrayList<Book>());
        return "bookSearch";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(defaultValue = "all") String searchKey, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,Model model) {

        System.out.println("BookSearchController.searchBooks - searchKey = "+searchKey+" page = " + page + ", size = " + size);
        System.out.println("BookSearchController.searchBooks - ");
        Pageable pageable = PageRequest.of(page,size, Sort.by("isbn").ascending());
        System.out.println(" pageable.getPageNumber() = " + pageable.getPageNumber()+" pageable.getPageSize() = " + pageable.getPageSize());
        Page<Book> pages = null;
        if(!StringUtils.isEmpty(searchKey) && !searchKey.equalsIgnoreCase("all"))
            pages = this.bookSearchService.searchBooks(searchKey,pageable);
        else
            pages = this.bookService.findAllPageWise(pageable);

        model.addAttribute("searchKey", searchKey);
        model.addAttribute("bookResult", pages.getContent());
        model.addAttribute("page", pages);

        return "bookSearch";
    }
}
