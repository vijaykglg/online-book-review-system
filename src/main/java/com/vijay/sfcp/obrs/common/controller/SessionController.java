package com.vijay.sfcp.obrs.common.controller;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 13 June 2020
*/

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionController {
    @GetMapping("/invalidSession")
    public String invalidSession() {
        return "/error/invalidSession";
    }

    @GetMapping("/sessionExpired")
    public String sessionExpired() {
        return "/error/sessionExpired";
    }

}
