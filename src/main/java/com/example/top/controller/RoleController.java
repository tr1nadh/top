package com.example.top.controller;

import com.example.top.entity.Role;
import com.example.top.service.RoleService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Log
public class RoleController {

    @Autowired
    private RoleService service;

    @GetMapping("/add-role")
    public String showAddRole() {
        return "admin-add-role";
    }

    @PostMapping("/add-role")
    public RedirectView addRole(Role role) {
        service.addRole(role);

        return new RedirectView("add-employee");
    }
}
