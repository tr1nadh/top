package com.example.top.service;

import com.example.top.entity.employee.Role;
import com.example.top.exception.DuplicateRoleException;
import com.example.top.exception.UnknownIdException;
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
    private final List<String> excludeList = List.of("Admin", "Developer");

    public void saveRole(Role role) {
        if (role == null)
            throw new IllegalArgumentException("The object 'Role' cannot be null");

        if (isRoleAlreadyExists(role))
            throw new DuplicateRoleException("Role '" + role.getName() + "' already existed");

        repository.save(role);

        log.info("Role '" + role.getName() + "' is saved");
    }

    private boolean isRoleAlreadyExists(Role role) {
        return findAllRolesWithNames().contains(role.getName());
    }

    public List<String> findAllRolesWithNames() {
        return repository.findAll().stream().map(Role::getName).toList();
    }

    public List<Role> findAllRoles() {
        var roles = repository
                .findByNameNotIn(excludeList);

        log.info("Successfully retrieved all roles");
        return roles;
    }

    public Role getRole(Long id) {
        if (id == null) throw new IllegalArgumentException("Id cannot be null");

        var optRole = repository.findById(id);

        if (optRole.isEmpty() || excludeList.contains(optRole.get().getName())) {
            log.severe("No role found with the id '" + id + "'");
            return new Role();
        }

        var role = optRole.get();
        log.info("Role '" + role.getName() + "' has been retrieved");
        return role;
    }

    public void deleteRole(Long id) {
        if (id == null) throw new IllegalArgumentException("Id cannot be null");

        var optRole = repository.findById(id);
        if (optRole.isEmpty() || excludeList.contains(optRole.get().getName()))
            throw new UnknownIdException("No role found with the id '" + id + "'");

        repository.deleteById(id);

        log.info("Role '" + optRole.get().getName() + "' has been deleted");
    }
}
