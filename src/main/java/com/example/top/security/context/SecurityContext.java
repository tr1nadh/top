package com.example.top.security.context;

import com.example.top.entity.employee.Employee;
import com.example.top.repository.AccountRepository;
import com.example.top.security.userdetails.EmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContext {

    @Autowired
    private static AccountRepository accountRepository;

    public static Employee getCurrentLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeDetails employeeDetails = (EmployeeDetails) authentication.getPrincipal();
        return accountRepository.findAccountByUsername(employeeDetails.getUsername()).getEmployee();
    }
}
