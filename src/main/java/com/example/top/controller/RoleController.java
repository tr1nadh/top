package com.example.top.controller;

import com.example.top.entity.Role;
import com.example.top.repository.RoleRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Log
public class RoleController {

    @Autowired
    private RoleRepository repository;

    @GetMapping("/add-role")
    public boolean addRole(Role role) {
        if (role == null) {
            log.severe("Cannot add null as a role");
            return false;
        }

        repository.save(role);

        log.info("Role with the name '" + role.getName() + "' has been added");
        return true;
    }

    @GetMapping("/roles")
    public List<Role> getRoles() {
        var roles = repository.findAll();

        log.info("Successfully retrieved all the roles");
        return roles;
    }
}
