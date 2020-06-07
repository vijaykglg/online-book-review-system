package com.vijay.sfcp.obrs.error.exceptions;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends DefaultRuntimeException{
    public NotFoundException() {
    }

    public NotFoundException(Map<String, String> parameters){
        super(parameters);
    }

    public NotFoundException(String errorMessages){
        super(errorMessages);
    }
}
