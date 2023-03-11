package com.example.top.service;

import com.example.top.entity.Role;
import com.example.top.repository.RoleRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public boolean addRole(Role role) {
        if (role == null) {
            log.severe("Cannot add null as a role");
            return false;
        }

        repository.save(role);

        log.info("New role '" + role.getName() + "' is added");
        return true;
    }

    public List<Role> findAllRoles() {
        var roles = repository.findAll();

        log.info("Successfully retrieved all roles");
        return roles;
    }

    public void updateRoleName(Role role, String name) {
        repository.updateNameByName(role.getName(), name);

        log.info("Successfully updated the role '" + role.getName() + "' to '" + name + "'");
    }

    public void deleteRole(Role role) {
        var result = repository.deleteRoleByName(role.getName());

        if (result == 0) log.info("Role with the name '" + role.getName() + "' cannot be deleted");
        log.info("Successfully deleted the role '" + role.getName() + "'") ;
    }
}
