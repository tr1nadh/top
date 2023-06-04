package com.example.top.service;

import com.example.top.entity.employee.Account;
import com.example.top.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ServiceHelper {

    @Autowired
    private AccountRepository repository;

    public Account getCurrentLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var employeeAccountDetails = (com.example.top.security.userdetails.EmployeeAccountDetails) authentication.getPrincipal();
        return repository.findAccountByUsername(employeeAccountDetails.getUsername());
    }
}
