package com.example.top.controller;

import com.example.top.entity.employee.Employee;
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

    @GetMapping({"/add-employee", "/edit-employee"})
    public ModelAndView addEmployee(Long id) {
        var employee = (id == null) ? new Employee() : empService.getEmployee(id);
        var roles = roleService.findAllRoles();

        var mv = new ModelAndView();
        mv.addObject("employee", employee);
        mv.addObject("roles", roles);
        mv.setViewName("employee/add-employee");

        if (id != null) mv.setViewName("employee/edit-employee");

        return mv;
    }

    @PostMapping("/save-employee")
    public RedirectView saveEmployee(Employee employee) {
        empService.saveEmployee(employee);

        return new RedirectView("add-employee");
    }

    @RequestMapping("/employees")
    public ModelAndView getEmployees() {
        var mv = new ModelAndView();
        mv.addObject("employees", empService.findAllEmployees());
        mv.setViewName("employee/employee");

        return mv;
    }

    @PostMapping("/update-employee")
    public RedirectView updateEmployee(Employee employee) {
        empService.updateEmployee(employee);

        return new RedirectView("employees");
    }

    @GetMapping("/delete-employee")
    public RedirectView deleteEmployee(Long id) {
        empService.deleteEmployee(id);

        return new RedirectView("employees");
    }
}
