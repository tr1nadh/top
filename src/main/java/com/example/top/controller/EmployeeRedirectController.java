package com.example.top.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeRedirectController {

    @GetMapping("/add-employee")
    public String addEmployee() {
        return "admin-add-employee";
    }
}
