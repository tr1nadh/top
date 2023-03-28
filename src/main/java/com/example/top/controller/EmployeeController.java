package com.example.top.controller;

import com.example.top.dto.EmployeeDto;
import com.example.top.entity.employee.Employee;
import com.example.top.service.EmployeeService;
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
public class EmployeeController {

    @Autowired
    private EmployeeService empService;
    @Autowired
    private RoleService roleService;

    @GetMapping({"/add-employee", "/edit-employee"})
    public ModelAndView renderEmployee(Long id) {
        var employee = (id == null) ? new Employee() : empService.getEmployee(id);

        var mv = new ModelAndView();
        mv.addObject("employee", employee);
        mv.addObject("roles", roleService.findAllRoles());
        mv.setViewName("employee/save-employee");

        return mv;
    }

    @PostMapping({"/save-employee", "/update-employee"})
    public ModelAndView saveEmployee(@Valid @ModelAttribute("employee") EmployeeDto employee, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employee);
            model.addAttribute("roles", roleService.findAllRoles());
            return new ModelAndView("employee/save-employee", model);
        }

        empService.saveEmployee(Mapper.map(employee, new Employee()));

        return new ModelAndView("redirect:/employees");
    }

    @RequestMapping("/employees")
    public ModelAndView getEmployees() {
        var mv = new ModelAndView();
        mv.addObject("employees", empService.findAllEmployees());
        mv.setViewName("employee/employee");

        return mv;
    }

    @GetMapping("/delete-employee")
    public RedirectView deleteEmployee(Long id) {
        empService.deleteEmployee(id);

        return new RedirectView("employees");
    }
}
