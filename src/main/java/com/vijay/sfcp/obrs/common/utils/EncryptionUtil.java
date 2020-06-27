package com.vijay.sfcp.obrs.common.utils;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 27 June 2020
*/

import com.vijay.sfcp.obrs.user.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {
    private static EncryptionService encryptionService;

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    public static String getEncryptedPassword(String password){
        return encryptionService.encryptString(password);
    }
}
