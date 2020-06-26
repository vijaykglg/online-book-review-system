package com.vijay.sfcp.obrs.common.controller;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/
import com.vijay.sfcp.obrs.common.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final String CLASS_NAME = this.getClass().getName();

    @GetMapping("/login")
    public String login() {
        LogUtil.logDebug(LOG,CLASS_NAME,"login","Login Flow Initialized.");
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        LogUtil.logDebug(LOG,CLASS_NAME,"home","Redirecting to home");
        return "home";
    }
}
