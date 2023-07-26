package com.example.top.controller;

import com.example.top.dto.employee.RoleDto;
import com.example.top.entity.employee.Role;
import com.example.top.service.RoleService;
import com.example.top.util.mapper.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("employees/roles")
public class RoleController extends AController {

    @Autowired
    private RoleService service;

    @GetMapping("/add-role")
    public String renderRole() {
        return "employee/role/role";
    }

    @PostMapping("/save-role")
    public RedirectView saveRole(@Valid @ModelAttribute("role") RoleDto role, BindingResult bindingResult,
                                 RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("alertMessage", bindingResult.getFieldError("name").getDefaultMessage());
            return new RedirectView("view");
        }

        service.saveRole(Mapper.map(role, new Role()));

        var message = "";
        if (role.getRoleId() == null) message = "New role "+ role.getName() +" has been saved";
        else message = "Role has been successfully renamed";
        attributes.addFlashAttribute("alertMessage", message);
        return new RedirectView("view");
    }

    @RequestMapping("/view")
    public ModelAndView getRoles(@RequestParam(defaultValue = "0") int page) {
        var mv = new ModelAndView();
        mv.addObject("roles", service.findAllRoles(page));
        mv.addObject("currentPage", page);
        mv.setViewName("employee/role/role");

        return mv;
    }

    @GetMapping("/delete-role")
    public RedirectView deleteRole(Long id, RedirectAttributes attributes) {
        var role = service.deleteRole(id);

        var message = "Role '" + role.getName() + "' has been deleted";
        attributes.addFlashAttribute("alertMessage", message);
        return new RedirectView("view");
    }
}
