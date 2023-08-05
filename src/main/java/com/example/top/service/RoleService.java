package com.example.top.service;

import com.example.top.dto.ResponseDto;
import com.example.top.entity.employee.Role;
import com.example.top.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        return ResponseDto.builder().success(true).message(message).build();
    }

    public ResponseDto findAllRoles(int page) {
        var roles = repository
                .findByNameNotIn(excludeRoles, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "roleId")));

        var message = "Successfully retrieved roles of page '"+ page +"'";
        return ResponseDto.builder().success(true).message(message).data(roles).build();
    }

    public ResponseDto findAllRoles() {
        var roles = repository
                .findByNameNotIn(excludeRoles);

        var message = "Successfully retrieved all roles";
        return ResponseDto.builder().success(true).message(message).data(roles).build();
    }

    public ResponseDto getRole(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var optRole = repository.findById(id);
        if (optRole.isEmpty() || excludeRoles.contains(optRole.get().getName()))
            throw new IllegalStateException("No role found with the id '" + id + "'");

        var role = optRole.get();
        var message = "Role '" + role.getName() + "' has been retrieved";
        return ResponseDto.builder().success(true).message(message).data(role).build();
    }

    public ResponseDto deleteRole(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var optRole = repository.findById(id);
        if (optRole.isEmpty() || excludeRoles.contains(optRole.get().getName()))
            throw new IllegalStateException("Cannot delete role: No role exists with the id '" + id + "'");

        repository.deleteById(id);

        var message = "Role '" + optRole.get().getName() + "' has been deleted";
        return ResponseDto.builder().success(true).message(message).build();
    }
}
