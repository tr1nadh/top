package com.example.top.controller;

import com.example.top.dto.employee.RoleDto;
import com.example.top.entity.employee.Role;
import com.example.top.service.RoleService;
import com.example.top.util.mapper.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoleController extends ControllerHelper {

    @Autowired
    private RoleService service;

    @GetMapping({"/add-role", "/update-role"})
    public ModelAndView renderRole(Long id) {
        return getRenderView((id == null) ? new Role() : service.getRole(id));
    }

    @PostMapping("/save-role")
    public ModelAndView saveRole(@Valid @ModelAttribute("role") RoleDto role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return getRenderView(role);

        var toMapping = (role.getRoleId() == null) ? "/add-role" : "/update-role?id=" + role.getRoleId();

        service.saveRole(Mapper.map(role, new Role()));

        return getAlertView("Role saved", toMapping);
    }

    private ModelAndView getRenderView(Object role) {
        var mv = new ModelAndView();
        mv.addObject("role", role);
        mv.setViewName("employee/role/save-role");

        return mv;
    }

    @RequestMapping("/roles")
    public ModelAndView getRoles() {
        var mv = new ModelAndView();
        mv.addObject("roles", service.findAllRoles());
        mv.setViewName("employee/role/role");

        return mv;
    }

    @GetMapping("/delete-role")
    public ModelAndView deleteRole(Long id) {
        service.deleteRole(id);

        return new ModelAndView("redirect:/roles");
    }
}
