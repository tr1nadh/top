package com.example.top.service;

import com.example.top.entity.employee.Role;
import com.example.top.exception.DuplicateRoleNameException;
import com.example.top.exception.UnknownIdException;
import com.example.top.repository.RoleRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public void saveRole(Role role) {
        if (role == null)
            throw new IllegalArgumentException("The object 'Role' cannot be null");

        if (findAllRolesWithNames().contains(role.getName()))
            throw new DuplicateRoleNameException("Role '" + role.getName() + "' already existed");

        repository.save(role);

        log.info("Role with the name '" + role.getName() + "' is saved");
    }

    public List<String> findAllRolesWithNames() {
        var list = new ArrayList<String>();
        for (var role : findAllRoles()) list.add(role.getName());

        return list;
    }

    public List<Role> findAllRoles() {
        var roles = repository
                .findByNameNotIn(List.of("Admin", "Developer"));

        log.info("Successfully retrieved all roles");
        return roles;
    }

    public Role getRole(Long id) {
        if (id == null) throw new IllegalArgumentException("Id cannot be null");

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
        if (id == null) throw new IllegalArgumentException("Id cannot be null");

        var optRole = repository.findById(id);
        if (optRole.isEmpty())
            throw new UnknownIdException("No role found with the id '" + id + "'");

        repository.deleteById(id);

        log.info("Role with the name '" + optRole.get().getName() + "' has been deleted");
    }
}
