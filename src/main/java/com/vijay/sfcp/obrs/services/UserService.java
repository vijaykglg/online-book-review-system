package com.vijay.sfcp.obrs.services;

import com.vijay.sfcp.obrs.domain.User;

public interface UserService extends CRUDService<User> {
    User findByUsername(String username);
}
