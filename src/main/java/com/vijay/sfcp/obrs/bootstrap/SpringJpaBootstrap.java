package com.vijay.sfcp.obrs.bootstrap;

import com.vijay.sfcp.obrs.domain.Role;
import com.vijay.sfcp.obrs.domain.User;
import com.vijay.sfcp.obrs.services.RoleService;
import com.vijay.sfcp.obrs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringJpaBootstrap implements CommandLineRunner {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        loadUsers();
        loadRoles();
        assignUsersToUserRole();
        assignUsersToAdminRole();
        assignUsersToPublisherRole();
    }

    private void loadUsers() {
        User user1 = new User();
        user1.setUsername("user");
        user1.setPassword("user");
        userService.saveOrUpdate(user1);
        System.out.println("Saved User1 " + user1.getUsername());

        User user2 = new User();
        user2.setUsername("admin");
        user2.setPassword("admin");
        userService.saveOrUpdate(user2);
        System.out.println("Saved User2 " + user2.getUsername());

        User user3 = new User();
        user3.setUsername("publisher");
        user3.setPassword("publisher");
        userService.saveOrUpdate(user3);
        System.out.println("Saved user3 " + user3.getUsername());
    }

    private void loadRoles() {
        Role role = new Role();
        role.setRole("USER");
        roleService.saveOrUpdate(role);
        System.out.println("Saved role " + role.getRole());

        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        roleService.saveOrUpdate(adminRole);
        System.out.println("Saved role " + adminRole.getRole());

        Role publisherRole = new Role();
        publisherRole.setRole("PUBLISHER");
        roleService.saveOrUpdate(publisherRole);
        System.out.println("Saved role " + publisherRole.getRole());
    }

    private void assignUsersToUserRole() {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();
        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("USER")) {
                users.forEach(user -> {
                    if (user.getUsername().equals("user")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }

    private void assignUsersToAdminRole() {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();
        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ADMIN")) {
                users.forEach(user -> {
                    if (user.getUsername().equals("admin")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }

    private void assignUsersToPublisherRole() {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();
        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("PUBLISHER")) {
                users.forEach(user -> {
                    if (user.getUsername().equals("publisher")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }


}
