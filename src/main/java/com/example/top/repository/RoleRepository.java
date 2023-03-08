package com.example.top.repository;

import com.example.top.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Modifying
    @Transactional
    @Query(value = "update Role r set r.name = :newName where r.name = :oldName")
    int updateNameByName(String oldName, String newName);
}
