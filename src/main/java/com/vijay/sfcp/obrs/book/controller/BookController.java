package com.vijay.sfcp.obrs.book.controller;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/

import com.vijay.sfcp.obrs.author.entity.Author;
import com.vijay.sfcp.obrs.author.service.AuthorService;
import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.book.service.BookService;
import com.vijay.sfcp.obrs.category.entity.Category;
import com.vijay.sfcp.obrs.category.service.CategoryService;
import com.vijay.sfcp.obrs.common.service.StorageService;
import com.vijay.sfcp.obrs.common.utils.CommonUtil;
import com.vijay.sfcp.obrs.common.utils.LogUtil;
import com.vijay.sfcp.obrs.review.entity.Review;
import com.vijay.sfcp.obrs.review.service.ReviewService;
import com.vijay.sfcp.obrs.user.entity.User;
import com.vijay.sfcp.obrs.user.security.UserDetailsImpl;
import com.vijay.sfcp.obrs.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final String CLASS_NAME = this.getClass().getName();

    private BookService bookService;
    private AuthorService authorService;
    private CategoryService categoryService;
    private UserService userService;
    private ReviewService reviewService;
    private StorageService storageService;

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

    @Autowired
    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String showBooks(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, Model model) {
        LogUtil.logDebug(LOG,CLASS_NAME,"showBooks","page = " + page + ", size = " + size);

        Pageable pageable = PageRequest.of(page,size, Sort.by("isbn").ascending());
        LogUtil.logDebug(LOG,CLASS_NAME,"showBooks"," pageable.getPageNumber() = " + pageable.getPageNumber()+" pageable.getPageSize() = " + pageable.getPageSize());

        Page<Book> pages = this.bookService.findAllPageWise(pageable);

        model.addAttribute("bookResult", pages.getContent());
        model.addAttribute("page", pages);

        List<Category> categoryList = (List<Category>) this.categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        return "home";
    }

    @PostMapping("/save")
    public String save(@AuthenticationPrincipal UserDetailsImpl user, @Valid @ModelAttribute("book") Book book, @Valid String author, @Valid String category, BindingResult bindingResult, Model model) {
        LogUtil.logDebug(LOG,CLASS_NAME,"save","user.getUsername() = "+user.getUsername());
        User byUsername = this.userService.findByUsername(user.getUsername());
        LogUtil.logDebug(LOG,CLASS_NAME,"save","byUsername.toString() = "+byUsername.toString());

        book.setPublishers(new HashSet<>(Arrays.asList(byUsername)));

        String filename = this.storageService.store(book.getIsbn(),book.getImage());
        LogUtil.logDebug(LOG,CLASS_NAME,"save","filename = " + filename);

        if(!StringUtils.isEmpty(filename))
            book.setBookImage(filename);

        if (bindingResult.hasErrors()) {
            LogUtil.logError(LOG,CLASS_NAME,"save","bindingResult = " + bindingResult.getAllErrors().toString());
            return "book";
        }

        this.bookService.saveOrUpdate(book);
        return "redirect:/book/byPublisher";
    }

    @GetMapping("/delete")
    public String delete(Integer id) {
        this.bookService.deleteById(id);
        return "redirect:/book/byPublisher";
    }

    @GetMapping("/bookDetail")
    public String bookDetail(@RequestParam("id") Integer id, Model model) {
        LogUtil.logDebug(LOG,CLASS_NAME,"bookDetail","id = " + id);
        Book bookDetail = this.bookService.findById(id);

        List<Review> allById = this.reviewService.findReviewsByBook(bookDetail);
//        allById.forEach(review -> System.out.println(review));
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
        LogUtil.logDebug(LOG,CLASS_NAME,"showBooksByPublisher","user = " + user + ", page = " + page + ", size = " + size);
        Pageable pageable = PageRequest.of(page,size, Sort.by("isbn").ascending());

        model.addAttribute("category", new Category());
        model.addAttribute("author", new Author());

        LogUtil.logDebug(LOG,CLASS_NAME,"showBooksByPublisher","user.getUsername() = " + user.getUsername());
        User byUsername = this.userService.findByUsername(user.getUsername());

        Page<Book> pages = this.bookService.findAllByPublishers(byUsername,pageable);
        LogUtil.logDebug(LOG,CLASS_NAME,"showBooksByPublisher"," pages.getNumber() = " +pages.getNumber()+" pages.getTotalPages() = " + pages.getTotalPages()+" pages.getTotalElements() = " + pages.getTotalElements());

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
    public String showBooksByCategory(@RequestParam(defaultValue = "all") String category,@RequestParam(defaultValue = "home") String action, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, Model model) {
        LogUtil.logDebug(LOG,CLASS_NAME,"showBooksByCategory","page = "+page+" size = "+size+" category = "+category);

        Page<Book> pages = null;

        Pageable pageable = PageRequest.of(page,size, Sort.by("isbn").ascending());

        if(!StringUtils.isEmpty(category) && category.equalsIgnoreCase("all"))
            pages = this.bookService.findAllPageWise(pageable);
        else
            pages = this.bookService.findAllByCategory(category,pageable);

        LogUtil.logDebug(LOG,CLASS_NAME,"showBooksByCategory"," pages.getNumber() = " +pages.getNumber()+" pages.getTotalPages() = " + pages.getTotalPages()+" pages.getTotalElements() = " + pages.getTotalElements());

        model.addAttribute("bookResult", pages.getContent());
        model.addAttribute("page", pages);

        List<Category> categoryList = (List<Category>) this.categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("book", new Book());

        if(!StringUtils.isEmpty(action) && action.equalsIgnoreCase("register"))
            model.addAttribute("message","Welcome, You have successfully registered with Online Book review System. Please Login to continue.");

        return "home";
    }

    @GetMapping("/byAuthor")
    public String showBooksByAuthor(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer authorId, Model model) {
        LogUtil.logDebug(LOG,CLASS_NAME,"showBooksByCategory","page = "+page+" size = "+size+" authorId = "+authorId);

        Page<Book> pages = null;

        Pageable pageable = PageRequest.of(page,size, Sort.by("isbn").ascending());

        if(!StringUtils.isEmpty(authorId) && authorId == 0)
            pages = this.bookService.findAllPageWise(pageable);
        else
            pages = this.bookService.findAllByAuthor(authorId,pageable);

        LogUtil.logDebug(LOG,CLASS_NAME,"showBooksByAuthor"," pages.getNumber() = " +pages.getNumber()+" pages.getTotalPages() = " + pages.getTotalPages()+" pages.getTotalElements() = " + pages.getTotalElements());

        model.addAttribute("bookResult", pages.getContent());
        model.addAttribute("page", pages);
        model.addAttribute("authorId", authorId);
        return "booksByAuthor";
    }
}
