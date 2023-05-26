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
    private final List<String> excludeRoles = List.of("Admin", "Developer");

    public void saveRole(Role role) {
        if (role == null)
            throw new IllegalArgumentException("'role' cannot be null");

        repository.save(role);

        log.info("Role '" + role.getName() + "' is saved");
    }

    public List<Role> findAllRoles() {
        var roles = repository
                .findByNameNotIn(excludeRoles);

        log.info("Successfully retrieved all roles");
        return roles;
    }

    public Role getRole(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var optRole = repository.findById(id);
        if (optRole.isEmpty() || excludeRoles.contains(optRole.get().getName()))
            throw new IllegalStateException("No role found with the id '" + id + "'");

        var role = optRole.get();
        log.info("Role '" + role.getName() + "' has been retrieved");
        return role;
    }

    public Role deleteRole(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var optRole = repository.findById(id);
        if (optRole.isEmpty() || excludeRoles.contains(optRole.get().getName()))
            throw new IllegalStateException("Cannot delete role: No role exists with the id '" + id + "'");

        repository.deleteById(id);

        log.info("Role '" + optRole.get().getName() + "' has been deleted");
        return optRole.get();
    }
}
