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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/account")
public class AccountController extends AController {

    @Autowired
    private AccountService service;

    @RequestMapping("/settings")
    public String renderSettings() {
        return "settings";
    }

    @PostMapping("/update-emp-username")
    public RedirectView updateEmployeeUsername(@Valid @ModelAttribute("updateEmpUsername") UpdateEmpUsernameDto updateEmp,
                                               BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("alertMessage", bindingResult.getFieldError().getDefaultMessage());
            return new RedirectView("settings");
        }

        service.changeUsername(updateEmp.getNewUsername());

        attributes.addFlashAttribute("alertMessage", "Username successfully changed");
        return new RedirectView("settings");
    }

    @PostMapping("/update-emp-password")
    public ModelAndView updateEmployeePassword(@Valid @ModelAttribute("updateEmpPassword") UpdateEmpPasswordDto updateEmp,
                                               BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("alertMessage", bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView("redirect:settings");
        }

        service.changePassword(updateEmp.getOldPassword(), updateEmp.getNewPassword());

        return getAlertView("Password changed successfully, please login again.", "/login");
    }
}
