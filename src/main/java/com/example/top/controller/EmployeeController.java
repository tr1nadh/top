package com.example.top.controller;

import com.example.top.dto.employee.EmployeeDto;
import com.example.top.dto.employee.UpdateAccountDto;
import com.example.top.entity.employee.Account;
import com.example.top.entity.employee.Employee;
import com.example.top.service.EmployeeService;
import com.example.top.service.RoleService;
import com.example.top.util.mapper.EmployeeMapper;
import com.example.top.util.mapper.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/employees")
public class EmployeeController extends AController {

    @Autowired
    private EmployeeService empService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/new-employee")
    public ModelAndView renderNewEmployee() {
        return getNewEmployeeView(new Employee());
    }

    @GetMapping("/update-employee")
    public ModelAndView renderUpdateEmployeeInfo(Long id) {
        return getUpdateEmployeeView(empService.getEmployee(id).getData());
    }

    @GetMapping("/update-emp-account")
    public ModelAndView renderUpdateEmployeeAccount(Long id) {
        var mv = new ModelAndView();
        mv.addObject("account", empService.getAccount(id).getData());
        mv.addObject("employeeId", id);
        mv.setViewName("employee/save-emp-account");

        return mv;
    }

    @PostMapping("/save-employee")
    public ModelAndView saveEmployee(@Valid @ModelAttribute("employee") EmployeeDto employee, BindingResult bindingResult,
                                     RedirectAttributes attributes) {
        if (bindingResult.hasErrors() && !isAccountNull(employee, bindingResult)) return getNewEmployeeView(employee);

        var response = empService.saveEmployee(EmployeeMapper.map(employee));

        attributes.addFlashAttribute("alertMessage", response.getMessage());
        return new ModelAndView("redirect:view");
    }

    private boolean isAccountNull(EmployeeDto employee, BindingResult bindingResult) {
        return !employee.isHasAccount() && areThereOtherErrorsThenAccountErrors(bindingResult);
    }

    private boolean areThereOtherErrorsThenAccountErrors(BindingResult bindingResult) {
        return noOfIgnoreErrors(bindingResult) == noOfErrors(bindingResult);
    }

    private int noOfIgnoreErrors(BindingResult bindingResult) {
        var no = 0;
        if (bindingResult.hasFieldErrors("account.username")) no++;
        if (bindingResult.hasFieldErrors("account.password")) no++;

        return no;
    }

    private int noOfErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors().size();
    }

    private ModelAndView getNewEmployeeView(Object employee) {
        var mv = new ModelAndView();
        mv.addObject("employee", employee);
        mv.addObject("roles", roleService.findAllRoles().getData());
//        mv.setViewName("employee/save-employee");

        return mv;
    }

    @PostMapping("/save-emp-info")
    public ModelAndView saveEmployeeInfo(@Valid @ModelAttribute("employee") EmployeeDto employee, BindingResult bindingResult,
                                         RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) return getUpdateEmployeeView(employee);

        var response = empService.saveEmployee(EmployeeMapper.mapInfo(employee));

        attributes.addFlashAttribute("alertMessage", response.getMessage());
        return new ModelAndView("redirect:view");
    }

    private ModelAndView getUpdateEmployeeView(Object employee) {
        var mv = new ModelAndView();
        mv.addObject("employee",  employee);
        mv.addObject("roles", roleService.findAllRoles().getData());
        mv.setViewName("employee/save-emp-info");

        return mv;
    }

    @PostMapping("/save-emp-account")
    public ModelAndView saveAccount(@Valid @ModelAttribute("account") UpdateAccountDto account, BindingResult bindingResult,
                                    RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            var mv = new ModelAndView();
            mv.addObject("account", account);
            mv.setViewName("employee/save-emp-account");

            return mv;
        }

        var response = empService.saveAccount(account.getEmployeeId(), Mapper.map(account, new Account()));

        attributes.addFlashAttribute("alertMessage", response.getMessage());
        return new ModelAndView("redirect:view");
    }

    @RequestMapping("/view")
    public ModelAndView getEmployees(String search_in, String search,
                                     @RequestParam(required = false) Integer page) {
        page = (page == null) ? 0 : page;
        if (search_in != null && search != null) {
            Object employees = null;
            switch (search_in) {
                case "name" -> employees = empService.findEmployeesByNameContaining(search, page).getData();
                case "phoneNo" -> employees = empService.findEmployeesByPhoneNoContaining(search, page).getData();
                case "email" -> employees = empService.findEmployeesByEmailContaining(search, page).getData();
            }

            var mv = getEmployeesView(employees, page);
            mv.addObject("search", search);
            mv.addObject("search_in", search_in);
            return mv;
        }

        return getEmployeesView(empService.findAllEmployees(page).getData(), page);
    }

    private ModelAndView getEmployeesView(Object employees, Integer page) {
        var mv = new ModelAndView();
        mv.addObject("employees", employees);
        mv.addObject("currentPage", page);
        mv.addObject("employee",  new Employee());
        mv.addObject("roles", roleService.findAllRoles().getData());
        mv.setViewName("employee/save-emp-info");
        mv.setViewName("employee/employee");
        return mv;
    }

    @GetMapping("/delete-employee")
    public RedirectView deleteEmployee(Long id, RedirectAttributes attributes) {
        var response = empService.deleteEmployee(id);

        attributes.addFlashAttribute("alertMessage", response.getMessage());
        return new RedirectView("view");
    }
}
