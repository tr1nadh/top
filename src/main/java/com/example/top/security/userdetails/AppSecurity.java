package com.example.top.security.userdetails;

import com.example.top.entity.employee.Account;
import com.example.top.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AppSecurity {

    @Autowired
    private AccountRepository repository;

    public EmployeeAccountDetails getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (EmployeeAccountDetails) authentication.getPrincipal();
    }

    public Account getCurrentLoggedInUserDetails() {
        return repository.findAccountByUsername(getCurrentLoggedInUser().getUsername());
    }
}
