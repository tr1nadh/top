package com.example.top.service;

import com.example.top.entity.Employee;
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

    public boolean addEmployee(Employee employee) {
        if (employee == null) {
            log.severe("Cannot add null as an employee");
            return false;
        }

        repository.save(employee);

        var name = employee.getFirstname() + " " + employee.getLastname();
        log.info("Employee with name '" + name + "' has been added" );
        return true;
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

    public boolean updateEmployee(Employee employee) {
        var result = repository.updateEmployeeById(
                employee.getFirstname(), employee.getLastname(), employee.getRole(),
                employee.getPhoneNo(), employee.getEmailAddress(), employee.getGender(),
                employee.getEmployeeId());

        var name = employee.getFirstname() + " " + employee.getLastname();
        if (result == 0) {
            log.info("Employee with the name '" + name + "' has been successfully updated");
            return true;
        }

        log.info("Employee with the name '" + name + "' has not updated");
        return false;
    }

    public void deleteEmployee(Long id) {
        var employee = repository.getReferenceById(id);
        repository.deleteById(id);

        var name = employee.getFirstname() + " " + employee.getLastname();
        log.info("Employee with name '" + name + "' has been deleted");
    }


}
