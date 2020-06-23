package com.vijay.sfcp.obrs.common.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 22 June 2020
*/

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UtilService {
    public boolean hasAnyRole(HttpServletRequest req, String... roles) {
        for (final String role : roles) {
            if (req.isUserInRole(role)) {
                return true;
            }
        }
        return false;
    }
}
