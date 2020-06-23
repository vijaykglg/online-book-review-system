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
import com.vijay.sfcp.obrs.common.utils.CommonUtil;
import com.vijay.sfcp.obrs.review.entity.Review;
import com.vijay.sfcp.obrs.review.service.ReviewService;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    private BookService bookService;
    private AuthorService authorService;
    private CategoryService categoryService;
    private UserService userService;
    private ReviewService reviewService;

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

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/")
    public String showBooks(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, Model model) {

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
    public String save(@AuthenticationPrincipal UserDetailsImpl user,@ModelAttribute("book") Book book,@Valid String author,@Valid String category) {
        System.out.println("book = " + book.toString() + ", author = " + author + ", category = " + category);
        System.out.println("BookController.showBooksByPublisher - user.getUsername() = "+user.getUsername());
        User byUsername = this.userService.findByUsername(user.getUsername());
        System.out.println("BookController.showBooksByPublisher - byUsername.toString() = "+byUsername.toString());
        book.setPublishers(new HashSet<>(Arrays.asList(byUsername)));
        System.out.println("After Update - book = " + book.toString());
        this.bookService.saveOrUpdate(book);
        return "redirect:/book/byPublisher";
    }

    @GetMapping("/delete")
    public String delete(Integer id) {
        this.bookService.deleteById(id);
        return "redirect:/book/";
    }

    @GetMapping("/bookDetail")
    public String bookDetail(@RequestParam("id") Integer id, Model model) {
        System.out.println("BookController.bookDetail - id = " + id);
        Book bookDetail = this.bookService.findById(id);
        System.out.println("BookController - bookDetail() - bookDetail = " + bookDetail);

        List<Review> allById = this.reviewService.findReviewsByBook(bookDetail);
        allById.forEach(review -> System.out.println(review));
        CommonUtil.ratingCalculate(allById,model);

        model.addAttribute("bookDetail", bookDetail);
        model.addAttribute("reviews", allById);
        return "bookDetail";
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
    public String showBooksByCategory(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "all") String category, Model model) {
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

    @GetMapping("/byAuthor")
    public String showBooksByAuthor(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer authorId, Model model) {
        System.out.println("BookController.showBooksByAuthor - page = "+page+" size = "+size+" authorId = "+authorId);
        Page<Book> pages = null;

        Pageable pageable = PageRequest.of(page,size, Sort.by("isbn").ascending());
        System.out.println(" pageable.getPageNumber() = " + pageable.getPageNumber()+" pageable.getPageSize() = " + pageable.getPageSize());

        if(!StringUtils.isEmpty(authorId) && authorId == 0)
            pages = this.bookService.findAllPageWise(pageable);
        else
            pages = this.bookService.findAllByAuthor(authorId,pageable);

        System.out.println(" pages.getNumber() = " +pages.getNumber()+" pages.getTotalPages() = " + pages.getTotalPages()+" pages.getTotalElements() = " + pages.getTotalElements());

        model.addAttribute("bookResult", pages.getContent());
        model.addAttribute("page", pages);
        model.addAttribute("authorId", authorId);
        return "booksByAuthor";
    }
}
