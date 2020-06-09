package com.vijay.sfcp.obrs.common.controller;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        System.out.println("LoginController.login - /login");
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        System.out.println("LoginController.home - /home");
        return "home";
    }
}
