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

    @GetMapping("/add-role")
    public ModelAndView addRole() {
        var mv = new ModelAndView();
        mv.addObject("role", new Role());
        mv.setViewName("employee/role/add-role");

        return mv;
    }

    @PostMapping("/save-role")
    public RedirectView saveRole(Role role) {
        service.saveRole(role);

        return new RedirectView("roles");
    }

    @RequestMapping("/roles")
    public ModelAndView getRoles() {
        var roles = service.findAllRoles();

        var mv = new ModelAndView();
        mv.addObject("roles", roles);
        mv.setViewName("employee/role/role");

        return mv;
    }

    @GetMapping("/edit-role")
    public ModelAndView editRole(Long id) {
        var role = service.getRole(id);

        var mv = new ModelAndView();
        mv.addObject("role", role);
        mv.setViewName("employee/role/update-role");

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
