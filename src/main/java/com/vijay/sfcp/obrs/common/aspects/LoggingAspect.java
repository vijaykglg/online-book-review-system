package com.vijay.sfcp.obrs.common.aspects;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 23 June 2020
*/

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.stereotype.Controller *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.vijay.sfcp.obrs..*)" +
            " || within(com.vijay.sfcp.obrs.common.controller..*)" +
            " || within(com.vijay.sfcp.obrs.common.service..*)" +
            " || within(com.vijay.sfcp.obrs.common.repository..*)" +
            " || within(com.vijay.sfcp.obrs.error..*)" +
            " || within(com.vijay.sfcp.obrs.error.exceptions..*)" +
            " || within(com.vijay.sfcp.obrs.author.controller..*)" +
            " || within(com.vijay.sfcp.obrs.author.service..*)" +
            " || within(com.vijay.sfcp.obrs.author.repository..*)" +
            " || within(com.vijay.sfcp.obrs.book.controller..*)" +
            " || within(com.vijay.sfcp.obrs.book.service..*)" +
            " || within(com.vijay.sfcp.obrs.book.repository..*)" +
            " || within(com.vijay.sfcp.obrs.book.specification..*)" +
            " || within(com.vijay.sfcp.obrs.category.controller..*)" +
            " || within(com.vijay.sfcp.obrs.category.service..*)" +
            " || within(com.vijay.sfcp.obrs.category.repository..*)" +
            " || within(com.vijay.sfcp.obrs.review.controller..*)" +
            " || within(com.vijay.sfcp.obrs.review.service..*)" +
            " || within(com.vijay.sfcp.obrs.review.repository..*)" +
            " || within(com.vijay.sfcp.obrs.role.controller..*)" +
            " || within(com.vijay.sfcp.obrs.role.service..*)" +
            " || within(com.vijay.sfcp.obrs.role.repository..*)" +
            " || within(com.vijay.sfcp.obrs.user.controller..*)" +
            " || within(com.vijay.sfcp.obrs.user.service..*)" +
            " || within(com.vijay.sfcp.obrs.user.repository..*)" +
            " || within(com.vijay.sfcp.obrs.user.security..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Utility methods in the application's common packages.
     */
    @Pointcut("within(com.vijay.sfcp.obrs..*)" +
            " || within(com.vijay.sfcp.obrs.common.utils..*)")
    public void applicationPackageUtilPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Config methods in the application's config packages.
     */
    @Pointcut("within(com.vijay.sfcp.obrs..*)" +
            " || within(com.vijay.sfcp.obrs.config..*)"+
            " || within(com.vijay.sfcp.obrs.config.*..*)")
    public void applicationPackageConfigPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Config methods in the application's config packages.
     */
    @Pointcut("within(com.vijay.sfcp.obrs..*)" +
            " || within(com.vijay.sfcp.obrs.error..*)"+
            " || within(com.vijay.sfcp.obrs.error.*..*)")
    public void applicationPackageErrorPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut() && applicationPackageUtilPointcut() && applicationPackageConfigPointcut() && applicationPackageErrorPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut() && springBeanPointcut() && applicationPackageUtilPointcut() && applicationPackageConfigPointcut() && applicationPackageErrorPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();

        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}", className, methodName, Arrays.toString(joinPoint.getArgs()));
        }
        try {
            //Measure method execution time
            stopWatch.start();
            Object result = joinPoint.proceed();
            stopWatch.stop();

            //Log method execution time
            if (log.isDebugEnabled()) {
                log.debug("Execution Time of: {}.{}() :: {} ms", className, methodName, stopWatch.getTotalTimeMillis());
            }
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", className, methodName, result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()), className, methodName);
            throw e;
        }
    }
}
