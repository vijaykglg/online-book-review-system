package com.vijay.sfcp.obrs.config.security;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 12 June 2020
*/

import com.vijay.sfcp.obrs.common.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessDeniedHandlerCustomImpl implements AccessDeniedHandler {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final String CLASS_NAME = this.getClass().getName();

    private String errorPage;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        LogUtil.logDebug(LOG, CLASS_NAME, "handle", "errorPage = " + errorPage);
        LogUtil.logDebug(LOG, CLASS_NAME, "handle", "request.getContextPath() = "+request.getContextPath());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String userName = (auth == null) ? "anonymous" : auth.getName();
        LogUtil.logDebug(LOG, CLASS_NAME, "handle", "User: " + userName + " attempted unauthorized access to URL: " + request.getRequestURL() + " . message: " + accessDeniedException.getMessage());
        LogUtil.logDebug(LOG, CLASS_NAME, "handle", "isCommitted = "+response.isCommitted());
        if (!response.isCommitted()) {
            if (errorPage != null) {
                // Set the 403 status code.
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);

                // forward to error page.
                RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath()+errorPage);
                dispatcher.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN,
                        accessDeniedException.getMessage());
            }
        }
    }

    public void setErrorPage(String errorPage) {
        if ((errorPage != null) && !errorPage.startsWith("/")) {
            throw new IllegalArgumentException("errorPage must begin with '/'");
        }
        LogUtil.logDebug(LOG, CLASS_NAME, "setErrorPage", "errorPage = " + errorPage);
        this.errorPage = errorPage;
    }
}
