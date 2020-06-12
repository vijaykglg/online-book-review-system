package com.vijay.sfcp.obrs.user.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/
import com.vijay.sfcp.obrs.error.exceptions.AlreadyExistsException;
import com.vijay.sfcp.obrs.error.exceptions.NotFoundException;
import com.vijay.sfcp.obrs.role.entity.Role;
import com.vijay.sfcp.obrs.role.repository.RoleRepository;
import com.vijay.sfcp.obrs.user.entity.User;
import com.vijay.sfcp.obrs.user.repository.UserRepository;
import com.vijay.sfcp.obrs.user.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Profile("springdatajpa")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private EncryptionService encryptionService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public List<?> findAll() {
        List<User> users = new ArrayList<>();
        this.userRepository.findAll().forEach(users::add); //fun with Java 8
        return users;
    }

    /*@Override
    public List<?> findAllWithRole() {
        List<UserRoleDTO> users = new ArrayList<>();
        this.userRepository.findAllWithRole().forEach(users::add); //fun with Java 8
        return users;
    }*/

    @Override
    public User findById(Integer id) {
        Optional<User> user = this.userRepository.findById(id);
        if(user.isPresent())
            return user.get();
        else
            System.err.println("User Not Found");

        throw new NotFoundException("User Not Found");
    }

    @Override
    public User saveOrUpdate(User domainObject) {
        if(domainObject.getPassword() != null){
            domainObject.setEncryptedPassword(encryptionService.encryptString(domainObject.getPassword()));
        }
        return this.userRepository.save(domainObject);
    }
    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (this.userRepository.existsById(id)) {
            System.out.println("User exists for this user id");
            this.userRepository.deleteById(id);
        } else {
            System.err.println("User Not Found ");
            throw new NotFoundException("User Not Found For This id -  " + id);
        }
    }

    @Override
    public User registerNewUser(final User user, String role) {
        if (existsByEmail(user.getEmail())) {
            throw new AlreadyExistsException("There is an account with this email address: " + user.getEmail());
        }
        if (existsByUsername(user.getUserName())) {
            throw new AlreadyExistsException("There is an account with this username: " + user.getUserName());
        }
        if(user.getPassword() != null){
            user.setEncryptedPassword(encryptionService.encryptString(user.getPassword()));
        }
        Role byRole = this.roleRepository.findByRole(role);
        user.setRoles(new HashSet<Role>(Arrays.asList(byRole)));

        return this.userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUserName(username);
    }

    @Override
    public boolean existsById(Integer id) {
        return this.userRepository.existsById(id);
    }

    private boolean existsByEmail(final String email) {
        return this.userRepository.findByEmail(email) != null;
    }

    private boolean existsByUsername(final String userName) {
        return this.userRepository.findByUserName(userName) != null;
    }

    @Override
    public List<?> findUsersByRoles(String role){
        List<User> users = new ArrayList<>();
//        Role publisherRole = roleRepository.findByRole("ROLE_PUBLISHER");
        Role byRole = this.roleRepository.findByRole(role);
        this.userRepository.findUsersByRolesIn(new HashSet<Role>(Arrays.asList(byRole))).forEach(users::add); //fun with Java 8
        return users;
    }
}
