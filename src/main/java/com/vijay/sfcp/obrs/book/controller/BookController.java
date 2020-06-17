package com.vijay.sfcp.obrs.book.controller;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/

import com.vijay.sfcp.obrs.author.entity.Author;
import com.vijay.sfcp.obrs.author.services.AuthorService;
import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.book.service.BookService;
import com.vijay.sfcp.obrs.category.entity.Category;
import com.vijay.sfcp.obrs.category.service.CategoryService;
import com.vijay.sfcp.obrs.user.entity.User;
import com.vijay.sfcp.obrs.user.security.UserDetailsImpl;
import com.vijay.sfcp.obrs.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    private BookService bookService;
    private AuthorService authorService;
    private CategoryService categoryService;
    private UserService userService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showBooks(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "6") Integer size, Model model) {

        System.out.println("page = " + page + ", size = " + size);
        Pageable pageable = PageRequest.of(page,size, Sort.by("isbn").ascending());
        System.out.println(" pageable.getPageNumber() = " + pageable.getPageNumber()+" pageable.getPageSize() = " + pageable.getPageSize());

        Page<Book> pages = this.bookService.findAllPageWise(pageable);

        model.addAttribute("bookResult", pages.getContent());
        model.addAttribute("page", pages);

        List<Category> categoryList = (List<Category>) this.categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        return "home";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("book") Book book,@Valid String author,@Valid String category) {
        System.out.println("book = " + book.toString() + ", author = " + author + ", category = " + category);
        this.bookService.saveOrUpdate(book);
        return "redirect:/book/";
    }

    @GetMapping("/delete")
    public String delete(Integer id) {
        this.bookService.deleteById(id);
        return "redirect:/book/";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable("id") Integer id, Model model) {
        Book book = this.bookService.findById(id);
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public Book getBook(Integer id) {
        return this.bookService.findById(id);
    }

    @GetMapping("/byPublisher")
    public String showBooksByPublisher(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, Model model) {
        System.out.println("BookController.showBooksByPublisher - user = " + user + ", page = " + page + ", size = " + size);

        Pageable pageable = PageRequest.of(page,size, Sort.by("isbn").ascending());
        System.out.println(" pageable.getPageNumber() = " + pageable.getPageNumber()+" pageable.getPageSize() = " + pageable.getPageSize());

        model.addAttribute("category", new Category());
        model.addAttribute("author", new Author());

        System.out.println("BookController.showBooksByPublisher - user.getUsername() = "+user.getUsername());
        User byUsername = this.userService.findByUsername(user.getUsername());
        System.out.println("BookController.showBooksByPublisher - byUsername.toString() = "+byUsername.toString());
        Page<Book> pages = this.bookService.findAllByPublishers(byUsername,pageable);
        System.out.println(" pages.getNumber() = " +pages.getNumber()+" pages.getTotalPages() = " + pages.getTotalPages()+" pages.getTotalElements() = " + pages.getTotalElements());

        model.addAttribute("bookResult", pages.getContent());
        model.addAttribute("page", pages);

        List<Author> authorList = (List<Author>) this.authorService.findAll();
        model.addAttribute("authorList", authorList);

        List<Category> categoryList = (List<Category>) this.categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("book", new Book());
        return "book";
    }

    @GetMapping("/byCategory")
    public String showBooksByCategory(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "6") Integer size, @RequestParam(defaultValue = "all") String category, Model model) {
        System.out.println("BookController.showBooksByCategory - page = "+page+" size = "+size+" category = "+category);
        Page<Book> pages = null;

        Pageable pageable = PageRequest.of(page,size, Sort.by("isbn").ascending());
        System.out.println(" pageable.getPageNumber() = " + pageable.getPageNumber()+" pageable.getPageSize() = " + pageable.getPageSize());

        if(!StringUtils.isEmpty(category) && category.equalsIgnoreCase("all"))
            pages = this.bookService.findAllPageWise(pageable);
        else
            pages = this.bookService.findAllByCategory(category,pageable);

        System.out.println(" pages.getNumber() = " +pages.getNumber()+" pages.getTotalPages() = " + pages.getTotalPages()+" pages.getTotalElements() = " + pages.getTotalElements());

        model.addAttribute("bookResult", pages.getContent());
        model.addAttribute("page", pages);

        List<Category> categoryList = (List<Category>) this.categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("book", new Book());
        return "home";
    }


}
