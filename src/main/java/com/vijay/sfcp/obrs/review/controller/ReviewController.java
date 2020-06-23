package com.vijay.sfcp.obrs.review.controller;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 19 June 2020
*/

import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.book.service.BookService;
import com.vijay.sfcp.obrs.common.utils.CommonUtil;
import com.vijay.sfcp.obrs.review.entity.Review;
import com.vijay.sfcp.obrs.review.entity.ReviewId;
import com.vijay.sfcp.obrs.review.service.ReviewService;
import com.vijay.sfcp.obrs.user.entity.User;
import com.vijay.sfcp.obrs.user.security.UserDetailsImpl;
import com.vijay.sfcp.obrs.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {

    private ReviewService reviewService;
    private BookService bookService;
    private UserService userService;

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/id")
    public String findReviewsByBook(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam(defaultValue = "0") Integer id, Model model) {
        System.out.println("ReviewController.findReviewsByBook - user = " + user + ", id = " + id);
        System.out.println("ReviewController.findReviewsByBook - user.getUsername() = "+user.getUsername());
        Book bookDetail = this.bookService.findById(id);
        System.out.println("ReviewController.findReviewsByBook - bookDetail = "+bookDetail);

        User byUsername = this.userService.findByUsername(user.getUsername());

        List<Review> allById = this.reviewService.findReviewsByBook(bookDetail);
        CommonUtil.ratingCalculate(allById,model);

        model.addAttribute("bookDetail", bookDetail);
        model.addAttribute("reviews", allById);
        return "bookDetail";
    }

    @GetMapping("/isbn")
    public String findReviewsByBook_Isbn(@RequestParam(defaultValue = "0") String isbn, Model model) {
        List<Review> reviewsByBook_isbn = this.reviewService.findReviewsByBook_Isbn(isbn);

        model.addAttribute("bookDetail", new Book());
        model.addAttribute("reviews", reviewsByBook_isbn);

        return "redirect:/book/bookDetail";
    }

    @GetMapping("/title")
    public String findReviewsByBook_Title(@RequestParam(defaultValue = "0") String title, Model model) {
        List<Review> reviewsByBook_title = this.reviewService.findReviewsByBook_Title(title);
        model.addAttribute("bookDetail", new Book());
        model.addAttribute("reviews", reviewsByBook_title);

        return "redirect:/book/bookDetail";
    }

    @PostMapping("/save")
    public String save(@AuthenticationPrincipal UserDetailsImpl user,@RequestParam("bookId") Integer bookId,@ModelAttribute("review") Review review,Model model) {
        System.out.println("ReviewController.save - user = " + user.getUsername() + ", bookId = " + bookId + ", review = " + review);
        Book book = this.bookService.findById(bookId);
        User byUsername = this.userService.findByUsername(user.getUsername());

        ReviewId reviewId = new ReviewId(book,byUsername);
        review.setId(reviewId);
        this.reviewService.saveOrUpdate(review);

        return "redirect:/book/bookDetail/?id="+bookId;
    }

    @GetMapping("/delete")
    public String delete(ReviewId reviewId) {
        this.reviewService.delete(reviewId);
        return "redirect:/book/bookDetail";
    }
}
