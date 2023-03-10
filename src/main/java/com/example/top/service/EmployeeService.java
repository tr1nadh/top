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

    public void deleteEmployee(Long id) {
        var employee = employeeRepository.getReferenceById(id);
        employeeRepository.deleteById(id);

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

    public void updateRoleName(Role role, String name) {
        roleRepository.updateNameByName(role.getName(), name);

        log.info("Successfully updated the role '" + role.getName() + "' to '" + name + "'");
    }

    public void deleteRole(Role role) {
        var result = roleRepository.removeRoleByName(role.getName());

        if (result == 0) log.info("Role with the name '" + role.getName() + "' cannot be deleted");
        log.info("Successfully deleted the role '" + role.getName() + "'") ;
    }
}
