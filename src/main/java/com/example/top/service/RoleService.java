package com.example.top.service;

import com.example.top.entity.employee.Role;
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

    public void saveRole(Role role) {
        if (role == null) {
            log.severe("Cannot add null as a role");
            return;
        }

        repository.save(role);

        log.info("New role '" + role.getName() + "' is added");
    }

    public List<Role> findAllRoles() {
        var roles = repository.findAll();

        log.info("Successfully retrieved all roles");
        return roles;
    }

    public Role getRole(Long id) {
        var role = repository.getReferenceById(id);

        log.info("Role with the name '" + role.getName() + "' has been retrieved");
        return role;
    }

    public void updateRole(Role role) {
        repository.save(role);

        log.info("Role with the id '" + role.getRoleId() + "' has been updated");
    }

    public void deleteRole(Long id) {
        repository.deleteById(id);

        log.info("Role with the id '" + id + "' has been deleted");
    }
}
