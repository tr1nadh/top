package com.example.top.repository;

import com.example.top.entity.employee.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByNameNotIn(List<String> roles);
    List<Role> findByName(String name);
}
