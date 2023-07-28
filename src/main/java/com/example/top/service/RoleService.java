package com.example.top.service;

import com.example.top.dto.ResponseDto;
import com.example.top.entity.employee.Role;
import com.example.top.repository.RoleRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class RoleService {

    @Autowired
    private RoleRepository repository;
    private final List<String> excludeRoles = List.of("Admin", "Developer");

    public ResponseDto saveRole(Role role) {
        if (role == null)
            throw new IllegalArgumentException("'role' cannot be null");

        repository.save(role);

        var message = (role.getRoleId() == null) ? "New role '"+ role.getName() +"' has been saved" :
                "Role renamed successfully";
        log.info(message);

        return ResponseDto.builder().success(true).message(message).build();
    }

    public List<Role> findAllRoles(int page) {
        var roles = repository
                .findByNameNotIn(excludeRoles, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "roleId")));

        log.info("Successfully retrieved all roles");
        return roles;
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

    public ResponseDto deleteRole(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var optRole = repository.findById(id);
        if (optRole.isEmpty() || excludeRoles.contains(optRole.get().getName()))
            throw new IllegalStateException("Cannot delete role: No role exists with the id '" + id + "'");

        repository.deleteById(id);

        var message = "Role '" + optRole.get().getName() + "' has been deleted";
        log.info(message);

        return ResponseDto.builder().success(true).message(message).build();
    }
}
