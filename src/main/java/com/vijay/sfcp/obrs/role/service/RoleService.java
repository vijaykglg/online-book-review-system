package com.vijay.sfcp.obrs.role.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/
import com.vijay.sfcp.obrs.common.service.CRUDService;
import com.vijay.sfcp.obrs.role.entity.Role;

public interface RoleService extends CRUDService<Role> {
    Role findByRole(String role);
}
