package com.vijay.sfcp.obrs.common.utils;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 11 June 2020
*/

import com.vijay.sfcp.obrs.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SessionUtil {
    private SessionRegistry sessionRegistry;

    @Autowired
    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    public List<SessionInformation> getActiveSessions()
    {
        List<SessionInformation> activeSessions = new ArrayList<>();
        for ( Object principal : sessionRegistry.getAllPrincipals() )
        {
            activeSessions.addAll( sessionRegistry.getAllSessions( principal, false ) );
        }
        return activeSessions;
    }

    public User getUser(SessionInformation session )
    {
        Object principalObj = session.getPrincipal();
        if ( principalObj instanceof User )
        {
            User user = (User) principalObj;
            return user;
        }
        return null;
    }
}
