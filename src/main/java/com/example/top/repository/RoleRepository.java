package com.example.top.repository;

import com.example.top.entity.employee.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Modifying
    @Query(value = "update Role r set r.name = :newName where r.name = :oldName")
    int updateNameByName(String oldName, String newName);

    @Modifying
    int deleteRoleByName(String name);
}
