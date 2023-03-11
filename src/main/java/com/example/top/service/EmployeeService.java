package com.example.top.service;

import com.example.top.entity.Employee;
import com.example.top.repository.EmployeeRepository;
import com.example.top.repository.RoleRepository;
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
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleRepository roleRepository;

    public boolean addEmployee(Employee employee) {
        if (employee == null) {
            log.severe("Cannot add null as an employee");
            return false;
        }

        employeeRepository.save(employee);

        var name = employee.getFirstname() + " " + employee.getLastname();
        log.info("Employee with name '" + name + "' has been added" );
        return true;
    }

    public List<Employee> findAllEmployees() {
        var employees = employeeRepository.findAll();

        log.info("Successfully retrieved all employees");
        return employees;
    }

    public Employee getEmployee(Long id) {
        var employee = employeeRepository.getReferenceById(id);

        var name = employee.getFirstname() + " " + employee.getLastname();
        log.info("Employee with name '" + name + "' has been retrieved");
        return employee;
    }

    public boolean updateEmployee(Employee employee) {
        var result = employeeRepository.updateEmployeeById(
                employee.getFirstname(), employee.getLastname(), employee.getRole(),
                employee.getPhoneNo(), employee.getEmailAddress(), employee.getGender(),
                employee.getId());

        var name = employee.getFirstname() + " " + employee.getLastname();
        if (result == 0) {
            log.info("Employee with the name '" + name + "' has been successfully updated");
            return true;
        }

        log.info("Employee with the name '" + name + "' has not updated");
        return false;
    }

    public void deleteEmployee(Long id) {
        var employee = employeeRepository.getReferenceById(id);
        employeeRepository.deleteById(id);

        var name = employee.getFirstname() + " " + employee.getLastname();
        log.info("Employee with name '" + name + "' has been deleted");
    }


}
