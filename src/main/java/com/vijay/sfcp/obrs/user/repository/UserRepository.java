package com.vijay.sfcp.obrs.user.repository;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/

import com.vijay.sfcp.obrs.role.entity.Role;
import com.vijay.sfcp.obrs.user.dto.UserRoleDTO;
import com.vijay.sfcp.obrs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String userName);

    User findByEmail(String email);

    List<User> findUsersByRolesIn(HashSet<Role> roles);

    @Override
    void delete(User user);

    /*@Transactional
    @Modifying
    @Query(value = "update user u set u.firstName = :firstname, u.lastName = :lastName,u.email = :email, u.active = :active where u.id = :id", nativeQuery = true)
    void updateUser(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email, @Param("active") String active, @Param("id") Integer id);
*/
    /*@Transactional
    @Query(value = "SELECT U.USER_NAME as userName,U.FIRST_NAME as firstName,U.LAST_NAME as lastName,U.ACTIVE as active,R.ROLE as role FROM USERS U,ROLE R,USERS_ROLES UR WHERE U.ID=UR.USER_ID AND R.ID=UR.ROLES_ID", nativeQuery = true)
    List<UserRoleDTO> findAllWithRole();*/

/*    @Transactional
    @Query(value = "")
    List<UserRoleDTO> findAllWithRole();*/
}
