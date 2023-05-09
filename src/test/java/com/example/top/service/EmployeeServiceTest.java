package com.example.top.service;

import com.example.top.entity.employee.Employee;
import com.example.top.repository.EmployeeRepository;
import com.example.top.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void generateEmployees() {
        var role = roleRepository.findById(29L).get();
        for (var i = 2; i <= 20; i++) {
            var emp = new Employee();
            emp.setFullName("emp" + i);
            emp.setRole(role);
            emp.setPhoneNo("989898989" + i);
            emp.setEmailAddress("emp" + i + "@email.com");
            emp.setGender("Male");
            repository.save(emp);
        }

    }
}