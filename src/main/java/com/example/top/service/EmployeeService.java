package com.example.top.service;

import com.example.top.entity.Employee;
import com.example.top.entity.Role;
import com.example.top.repository.EmployeeRepository;
import com.example.top.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ToString
@AllArgsConstructor
@Log
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

    public void removeEmployee(Employee employee) {
        employeeRepository.removeEmployeeByFirstname(employee.getFirstname());

        var name = employee.getFirstname() + " " + employee.getLastname();
        log.info("Employee with name '" + name + "' has been deleted");
    }

    public boolean addRole(Role role) {
        if (role == null) {
            log.severe("Cannot add null as a role");
            return false;
        }

        roleRepository.save(role);
        log.info("New role '" + role.getName() + "' is added");
        return true;
    }

    public List<Role> findAllRoles() {
        var roles = roleRepository.findAll();

        log.info("Successfully retrieved all roles");
        return roles;
    }

    public void updateRole(Role role, String name) {
        roleRepository.updateNameByName(role.getName(), name);

        log.info("Successfully updated the role '" + role.getName() + "' to '" + name + "'");
    }

    public void removeRole(Role role) {
        roleRepository.removeRoleByName(role.getName());

        log.info("Successfully deleted the role '" + role.getName() + "'") ;
    }
}
