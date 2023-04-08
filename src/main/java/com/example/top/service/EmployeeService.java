package com.example.top.service;

import com.example.top.entity.employee.Employee;
import com.example.top.repository.EmployeeRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void saveEmployee(Employee employee) {
        if (employee == null) {
            log.severe("Cannot add null as an employee");
            return;
        }

        employee.getAccount().setEmployee(employee);
        repository.save(checkPasswordChange(employee));

        var name = employee.getFirstname() + " " + employee.getLastname();
        log.info("Employee with the name '" + name + "' has been saved" );
    }

    public Employee checkPasswordChange(Employee employee) {
        var account = employee.getAccount();
        if (account.isPasswordChanged()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setPasswordChanged(false);
        }

        return employee;
    }

    public List<Employee> findAllEmployees() {
        var employees = repository
                .findByRoleNameNotIn(List.of("Admin", "Developer"));

        log.info("Successfully retrieved all employees");
        return employees;
    }

    public Employee getEmployee(Long id) {
        var optEmployee = repository.findById(id);

        if (optEmployee.isEmpty()) {
             log.severe("No employee found with the id '" + id + "'");
             return null;
        }

        var employee = optEmployee.get();
        var name = employee.getFirstname() + " " + employee.getLastname();
        log.info("Employee with name '" + name + "' has been retrieved");
        return employee;
    }

    public void deleteEmployee(Long id) {
        var employee = getEmployee(id);
        if (employee == null) {
            log.severe("Cannot delete the employee with the id '" + id + "' which doesn't exists");
            return;
        }

        repository.deleteById(id);

        var name = employee.getFirstname() + " " + employee.getLastname();
        log.info("Employee with name '" + name + "' has been deleted");
    }
}
