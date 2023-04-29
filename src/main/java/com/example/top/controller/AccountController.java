package com.example.top.controller;

import com.example.top.dto.employee.UpdateEmpPasswordDto;
import com.example.top.dto.employee.UpdateEmpUsernameDto;
import com.example.top.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController extends AController {

    @Autowired
    private AccountService service;

    @PostMapping("/update-emp-username")
    public ModelAndView updateEmployeeUsername(@Valid @ModelAttribute("updateEmp")
                                               UpdateEmpUsernameDto updateEmp, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) service.updateUsername(updateEmp.getOldUsername(), updateEmp.getNewUsername());

        return getAlertView("Username changed", updateEmp.getFromMapping());
    }

    @PostMapping("/update-emp-password")
    public ModelAndView updateEmployeePassword(@Valid @ModelAttribute("updateEmp")
                                               UpdateEmpPasswordDto updateEmp, BindingResult bindingResult) {
        if (!bindingResult.hasErrors())
            service.updatePassword(updateEmp.getUsername(),
                    updateEmp.getOldPassword(), updateEmp.getNewPassword());

        return getAlertView("Password changed", updateEmp.getFromMapping());
    }
}
