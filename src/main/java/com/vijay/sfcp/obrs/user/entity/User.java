package com.vijay.sfcp.obrs.user.entity;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.common.entity.AbstractEntityClass;
import com.vijay.sfcp.obrs.role.entity.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User extends AbstractEntityClass implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "*Please provide your first name")
    @Size(min = 1, max = 32, message = "First name must be between 1 and 32 characters")
    private String firstName;

    @NotEmpty(message = "*Please provide your last name")
    @Size(min = 1, max = 32, message = "Last name must be between 1 and 32 characters")
    private String lastName;

    @NotEmpty(message = "*Please provide your user name")
    @Size(min = 1, max = 32, message = "User name must be between 5 and 32 characters")
    private String userName;

    private String description;

    @Transient
    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
    @JsonIgnore
    private String password;

    @JsonIgnore
    private String encryptedPassword;

    @NotEmpty(message = "*Please provide an email")
    @Email(message = "*Please provide a valid Email")
    private String email;

    private Boolean active = true;

    private Integer failedLoginAttempts = 0;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "publishers", fetch = FetchType.LAZY)//mappedBy = "publishers" refers to the 'publishers' property in Book Class
    private Set<Book> books = new HashSet<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", description='" + description + '\'' +
                ", password='" + password + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                ", failedLoginAttempts=" + failedLoginAttempts +
              /*  ", roles=" + roles +
                ", books=" + books +*/
                '}';
    }
}
