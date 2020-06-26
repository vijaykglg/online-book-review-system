package com.vijay.sfcp.obrs.error;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/

import com.vijay.sfcp.obrs.common.utils.LogUtil;
import com.vijay.sfcp.obrs.error.exceptions.AlreadyExistsException;
import com.vijay.sfcp.obrs.error.exceptions.BadCredentialsException;
import com.vijay.sfcp.obrs.error.exceptions.DefaultRuntimeException;
import com.vijay.sfcp.obrs.error.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@ControllerAdvice
@Controller
public class ExceptionHandler extends SimpleMappingExceptionResolver {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final String CLASS_NAME = this.getClass().getName();

    /*@Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }*/

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(DefaultRuntimeException.class)
    public ModelAndView handleValidationExceptions(DefaultRuntimeException ex) {
        LogUtil.logError(LOG,CLASS_NAME,"handleValidationExceptions","BAD_REQUEST",ex);
        return new ModelAndView("error").addObject(ex.getErrors());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundExceptions(NotFoundException ex) {
        LogUtil.logError(LOG,CLASS_NAME,"handleNotFoundExceptions","NOT_FOUND",ex);
        return new ModelAndView("error").addObject(ex.getErrors()).addObject(ex.getAdditionalMessages());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
    public ModelAndView handleBadCredentialsExceptions(BadCredentialsException ex) {
        LogUtil.logError(LOG,CLASS_NAME,"handleBadCredentialsExceptions","UNAUTHORIZED",ex);
        return new ModelAndView("error").addObject(ex.getErrorMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @org.springframework.web.bind.annotation.ExceptionHandler(AlreadyExistsException.class)
    public ModelAndView handleAlreadyExistsExceptions(AlreadyExistsException ex) {
        LogUtil.logError(LOG,CLASS_NAME,"handleAlreadyExistsExceptions","CONFLICT",ex);
        return new ModelAndView("error").addObject(ex.getErrorMessage());
    }
}
