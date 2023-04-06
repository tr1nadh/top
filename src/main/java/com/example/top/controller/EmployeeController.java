package com.example.top.controller;

import com.example.top.dto.EmployeeDto;
import com.example.top.dto.UpdateEmpPasswordDto;
import com.example.top.dto.UpdateEmpUsernameDto;
import com.example.top.entity.employee.Employee;
import com.example.top.securitydetails.EmployeeDetails;
import com.example.top.service.EmployeeService;
import com.example.top.service.RoleService;
import com.example.top.util.mapper.EmployeeMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Objects;

@Controller
public class EmployeeController extends ControllerHelper {

    @Autowired
    private EmployeeService empService;
    @Autowired
    private RoleService roleService;

    @GetMapping({"/add-employee", "/edit-employee"})
    public ModelAndView renderEmployee(Long id) {
        return getRenderView((id == null) ? new Employee() : empService.getEmployee(id));
    }

    @PostMapping("/save-employee")
    public ModelAndView saveEmployee(@Valid @ModelAttribute("employee") EmployeeDto employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors() && !isValidPasswordUpdate(employee, bindingResult)) return getRenderView(employee);

        empService.saveEmployee(EmployeeMapper.map(employee));

        return new ModelAndView("redirect:/employees");
    }

    private boolean isValidPasswordUpdate(EmployeeDto employee, BindingResult bindingResult) {
        return employee.getEmployeeId() != null &&
                bindingResult.getAllErrors().size() == 1 &&
                Objects.requireNonNull(bindingResult.findEditor("password", String.class)).getValue() == null;
    }

    private ModelAndView getRenderView(Object employee) {
        var mv = new ModelAndView();
        mv.addObject("employee", employee);
        mv.addObject("roles", roleService.findAllRoles());
        mv.setViewName("employee/save-employee");
        return mv;
    }

    @RequestMapping("/employees")
    public ModelAndView getEmployees() {
        var mv = new ModelAndView();
        mv.addObject("employees", empService.findAllEmployees());
        mv.setViewName("employee/employee");

        return mv;
    }

    @PostMapping("/update-emp-username")
    public ModelAndView updateEmployeeUsername(@Valid @ModelAttribute("updateEmp")
                                               UpdateEmpUsernameDto updateEmp, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            empService.updateUsername(updateEmp.getOldUsername(), updateEmp.getNewUsername());
            var auth = SecurityContextHolder.getContext().getAuthentication();
            var emp = (EmployeeDetails) auth.getPrincipal();
            emp.setUsername(updateEmp.getNewUsername());
        }

        return getAlertView("Username changed", updateEmp.getFromMapping());
    }

    @PostMapping("/update-emp-password")
    public ModelAndView updateEmployeePassword(@Valid @ModelAttribute("updateEmp")
                                               UpdateEmpPasswordDto updateEmp, BindingResult bindingResult) {
        if (!bindingResult.hasErrors())
            empService.updatePassword(updateEmp.getUsername(),
                    updateEmp.getOldPassword(), updateEmp.getNewPassword());

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
        }

        return getAlertView("Password changed", updateEmp.getFromMapping());
    }

    @GetMapping("/delete-employee")
    public RedirectView deleteEmployee(Long id) {
        empService.deleteEmployee(id);

        return new RedirectView("employees");
    }
}
