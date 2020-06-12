package com.vijay.sfcp.obrs.common.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 11 June 2020
*/

import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SessionService {
    List<SessionInformation> getActiveSessions();

    User getUser(SessionInformation session);

    Authentication getSession(HttpServletRequest request);

    User getLoggedInUserFromSession(HttpServletRequest httpServletRequest);

    List<String> getAllLoggedUsernames();

    void expireUserSessions(String username);
}
