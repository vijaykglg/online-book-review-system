package com.vijay.sfcp.obrs.error.exceptions;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 22 June 2020
*/

import com.vijay.sfcp.obrs.common.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileStorageException extends RuntimeException{
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final String CLASS_NAME = this.getClass().getName();


    private static final long serialVersionUID = 1L;
    private String msg;

    public FileStorageException(String msg)
    {
        LogUtil.logDebug(LOG,CLASS_NAME,"FileStorageException","msg = " + msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
