package com.example.top.controller;

import com.example.top.dto.RoleDto;
import com.example.top.entity.employee.Role;
import com.example.top.service.RoleService;
import com.example.top.util.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RoleController {

    @Autowired
    private RoleService service;

    @GetMapping({"/add-role", "/edit-role"})
    public ModelAndView renderRole(Long id) {
        var role = (id == null) ? new Role() : service.getRole(id);

        var mv = new ModelAndView();
        mv.addObject("role", role);
        mv.setViewName("employee/role/save-role");

        return mv;
    }

    @PostMapping({"/save-role", "/update-role"})
    public ModelAndView saveRole(@Valid @ModelAttribute("role") RoleDto role,
                                 BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("role", role);
            return new ModelAndView("employee/role/save-role");
        }

        service.saveRole(Mapper.map(role, new Role()));

        return new ModelAndView("redirect:/roles");
    }

    @RequestMapping("/roles")
    public ModelAndView getRoles() {
        var mv = new ModelAndView();
        mv.addObject("roles", service.findAllRoles());
        mv.setViewName("employee/role/role");

        return mv;
    }

    @GetMapping("/delete-role")
    public RedirectView deleteRole(Long id) {
        service.deleteRole(id);

        return new RedirectView("roles");
    }
}
