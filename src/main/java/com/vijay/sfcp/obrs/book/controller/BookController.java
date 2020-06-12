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
import com.vijay.sfcp.obrs.common.service.SessionService;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    private BookService bookService;
    private AuthorService authorService;
    private CategoryService categoryService;
    private SessionService sessionService;
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
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showBooks(Model model, @RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "10") Integer size) {

        System.out.println("page = " + page + ", size = " + size);
        Pageable pageable = PageRequest.of(page,size, Sort.by("isbn").ascending());
        System.out.println(" pageable.getPageNumber() = " + pageable.getPageNumber()+" pageable.getPageSize() = " + pageable.getPageSize());
        model.addAttribute("category", new Category());
        model.addAttribute("author", new Author());

        Page<Book> pages = this.bookService.findAllPageWise(pageable);

        //CommonUtil.pageModel(Book.class,model, pages,"/book/");
        model.addAttribute("bookResult", pages.getContent());
        model.addAttribute("page", pages);

        List<Author> authorList = (List<Author>) this.authorService.findAll();
        model.addAttribute("authorList", authorList);

        List<Category> categoryList = (List<Category>) this.categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("book", new Book());
        return "book";
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

    @GetMapping("/searchBooks")
    public String searchBooks() {
        return "searchBooks";
    }

    @GetMapping("/byPublisher")
    public String showBooksByPublisher(@AuthenticationPrincipal UserDetailsImpl user, Model model, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        System.out.println("BookController.showBooksByPublisher - user = " + user + ", page = " + page + ", size = " + size);

        Pageable pageable = PageRequest.of(page,size, Sort.by("isbn").ascending());
        System.out.println(" pageable.getPageNumber() = " + pageable.getPageNumber()+" pageable.getPageSize() = " + pageable.getPageSize());
        model.addAttribute("category", new Category());
        model.addAttribute("author", new Author());
        /*System.out.println("BookController.showBooksByPublisher - sessionService = "+sessionService);
        List<String> allLoggedUsernames = sessionService.getAllLoggedUsernames();
        System.out.println("BookController.showBooksByPublisher - allLoggedUsernames = "+allLoggedUsernames);*/
        System.out.println("BookController.showBooksByPublisher - user.getUsername() = "+user.getUsername());
        User byUsername = this.userService.findByUsername(user.getUsername());
        Page<Book> pages = this.bookService.findAllByPublishers(pageable,byUsername);

        //CommonUtil.pageModel(Book.class,model, pages,"/book/");
        model.addAttribute("bookResult", pages.getContent());
        model.addAttribute("page", pages);

        List<Author> authorList = (List<Author>) this.authorService.findAll();
        model.addAttribute("authorList", authorList);

        List<Category> categoryList = (List<Category>) this.categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("book", new Book());
        return "book";
    }
}
