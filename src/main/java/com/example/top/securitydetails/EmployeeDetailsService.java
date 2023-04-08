package com.example.top.securitydetails;

import com.example.top.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EmployeeDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var employee = repository.findEmployeeByAccountUsername(username);

        if (employee == null) throw new UsernameNotFoundException("Could not find user");

        return new EmployeeDetails(employee);
    }
}
