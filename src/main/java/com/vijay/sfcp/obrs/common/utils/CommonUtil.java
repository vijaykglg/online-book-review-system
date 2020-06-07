package com.vijay.sfcp.obrs.common.utils;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 07 June 2020
*/

import com.vijay.sfcp.obrs.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public class CommonUtil {
    public static <T, x> void pageModel(T x, Model model, Page<?> pages,String url) {
        PageWrapper<x> page = new PageWrapper(pages, url);
        model.addAttribute("page", page);
    }
}
