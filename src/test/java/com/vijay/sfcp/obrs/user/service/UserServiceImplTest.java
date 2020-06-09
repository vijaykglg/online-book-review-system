package com.vijay.sfcp.obrs.user.service;

import com.vijay.sfcp.obrs.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    void findUsersByRole() {
        List<User> usersByRole = (List<User>) userService.findUsersByRoles("ROLE_USER");
        System.out.println("usersByRole.toString() = " + usersByRole.toString());
        assertEquals(1,1);
    }
}