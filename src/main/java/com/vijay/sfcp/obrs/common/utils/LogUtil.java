package com.vijay.sfcp.obrs.common.utils;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 24 June 2020
*/

import org.slf4j.Logger;

public class LogUtil {
    public static void logInfo(Logger log, String className, String methodName, String infoMessage) {
        if (log.isInfoEnabled()) {
            log.info("{}.{}() - {}", className, methodName, infoMessage);
        }
    }

    public static void logDebug(Logger log, String className, String methodName, String debugMessage) {
        if (log.isDebugEnabled()) {
            log.debug("{}.{}() - {}", className, methodName, debugMessage);
        }
    }

    public static void logError(Logger log, String className, String methodName, String errorMessage) {
        if (log.isErrorEnabled()) {
            log.error("{}.{}() - ERROR - {}", className, methodName, errorMessage);
        }
    }

    public static void logError(Logger log, String className, String methodName, String errorMessage, Throwable throwable) {
        if (log.isErrorEnabled()) {
            log.error("{}.{}() - Exception Occurred - {}", className, methodName, errorMessage, throwable);
        }
    }

    public static void logTrace(Logger log, String className, String methodName, String traceMessage) {
        if (log.isTraceEnabled()) {
            log.trace("{}.{}() - {}", className, methodName, traceMessage);
        }
    }
}
