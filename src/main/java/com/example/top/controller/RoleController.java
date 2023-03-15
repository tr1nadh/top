package com.example.top.controller;

import com.example.top.entity.Role;
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
    public String addRole() {
        return "employee/role/add-role";
    }

    @PostMapping("/save-role")
    public RedirectView showRole(Role role) {
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
}
