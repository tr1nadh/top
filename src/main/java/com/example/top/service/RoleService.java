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

        log.info("Role with the name '" + role.getName() + "' is saved");
    }

    public List<Role> findAllRoles() {
        var roles = repository
                .findByNameNotIn(List.of("Admin", "Developer"));

        log.info("Successfully retrieved all roles");
        return roles;
    }

    public Role getRole(Long id) {
        var optRole = repository.findById(id);

        if (optRole.isEmpty()) {
            log.severe("No role found with the id '" + id + "'");
            return null;
        }

        var role = optRole.get();
        log.info("Role with the name '" + role.getName() + "' has been retrieved");
        return role;
    }

    public void deleteRole(Long id) {
        var role = getRole(id);
        if (role == null) {
            log.severe("Cannot delete the role with the id '" + id + "' which doesn't exists");
            return;
        }

        repository.deleteById(id);

        log.info("Role with the name '" + role.getName() + "' has been deleted");
    }
}
