package com.example.top.controller;

import com.example.top.service.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    @Autowired
    private AuthenticateService authService;

    @PostMapping("/auth-admin")
    public ModelAndView authAdmin(String username, String password) {
        if (authService.auth(username, password)) return new ModelAndView("redirect:/employees");

        var mv = new ModelAndView();
        mv.addObject("msg", "Username or password is wrong");
        mv.addObject("fallbackUrl", "admin-login");
        mv.setViewName("error");

        return mv;
    }
}
