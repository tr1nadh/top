package com.example.top.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/admin-login")
    public String loginAdmin() {
        return "admin/admin-login";
    }

    @RequestMapping("/employee-login")
    public String loginEmployee() {
        return "employee/employee-login";
    }
}
