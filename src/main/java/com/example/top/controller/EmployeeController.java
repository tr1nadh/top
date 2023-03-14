package com.example.top.controller;

import com.example.top.entity.Employee;
import com.example.top.service.EmployeeService;
import com.example.top.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/add-employee")
    public ModelAndView showAddEmployee() {
        var roles = roleService.findAllRoles();

        var mv = new ModelAndView();
        mv.addObject("roles", roles);
        mv.setViewName("admin-add-employee");

        return mv;
    }

    @PostMapping("/add-emp")
    public RedirectView addEmployee(Employee employee) {
        empService.saveEmployee(employee);

        return new RedirectView("employees");
    }

    @RequestMapping("/employees")
    public ModelAndView getEmployees() {
        var mv = new ModelAndView();
        mv.addObject("employees", empService.findAllEmployees());
        mv.setViewName("admin-employee");

        return mv;
    }

    @GetMapping("/edit-employee")
    public ModelAndView editEmployee(Long id) {
        var employee = empService.getEmployee(id);

        var mv = new ModelAndView();
        mv.addObject("employee", employee);
        mv.setViewName("admin-update-employee");

        return mv;
    }

    @PostMapping("/update-emp")
    public RedirectView updateEmployee(Employee employee) {
        empService.updateEmployee(employee);

        return new RedirectView("employees");
    }

    @GetMapping("/delete-emp")
    public RedirectView deleteEmployee(Long id) {
        empService.deleteEmployee(id);

        return new RedirectView("employees");
    }
}
