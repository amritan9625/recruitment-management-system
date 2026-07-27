package com.amritan.backend.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amritan.backend.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Page<Role> findAll(Pageable pageable);

	Page<Role> findByRoleNameContainingIgnoreCase(String keyword, Pageable pageable);
}
