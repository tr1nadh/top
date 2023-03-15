package com.example.top.service;

import com.example.top.entity.employee.Employee;
import com.example.top.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ToString
@AllArgsConstructor
@Log
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public void saveEmployee(Employee employee) {
        if (employee == null) {
            log.severe("Cannot add null as an employee");
            return;
        }

        repository.save(employee);

        var name = employee.getFirstname() + " " + employee.getLastname();
        log.info("Employee with name '" + name + "' has been added" );
    }

    public List<Employee> findAllEmployees() {
        var employees = repository.findAll();

        log.info("Successfully retrieved all employees");
        return employees;
    }

    public Employee getEmployee(Long id) {
        var employee = repository.getReferenceById(id);

        var name = employee.getFirstname() + " " + employee.getLastname();
        log.info("Employee with name '" + name + "' has been retrieved");
        return employee;
    }

    public void updateEmployee(Employee employee) {
        repository.save(employee);

        var name = employee.getFirstname() + " " + employee.getLastname();
        log.info("Employee with the name '" + name + "' has been updated");
    }

    public void deleteEmployee(Long id) {
        var employee = repository.getReferenceById(id);
        repository.deleteById(id);

        var name = employee.getFirstname() + " " + employee.getLastname();
        log.info("Employee with name '" + name + "' has been deleted");
    }


}
