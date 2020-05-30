package com.vijay.sfcp.obrs.repositories;

import com.vijay.sfcp.obrs.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
}
