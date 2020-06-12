package com.vijay.sfcp.obrs.common.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 11 June 2020
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public List<SessionInformation> getActiveSessions() {
        List<SessionInformation> activeSessions = new ArrayList<>();
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            activeSessions.addAll(sessionRegistry.getAllSessions(principal, false));
        }
        return activeSessions;
    }

    @Override
    public User getUser(SessionInformation session) {
        Object principalObj = session.getPrincipal();
        if (principalObj instanceof User) {
            User user = (User) principalObj;
            return user;
        }
        return null;
    }

    @Override
    public Authentication getSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SecurityContext ctx = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication auth = ctx.getAuthentication();
        return auth;
    }

    @Override
    public User getLoggedInUserFromSession(HttpServletRequest httpServletRequest) {
        User user = null;
        Authentication authentication = getSession(httpServletRequest);
        if (authentication instanceof User) {
            user = (User) authentication.getPrincipal();
            System.out.println("SessionServiceImpl.getLoggedInUser - " + user.getUsername());
            System.out.println("SessionServiceImpl.getLoggedInUser - " + user.toString());
        }
        return user;
    }

    @Override
    public List<String> getAllLoggedUsernames() {
        List<String> allUsernames = new ArrayList<String>();
        List<SessionInformation> activeUserSessions = getActiveSessions();

        List<SessionInformation> activeSessions = getActiveSessions();
        if (!activeUserSessions.isEmpty()) {
            activeSessions.forEach(item -> allUsernames.add(getUser(item).getUsername()));
            /*for (SessionInformation item : activeSessions) {
                User user = getUser(item);
                if (user != null) {
                    allUsernames.add(user.getUsername());
                }
            }*/
        }
        return allUsernames;
    }

    @Override
    public void expireUserSessions(String username) {
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof User) {
                User user = (User) principal;
                if (user.getUsername().equals(username)) {
                    for (SessionInformation information : sessionRegistry.getAllSessions(user, true)) {
                        information.expireNow();
                    }
                }
            }
        }
    }
}
