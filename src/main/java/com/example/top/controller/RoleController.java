package com.example.top.controller;

import com.example.top.entity.employee.Role;
import com.example.top.service.RoleService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Log
public class RoleController {

    @Autowired
    private RoleService service;

    @GetMapping({"/add-role", "/edit-role"})
    public ModelAndView renderRole(Long id) {
        var role = (id == null) ? new Role() : service.getRole(id);

        var mv = new ModelAndView();
        mv.addObject("role", role);
        mv.setViewName("employee/role/add-role");

        if (id != null) mv.setViewName("employee/role/edit-role");

        return mv;
    }

    @PostMapping({"/save-role", "/update-role"})
    public RedirectView saveRole(Role role) {
        service.saveRole(role);

        if (role.getRoleId() != null) return new RedirectView("roles");

        return new RedirectView("employee/role/add-role");
    }

    @RequestMapping("/roles")
    public ModelAndView getRoles() {
        var roles = service.findAllRoles();

        var mv = new ModelAndView();
        mv.addObject("roles", roles);
        mv.setViewName("employee/role/role");

        return mv;
    }

    @PostMapping("/update-role")
    public RedirectView updateRole(Role role) {
        service.updateRole(role);

        return new RedirectView("roles");
    }

    @GetMapping("/delete-role")
    public RedirectView deleteRole(Long id) {
        service.deleteRole(id);

        return new RedirectView("roles");
    }
}
