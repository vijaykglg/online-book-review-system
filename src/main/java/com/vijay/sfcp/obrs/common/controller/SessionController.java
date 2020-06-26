package com.vijay.sfcp.obrs.common.controller;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 13 June 2020
*/

import com.vijay.sfcp.obrs.common.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final String CLASS_NAME = this.getClass().getName();

    @GetMapping("/invalidSession")
    public String invalidSession() {
        LogUtil.logError(LOG,CLASS_NAME,"invalidSession","This is an invalid session. Please refresh or login again.");
        return "/error/invalidSession";
    }

    @GetMapping("/sessionExpired")
    public String sessionExpired() {
        LogUtil.logError(LOG,CLASS_NAME,"sessionExpired","Session expired.");
        return "/error/sessionExpired";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        LogUtil.logError(LOG,CLASS_NAME,"accessDenied","Access Denied.");
        return "/error/403";
    }

}
