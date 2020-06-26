package com.vijay.sfcp.obrs.error.exceptions;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DefaultRuntimeException extends RuntimeException{
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final String CLASS_NAME = this.getClass().getName();

    private Map<String, String> errors;
    private List<String> additionalMessages;

    public DefaultRuntimeException() {
    }

    public DefaultRuntimeException(Map<String, String> errors) {
        this.errors = errors;
    }

    public DefaultRuntimeException(String errorMessage) {
        additionalMessages.add(errorMessage);
    }
    public DefaultRuntimeException(String parameter, String errorMessage){
        Map<String, String> errors = new HashMap<>();
        errors.put(parameter, errorMessage);
        this.errors = errors;
    }

    public DefaultRuntimeException(Map<String, String> errors, List<String> additionalMessages) {
        this.errors = errors;
        this.additionalMessages = additionalMessages;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public List<String> getAdditionalMessages() {
        return additionalMessages;
    }

    public void setAdditionalMessages(List<String> additionalMessages) {
        this.additionalMessages = additionalMessages;
    }
}
