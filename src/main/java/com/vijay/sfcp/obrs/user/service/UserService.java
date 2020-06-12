package com.vijay.sfcp.obrs.user.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/
import com.vijay.sfcp.obrs.common.service.CRUDService;
import com.vijay.sfcp.obrs.user.entity.User;

import java.util.List;

public interface UserService extends CRUDService<User> {
//    List<?> findAllWithRole();

    User registerNewUser(User user, String role);

    User findByUsername(String username);

    boolean existsById(Integer id);

    List<?> findUsersByRoles(String role);
}
