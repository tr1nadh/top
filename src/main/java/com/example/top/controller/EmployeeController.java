package com.example.top.controller;

import com.example.top.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService empService;

    @RequestMapping("/employees")
    public ModelAndView getEmployees() {
        var mv = new ModelAndView();
        mv.addObject("employees", empService.findAllEmployees());
        mv.setViewName("admin/admin-employee");

        return mv;
    }

    @GetMapping("/remove-emp")
    public RedirectView removeEmployee(Long id) {
        empService.deleteEmployee(id);

        return new RedirectView("employees");
    }
}
