package com.example.top.controller;

import com.example.top.dto.EmployeeDto;
import com.example.top.entity.employee.Employee;
import com.example.top.service.EmployeeService;
import com.example.top.service.RoleService;
import com.example.top.util.mapper.EmployeeMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class EmployeeController extends ControllerHelper {

    @Autowired
    private EmployeeService empService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/add-employee")
    public ModelAndView renderAddEmployee() {
        return getAddView(new Employee());
    }

    @GetMapping("/update-employee")
    public ModelAndView renderUpdateEmployeeInfo(Long id) {
        return getUpdateView(empService.getEmployee(id));
    }

    @PostMapping("/save-employee")
    public ModelAndView saveEmployee(@Valid @ModelAttribute("employee") EmployeeDto employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors() && !isAccountNull(employee, bindingResult)) return getAddView(employee);

        empService.saveEmployee(EmployeeMapper.map(employee));

        return new ModelAndView("redirect:/employees");
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

    private ModelAndView getAddView(Object employee) {
        var mv = new ModelAndView();
        mv.addObject("employee", employee);
        mv.addObject("roles", roleService.findAllRoles());
        mv.setViewName("employee/save-employee");

        return mv;
    }

    @PostMapping("/save-emp-info")
    public ModelAndView saveEmployeeInfo(@Valid @ModelAttribute("employee") EmployeeDto employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return getUpdateView(employee);

        empService.saveEmployee(EmployeeMapper.mapInfo(employee));

        return new ModelAndView("redirect:/employees");
    }

    private ModelAndView getUpdateView(Object employee) {
        var mv = new ModelAndView();
        mv.addObject("employee",  employee);
        mv.addObject("roles", roleService.findAllRoles());
        mv.setViewName("employee/save-emp-info");

        return mv;
    }

    @RequestMapping("/employees")
    public ModelAndView getEmployees() {
        var mv = new ModelAndView();
        mv.addObject("employees", empService.findAllEmployees());
        mv.setViewName("employee/employee");

        return mv;
    }

    @GetMapping("/delete-employee")
    public RedirectView deleteEmployee(Long id) {
        empService.deleteEmployee(id);

        return new RedirectView("employees");
    }
}
